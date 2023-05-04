package com.fis.crm.service.impl;

import com.fis.crm.commons.ExcelUtils;
import com.fis.crm.domain.CompanySuspend;
import com.fis.crm.domain.CompanySuspend_;
import com.fis.crm.service.CompanySuspendService;
import com.fis.crm.service.dto.CompanySuspendDTO;
import com.fis.crm.service.mapper.CompanySuspendMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
public class CompanySuspendServiceImpl implements CompanySuspendService {

    private final CompanySuspendMapper companySuspendMapper;

    private final EntityManager entityManager;

    private final ExcelUtils excelUtils;

    private final Logger log = LoggerFactory.getLogger(CompanySuspendServiceImpl.class);


    public CompanySuspendServiceImpl(CompanySuspendMapper companySuspendMapper, EntityManager entityManager, ExcelUtils excelUtils) {
        this.companySuspendMapper = companySuspendMapper;
        this.entityManager = entityManager;
        this.excelUtils = excelUtils;
    }

    @Override
    public Page<CompanySuspendDTO> search(CompanySuspendDTO companySuspendDTO, Pageable pageable) throws ParseException {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            CriteriaQuery<CompanySuspend> criteriaQuery = cb.createQuery(CompanySuspend.class);
            Root<CompanySuspend> root = criteriaQuery.from(CompanySuspend.class);
            List<Predicate> predicates = new ArrayList<>();
            if (companySuspendDTO.getCid() != null) {
                predicates.add(cb.like(cb.lower(root.get(CompanySuspend_.CID)), "%" + companySuspendDTO.getCid().toLowerCase() + "%"));
            }
            if (companySuspendDTO.getTaxCode() != null) {
                predicates.add(cb.like(cb.lower(root.get(CompanySuspend_.TAX_CODE)), "%" + companySuspendDTO.getTaxCode().toLowerCase() + "%"));
            }
            if (companySuspendDTO.getName() != null) {
                predicates.add(cb.like(cb.lower(root.get(CompanySuspend_.NAME)), "%" + companySuspendDTO.getName().toLowerCase() + "%"));
            }
            if (companySuspendDTO.getSuspendTimeFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get(CompanySuspend_.SUSPEND_TIME), formatter.parse(companySuspendDTO.getSuspendTimeFrom())));
            }
            if (companySuspendDTO.getSuspendTimeTo() != null) {
                Date date = formatter.parse(companySuspendDTO.getSuspendTimeTo());
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DATE, 1);
                date = c.getTime();
                predicates.add(cb.lessThanOrEqualTo(root.get(CompanySuspend_.SUSPEND_TIME), date));
            }
            criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));
            criteriaQuery.orderBy(cb.asc(root.get(CompanySuspend_.SUSPEND_TIME)));

            List<CompanySuspend> rs = entityManager.createQuery(criteriaQuery)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            Root<CompanySuspend> rootCount = countQuery.from(CompanySuspend.class);
            countQuery.select(cb.count(rootCount)).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            Long count = entityManager.createQuery(countQuery).getSingleResult();
            List<CompanySuspendDTO> rsDTOs = rs.stream().map(companySuspendMapper::toDto).collect(Collectors.toList());
            return new PageImpl<>(rsDTOs, pageable, count);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
