package com.fis.crm.service;

import com.fis.crm.domain.CampaignResourceDetail;
import com.fis.crm.service.dto.CampaignResourceDetailDTO;
import com.fis.crm.service.dto.CampaignResourceGeneralDTO;
import com.fis.crm.service.dto.GeneralCampaignDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CampaignResourceDetail}.
 */
public interface CampaignResourceDetailService {

    /**
     * Save a campaignResourceDetai1.
     *
     * @param campaignResourceDetailDTO the entity to save.
     * @return the persisted entity.
     */
    CampaignResourceDetailDTO save(CampaignResourceDetailDTO campaignResourceDetailDTO);

    /**
     * Get all the campaignResourceDetai1s.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CampaignResourceDetailDTO> findAll(Pageable pageable);


    /**
     * Get the "id" campaignResourceDetai1.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CampaignResourceDetailDTO> findOne(Long id);

    /**
     * Delete the "id" campaignResourceDetai1.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Optional<Long> getNumberCampaignResourceDetailsNotGroup(Long id);

    boolean groupCampaignResourceDetails(GeneralCampaignDTO generalCampaignDTO) throws Exception;

    boolean groupNotDoneCampaignResourceDetails(GeneralCampaignDTO generalCampaignDTO) throws Exception;

    List<CampaignResourceGeneralDTO> getCampaignGroupDetails(Long campaignId) throws Exception;

    Optional<Long> getCampaignGroupDetailsNotUser(Long campaignId, Long groupId);

    List<CampaignResourceGeneralDTO> getCampaignUserDetails(CampaignResourceDetailDTO campaignResourceGeneralDTO) throws Exception;

    boolean assignCampaignUser(GeneralCampaignDTO generalCampaignDTO) throws Exception;

    boolean restoreCampaignGroup(GeneralCampaignDTO generalCampaignDTO) throws Exception;

    boolean restoreNotDoneCampaignGroup(GeneralCampaignDTO generalCampaignDTO) throws Exception;

    boolean restoreCampaignResource(GeneralCampaignDTO generalCampaignDTO) throws Exception;

    boolean restoreNotDoneCampaignResource(GeneralCampaignDTO generalCampaignDTO) throws Exception;

    Optional<Long> getNoCampaignGroupYetCallForUser(Long campaignResourceId, Long groupId, Long assignUserId) throws Exception;

    Optional<Long> getNoCampaignYetCallForUser(Long groupId) throws Exception;

    CampaignResourceDetailDTO getCampaignResourceFromCID(String cid);
}
