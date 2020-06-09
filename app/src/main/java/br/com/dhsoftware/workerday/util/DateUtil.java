package br.com.dhsoftware.workerday.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static DateUtil dateUtil;

    public static DateUtil getInstanceDateUtil() {
        if(dateUtil == null)
            return dateUtil = new DateUtil();

        return dateUtil;
    }


    public Calendar convertStringDataAndTimeToCalendar(String date, String time){
        DateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Calendar calendar = Calendar.getInstance(Locale.getDefault());

        try {
            Date dateFormat = simpleDateFormat.parse(date + " " + time);
            calendar.setTime(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        System.out.println(calendar.getTime());

        return calendar;

    }

    public Calendar convertStringDataToCalendar(String date){
        DateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance(Locale.getDefault());

        try {
            Date dateFormat = simpleDateFormat.parse(date);
            calendar.setTime(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        System.out.println(calendar.getTime());

        return calendar;
    }
}
