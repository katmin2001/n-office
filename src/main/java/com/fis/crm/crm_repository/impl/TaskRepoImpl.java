//package com.fis.crm.crm_repository.impl;
//
//import com.fis.crm.crm_entity.CrmTask;
//import com.fis.crm.crm_repository.ProjectRepo;
//import com.fis.crm.crm_repository.TaskRepo;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Repository;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//public class TaskRepoImpl implements TaskRepo {
//
//    /**
//     * @param id
//     * @return
//     */
//    @Override
//    public List<CrmTask> findTasksByProjectId(Long id) {
//        return null;
//    }
//
//    /**
//     * @param taskName
//     * @param startDate
//     * @param endDate
//     * @param statusCode
//     * @param giverTaskId
//     * @param receiverTaskId
//     * @param stageId
//     * @param projectTask
//     */
//    @Override
//    public void createTask(String taskName, Date startDate, Date endDate, Long statusCode, Long giverTaskId, Long receiverTaskId, Long stageId, Long projectTask) {
//
//    }
//
//    /**
//     * @return
//     */
//    @Override
//    public List<CrmTask> findAll() {
//        return null;
//    }
//
//    /**
//     * @param sort
//     * @return
//     */
//    @Override
//    public List<CrmTask> findAll(Sort sort) {
//        return null;
//    }
//
//    /**
//     * @param pageable
//     * @return
//     */
//    @Override
//    public Page<CrmTask> findAll(Pageable pageable) {
//        return null;
//    }
//
//    /**
//     * @param iterable
//     * @return
//     */
//    @Override
//    public List<CrmTask> findAllById(Iterable<Long> iterable) {
//        return null;
//    }
//
//    /**
//     * @return
//     */
//    @Override
//    public long count() {
//        return 0;
//    }
//
//    /**
//     * @param aLong
//     */
//    @Override
//    public void deleteById(Long aLong) {
//
//    }
//
//    /**
//     * @param crmTask
//     */
//    @Override
//    public void delete(CrmTask crmTask) {
//
//    }
//
//    /**
//     * @param iterable
//     */
//    @Override
//    public void deleteAll(Iterable<? extends CrmTask> iterable) {
//
//    }
//
//    /**
//     *
//     */
//    @Override
//    public void deleteAll() {
//
//    }
//
//    /**
//     * @param s
//     * @param <S>
//     * @return
//     */
//    @Override
//    public <S extends CrmTask> S save(S s) {
//        return null;
//    }
//
//    /**
//     * @param iterable
//     * @param <S>
//     * @return
//     */
//    @Override
//    public <S extends CrmTask> List<S> saveAll(Iterable<S> iterable) {
//        return null;
//    }
//
//    /**
//     * @param aLong
//     * @return
//     */
//    @Override
//    public Optional<CrmTask> findById(Long aLong) {
//        return Optional.empty();
//    }
//
//    /**
//     * @param aLong
//     * @return
//     */
//    @Override
//    public boolean existsById(Long aLong) {
//        return false;
//    }
//
//    /**
//     *
//     */
//    @Override
//    public void flush() {
//
//    }
//
//    /**
//     * @param s
//     * @param <S>
//     * @return
//     */
//    @Override
//    public <S extends CrmTask> S saveAndFlush(S s) {
//        return null;
//    }
//
//    /**
//     * @param iterable
//     */
//    @Override
//    public void deleteInBatch(Iterable<CrmTask> iterable) {
//
//    }
//
//    /**
//     *
//     */
//    @Override
//    public void deleteAllInBatch() {
//
//    }
//
//    /**
//     * @param aLong
//     * @return
//     */
//    @Override
//    public CrmTask getOne(Long aLong) {
//        return null;
//    }
//
//    /**
//     * @param example
//     * @param <S>
//     * @return
//     */
//    @Override
//    public <S extends CrmTask> Optional<S> findOne(Example<S> example) {
//        return Optional.empty();
//    }
//
//    /**
//     * @param example
//     * @param <S>
//     * @return
//     */
//    @Override
//    public <S extends CrmTask> List<S> findAll(Example<S> example) {
//        return null;
//    }
//
//    /**
//     * @param example
//     * @param sort
//     * @param <S>
//     * @return
//     */
//    @Override
//    public <S extends CrmTask> List<S> findAll(Example<S> example, Sort sort) {
//        return null;
//    }
//
//    /**
//     * @param example
//     * @param pageable
//     * @param <S>
//     * @return
//     */
//    @Override
//    public <S extends CrmTask> Page<S> findAll(Example<S> example, Pageable pageable) {
//        return null;
//    }
//
//    /**
//     * @param example
//     * @param <S>
//     * @return
//     */
//    @Override
//    public <S extends CrmTask> long count(Example<S> example) {
//        return 0;
//    }
//
//    /**
//     * @param example
//     * @param <S>
//     * @return
//     */
//    @Override
//    public <S extends CrmTask> boolean exists(Example<S> example) {
//        return false;
//    }
//}
//
