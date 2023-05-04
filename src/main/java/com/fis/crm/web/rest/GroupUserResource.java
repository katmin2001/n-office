package com.fis.crm.web.rest;

import com.fis.crm.domain.Campaign;
import com.fis.crm.service.GroupUserMappingService;
import com.fis.crm.service.GroupUserService;
import com.fis.crm.service.dto.GroupUserDTO;
import com.fis.crm.service.dto.MessageResponse;
import com.fis.crm.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fis.crm.domain.GroupUser}.
 */
@RestController
@RequestMapping("/api")
public class GroupUserResource {

    private final Logger log = LoggerFactory.getLogger(GroupUserResource.class);

    private static final String ENTITY_NAME = "groupUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupUserService groupUserService;

    final
    GroupUserMappingService groupUserMappingService;

    public GroupUserResource(GroupUserService groupUserService, GroupUserMappingService groupUserMappingService) {
        this.groupUserService = groupUserService;
        this.groupUserMappingService = groupUserMappingService;
    }

    /**
     * {@code POST  /group-users} : Create a new groupUser.
     *
     * @param groupUserDTO the groupUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupUserDTO, or with status {@code 400 (Bad Request)} if the groupUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/group-users")
    public ResponseEntity<GroupUserDTO> createGroupUser(@RequestBody GroupUserDTO groupUserDTO) throws URISyntaxException {
        log.debug("REST request to save GroupUser : {}", groupUserDTO);
        if (groupUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new groupUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupUserDTO result = groupUserService.save(groupUserDTO);
        return ResponseEntity.created(new URI("/api/group-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /group-users} : Updates an existing groupUser.
     *
     * @param groupUserDTO the groupUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupUserDTO,
     * or with status {@code 400 (Bad Request)} if the groupUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/group-users")
    public ResponseEntity<GroupUserDTO> updateGroupUser(@RequestBody GroupUserDTO groupUserDTO) throws URISyntaxException {
        log.debug("REST request to update GroupUser : {}", groupUserDTO);
        if (groupUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroupUserDTO result = groupUserService.save(groupUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groupUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /group-users} : get all the groupUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupUsers in body.
     */
    @GetMapping("/group-users")
    public ResponseEntity<List<GroupUserDTO>> getAllGroupUsers(Pageable pageable) {
        log.debug("REST request to get a page of GroupUsers");
        Page<GroupUserDTO> page = groupUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /group-users/:id} : get the "id" groupUser.
     *
     * @param id the id of the groupUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/group-users/{id}")
    public ResponseEntity<GroupUserDTO> getGroupUser(@PathVariable Long id) {
        log.debug("REST request to get GroupUser : {}", id);
        Optional<GroupUserDTO> groupUserDTO = groupUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupUserDTO);
    }

    /**
     * {@code DELETE  /group-users/:id} : delete the "id" groupUser.
     *
     * @param id the id of the groupUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/group-users/{id}")
    public ResponseEntity<Void> deleteGroupUser(@PathVariable Long id) {
        log.debug("REST request to delete GroupUser : {}", id);
        if (groupUserMappingService.countUserInGroup(id) != 0) {
            throw new BadRequestAlertException("group_have_member", "", "");
        }
        List<Long> ids = groupUserMappingService.countCampaignUseGroup(id);
        StringBuilder result = new StringBuilder();
        if (ids.size() > 0) {
            for (Long idLong : ids) {
                if (ids.indexOf(idLong) == ids.size() - 1) {
                    result.append(idLong);
                } else {
                    result.append(idLong).append(", ");
                }
            }
            throw new BadRequestAlertException("group_have_campaign:" + result, "", "");
        }
        groupUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/group-users/campaign/{campaignId}")
    public ResponseEntity<List<GroupUserDTO>> getAllGroupUsersByCampaignId(@PathVariable Long campaignId) throws Exception {
        Optional<List<GroupUserDTO>> lstGroupUserDTO = groupUserService.getAllGroupUsersByCampaignId(campaignId);
        return ResponseUtil.wrapOrNotFound(lstGroupUserDTO);
    }

    @GetMapping("/group-users/campaigns/{campaignId}")
    public ResponseEntity<MessageResponse<List<GroupUserDTO>>> getAllGroupUsersByCampaignId2(@PathVariable Long campaignId) throws Exception {
        List<GroupUserDTO> lstGroupUserDTO = groupUserService.getAllGroupUsersByCampaignId2(campaignId);
        return new ResponseEntity<>(new MessageResponse<>("ok", lstGroupUserDTO), HttpStatus.OK);
    }
}
