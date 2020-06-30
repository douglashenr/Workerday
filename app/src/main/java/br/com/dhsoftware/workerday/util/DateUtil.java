package br.com.dhsoftware.workerday.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import br.com.dhsoftware.workerday.model.Registry;

public class DateUtil {

    private static DateUtil dateUtil;

    public static DateUtil getInstanceDateUtil() {
        if (dateUtil == null)
            return dateUtil = new DateUtil();

        return dateUtil;
    }

    public String convertCalendarToStringDate(Calendar calendar) {
        if (calendar == null)
            return null;
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
        String date = s.format(calendar.getTime());
        return date;
    }

    public String convertCalendarToStringTime(Calendar calendar) {
        if (calendar == null)
            return null;
        SimpleDateFormat s = new SimpleDateFormat("HH:mm");
        String date = s.format(calendar.getTime());
        return date;
    }

    public Calendar convertStringDataAndTimeToCalendar(String date, String time) {
        if (date == null || date.equals(""))
            return null;
        DateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Calendar calendar = Calendar.getInstance(Locale.getDefault());

        try {
            Date dateFormat = simpleDateFormat.parse(date + " " + time);
            calendar.setTime(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //System.out.println(calendar.getTime());

        return calendar;
    }

    public String nameOfDay(Calendar calendar) {
        if (calendar.get(Calendar.DAY_OF_WEEK) == 6)
            return "Sexta-feira";

        if (calendar.get(Calendar.DAY_OF_WEEK) == 2)
            return "Segunda-feira";

        if (calendar.get(Calendar.DAY_OF_WEEK) == 5)
            return "Quinta-feira";

        if (calendar.get(Calendar.DAY_OF_WEEK) == 3)
            return "Terça-feira";

        if (calendar.get(Calendar.DAY_OF_WEEK) == 4)
            return "Quarta-feira";

        if (calendar.get(Calendar.DAY_OF_WEEK) == 1)
            return "Domingo";

        //if(calendar.equals(Calendar.SATURDAY))
        return "Sábado";
    }

    public Calendar convertStringDateToCalendar(String date) {
        if (date == null || date.equals(""))
            return null;
        DateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance(Locale.getDefault());

        try {
            Date dateFormat = simpleDateFormat.parse(date);
            calendar.setTime(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //System.out.println("ConvertStringDataToCalendar: " + calendar.getTime());

        return calendar;
    }

    public Calendar convertStringTimeToCalendar(String time) {
        if (time == null || time.equals(""))
            return null;
        DateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance(Locale.getDefault());

        try {
            Date dateFormat = simpleDateFormat.parse(time);
            calendar.setTime(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //System.out.println("ConvertStringDataToCalendar: " + calendar.getTime());

        return calendar;
    }


    public long calculateTimeFromRegistryToLong(Registry registry) {
        if (registry.getObservation() == enumObservation.ABSENCE)
            return -3;

        if (registry.getObservation() == enumObservation.ATESTADO)
            return -2;

        if (registry.getLeave() == null || registry.getEntrance() == null)
            return -1;

        try {
            long time = convertStringTimeToDateObject(convertCalendarToStringTime(registry.getLeave())).getTime() - convertStringTimeToDateObject(convertCalendarToStringTime(registry.getEntrance())).getTime();
            if (registry.getEntranceLunch() != null && registry.getLeave() != null)
                time -= convertStringTimeToDateObject(convertCalendarToStringTime(registry.getLeaveLunch())).getTime() - convertStringTimeToDateObject(convertCalendarToStringTime(registry.getEntranceLunch())).getTime();

            if (registry.getObservation() == enumObservation.DECLARACAO_DE_HORAS)
                time += convertStringTimeToDateObject(convertCalendarToStringTime(registry.getTimeDeclaration())).getTime();


            return time;
        } catch (Exception e) {
            e.printStackTrace();
            return -4;
        }
    }

    public long calculateExtraTimeFromRegistryToLong(Registry registry) {
        long totalTime = calculateTimeFromRegistryToLong(registry);
        System.out.println(registry);
        if (totalTime < 0)
            return totalTime;

        try {
            totalTime = totalTime - convertStringTimeToDateObject(convertCalendarToStringTime(registry.getRequiredTimeToWork())).getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return -4;
        }

        return totalTime;
    }

    public String calculateExtraTimeFromRegistryToString(Registry registry) {
        System.out.println("RET" + calculateExtraTimeFromRegistryToLong(registry));
        if (calculateExtraTimeFromRegistryToLong(registry) == -4 || calculateExtraTimeFromRegistryToLong(registry) == -3 ||
                calculateExtraTimeFromRegistryToLong(registry) == -2 || calculateExtraTimeFromRegistryToLong(registry) == -1)
            return "00:00h";

        System.out.println("RET" + calculateExtraTimeFromRegistryToLong(registry));

        return getTimeLongToStringFormat(calculateExtraTimeFromRegistryToLong(registry));
    }

    private String getTimeLongToStringFormat(long time) {
        int hour = (int) TimeUnit.MILLISECONDS.toHours(time);
        int minute = (int) ((time / (1000 * 60)) % 60);

        if (minute < 10)
            return hour + ":" + "0" + minute + "h";


        return hour + ":" + minute + "h";
    }

    public String calculateTimeFromRegistryToString(Registry registry) {
        if (calculateTimeFromRegistryToLong(registry) == -1)
            return "00:00h";

        if (calculateTimeFromRegistryToLong(registry) == -2)
            return "00:00h (ATESTADO)";

        if (calculateTimeFromRegistryToLong(registry) == -3)
            return "00:00h (FALTA)";

        if (calculateTimeFromRegistryToLong(registry) == -4) {
            return "00:00h (ERRO)";
        }


        return getTimeLongToStringFormat(calculateTimeFromRegistryToLong(registry));
    }

    public String calculateTotalTimeFromArrayRegistryToString(ArrayList<Registry> registries) {
        int hour = 0, minute = 0;
        for (int count = 0; count < registries.size(); count++) {
            if (calculateTimeFromRegistryToLong(registries.get(count)) != -1 || calculateTimeFromRegistryToLong(registries.get(count)) != -2) {
                hour += (int) TimeUnit.MILLISECONDS.toHours(calculateTimeFromRegistryToLong(registries.get(count)));
                minute += (int) ((calculateTimeFromRegistryToLong(registries.get(count)) / (1000 * 60)) % 60);

                if (minute >= 60) {
                    hour += 1;
                    minute = minute % 60;
                }
            }
        }
        return hour + ":" + minute + "h";
    }

    private Date convertStringTimeToDateObject(String time) throws Exception {

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date;
        return date = format.parse(time);
    }

}
