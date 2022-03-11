package com.jitendra.homehelp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String getTodayAsStringByGivenFormat(String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String strDate = formatter.format(new Date());
        return strDate;
    }
}
