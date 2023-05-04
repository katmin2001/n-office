package com.fis.crm.repository.impl;

import com.fis.crm.commons.MaperUtils;
import com.fis.crm.service.dto.UserDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class GroupUserMappingCustomRepository {

    final
    EntityManager entityManager;

    public GroupUserMappingCustomRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<UserDTO> findAllUserNotInGroup(Long groupId) {
        String mainSql = "select u.id, u.first_name from jhi_user u where u.id not in(select g.user_id user_id from group_user_mapping g where g.group_id = :groupId)\n" +
            "and u.activated = 1";
        Query query = entityManager.createNativeQuery(mainSql);
        query.setParameter("groupId", groupId);
        List<Object[]> objects = query.getResultList();
        return new MaperUtils(objects).add("id").add("fullName").transform(UserDTO.class);
    }

    public List<UserDTO> findAllUserInGroup(Long groupId) {
        String mainSql = "select u.id, u.login, u.first_name, u.internal_number from group_user_mapping g join jhi_user u on g.user_id = u.id\n" +
            "where u.activated = 1 and g.group_id = :groupId";
        Query query = entityManager.createNativeQuery(mainSql);
        query.setParameter("groupId", groupId);
        List<Object[]> objects = query.getResultList();
        return new MaperUtils(objects).add("id").add("login").add("fullName").add("internalNumber").transform(UserDTO.class);
    }

    public List<Object> getUserLoginById(Long id) {
        String mainSql = "select u.login from jhi_user u where u.id = :id";
        Query query = entityManager.createNativeQuery(mainSql);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
