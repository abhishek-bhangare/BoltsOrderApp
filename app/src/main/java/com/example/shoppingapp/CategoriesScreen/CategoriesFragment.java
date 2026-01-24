
package com.example.shoppingapp.CategoriesScreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppingapp.CategoriesProductDetails.ProductDetailsFragment;
import com.example.shoppingapp.R;

import java.util.ArrayList;

public class CategoriesFragment extends Fragment {

    // ✅ Only Fruits & Vegetables RecyclerView active
    RecyclerView rvCategory1;

    // ❌ Other categories commented (as XML)
    // RecyclerView rvCategory2, rvCategory3, rvCategory4, rvCategory5;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        // ✅ Fruits & Vegetables
        rvCategory1 = view.findViewById(R.id.rvCategory1);



        // ✅ Only Fruits & Vegetables setup
        setupRecycler(rvCategory1, getFruitsVegetables(), "Fruits");


        return view;
    }

    private void setupRecycler(RecyclerView recyclerView,
                               ArrayList<CategoryItemModel> list,
                               String mainCategory) {

        CategoryItemAdapter adapter = new CategoryItemAdapter(
                list,
                getContext(),
                mainCategory,
                (model, categoryName) ->
                        openProductDetails(model, categoryName)
        );

        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false)
        );

        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    // 🔥 Open product details screen
    private void openProductDetails(CategoryItemModel model, String mainCategory) {

        ProductDetailsFragment fragment = new ProductDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putString("name", model.getName());
        bundle.putInt("image", model.getImage());
        bundle.putString("categoryType", mainCategory);
        fragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, fragment)
                .addToBackStack(null)
                .commit();
    }

    // ================== DUMMY DATA ==================

    // ✅ Fruits & Vegetables ONLY
    private ArrayList<CategoryItemModel> getFruitsVegetables() {
        ArrayList<CategoryItemModel> list = new ArrayList<>();
        list.add(new CategoryItemModel(R.drawable.partsimg, "Engine"));
        list.add(new CategoryItemModel(R.drawable.partsimg, "Brakes"));
        list.add(new CategoryItemModel(R.drawable.partsimg, "Suspension"));
        list.add(new CategoryItemModel(R.drawable.partsimg, "Electrical"));
        list.add(new CategoryItemModel(R.drawable.partsimg, "Battery"));
        list.add(new CategoryItemModel(R.drawable.partsimg, "Tyres"));
        list.add(new CategoryItemModel(R.drawable.partsimg, "Oils"));
        list.add(new CategoryItemModel(R.drawable.partsimg, "Filters"));

        return list;
    }


}
