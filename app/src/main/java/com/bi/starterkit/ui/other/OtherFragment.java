package com.bi.starterkit.ui.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bi.starterkit.R;
import com.bi.starterkit.model.Dessert;
import com.bi.starterkit.ui.home.GridRecyclerAdapter;

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

public class OtherFragment extends Fragment {
    public static final String TAG = OtherFragment.class.getSimpleName();

    RecyclerView rvItem;
    FloatingActionButton fabLock;
    LinearRecyclerAdapter adapter;

    public static OtherFragment newInstance() {
        Bundle args = new Bundle();
        OtherFragment fragment = new OtherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initialize(view);

        return view;
    }

    private void initialize(View view) {
        rvItem = (RecyclerView) view.findViewById(R.id.rv_item);
        fabLock = (FloatingActionButton) view.findViewById(R.id.fab_lock);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //  Use when your list size is constant for better performance
        rvItem.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvItem.setLayoutManager(linearLayoutManager);
        List<Dessert> desserts = Dessert.prepareDesserts(
                getActivity().getResources().getStringArray(R.array.dessert_names),
                getActivity().getResources().getStringArray(R.array.dessert_descriptions));
        adapter = new LinearRecyclerAdapter(desserts);
        rvItem.setAdapter(adapter);
    }
}
