package com.bi.starterkit.utils;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.util.UUID;

/**
 * android-starterkit
 * <p>
 * This source code is part of intellectual property rights of PT. Badr Interactive.
 * Any use of this source code without permission from PT. Badr Interactive is prohibitted
 * and violated the company policy. Any violation will be dealt in accordance with the
 * existing mechanism in the company.
 * <p>
 * Source code ini merupakan bagian dari hak kekayaan intelektual PT. Badr Interactive.
 * Tidak diizinkan untuk menggunakan source code ini tanpa seizin PT. Badr Interactive.
 * Setiap pelanggaran yang dilakukan akan ditindak dengan mekanisme yang berlaku di perusahaan.
 * <p>
 * Created by Roland on 7/6/2017.
 */

public class Utils {

    public static String getDeviceUuId(Context context) {
        String UUID = "";
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (tm != null) {
            final String tmDevice, tmSerial, androidId;
            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
            androidId = "" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

            java.util.UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
            UUID = deviceUuid.toString();
            return UUID;
        }
        return UUID;
    }

}
