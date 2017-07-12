package com.bi.starterkit.utils;

import android.util.Patterns;
import android.widget.EditText;

import com.bi.starterkit.app.Config;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * android-starterkit
 *
 * This source code is part of intellectual property rights of PT. Badr Interactive.
 * Any use of this source code without permission from PT. Badr Interactive is prohibitted
 * and violated the company policy. Any violation will be dealt in accordance with the
 * existing mechanism in the company.
 *
 * Source code ini merupakan bagian dari hak kekayaan intelektual PT. Badr Interactive.
 * Tidak diizinkan untuk menggunakan source code ini tanpa seizin PT. Badr Interactive.
 * Setiap pelanggaran yang dilakukan akan ditindak dengan mekanisme yang berlaku di perusahaan.
 *
 * Created by Mohamad on 11-Aug-16.
 */
public class FormHelper {
    public static boolean isEmptyEditor(EditText et) {
        return et.getText().toString().trim().equals("");
    }

    public static boolean isEmptyEditor(EditText et, String errorMessage) {
        if (et.getText().toString().trim().equals("")) {
            et.setError(errorMessage);
            et.requestFocus();
            return true;
        }
        return false;
    }

    public static boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static Date getDateFromString(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getDateFromString(String date) {
        return getDateFromString(date, Config.SERVER_DATE_FORMAT);
    }

    public static String convertDateFromServer(String date, String format) {
        return getStringFormatDate(getDateFromString(date), format);
    }

    public static String getStringFormatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    //price format using italy locale because same preferences
    public static String formatPrice(int ff) {
        return "Rp. " + NumberFormat.getInstance(Locale.ITALY).format(ff);
    }

    public static String formatPrice(double ff) {
        return "Rp. " + NumberFormat.getInstance(Locale.ITALY).format(ff);
    }

    public static int deformatPrice(String leS) {
        return Integer.parseInt(leS.substring(3).replace(".", ""));
    }
}
