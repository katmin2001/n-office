package com.fis.crm.service.impl;

import com.fis.crm.commons.ExcelUtils;
import com.fis.crm.domain.ContractorsSuspend;
import com.fis.crm.domain.ContractorsSuspend_;
import com.fis.crm.repository.ContractorsSuspendRepository;
import com.fis.crm.service.ContractorsSuspendService;
import com.fis.crm.service.dto.ContractorsSuspendDTO;
import com.fis.crm.service.mapper.ContractorsSuspendMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContractorsSuspendServiceImpl implements ContractorsSuspendService {

    private final ContractorsSuspendRepository contractorsSuspendRepository;

    private final ContractorsSuspendMapper contractorsSuspendMapper;

    private final EntityManager entityManager;

    private final ExcelUtils excelUtils;

    private final Logger log = LoggerFactory.getLogger(ContractorsSuspendServiceImpl.class);

    public ContractorsSuspendServiceImpl(ContractorsSuspendRepository contractorsSuspendRepository, ContractorsSuspendMapper contractorsSuspendMapper, EntityManager entityManager, ExcelUtils excelUtils) {
        this.contractorsSuspendRepository = contractorsSuspendRepository;
        this.contractorsSuspendMapper = contractorsSuspendMapper;
        this.entityManager = entityManager;
        this.excelUtils = excelUtils;
    }

    @Override
    public Page<ContractorsSuspendDTO> search(ContractorsSuspendDTO contractorsSuspendDTO,
                                              Pageable pageable) throws ParseException {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            CriteriaQuery<ContractorsSuspend> criteriaQuery = cb.createQuery(ContractorsSuspend.class);
            Root<ContractorsSuspend> root = criteriaQuery.from(ContractorsSuspend.class);
            List<Predicate> predicates = new ArrayList<>();
            if (contractorsSuspendDTO.getCid() != null) {
                predicates.add(cb.like(cb.lower(root.get(ContractorsSuspend_.CID)), "%" + contractorsSuspendDTO.getCid().toLowerCase() + "%"));
            }
            if (contractorsSuspendDTO.getTaxCode() != null) {
                predicates.add(cb.like(cb.lower(root.get(ContractorsSuspend_.TAX_CODE)), "%" + contractorsSuspendDTO.getTaxCode().toLowerCase() + "%"));
            }
            if (contractorsSuspendDTO.getFeeType() != null) {
                predicates.add(cb.like(cb.lower(root.get(ContractorsSuspend_.FEE_TYPE)), "%" + contractorsSuspendDTO.getFeeType().toLowerCase() + "%"));
            }
            if (contractorsSuspendDTO.getName() != null) {
                predicates.add(cb.like(cb.lower(root.get(ContractorsSuspend_.NAME)), "%" + contractorsSuspendDTO.getName().toLowerCase() + "%"));
            }
//            if (contractorsSuspendDTO.getSuspendTimeFrom() != null) {
//                predicates.add(cb.greaterThanOrEqualTo(root.get(ContractorsSuspend_.SUSPEND_TIME), formatter.parse(contractorsSuspendDTO.getSuspendTimeFrom())));
//            }
//            if (contractorsSuspendDTO.getSuspendTimeTo() != null) {
//                Date date = formatter.parse(contractorsSuspendDTO.getSuspendTimeTo());
//                Calendar c = Calendar.getInstance();
//                c.setTime(date);
//                c.add(Calendar.DATE, 1);
//                date = c.getTime();
//                predicates.add(cb.lessThanOrEqualTo(root.get(ContractorsSuspend_.SUSPEND_TIME), date));
//            }
            criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));
//            criteriaQuery.orderBy(cb.asc(root.get(ContractorsSuspend_.SUSPEND_TIME)));

            List<ContractorsSuspend> rs = entityManager.createQuery(criteriaQuery)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            Root<ContractorsSuspend> rootCount = countQuery.from(ContractorsSuspend.class);
            countQuery.select(cb.count(rootCount)).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            Long count = entityManager.createQuery(countQuery).getSingleResult();
            List<ContractorsSuspendDTO> rsDTOs = rs.stream().map(contractorsSuspendMapper::toDto).collect(Collectors.toList());
            return new PageImpl<>(rsDTOs, pageable, count);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
