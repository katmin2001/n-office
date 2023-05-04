package com.fis.crm.web.rest;

import com.fis.crm.config.Constants;
import com.fis.crm.domain.CampaignResourceDetail;
import com.fis.crm.domain.User;
import com.fis.crm.service.ActionLogService;
import com.fis.crm.service.CampaignResourceDetailService;
import com.fis.crm.service.CampaignService;
import com.fis.crm.service.UserService;
import com.fis.crm.service.dto.*;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import com.fis.crm.web.rest.response.ResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link CampaignResourceDetail}.
 */
@RestController
@RequestMapping("/api")
public class CampaignResourceDetailResource {

    private final Logger log = LoggerFactory.getLogger(CampaignResourceDetailResource.class);

    private static final String ENTITY_NAME = "campaignResourceDetail";

    private final ResponseFactory responseFactory;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CampaignResourceDetailService campaignResourceDetailService;

    private final UserService userService;
    private final ActionLogService actionLogService;
    private final CampaignService campaignService;

    public CampaignResourceDetailResource(ResponseFactory responseFactory, CampaignResourceDetailService campaignResourceDetailService, UserService userService, ActionLogService actionLogService, CampaignService campaignService) {
        this.responseFactory = responseFactory;
        this.campaignResourceDetailService = campaignResourceDetailService;
        this.userService = userService;
        this.actionLogService = actionLogService;
        this.campaignService = campaignService;
    }

    /**
     * {@code POST  /campaign-resource-detail} : Create a new campaignResourceDetail.
     *
     * @param campaignResourceDetailDTO the campaignResourceDetailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new campaignResourceDetailDTO, or with status {@code 400 (Bad Request)} if the campaignResourceDetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/campaign-resource-detail")
    public ResponseEntity<CampaignResourceDetailDTO> createCampaignResourceDetail(@RequestBody CampaignResourceDetailDTO campaignResourceDetailDTO) throws URISyntaxException {
        log.debug("REST request to save CampaignResourceDetail : {}", campaignResourceDetailDTO);
        if (campaignResourceDetailDTO.getId() != null) {
            throw new BadRequestAlertException("A new campaignResourceDetail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CampaignResourceDetailDTO result = campaignResourceDetailService.save(campaignResourceDetailDTO);
        return ResponseEntity.created(new URI("/api/campaign-resource-detail/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /campaign-resource-detail} : Updates an existing campaignResourceDetail.
     *
     * @param campaignResourceDetailDTO the campaignResourceDetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated campaignResourceDetailDTO,
     * or with status {@code 400 (Bad Request)} if the campaignResourceDetailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the campaignResourceDetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/campaign-resource-detail")
    public ResponseEntity<CampaignResourceDetailDTO> updateCampaignResourceDetail(@RequestBody CampaignResourceDetailDTO campaignResourceDetailDTO) throws URISyntaxException {
        log.debug("REST request to update CampaignResourceDetail : {}", campaignResourceDetailDTO);
        if (campaignResourceDetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CampaignResourceDetailDTO result = campaignResourceDetailService.save(campaignResourceDetailDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, campaignResourceDetailDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /campaign-resource-detail} : get all the campaignResourceDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of campaignResourceDetails in body.
     */
    @GetMapping("/campaign-resource-detail")
    public ResponseEntity<List<CampaignResourceDetailDTO>> getAllCampaignResourceDetails(Pageable pageable) {
        log.debug("REST request to get a page of CampaignResourceDetails");
        Page<CampaignResourceDetailDTO> page = campaignResourceDetailService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /campaign-resource-detail/:id} : get the "id" campaignResourceDetail.
     *
     * @param id the id of the campaignResourceDetailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the campaignResourceDetailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/campaign-resource-detail/{id}")
    public ResponseEntity<CampaignResourceDetailDTO> getCampaignResourceDetail(@PathVariable Long id) {
        log.debug("REST request to get CampaignResourceDetail : {}", id);
        Optional<CampaignResourceDetailDTO> campaignResourceDetailDTO = campaignResourceDetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(campaignResourceDetailDTO);
    }

