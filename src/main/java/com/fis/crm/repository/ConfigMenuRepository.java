package com.fis.crm.repository;

import com.fis.crm.domain.ConfigMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Spring Data SQL repository for the ConfigMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfigMenuRepository extends JpaRepository<ConfigMenu, Long>, ConfigMenuCustomRepository {

    List<ConfigMenu> findByStatusAndIdInOrderByOrderIndex(Integer status, Set<Long> Ids);

    Optional<List<ConfigMenu>> findByStatusOrderByOrderIndex(Integer status);

}
