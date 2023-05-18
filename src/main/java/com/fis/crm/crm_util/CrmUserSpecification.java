package com.fis.crm.crm_util;

import com.fis.crm.crm_entity.CrmUser;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//public class CrmUserSpecification {
//    public static Specification<CrmUser> findUserDto(String fullName, Date create_date, String phone,
//                                                     Date birthday, String address, String status){
//        return (root, query, criteriaBuilder) -> {
//            List<Predicate> predicate = new ArrayList<>();
//            if(fullName != null && !fullName.isEmpty()){
//                predicate.add(criteriaBuilder.like(root.get("fullName"), "%" + fullName + "%"));
//            }
//            if(create_date != null && !(FormatDate.dateToString(create_date)).isEmpty()){
//                predicate.add(criteriaBuilder.like(root.get("createDate"), "%" + FormatDate.dateToString(create_date) + "%"));
//            }
//            if(phone != null && !phone.isEmpty()){
//                predicate.add(criteriaBuilder.like(root.get("phone"), "%" + phone + "%"));
//            }
//            if(birthday != null && !(FormatDate.dateToString(birthday)).isEmpty()){
//                predicate.add(criteriaBuilder.like(root.get("birthday"), "%" + FormatDate.dateToString(birthday) + "%"));
//            }
//            if(address != null && !address.isEmpty()){
//                predicate.add(criteriaBuilder.like(root.get("address"), "%" + address + "%"));
//            }
//            if(status != null && !status.isEmpty()){
//                predicate.add(criteriaBuilder.like(root.get("status"), "%" + status + "%"));
//            }
//            return criteriaBuilder.and(predicate.toArray(new Predicate[predicate.size()]));
//        };
//    }
//}
