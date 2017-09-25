package com.bi.starterkit.ui.other;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bi.starterkit.R;
import com.bi.starterkit.model.Dessert;
import com.bi.starterkit.utils.view.ColorGenerator;
import com.bi.starterkit.utils.view.TextDrawable;

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

public class LinearRecyclerAdapter extends RecyclerView.Adapter<LinearRecyclerAdapter.ViewHolder> {
    private List<Dessert> desserts = new ArrayList<>();
    private String letter;
    private ColorGenerator generator = ColorGenerator.MATERIAL;

    public LinearRecyclerAdapter(List<Dessert> desserts) {
        this.desserts = desserts;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_linear_recycler, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(desserts.get(position).getName());
        holder.description.setText(desserts.get(position).getDescription());
        //        Get the first letter of list item
        letter = String.valueOf(desserts.get(position).getName().charAt(0));
        //        Create a new TextDrawable for our image's background
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter, generator.getRandomColor());

        holder.letter.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return desserts == null ? 0 : desserts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        AppCompatImageView letter;

        ViewHolder(View itemView) {
            super(itemView);
            letter = (AppCompatImageView) itemView.findViewById(R.id.iv_image);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            description = (TextView) itemView.findViewById(R.id.tv_subtitle);
        }
    }

}


