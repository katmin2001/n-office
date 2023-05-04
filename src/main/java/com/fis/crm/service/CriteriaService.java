package com.fis.crm.service;

import com.fis.crm.service.dto.CriteriaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CriteriaService {

    Page<CriteriaDTO> search(String status, Long criteriaGroupId, String content, Pageable pageable);

    CriteriaDTO save(CriteriaDTO criteriaDTO);

    CriteriaDTO changeStatus(Long id, Boolean isDelete);

    List<CriteriaDTO> listCriteria();

    Optional<CriteriaDTO> findOne(Long id);

    List<CriteriaDTO> getListCriteriaByCriteriaGroupId(Long id);

}
