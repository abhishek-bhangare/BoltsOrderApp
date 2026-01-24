
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

        cardList.add(new OrderCardModel("Engine Parts", R.drawable.nutbolt));
        cardList.add(new OrderCardModel("Brake System", R.drawable.nutbolt));
        cardList.add(new OrderCardModel("Suspension", R.drawable.nutbolt));
        cardList.add(new OrderCardModel("Electrical Parts", R.drawable.nutbolt));
        cardList.add(new OrderCardModel("Batteries", R.drawable.nutbolt));
        cardList.add(new OrderCardModel("Tyres & Wheels", R.drawable.nutbolt));

        cardList.add(new OrderCardModel("Oils & Lubricants", R.drawable.nutbolt));
        cardList.add(new OrderCardModel("Filters", R.drawable.nutbolt));
        cardList.add(new OrderCardModel("Lights", R.drawable.nutbolt));
        cardList.add(new OrderCardModel("Body Parts", R.drawable.nutbolt));
        cardList.add(new OrderCardModel("Accessories", R.drawable.nutbolt));
        cardList.add(new OrderCardModel("Tools", R.drawable.nutbolt));

        cardList.add(new OrderCardModel("Cooling System", R.drawable.nutbolt));
        cardList.add(new OrderCardModel("Exhaust System", R.drawable.nutbolt));
        cardList.add(new OrderCardModel("Steering Parts", R.drawable.nutbolt));
        cardList.add(new OrderCardModel("Chain & Sprocket", R.drawable.nutbolt));
        cardList.add(new OrderCardModel("Clutch Parts", R.drawable.nutbolt));
        cardList.add(new OrderCardModel("Spark Plugs", R.drawable.nutbolt));
    }

    // 🔥 FIXED → Now RecyclerView is the scrollable view
    @Override
    protected View getScrollableView() {
        return recyclerView;
    }
}

