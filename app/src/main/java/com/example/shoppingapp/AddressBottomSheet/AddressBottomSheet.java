package com.example.shoppingapp.AddressBottomSheet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class AddressBottomSheet extends BottomSheetDialog {

    private List<AddressModel> addressList;
    private OnAddressSelected listener;

    public interface OnAddressSelected {
        void onSelect(AddressModel model);
        void onAddNew();
    }

    public AddressBottomSheet(@NonNull Context context, List<AddressModel> list,
                              OnAddressSelected listener) {
        super(context);
        this.addressList = list;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.bottomsheet_address, null);
        setContentView(view);

        // RecyclerView setup
        RecyclerView rv = view.findViewById(R.id.rvAddressList);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new AddressAdapter(addressList, model -> {
            listener.onSelect(model);
            dismiss();
        }));

        // Add New Address
        view.findViewById(R.id.txtAddNewAddress).setOnClickListener(v -> {
            listener.onAddNew();
            dismiss();
        });

        // Close button
        view.findViewById(R.id.btnClose).setOnClickListener(v -> dismiss());

        // ⭐ 70% Height Bottomsheet ⭐
        View sheet = findViewById(com.google.android.material.R.id.design_bottom_sheet);
        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(sheet);

        int seventyPercent = (int) (getContext().getResources()
                .getDisplayMetrics().heightPixels * 0.70);

        sheet.getLayoutParams().height = seventyPercent;

        behavior.setPeekHeight(seventyPercent);
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        behavior.setSkipCollapsed(false);
    }
}
