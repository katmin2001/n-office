package com.fis.crm.commons;

import com.fis.crm.service.response.ComboboxResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Map result sql and DTO
 */
public class MaperUtils {
    private final Logger logger = LoggerFactory.getLogger(MaperUtils.class);
    private List<Object[]> lst = new ArrayList<>();
    private List<String> lstField = new ArrayList<>();
    private DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public MaperUtils() {
    }

    public MaperUtils(List<Object[]> lst) {
        this.lst = lst;
    }

    public MaperUtils add(String field) {
        lstField.add(field);
        return this;
    }

    public <T> List<T> transform(Class<T> clzz) {
        List<T> list = new ArrayList<>();
        for (Object[] obj : lst) {
            try {
                T t = (T) clzz.newInstance();
                for (int i = 0; i < lstField.size(); i++) {
                    if(obj[i] == null) continue;
                    setValueToGivenField(getField(clzz, lstField.get(i)), t, obj[i]);
                }
                list.add(t);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return list;
    }

    private <T> Field getField(Class<T> clzz, String fieldName) throws NoSuchFieldException {
        Field lField = clzz.getDeclaredField(fieldName);
        lField.setAccessible(true);
        return lField;
    }


    private void setValueToGivenField(Field lField, Object aObject, Object aFieldValue) {
        try {
            if (aFieldValue == null) {
                lField.set(aObject, null);
            } else if (lField.getType() == Integer.class) {
                lField.set(aObject, Integer.valueOf(aFieldValue.toString()));
            } else if (lField.getType() == Long.class) {
                lField.set(aObject, Long.valueOf(aFieldValue.toString()));
            } else if (lField.getType() == Float.class) {
                lField.set(aObject, Float.valueOf(aFieldValue.toString()));
            } else if (lField.getType() == Double.class) {
                lField.set(aObject, Double.valueOf(aFieldValue.toString()));
            } else if (lField.getType() == Boolean.class) {
                lField.set(aObject, Boolean.valueOf(aFieldValue.toString()));
            } else if (lField.getType() == Date.class) {
                try {
                    lField.set(aObject, (Date) aFieldValue);
                } catch (Exception e) {
                    lField.set(aObject, sdf.parse(aFieldValue.toString()));
                }
            } else if (lField.getType() == BigDecimal.class) {
                lField.set(aObject, new BigDecimal(aFieldValue.toString()));
            } else if (lField.getType() == String.class) {
                lField.set(aObject, aFieldValue.toString());
            } else {
                lField.set(aObject, aFieldValue);
            }
        } catch (IllegalAccessException | ParseException ex) {
            logger.error("Cannot parse value from string - {}", aFieldValue);
        }
    }

    public List<ComboboxResponseDTO> convertToListCombobox(List<Object[]> data) {
        List<ComboboxResponseDTO> comboboxResponse = new ArrayList<>();
        if (data != null && !data.isEmpty()) {
            for (Object[] objects : data) {
                ComboboxResponseDTO cpBrandTelcoDTO = new ComboboxResponseDTO();
                cpBrandTelcoDTO.setValue(objects[0]);
                cpBrandTelcoDTO.setLabel(DataUtil.safeToString(objects[1]));

                comboboxResponse.add(cpBrandTelcoDTO);
            }
        }
        return comboboxResponse;
    }
}
