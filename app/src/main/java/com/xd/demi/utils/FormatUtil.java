package com.xd.demi.utils;

import android.text.TextUtils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created on 16/3/18.
 */
public class FormatUtil {

    private static DecimalFormat getDecimal(String format) {
        return new DecimalFormat(format);
    }

    /**
     * @param time
     * @param format 想要格式化的样式
     * @return
     */
    public static String time2Str(long time, String format) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 将UNIX时间戳转换为日期
     *
     * @param time
     * @param format
     * @return
     */
    public static String formatData(long time, String format) {
        if (time == 0) {
            return "";
        }
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * @param value
     * @param format
     * @return
     */
    public static String double2Str(double value, String format) {
        return getDecimal(format).format(value);
    }

    /**
     * 保留两位小数
     *
     * @param value
     * @return
     */
    public static String double2Str(double value) {
        if (value >= -1 && value <= 1) {
            return String.format("%.2f", value);
        } else {
            return getDecimal("#,###.00").format(value);
        }
    }

    public static String double2Str(double value, String format, RoundingMode mode) {
        DecimalFormat decimalFormat = getDecimal(format);
        if (mode != null) {
            decimalFormat.setRoundingMode(mode);
        }
        return decimalFormat.format(value);
    }

    public static String float2Str(float value, String format, RoundingMode mode) {
        DecimalFormat decimalFormat = getDecimal(format);
        if (mode != null) {
            decimalFormat.setRoundingMode(mode);
        }
        return decimalFormat.format(value);
    }

    public static double formatDouble2Db(double value, String format, RoundingMode mode) {
        DecimalFormat df = getDecimal(format);
        if (mode != null) {
            df.setRoundingMode(mode);
        }
        return Double.parseDouble(df.format(value));
    }

    public static int formatDouble2Int(double value, String format, RoundingMode mode) {
        DecimalFormat df = getDecimal(format);

        if (mode != null) {
            df.setRoundingMode(mode);
        }
        return Integer.parseInt(df.format(value));
    }

    public static String timeToGMT(long dateTime, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        if (dateTime < 10000000000L) {
            dateTime = dateTime * 1000;
        }
        c.setTimeInMillis(dateTime);
        Date date = c.getTime();
        return sdf.format(date);
    }

    public static String format(double value, int decimalDigitCount) {
        return String.format("%.0" + decimalDigitCount + "f", value);
    }


    public static String formatBankCard(String cardNumber) {
        return formatBankCard(cardNumber, false);
    }

    public static String formatBankCard(String cardNumber, boolean encrypt) {
        if (TextUtils.isEmpty(cardNumber)) {
            return cardNumber;
        }
        int length = cardNumber.length();
        if (length < 16 || length > 19) {
            return cardNumber;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(cardNumber.substring(0, 4));
        sb.append(" ");
        if (encrypt) {
            sb.append("**** ****");
        } else {
            sb.append(cardNumber.substring(4, 8));
            sb.append(" ");
            sb.append(cardNumber.substring(8, 12));
        }
        sb.append(" ");
        if (length == 19) {
            if (encrypt) {
                sb.append("****");
            } else {
                sb.append(cardNumber.substring(12, 16));
            }
            sb.append(" ");
            sb.append(cardNumber.substring(16));
        } else {
            sb.append(cardNumber.substring(12));
        }
        return sb.toString();
    }

    public static String encryptPhone(String phoneNumber) {
        return encryptPhone(phoneNumber, 3, 4);
    }

    public static String encryptPhone(String phoneNumber, int keepLengthStart, int keepLengthEnd) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return phoneNumber;
        }
        int length = phoneNumber.length();
        if (length < keepLengthStart + keepLengthEnd) {
            return phoneNumber;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(phoneNumber.substring(0, keepLengthStart));
        for (int i = 0; i < length - 7; i++) {
            sb.append("*");
        }
        sb.append(phoneNumber.substring(length - keepLengthEnd));
        return sb.toString();
    }

    public static String trimZero(String value) {
        if (TextUtils.isEmpty(value) || value.indexOf(".") < 1) {
            return value;
        }
        while (value.endsWith("0")) {
            value = value.substring(0, value.length() - 1);
        }
        if (value.endsWith(".")) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    public static String trimZero(double value) {
        return trimZero(value + "");
    }


    public static String formatNumber(int number){
        if(number >= 10000){
            return String.format("%.1f万", number/10000.0);
        }
        return String.valueOf(number);
    }

    public static String formatDecimal(Double doubleNum) {
        DecimalFormat df = new DecimalFormat("00.00%");
        return String.valueOf(df.format(doubleNum));
    }
}
