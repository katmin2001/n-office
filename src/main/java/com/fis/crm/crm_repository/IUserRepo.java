package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface IUserRepo extends JpaRepository<CrmUser, Long> {

    @Query(value = "SELECT u FROM CrmUser u "+
                    "where (:fullname is null or lower(u.fullname) like %:fullname%)  " +
                    "and (:createdate is null or u.createdate   = :createdate)  " +
                    "and (:phone is null or lower(u.phone) like %:phone%)  " +
                    "and (:birthday is null or u.birthday = :birthday)  " +
                    "and (:address is null or lower(u.address) like %:address%)  " +
                    "and (:status is null or lower(u.status) like %:status%) "
    )
    List<CrmUser> findCrmUsersByKeyword(@Param("fullname") String fullname,
                                        @Param("createdate") Date createdate,
                                        @Param("phone") String phone,
                                        @Param("birthday") Date birthday,
                                        @Param("address") String address,
                                        @Param("status") String status);

    @Query(value = "select u from CrmUser u where u.username = :username")
    CrmUser findCrmUserByUsername(@Param("username") String username);

    @Query(value = "select u from CrmUser u where u.userid = :id")
    CrmUser findCrmUserByUserid(@Param("id") Long id);
}

