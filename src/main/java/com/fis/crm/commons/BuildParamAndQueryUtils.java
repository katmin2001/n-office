package com.fis.crm.commons;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BuildParamAndQueryUtils {
    public static void putProvinceCodeToQuery(String provinceCode, Map<String, Object> parrams, StringBuilder sb, String tbAlias) {
        String alias = tbAlias + ".";
        if (DataUtil.isNullOrEmpty(tbAlias)) {
            alias = "";
        }
        sb.append(" and " + alias + "province_code = :provinceCode ");
        parrams.put("provinceCode", provinceCode);
    }

    public static void putProvinceToQuery(String provinceCode, Map<String, Object> parrams, StringBuilder sb, String tbAlias) {
        String alias = tbAlias + ".";
        if (DataUtil.isNullOrEmpty(tbAlias)) {
            alias = "";
        }
        sb.append(" and " + alias + "province = :provinceCode ");
        parrams.put("provinceCode", provinceCode);
    }

    public static void putDistrictCodeToQuery(String districtCode, Map<String, Object> parrams, StringBuilder sb, String tbAlias) {
        String alias = tbAlias + ".";
        if (DataUtil.isNullOrEmpty(tbAlias)) {
            alias = "";
        }
        sb.append(" and " + alias + "district_code = :districtCode ");
        parrams.put("districtCode", districtCode);
    }

    public static void putDistrictConcatProvinceCodeToQuery(String districtCode, Map<String, Object> parrams, StringBuilder sb, String tbAlias) {
        String alias = tbAlias + ".";
        if (DataUtil.isNullOrEmpty(tbAlias)) {
            alias = "";
        }
        sb.append(" and " + alias + "province || " + alias + "district = :districtCode ");
        parrams.put("districtCode", districtCode);
    }

    public void putVillageCodeToQuery(String villageCode, Map<String, Object> parrams, StringBuilder sb, String tbAlias) {
        String alias = tbAlias + ".";
        if (DataUtil.isNullOrEmpty(tbAlias)) {
            alias = "";
        }
        sb.append(" and " + alias + "village_code = :villageCode  ");
        parrams.put("villageCode", villageCode);
    }

    public static void putPrdIdToQuery(Object prdId, Map<String, Object> parrams, StringBuilder sb, String tbAlias) {
        String alias = tbAlias + ".";
        if (DataUtil.isNullOrEmpty(tbAlias)) {
            alias = "";
        }
        sb.append(" and " + alias + "prd_id = :prdId  ");
        parrams.put("prdId", prdId);
    }

    public static void putStaffOwnerIdToQuery(Object staffId, Map<String, Object> parrams, StringBuilder sb, String tbAlias) {
        String alias = tbAlias + ".";
        if (DataUtil.isNullOrEmpty(tbAlias)) {
            alias = "";
        }
        sb.append(" and " + alias + "staff_owner_id = :staffCode ");
        parrams.put("staffCode", staffId);
    }

    public static void putStationCodeToQuery(Object stationCode, Map<String, Object> parrams, StringBuilder sb, String tbAlias) {
        String alias = tbAlias + ".";
        if (DataUtil.isNullOrEmpty(tbAlias)) {
            alias = "";
        }
        sb.append(" and " + alias + "station_code = :stationCode ");
        parrams.put("stationCode", stationCode);
    }

    public static void putSalePointCodeToQuery(Object salePointCode, Map<String, Object> parrams, StringBuilder sb, String tbAlias) {
        String alias = tbAlias + ".";
        if (DataUtil.isNullOrEmpty(tbAlias)) {
            alias = "";
        }
        sb.append(" and " + alias + "sale_point_code = :salePointCode ");
        parrams.put("salePointCode", salePointCode);
    }

    public static void putManagerCodeToQuery(Object staffCode, Map<String, Object> parrams, StringBuilder sb, String tbAlias) {
        String alias = tbAlias + ".";
        if (DataUtil.isNullOrEmpty(tbAlias)) {
            alias = "";
        }
        sb.append(" and " + alias + "MANAGER_CODE = :staffCode ");
        parrams.put("staffCode", staffCode);
    }

    public static void putUnitCodeToQuery(Object unitCode, Map<String, Object> parrams, StringBuilder sb, String tbAlias) {
        String alias = tbAlias + ".";
        if (DataUtil.isNullOrEmpty(tbAlias)) {
            alias = "";
        }
        sb.append(" and " + alias + "unit = :unitCode ");
        parrams.put("unitCode", unitCode);
    }

    public static void putTennantCodeToQuery(Object tennantCode, Map<String, Object> parrams, StringBuilder sb, String tbAlias) {
        String alias = tbAlias + ".";
        if (DataUtil.isNullOrEmpty(tbAlias)) {
            alias = "";
        }
        sb.append(" and " + alias + "tenant_code = :tennantCode ");
        parrams.put("tennantCode", tennantCode);
    }

    public static void putObjCodeToQuery(Object objCode, Map<String, Object> parrams, StringBuilder sb, String tbAlias) {
        String alias = tbAlias + ".";
        if (DataUtil.isNullOrEmpty(tbAlias)) {
            alias = "";
        }
        sb.append(" and " + alias + "obj_code = :objCode ");
        parrams.put("objCode", objCode);
    }

    public static void putNodeCodeToQuery(Object nodeCode, Map<String, Object> parrams, StringBuilder sb, String tbAlias) {
        String alias = tbAlias + ".";
        if (DataUtil.isNullOrEmpty(tbAlias)) {
            alias = "";
        }
        sb.append(" and " + alias + "node_code = :nodeCode ");
        parrams.put("nodeCode", nodeCode);
    }

    public static void putTimeTypeToQuery(Object timeType, Map<String, Object> parrams, StringBuilder sb, String tbAlias) {
        String alias = tbAlias + ".";
        if (DataUtil.isNullOrEmpty(tbAlias)) {
            alias = "";
        }
        sb.append(" and " + alias + "time_type = :timeType ");
        parrams.put("timeType", timeType);
    }

    public static void putServiceIdsToQuery(List kpiIds, Map<String, Object> parrams, StringBuilder sb, String tbAlias) {
        String alias = tbAlias + ".";
        if (DataUtil.isNullOrEmpty(tbAlias)) {
            alias = "";
        }
        sb.append(" and " + alias + "kpi_id " + DbUtils.createInQuery("kpiIds", kpiIds));
        parrams.put("kpiIds", kpiIds);
    }
    public static void putInputLevelToQuery(Object inputLevel, Map<String, Object> parrams, StringBuilder sb, String tbAlias) {
        String alias = tbAlias + ".";
        if (DataUtil.isNullOrEmpty(tbAlias)) {
            alias = "";
        }
        sb.append(" and " + alias + "input_level = :inputLevel ");
        parrams.put("inputLevel", inputLevel);
    }

    public static void putInputLevelsToQuery(List inputLevels, Map<String, Object> parrams, StringBuilder sb, String tbAlias) {
        String alias = tbAlias + ".";
        if (DataUtil.isNullOrEmpty(tbAlias)) {
            alias = "";
        }
        sb.append(" and " + alias + "input_level " + DbUtils.createInQuery("inputLevels", inputLevels));
        parrams.put("inputLevels", inputLevels);
    }

    public static void putInputKeywordToQuery(List inputLevels, Map<String, Object> parrams, StringBuilder sb, String tbAlias) {
        String alias = tbAlias + ".";
        if (DataUtil.isNullOrEmpty(tbAlias)) {
            alias = "";
        }
        sb.append(" and " + alias + "input_level " + DbUtils.createInQuery("inputLevels", inputLevels));
        parrams.put("inputLevels", inputLevels);
    }
    public static void putFromDateToDateToQuery(Object fromDate, Object toDate, Map<String, Object> parrams, StringBuilder sb, String tbAlias) {
        String alias = tbAlias + ".";
        if (DataUtil.isNullOrEmpty(tbAlias)) {
            alias = "";
        }
        sb.append(" and " + alias + "prd_id >= :fromDate and " + alias + "prd_id <= :toDate");
        parrams.put("fromDate", fromDate);
        parrams.put("toDate", toDate);
    }


}
