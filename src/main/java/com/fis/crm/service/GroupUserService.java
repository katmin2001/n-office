package com.fis.crm.service;

import com.fis.crm.service.dto.GroupUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.fis.crm.domain.GroupUser}.
 */
public interface GroupUserService {

    /**
     * Save a groupUser.
     *
     * @param groupUserDTO the entity to save.
     * @return the persisted entity.
     */
    GroupUserDTO save(GroupUserDTO groupUserDTO);

    /**
     * Get all the groupUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GroupUserDTO> findAll(Pageable pageable);


    /**
     * Get the "id" groupUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GroupUserDTO> findOne(Long id);

    /**
     * Delete the "id" groupUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Optional<List<GroupUserDTO>> getAllGroupUsersByCampaignId(Long campaignId) throws Exception;

    List<GroupUserDTO> getAllGroupUsersByCampaignId2(Long campaignId) throws Exception;
}
