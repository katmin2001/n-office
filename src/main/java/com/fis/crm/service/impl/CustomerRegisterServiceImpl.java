package com.fis.crm.service.impl;

import com.fis.crm.domain.CustomerRegister;
import com.fis.crm.domain.CustomerRegister_;
import com.fis.crm.service.CustomerRegisterService;
import com.fis.crm.service.dto.CustomerRegisterDTO;
import com.fis.crm.service.mapper.CustomerRegisterMapper;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerRegisterServiceImpl implements CustomerRegisterService {

    private final Logger log = LoggerFactory.getLogger(CustomerRegisterServiceImpl.class);

    private final EntityManager entityManager;

    private final CustomerRegisterMapper customerRegisterMapper;

    public CustomerRegisterServiceImpl(EntityManager entityManager, CustomerRegisterMapper customerRegisterMapper) {
        this.entityManager = entityManager;
        this.customerRegisterMapper = customerRegisterMapper;
    }

    @Override
    public Page<CustomerRegisterDTO> search(CustomerRegisterDTO customerRegisterDTO, Pageable pageable) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            CriteriaQuery<CustomerRegister> criteriaQuery = cb.createQuery(CustomerRegister.class);
            Root<CustomerRegister> root = criteriaQuery.from(CustomerRegister.class);
            List<Predicate> predicates = new ArrayList<>();
            if (customerRegisterDTO.getCid() != null) {
                predicates.add(cb.like(cb.lower(root.get(CustomerRegister_.CID)), "%" + customerRegisterDTO.getCid().toLowerCase() + "%"));
            }
            if (customerRegisterDTO.getTaxCode() != null) {
                predicates.add(cb.like(cb.lower(root.get(CustomerRegister_.TAX_CODE)), "%" + customerRegisterDTO.getTaxCode().toLowerCase() + "%"));
            }
            if (customerRegisterDTO.getName() != null) {
                predicates.add(cb.like(cb.lower(root.get(CustomerRegister_.NAME)), "%" + customerRegisterDTO.getName().toLowerCase() + "%"));
            }
            if (customerRegisterDTO.getRequestType() != null) {
                predicates.add(cb.equal(cb.lower(root.get(CustomerRegister_.REQUEST_TYPE)), customerRegisterDTO.getRequestType().toLowerCase()));
            }
            if (customerRegisterDTO.getDeadline() != null) {
                predicates.add(cb.equal(cb.lower(root.get(CustomerRegister_.DEADLINE)), customerRegisterDTO.getDeadline().toLowerCase()));
            }
            if (customerRegisterDTO.getCtsStatus() != null) {
                predicates.add(cb.equal(cb.lower(root.get(CustomerRegister_.CTS_STATUS)), customerRegisterDTO.getCtsStatus().toLowerCase()));
            }
            if (customerRegisterDTO.getSendDateFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get(CustomerRegister_.SEND_DATE), formatter.parse(customerRegisterDTO.getSendDateFrom())));
            }
            if (customerRegisterDTO.getSendDateTo() != null) {
                Date date = formatter.parse(customerRegisterDTO.getSendDateTo());
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DATE, 1);
                date = c.getTime();
                predicates.add(cb.lessThanOrEqualTo(root.get(CustomerRegister_.SEND_DATE), date));
            }
            criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));
            criteriaQuery.orderBy(cb.asc(root.get(CustomerRegister_.SEND_DATE)));

            List<CustomerRegister> rs = entityManager.createQuery(criteriaQuery)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            Root<CustomerRegister> rootCount = countQuery.from(CustomerRegister.class);
            countQuery.select(cb.count(rootCount)).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
            Long count = entityManager.createQuery(countQuery).getSingleResult();
            List<CustomerRegisterDTO> rsDTOs = rs.stream().map(customerRegisterMapper::toDto).collect(Collectors.toList());
            return new PageImpl<>(rsDTOs, pageable, count);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
