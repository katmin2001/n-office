package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmTask;
import org.jboss.resteasy.annotations.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrmTaskRepo extends CrudRepository<CrmTask, Long>{

}