    /**
     * {@code DELETE  /campaign-resource-detail/:id} : delete the "id" campaignResourceDetail.
     *
     * @param id the id of the campaignResourceDetailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/campaign-resource-detail/{id}")
    public ResponseEntity<Void> deleteCampaignResourceDetail(@PathVariable Long id) {
        log.debug("REST request to delete CampaignResourceDetail : {}", id);
        campaignResourceDetailService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * Lay so luong du lieu tu nguon chua chia nhom
     *
     * @param id
     * @return
     */
    @GetMapping("/campaign-resource-detail/getNumberCampaignGroup/{id}")
    public ResponseEntity<Long> getCampaignResourceDetailsNotGroup(@PathVariable Long id) {
        Optional<Long> numberCampaignResourceDetailsNotGroup = campaignResourceDetailService.getNumberCampaignResourceDetailsNotGroup(id);
        return ResponseEntity.ok().body(numberCampaignResourceDetailsNotGroup.get());
    }

    /**
     * Chia du lieu tu nguon cho nhom
     *
     * @param generalCampaign
     * @return
     */
    @PostMapping("/campaign-resource-detail/group")
    public ResponseEntity<Boolean> groupCampaignResourceDetails(@RequestBody GeneralCampaignDTO generalCampaign) {
        boolean result = false;
        try {
            result = campaignResourceDetailService.groupCampaignResourceDetails(generalCampaign);
            Optional<CampaignDTO> campaignDTO = campaignService.findOne(generalCampaign.getCampaignId());
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                null, String.format("Nguồn dữ liệu chiến dịch: Phân công chiến dịch [%s]", campaignDTO.get().getName()),
                new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campaign_resource, "CONFIG_MENU_ITEM"));

        } catch (Exception e) {
            throw new BadRequestAlertException("Error", ENTITY_NAME, e.getMessage());
        }
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/campaign-resource-detail/group-not-done")
    public ResponseEntity<Boolean> groupNotDoneCampaignResourceDetails(@RequestBody GeneralCampaignDTO generalCampaign) {
        boolean result = false;
        try {
            result = campaignResourceDetailService.groupNotDoneCampaignResourceDetails(generalCampaign);
            Optional<CampaignDTO> campaignDTO = campaignService.findOne(generalCampaign.getCampaignId());
            Optional<User> userLog = userService.getUserWithAuthorities();
            actionLogService.saveActionLog(new ActionLogDTO(userLog.get().getId(), Constants.ACTION_LOG_TYPE.UPDATE + "",
                null, String.format("Nguồn dữ liệu chiến dịch: Phân công dữ liệu chưa hoàn thành [%s]", campaignDTO.get().getName()),
                new Date(), Constants.MENU_ID.OMS, Constants.MENU_ITEM_ID.campaign_resource, "CONFIG_MENU_ITEM"));

        } catch (Exception e) {
            throw new BadRequestAlertException("Error", ENTITY_NAME, e.getMessage());
        }
        return ResponseEntity.ok().body(result);
    }

    /**
     * Danh sach user va du lieu nguon
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/campaign-resource-detail/getCampaignGroup")
    public ResponseEntity<List<CampaignResourceGeneralDTO>> getCampaignGroupDetails(@RequestParam(name = "campaignId", required = false) Long campaignId) throws Exception {
        List<CampaignResourceGeneralDTO> campaignResourceGeneralDTOS = campaignResourceDetailService.getCampaignGroupDetails(campaignId);
        return ResponseEntity.ok().body(campaignResourceGeneralDTOS);
    }

    /**
     * Lay so luong du lieu chua chia user cua chien dich va nhom
     * Lay danh sach user cua nhom
     *
     * @param campaignId
     * @param groupId
     * @return
     * @throws Exception
     */
    @PostMapping("/campaign-resource-detail/getDataCampaignGroup")
    public ResponseEntity<GeneralCampaignDTO> getCampaignGroupDetailsNotUser(@RequestParam(value = "campaignId", required = true) Long campaignId,
                                                                             @RequestParam(value = "groupId", required = true) Long groupId) throws Exception {
        Optional<Long> result = campaignResourceDetailService.getCampaignGroupDetailsNotUser(campaignId, groupId);
        Optional<List<UserDTO>> userByGroupId = userService.getUserByGroupId(groupId);
        GeneralCampaignDTO generalCampaignDTO = new GeneralCampaignDTO();
        generalCampaignDTO.setNoCampaignGroupYetAssign(result.orElse(0l));
        generalCampaignDTO.setLstUserDto(userByGroupId.orElse(null));
        return ResponseEntity.ok().body(generalCampaignDTO);
    }

    /**
     * Lay danh sach user va du lieu tong hop theo chien dich va nhom
     *
     * @param campaignId
     * @param groupId
     * @return
     * @throws Exception
     */
    @PostMapping("/campaign-resource-detail/getCampaignUser")
    public ResponseEntity<List<CampaignResourceGeneralDTO>> getCampaignUserDetails(@RequestBody CampaignResourceDetailDTO campaignResourceGeneralDTO) throws Exception {
        List<CampaignResourceGeneralDTO> result = campaignResourceDetailService.getCampaignUserDetails(campaignResourceGeneralDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Chia du lieu nguon tu nhom cho user
     *
     * @param generalCampaign
     * @return
     * @throws Exception
     */
    @PostMapping("/campaign-resource-detail/assignCampaignUser")
    public ResponseEntity<Boolean> assignCampaignUser(@RequestBody GeneralCampaignDTO generalCampaign) throws Exception {
        boolean result = campaignResourceDetailService.assignCampaignUser(generalCampaign);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Lay so luong chua goi theo user tu nhom va nguon
     *
     * @param campaignResourceId
     * @param groupId
     * @param assignUserId
     * @return
     * @throws Exception
     */
    @PostMapping("/campaign-resource-detail/getNoCampaignGroupYetCallForUser")
    public ResponseEntity<Long> getNoCampaignGroupYetCallForUser(@RequestParam(value = "campaignResourceId", required = true) Long campaignResourceId,
                                                                 @RequestParam(value = "groupId", required = true) Long groupId,
                                                                 @RequestParam(value = "assignUserId", required = true) Long assignUserId) throws Exception {
        Optional<Long> result = campaignResourceDetailService.getNoCampaignGroupYetCallForUser(campaignResourceId, groupId, assignUserId);
        return ResponseEntity.ok().body(result.orElse(0L));
    }

    /**
     * Tra du lieu chua goi tu user ve nhom
     *
     * @param generalCampaignDTO
     * @return
     * @throws Exception
     */
    @PostMapping("/campaign-resource-detail/restoreCampaignGroup")
    public ResponseEntity<Boolean> restoreCampaignGroup(@RequestBody GeneralCampaignDTO generalCampaignDTO) throws Exception {
        boolean result = campaignResourceDetailService.restoreCampaignGroup(generalCampaignDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Tra du lieu chua hoan thanh goi ra tu user ve nhom
     *
     * @param generalCampaignDTO
     * @return
     * @throws Exception
     */
    @PostMapping("/campaign-resource-detail/restoreNotDoneCampaignGroup")
    public ResponseEntity<Boolean> restoreNotDoneCampaignGroup(@RequestBody GeneralCampaignDTO generalCampaignDTO) throws Exception {
        boolean result = campaignResourceDetailService.restoreNotDoneCampaignGroup(generalCampaignDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Lay so luong du lieu chua goi cua nhom tu nguon
     *
     * @param groupId
     * @return
     * @throws Exception
     */
    @PostMapping("/campaign-resource-detail/getNoCampaignYetCallForGroup")
    public ResponseEntity<Long> getNoCampaignYetCallForUser(@RequestParam(value = "groupId", required = true) Long groupId) throws Exception {
        Optional<Long> result = campaignResourceDetailService.getNoCampaignYetCallForUser(groupId);
        return ResponseEntity.ok().body(result.orElse(0L));
    }

    /**
     * Tra du lieu chua goi tu nhom ve nguon
     *
     * @param generalCampaignDTO
     * @return
     * @throws Exception
     */
    @PostMapping("/campaign-resource-detail/restoreCampaignResource")
    public ResponseEntity<Boolean> restoreCampaignResource(@RequestBody GeneralCampaignDTO generalCampaignDTO) throws Exception {
        boolean result = campaignResourceDetailService.restoreCampaignResource(generalCampaignDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Tra du lieu chua hoan thanh goi ra tu nhom ve nguon
     *
     * @param generalCampaignDTO
     * @return
     * @throws Exception
     */
    @PostMapping("/campaign-resource-detail/restoreNotDoneCampaignResource")
    public ResponseEntity<Boolean> restoreNotDoneCampaignResource(@RequestBody GeneralCampaignDTO generalCampaignDTO) throws Exception {
        boolean result = campaignResourceDetailService.restoreNotDoneCampaignResource(generalCampaignDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/campaign-resource-detail/get-from-cid/{cid}")
    public ResponseEntity<CampaignResourceDetailDTO> getCampaignResourceFromCID(@PathVariable(value = "cid") String cid) throws Exception {
        return ResponseEntity.ok().body(campaignResourceDetailService.getCampaignResourceFromCID(cid));
    }
}
