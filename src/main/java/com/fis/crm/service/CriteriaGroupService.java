package com.fis.crm.service;

import com.fis.crm.service.dto.CriteriaGroupDTO;
import com.fis.crm.service.dto.CriteriaGroupLoadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CriteriaGroupService {

    CriteriaGroupDTO save(CriteriaGroupDTO criteriaGroupDTO);

    Page<CriteriaGroupDTO> findAll(String search, Pageable pageable);

    List<CriteriaGroupDTO> findAll();

    Optional<CriteriaGroupDTO> findOne(Long id);

    void delete(Long id);

    boolean checkName(String name);

    Optional<List<CriteriaGroupDTO>> findCriteriaGroupDetail();

    Double getRemainingScore(Long criteriaGroupId, Long criteriaId);

    List<CriteriaGroupLoadDTO> loadToCbx();

}
