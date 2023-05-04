package com.fis.crm.service.impl;

import com.fis.crm.commons.CheckCharacterUtil;
import com.fis.crm.commons.GenCodeUtils;
import com.fis.crm.config.EmailServerConfig;
import com.fis.crm.config.SMSConfig;
import com.fis.crm.domain.*;
import com.fis.crm.repository.*;
import com.fis.crm.service.SendEmailAndSMSService;
import com.fis.crm.service.dto.GetSMSTokenRequestDTO;
import com.fis.crm.service.dto.SMSRequest;
import com.fis.crm.service.dto.SMSResponse;
import com.fis.crm.service.dto.SMSTokenResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SendEmailAndSMSServiceImpl implements SendEmailAndSMSService {

    private Logger log = LoggerFactory.getLogger(SendEmailAndSMSServiceImpl.class);

    private static Map<Integer, String> statusSendSMS = new HashMap<>();

    private final CampaignEmailBatchRepository campaignEmailBatchRepository;
    private final CampaignEmailResourceRepository campaignEmailResourceRepository;
    private final CampaignSmsBatchRepository campaignSmsBatchRepository;
    private final CampaignSMSResourceRepository campaignSMSResourceRepository;
    private final EmailServerConfig emailServerConfig;
    private final SMSConfig smsConfig;
    private final Environment env;
    private final SendSMSTokenRepository sendSMSTokenRepository;

    public SendEmailAndSMSServiceImpl(CampaignEmailBatchRepository campaignEmailBatchRepository,
                                      CampaignEmailResourceRepository campaignEmailResourceRepository,
                                      CampaignSmsBatchRepository campaignSmsBatchRepository,
                                      CampaignSMSResourceRepository campaignSMSResourceRepository,
                                      EmailServerConfig emailServerConfig,
                                      SMSConfig smsConfig,
                                      Environment env,
                                      SendSMSTokenRepository sendSMSTokenRepository
    ) throws Exception {
        this.campaignEmailBatchRepository = campaignEmailBatchRepository;
        this.campaignEmailResourceRepository = campaignEmailResourceRepository;
        this.campaignSmsBatchRepository = campaignSmsBatchRepository;
        this.campaignSMSResourceRepository = campaignSMSResourceRepository;
        this.emailServerConfig = emailServerConfig;
        this.smsConfig = smsConfig;
        this.env = env;
        this.sendSMSTokenRepository = sendSMSTokenRepository;
        this.statusList();
//        this.mailSender = emailServerConfig.getJavaMailSender();
//        this.message = this.mailSender.createMimeMessage();
    }

    private void statusList() {
        statusSendSMS.put(100, "Success");
        statusSendSMS.put(104, "Brandname is not exist");
        statusSendSMS.put(118, "SMS type is invalid");
        statusSendSMS.put(119, "Advertisement brand name must send at least 20 phone numbers");
        statusSendSMS.put(131, "Content length up to 422 characters");
        statusSendSMS.put(132, "No permission to send text messages with fixed number 8755");
        statusSendSMS.put(99, "An unknown error");
        statusSendSMS.put(177, "The network have not registry");
        statusSendSMS.put(159, "RequestId has length over 120 character");
        statusSendSMS.put(145, "Social media template is wrong");
        statusSendSMS.put(146, "Brandname CSKH template is wrong");
        statusSendSMS.put(101, "ApiKey or SecretKey is wrong");
        statusSendSMS.put(103, "Account not enough money");
    }

    @Override
    public void sendSMS() {
        log.debug("------->Starting send SMS.....");
        List<CampaignSmsBatch> smsList = campaignSmsBatchRepository.findByStatus("5");
        log.debug("------->So luong SMS can gui: " + smsList.size());
        if (smsList.size() > 0) {
            log.debug("------->Gui SMS: ");
            smsList.sort(Comparator.comparing(CampaignSmsBatch::getId));
            List<CampaignSmsBatch> smsBatch = smsList.stream().limit(50).collect(Collectors.toList());
            log.debug("------->So luong SMS batch can gui: " + smsList.size());
            for (CampaignSmsBatch x : smsBatch) {
                ResponseEntity<SMSResponse> response;
                try {
                    x.setSendDate(Instant.now());
                    log.info("Sending SMS....");
                    log.debug("------->Sending SMS.......: ");
                    response = processSendSMS(x.getPhoneNumber(), x.getContent(), 0);
                    log.debug("------->Result: ...... ");
                    log.debug(response.getBody().toString());
                    x.setStatus("2");
                    x.setResponseBatch("Success");
                    //update status table resource
                    if (x.getResourceId() != null) {
                        updateStatusSmsResource(x.getResourceId(), x.getSendDate(), "2");
                    }

//                    update resource table - chua co cot resourceId
                } catch (Exception e) {
                    log.debug("------->Result: Failed..... ");
                    log.error(e.getMessage());
                    x.setStatus("3");
                    x.setResponseBatch(e.getMessage());
                    //update status table resource
                    if (x.getResourceId() != null) {
                        updateStatusSmsResource(x.getResourceId(), x.getSendDate(), "3");
                    }
                } finally {
                    log.info("SMS " + x.getPhoneNumber() + ": " + x.getStatus());
                }
            }
        } else {
            log.info("All SMS was sent!");
        }
    }

    public void updateStatusSmsResource(Long resourceId, Instant sendDate, String status) {
        Optional<CampaignSMSResource> resourceOptional = campaignSMSResourceRepository.findById(resourceId);
        if (resourceOptional.isPresent()) {
            resourceOptional.get().setSendStatus(status);
            resourceOptional.get().setSendDate(sendDate);
        }
    }

    @Override
    public void sendMail() throws Exception {
        List<CampaignEmailBatch> emailList = campaignEmailBatchRepository.findByStatus("7");

        if (emailList.size() > 0) {
            emailList.sort(Comparator.comparing(CampaignEmailBatch::getCreateDate));
            List<CampaignEmailBatch> emailBatch = emailList.stream().limit(50).collect(Collectors.toList());
            boolean multipart = true;
            JavaMailSender mailSender = emailServerConfig.getJavaMailSender();
            List<MimeMessage> mimeMessages = new ArrayList<>(emailList.size());

            for (CampaignEmailBatch x : emailBatch) {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
                helper.setTo(x.getEmail());
                if (x.getCcEmail() != null && !x.getCcEmail().isEmpty() && CheckCharacterUtil.checkEmail(x.getCcEmail())) {
                    helper.setCc(x.getCcEmail());
                }
                if (x.getBccEmail() != null && !x.getBccEmail().isEmpty() && CheckCharacterUtil.checkEmail(x.getBccEmail())) {
                    helper.setBcc(x.getBccEmail());
                }
                if (x.getTitle() == null) {
                    helper.setSubject("");
                } else {
                    helper.setSubject(x.getTitle());
                }
                if (x.getContent() == null) {
                    helper.setText("");
                } else {
                    message.setContent(x.getContent(), "text/html; charset=UTF-8");
                }
                x.setSendDate(new Date().toInstant());
                mimeMessages.add(message);
            }
            try {
                mailSender.send(mimeMessages.toArray(new MimeMessage[0]));
                for (CampaignEmailBatch x : emailBatch) {
                    x.setStatus("2");
                    x.setResponse("Successful!");
                    if (x.getCampaignEmailResourceId() != null)
                        updateStatusOfEmailResource(x.getCampaignEmailResourceId(), x.getSendDate(), "2");
                }

            } catch (Exception e) {
                log.error(e.getMessage());
                e.printStackTrace();
                for (CampaignEmailBatch x : emailBatch) {
                    x.setStatus("3");
                    x.setResponse(e.getMessage());
                    if (x.getCampaignEmailResourceId() != null)
                        updateStatusOfEmailResource(x.getCampaignEmailResourceId(), x.getSendDate(), "3");
                }
            }

        } else {
            log.info("All emails was sent!");
        }
    }

    public String updateStatusOfEmailResource(Long campaignEmailResourceId,
                                              Instant sendDate,
                                              String sendStatus) {
        log.info("SendStatus: " + sendStatus);
        try {
            CampaignEmailResource emailResource = campaignEmailResourceRepository.getOne(campaignEmailResourceId);
            emailResource.setSendDate(sendDate);
            emailResource.setSendStatus(sendStatus);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("Email Resource id " + campaignEmailResourceId + " is not existed!");
        }
        return "ok";
    }

    @Transactional
    public ResponseEntity<SMSResponse> processSendSMS(String phone, String content, Integer count) {
        RestTemplate restTemplate = new RestTemplate();
        String token = getToken(count == 0 ? false : true);
        SMSRequest smsRequest = new SMSRequest();
        smsRequest.setAccessToken(token);
        smsRequest.setSessionId(GenCodeUtils.genSessionId());
        smsRequest.setBrandName(env.getProperty("sms-config.brand-name"));
        smsRequest.setPhone(phone);
        smsRequest.setMessage(Base64.getEncoder().encodeToString(content.getBytes()));
        HttpEntity<SMSRequest> request = new HttpEntity<>(smsRequest);
        String url = env.getProperty("sms-config.send-sms-url");
        log.info("----------url-send-sms-------------");
        log.info("---->" + url);
        ResponseEntity<SMSResponse> response = null;
        try {
            response = restTemplate
                .exchange(url, HttpMethod.POST, request, SMSResponse.class);
            HttpStatus s = response.getStatusCode();
        } catch (Exception e) {
            if (count == 0) {
                count++;
                this.processSendSMS(phone, content, count);
            } else {
                throw e;
            }
        }
        return response;
    }

    @Transactional
    public String getToken(Boolean createNew) {
        List<SendSMSToken> tokens = sendSMSTokenRepository.findAll();
        String token = "";
        if (tokens.size() == 0 || createNew == true) {
            if (tokens.size() > 0) {
                SendSMSToken smsToken = tokens.get(0);
                if (smsToken.getType().toString() == env.getProperty("sms-config.type")) {
                    if (smsToken.getCreatedDate().plus(1, ChronoUnit.DAYS).compareTo(Instant.now()) > 0) {
                        return smsToken.getToken();
                    }
                }
            }
            RestTemplate restTemplate = new RestTemplate();
            GetSMSTokenRequestDTO dto = new GetSMSTokenRequestDTO();
            dto.setClientId(env.getProperty("sms-config.client-id"));
            dto.setClientSecret(env.getProperty("sms-config.client-secret"));
            dto.setScope(env.getProperty("sms-config.scope"));
            dto.setSessionId(GenCodeUtils.genSessionId());
            dto.setGrantType("client_credentials");
            String url = env.getProperty("sms-config.get-token-url");
            HttpEntity<GetSMSTokenRequestDTO> request = new HttpEntity<>(dto);
            ResponseEntity<SMSTokenResponseDTO> response = restTemplate
                .exchange(url, HttpMethod.POST, request, SMSTokenResponseDTO.class);
            token = response.getBody().getAccessToken();
            SendSMSToken entity = new SendSMSToken();
            entity.setId(1l);
            entity.setToken(token);
            entity.setCreatedDate(Instant.now());
            entity.setType(Integer.valueOf(env.getProperty("sms-config.type")));
            sendSMSTokenRepository.save(entity);
        } else {
            token = tokens.get(0).getToken();
        }
        return token;
    }

}
