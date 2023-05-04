/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fis.crm.commons;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DbUtils {
    private static final Logger logger = LoggerFactory.getLogger(DbUtils.class);


    public static void setDoubleJdbc(PreparedStatement stmt, int index, Double value) throws Exception {
        if (value == null) {
            stmt.setNull(index, Types.DOUBLE);
        } else {
            stmt.setDouble(index, value);
        }
    }

    public static void setDateJdbc(PreparedStatement stmt, int index, Date value) throws Exception {
        if (!DataUtil.isNullObject(value)) {
            stmt.setTimestamp(index, new Timestamp(value.getTime()));
        } else {
            stmt.setNull(index, Types.TIMESTAMP);
        }
    }

    public static void setStringJdbc(PreparedStatement stmt, int index, String value) throws Exception {
        if (!DataUtil.isNullObject(value)) {
            stmt.setString(index, value);
        } else {
            stmt.setNull(index, Types.VARCHAR);
        }
    }

    public static Long getLong(ResultSet rs, int index) throws Exception {
        return DataUtil.safeToLong(rs.getObject(index));
    }

    public static void setLongJdbc(PreparedStatement stmt, int index, Long value) throws Exception {
        if (!DataUtil.isNullObject(value)) {
            stmt.setLong(index, value);
        } else {
            stmt.setNull(index, Types.BIGINT);
        }
    }

    public static void setClobJdbc(Connection conn, PreparedStatement stmt, int index, String value) throws Exception {
        Clob clob = conn.createClob();
        clob.setString(1, value);
        stmt.setClob(index, clob);
    }

    public static void setLongJdbcParamInQuery(PreparedStatement stmt, int startIndex, List<Long> objs) throws Exception {
        int idx = 0;
        for (Long obj : objs) {
            if (obj == null) {
                stmt.setNull(startIndex + idx++, Types.BIGINT);
            } else {
                stmt.setLong(startIndex + idx++, obj);
            }
        }
    }

    public static void setStringJdbcParamInQuery(PreparedStatement stmt, int startIndex, List<String> objs) throws Exception {
        int idx = 0;
        for (String obj : objs) {
            stmt.setString(startIndex + idx++, obj);
        }
    }

    public static void setParamInQuery(Query query, String prefix, List objs) {
        int idx = 1;
        for (Object obj : objs) {
            query.setParameter(prefix + String.valueOf(idx++), obj);
        }
    }

    public static List createListParamInQuery(String prefix, List objs) {
        List lst = new ArrayList<>();
        int idx = 1;
        for (Object obj : objs) {
            lst.add(new Object[]{prefix + String.valueOf(idx++), obj});
        }

        return lst;
    }

    public static Map<String, Object> createMapParamInQuery(String prefix, List objs) {
        Map<String, Object> map = new HashMap<>();
        int idx = 1;
        for (Object obj : objs) {
            map.put(prefix + String.valueOf(idx++), obj);
        }
        return map;
    }

    public static String createJdbcInQuery(List objs) {
        String inQuery = " IN (";
        for (int idx = 1; idx < objs.size() + 1; idx++) {
            inQuery += ",?";
        }
        inQuery += ") ";
        inQuery = inQuery.replaceFirst(",", "");

        return inQuery;
    }

    public static String createJdbcInToNumberQuery(List objs) {
        String inQuery = " IN (";
        for (int idx = 1; idx < objs.size() + 1; idx++) {
            inQuery += ",TO_NUMBER(?)";
        }
        inQuery += ") ";
        inQuery = inQuery.replaceFirst(",", "");

        return inQuery;
    }

    public static String createInQuery(String prefix, List objs) {
        String inQuery = " IN (";
        for (int idx = 1; idx < objs.size() + 1; idx++) {
            inQuery += ",:" + prefix + idx;
        }
        inQuery += ") ";
        inQuery = inQuery.replaceFirst(",", "");

        return inQuery;
    }


    public static Date getSysDate(EntityManager em) {
        String queryString = "SELECT SYSDATE() from dual";
        Query queryObject = em.createNativeQuery(queryString);
        return (Date) queryObject.getSingleResult();
    }

    /*
     * ThinhDD - 20/12/2011
     * Them ham cho da chu ky
     */
    public static Date getSysDate(EntityManager em, String pattern) {
        return getSysDate(em);
    }

/*    public static Date getTruncSysdate(EntityManager em) throws Exception {
        String strDate = DateUtil.date2ddMMyyyyString(getSysDate(em));
        return DateUtil.string2Date(strDate);
    }*/

    public static String getLpadSequence(EntityManager em, String sequenceName, String lpadLength, String lpadReplace) {
        String strLpadReplace = "'" + lpadReplace + "'";
        String sql = "select TO_CHAR (LPAD (" + sequenceName + ".nextval" + "," + lpadLength + "," + strLpadReplace + "))" + " from dual";
        Query query = em.createNativeQuery(sql);
        return query.getSingleResult().toString();
    }

    public static Long getSequence(EntityManager em, String sequenceName) {
        String sql = "select " + sequenceName + ".nextval from dual";
        Query query = em.createNativeQuery(sql);
        return ((BigDecimal) query.getSingleResult()).longValue();
    }

    public static void setInsertCareNull(StringBuilder sql, Object value, List parameterList) {
        setInsertCareNull(sql, value, parameterList, false);
    }

    public static void setInsertCareNull(StringBuilder sql, Object value, List parameterList, boolean isEnd) {
        if (value != null) {
            sql.append(isEnd ? "?)" : "?,");
            parameterList.add(value);
        } else {
            sql.append(isEnd ? "null)" : "?)");
        }
    }

    public static void setParramToQuery(Query query, Map<String, Object> params) {
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (DataUtil.isCollection(entry.getValue())) {
                    DbUtils.setParamInQuery(query, entry.getKey(), (List) entry.getValue());
                } else {
                    if (!DataUtil.isNullOrEmpty(query.getParameters()) && query.getParameter(entry.getKey()) != null) {
                        query.setParameter(entry.getKey(), entry.getValue());
                    } else {
                        logger.warn("Khong ton tai key", entry.getKey());
                    }
                }
            }
        }
    }

    public static void setParramToQuery(org.hibernate.query.Query queryHibernate, Map<String, Object> mapParam) {
        if (mapParam != null) {
            mapParam.forEach((param, value) -> {
                if (value instanceof Collection) {
                    queryHibernate.setParameterList(param, (List) value);
                } else {
                    queryHibernate.setParameter(param, value);
                }
            });
        }
    }

    public static <T> T transformMapToEntity(Map<String, Object> map, Class<T> clazz) {
        T result = null;
        try {
            Class<?> clazzObj = clazz;
            Object objTemp = clazzObj.newInstance();
            Map mapAnnotation = new ConcurrentHashMap();
            for (Field field : objTemp.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Column col = field.getAnnotation(Column.class);
                if (col != null) {
                    mapAnnotation.put(col.name().toLowerCase().trim(), field.getName());
                }
            }

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String colAlias = entry.getKey();
                Object value = entry.getValue();
                String fieldName = (String) mapAnnotation.get(colAlias.toLowerCase());
                if (fieldName != null) {
                    Field field = objTemp.getClass().getDeclaredField(fieldName);
                    if (field != null) {
                        field.setAccessible(true);
                        field.set(objTemp, value);
                    }
                }
            }
            result = (T) objTemp;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return result;
    }

    public static <T> T transformTuple(ObjectMapper mapper, Object[] rowData, String[] aliasNames, Class<T> clazz) {
        T result = null;
        try {
            Map<String, Object> map = new HashMap<>();
            for (int i = 0; i < aliasNames.length; i++) {
                String propertyName = aliasNames[i].toLowerCase();
                while (propertyName.indexOf("_") > 0 && propertyName.indexOf("_") < propertyName.length() - 1) {
                    String charNeedUpper = propertyName.substring(propertyName.indexOf("_") + 1, propertyName.indexOf("_") + 2);
                    propertyName = propertyName.replaceFirst("_" + charNeedUpper, charNeedUpper.toUpperCase());
                }
                map.put(propertyName, rowData[i]);
            }
            String json = mapper.writeValueAsString(map);
            result = mapper.readValue(json, clazz);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return result;
    }

    public static void close(PreparedStatement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

    }

    public static void close(CallableStatement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

    }

    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

    }

    public static void close(Connection rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

    }

    public static void closeAll(Connection conn, ResultSet resultSet, CallableStatement callableStatement) {
        try {
            if (callableStatement != null) {
                callableStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException throwables) {
            logger.error(throwables.getMessage(), throwables);
        }
    }
}

