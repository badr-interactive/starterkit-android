package com.bi.starterkit.ui.drawer;

import android.content.Context;
import android.graphics.PorterDuff;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bi.starterkit.R;

import static android.support.v4.content.ContextCompat.getColor;

/**
 * starterkit-android
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
 * Created by Roland on 9/18/2017.
 */

public class NavigationItem  {
    Context context;
    private ImageView navIcon;
    private TextView navText;

    public NavigationItem(LinearLayout item, String text, int source, Context context) {
        this.context = context;
        navIcon = (ImageView) item.findViewById(R.id.nav_icon);
        navText = (TextView) item.findViewById(R.id.nav_text);
        navIcon.setImageResource(source);
        navIcon.setColorFilter(getColor(context, R.color.grey), PorterDuff.Mode.MULTIPLY);
        navText.setText(text);
    }

    public void setActive() {
        navIcon.setColorFilter(getColor(context, R.color.green), PorterDuff.Mode.MULTIPLY);
        navText.setTextColor(getColor(context, R.color.green));
    }

    public void setInactive() {
        navIcon.setColorFilter(getColor(context, R.color.grey), PorterDuff.Mode.MULTIPLY);
        navText.setTextColor(getColor(context, R.color.grey));
    }

}

