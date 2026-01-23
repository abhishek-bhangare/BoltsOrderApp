
package com.example.shoppingapp.OrderScreen;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppingapp.BaseFragment;
import com.example.shoppingapp.ProfileScreen.ProfileActivity;
import com.example.shoppingapp.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class OrderFragment extends BaseFragment {
    private ShapeableImageView profileIcon;
    private RecyclerView recyclerView;
    private OrderCardsAdapter adapter;
    private ArrayList<OrderCardModel> cardList;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public OrderFragment() {}

    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileIcon = view.findViewById(R.id.profileIcon);
        recyclerView = view.findViewById(R.id.cardRecyclerView);
       // Profile
        profileIcon.setOnClickListener(v -> startActivity(new Intent(getActivity(), ProfileActivity.class)));
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));

        loadCardData();

        adapter = new OrderCardsAdapter(requireContext(), cardList);
        recyclerView.setAdapter(adapter);
    }

    private void loadCardData() {
        cardList = new ArrayList<>();

        cardList.add(new OrderCardModel("Fruits", R.drawable.driedfruits));
        cardList.add(new OrderCardModel("Vegetables", R.drawable.veg1));
        cardList.add(new OrderCardModel("Snacks", R.drawable.fruit));
        cardList.add(new OrderCardModel("Dairy", R.drawable.bread));
        cardList.add(new OrderCardModel("Bakery", R.drawable.sample_img));
        cardList.add(new OrderCardModel("Beverages", R.drawable.veg1));

        cardList.add(new OrderCardModel("Oil & Ghee", R.drawable.fruit));
        cardList.add(new OrderCardModel("Baby Care", R.drawable.sample_img));
        cardList.add(new OrderCardModel("Household Items", R.drawable.bread));
        cardList.add(new OrderCardModel("Frozen Food", R.drawable.fruit));
        cardList.add(new OrderCardModel("Masala & Spices", R.drawable.driedfruits));
        cardList.add(new OrderCardModel("Chocolates", R.drawable.veg1));

        cardList.add(new OrderCardModel("Pet Supplies", R.drawable.driedfruits));
        cardList.add(new OrderCardModel("Cleaning Tools", R.drawable.fruit));
        cardList.add(new OrderCardModel("Dry Fruits", R.drawable.bread));
        cardList.add(new OrderCardModel("Beauty & Hygiene", R.drawable.sample_img));
        cardList.add(new OrderCardModel("Personal Care", R.drawable.fruit));
        cardList.add(new OrderCardModel("Kitchen Items", R.drawable.veg1));
    }

    // 🔥 FIXED → Now RecyclerView is the scrollable view
    @Override
    protected View getScrollableView() {
        return recyclerView;
    }
}

