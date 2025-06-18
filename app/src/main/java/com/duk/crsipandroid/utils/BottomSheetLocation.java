package com.duk.crsipandroid.utils;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duk.crsipandroid.R;
import com.duk.crsipandroid.adapters.BottomSheetLocationAdapter;
import com.duk.crsipandroid.mvp.LocationLatLong;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class BottomSheetLocation extends BottomSheetDialogFragment implements BottomSheetLocationAdapter.onItemClickListener {

    List<LocationLatLong> items;
    onItemClickListener listener;

    public interface onItemClickListener{
        void onSheetItemClick(LocationLatLong item, int position);
    }

    public void setItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }

    public BottomSheetLocation(List<LocationLatLong> items){
        this.items = items;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bs_location, container, false);
        RecyclerView rv_location_bs = view.findViewById(R.id.rv_location_bs);
        rv_location_bs.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        BottomSheetLocationAdapter bottomSheetLocationAdapter = new BottomSheetLocationAdapter(items);
        bottomSheetLocationAdapter.setItemClickListener(this);
        rv_location_bs.setAdapter(bottomSheetLocationAdapter);
        return view;
    }

    @Override
    public void onSheetItemClick(LocationLatLong item, int position) {
        listener.onSheetItemClick(item, position);
    }
}
