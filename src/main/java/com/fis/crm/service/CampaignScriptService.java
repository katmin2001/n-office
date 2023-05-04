package com.fis.crm.service;

import com.fis.crm.service.dto.CampaignScriptDTO;
import com.fis.crm.service.dto.CampaignScriptListCbxDTO;
import com.fis.crm.service.dto.CampaignScriptQuestionResponseDTO;
import com.fis.crm.service.dto.CopyScriptRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.CampaignScript}.
 */
public interface CampaignScriptService {

    /**
     * Save a campaignScript.
     *
     * @param campaignScriptDTO the entity to save.
     * @return the persisted entity.
     */
    CampaignScriptDTO save(CampaignScriptDTO campaignScriptDTO);

    /**
     * Get all the campaignScripts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CampaignScriptDTO> findAll(Pageable pageable);


    /**
     * Get the "id" campaignScript.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CampaignScriptDTO> findOne(Long id);

    /**
     * Delete the "id" campaignScript.
     *
     * @param id the id of the entity.
     */
    void delete(Long id) throws Exception;

    boolean validateBeforeAdd(CampaignScriptDTO campaignScriptDTO);

    boolean checkName(String name);

    boolean checkNameToLowerCase(String name);

    List<CampaignScriptListCbxDTO> getListCampaignScriptForCombobox();

    List<CampaignScriptQuestionResponseDTO> uploadFile(MultipartFile file, Long campaignScriptId, Integer deleteAllQuestion) throws Exception;

    CampaignScriptDTO updateCampaignScript(CampaignScriptDTO dto);

    String copyScript(CopyScriptRequestDTO copyScriptRequestDTO);
}
