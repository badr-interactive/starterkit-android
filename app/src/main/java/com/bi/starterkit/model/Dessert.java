package com.bi.starterkit.model;

import java.util.ArrayList;
import java.util.List;

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

public class Dessert {

    private String name;
    private String description;
    private String firstLetter;

    public Dessert(String name, String description) {
        this.name = name;
        this.firstLetter = String.valueOf(name.charAt(0));
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public static List<Dessert> prepareDesserts(String[] names, String[] descriptions) {
        List<Dessert> desserts = new ArrayList<>(names.length);

        for (int i = 0; i < names.length; i++) {
            Dessert dessert = new Dessert(names[i], descriptions[i]);
            desserts.add(dessert);
        }

        return desserts;
    }
}
