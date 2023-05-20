package com.fis.crm.crm_util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDate {
    //Date to String dd/MM/yyyy
    public static String dateToString(Date date, SimpleDateFormat dateFormat){
        return dateFormat.format(date);
    }
}
