package com.fis.crm.crm_repository;

import com.fis.crm.crm_entity.CrmProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CrmProjectRepo extends JpaRepository<CrmProject, Long> {
    @Query(value = "select p from CrmProject p where p.code = :code")
    public CrmProject findCrmProjectByCode(@Param("code") String code);

    @Query(value = "SELECT *\n" +
        "FROM CRM_PROJECT P\n" +
        "         LEFT JOIN CRM_PROJECT_STATUS PS ON PS.ID = P.STATUS_ID\n" +
        "         LEFT JOIN CRM_USER U ON U.USERID = P.MANAGER_ID\n" +
        "         LEFT JOIN CRM_PROJECT_MEMBER PM ON PM.PROJECT_ID = P.ID\n" +
        "         LEFT JOIN CRM_USER EMP ON PM.MEMBER_ID = EMP.USERID\n" +
        "WHERE (:projectName IS NULL OR lower(p.NAME) LIKE '%' || lower(:projectName) || '%')\n" +
        "  AND (:projectCode IS NULL OR lower(p.CODE) LIKE '%' || lower(:projectCode) || '%')\n" +
        "  AND (:projectStatus IS NULL OR lower(PS.NAME) LIKE '%' || lower(:projectStatus) || '%')\n" +
        "  AND (:managerName IS NULL OR lower(U.FULLNAME) LIKE '%' || lower(:managerName) || '%')\n" +
        "  AND (:memberName IS NULL OR lower(EMP.FULLNAME) LIKE '%' || lower(:memberName) || '%')\n" +
        "  AND ((:projectProcess IS NULL)\n" +
        "  OR (lower(:projectProcess) = 'trong hạn' AND P.END_DATE >= CURRENT_DATE)\n" +
        "  OR (lower(:projectProcess) = 'quá hạn' AND P.END_DATE < CURRENT_DATE))",
        nativeQuery = true)
    List<CrmProject> searchProjects(@Param("projectCode") String projectCode,
                                    @Param("projectName") String projectName,
                                    @Param("projectStatus") String projectStatus,
                                    @Param("projectProcess") String projectProcess,
                                    @Param("managerName") String managerName,
                                    @Param("memberName") String memberName);
}
