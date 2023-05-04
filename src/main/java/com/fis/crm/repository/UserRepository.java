package com.fis.crm.repository;

import com.fis.crm.domain.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmailIgnoreCase(String email);

    @Query(value = "select u from User u where upper(u.login) = :login ")
    Optional<User> findOneByLogin(@Param("login") String login);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    @Query(value = "select u from User u where upper(u.login) = :login or lower(u.login) = :login ")
    Optional<User> findOneWithAuthoritiesByLogin(@Param("login") String login);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
    Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    Page<User> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User ju SET ju.createTicket = :ct, ju.processTicket = :pt, ju.confirmTicket = :cft WHERE ju.id = :id")
    void updateUser(@Param("ct") Boolean createTicket, @Param("pt") Boolean processTicket, @Param("cft") Boolean confirmTicket, @Param("id") Long id);

    @Query(value = "SELECT ju FROM User ju " +
        "WHERE (lower(ju.login) LIKE concat('%', concat(LOWER(:keyword), '%')) OR LOWER(ju.firstName) LIKE concat('%', concat(LOWER(:keyword) , '%')) OR :keyword is null)")
    Page<User> findUserByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT ju FROM User ju " +
        "WHERE (lower(ju.login) LIKE concat('%', concat(LOWER(:keyword), '%')) OR LOWER(ju.firstName) LIKE concat('%', concat(LOWER(:keyword) , '%')) OR :keyword is null) " +
        "AND (:createTicket = false OR ju.createTicket = :createTicket) AND (:processTicket = false OR ju.processTicket = :processTicket) AND (:confirmTicket = false OR ju.confirmTicket = :confirmTicket)")
    Page<User> findUserByKeywordAndAnother(@Param("keyword") String keyword, @Param("createTicket") Boolean createTicket, @Param("processTicket") Boolean processTicket, @Param("confirmTicket") Boolean confirmTicket, Pageable pageable);

    @Query(value = "select ju from User ju where ju.id =:id")
    User getFirstAndLastNameById(@Param("id") Long id);

    Optional<User> findFirstById(Long id);

    User findByLogin(String username);


    @Query(value = "select ju from User ju join GroupUserMapping gm on ju.id = gm.userId where gm.groupId = :groupId and gm.status = '1'")
    Optional<List<User>> getUserByGroupId(@Param("groupId") Long id);

    Optional<List<User>> getUserByType(String type);

    @Query(value = "select ju from User ju where ju.activated = '1' and ju.type = :type")
    Optional<List<User>> findAllByActivatedAndType(@Param("type") String type);

    @Query(value = "select ju from User ju where ju.activated = '1'")
    Optional<List<User>> findAllByActivated();

    @Query(value = "select to_number(v.code) " +
        "from option_set_value v " +
        "where v.option_set_id in (" +
        "select o.option_set_id " +
        "from option_set o " +
        "where o.code = 'SO_LAN_LOGIN_FAIL' ) and rownum=1", nativeQuery = true)
    Integer getMaxLogin();

    @Query(value = "select ju from User ju where ju.activated = true and ju.type = :type")
    List<User> getActivatedUserByType(@Param("type")String type);

    @Query(value = "select ju from User ju join fetch ju.userRoles d  " +
        " where ju.activated = true and d.role.id = 2 and  d.status = '1'")
    List<User> getTDV();

    @Query(value = "select ju from User ju join fetch ju.userRoles d  " +
        " where ju.activated = true and d.role.id = 1 and  d.status = '1'")
    List<User> getLeadTDV();

    @Query(value = "select ju from User ju where ju.activated = true")
    List<User> getActivatedUsers();

    @Query(value = "select count(ju) from User ju where lower(ju.email) = lower(:email) and ju.id <> :id")
    Long checkEmailExists(@Param("id") Long id, @Param("email") String email);
}
