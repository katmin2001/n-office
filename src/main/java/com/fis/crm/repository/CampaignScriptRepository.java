package com.fis.crm.repository;

import com.fis.crm.domain.CampaignScript;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the CampaignScript entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampaignScriptRepository extends JpaRepository<CampaignScript, Long> {

    List<CampaignScript> findCampaignScriptByName(@Param("name") String name);

    @Query(value = "select * from campaign_script where lower(name) like :name and status = '1'", nativeQuery = true)
    CampaignScript findByName(@Param("name") String name);

    @Query(value = "select id, name from campaign_script where status ='1' ", nativeQuery = true)
    List<Object[]> getListCampaignScriptForCombobox();

    List<CampaignScript> findByCode(String code);

    @Query(value = "select * from campaign_script where status ='1' order by CREATE_DATETIME desc ", nativeQuery = true)
    Page<CampaignScript> findAll(Pageable pageable);

    List<CampaignScript> findByOrderByCreateDatetimeDesc();
}
