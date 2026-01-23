
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

    RecyclerView rvCategory1, rvCategory2, rvCategory3, rvCategory4, rvCategory5;

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

        rvCategory1 = view.findViewById(R.id.rvCategory1);
        rvCategory2 = view.findViewById(R.id.rvCategory2);
        rvCategory3 = view.findViewById(R.id.rvCategory3);
        rvCategory4 = view.findViewById(R.id.rvCategory4);
        rvCategory5 = view.findViewById(R.id.rvCategory5);

        // Pass MAIN CATEGORY NAME
        setupRecycler(rvCategory1, getFruitsVegetables(), "Fruits");
        setupRecycler(rvCategory2, getBakeryDairy(), "Bakery");
        setupRecycler(rvCategory3, getSnacks(), "Snacks");
        setupRecycler(rvCategory4, getHousehold(), "Household");
        setupRecycler(rvCategory5, getBeauty(), "Beauty");

        return view;
    }

    private void setupRecycler(RecyclerView recyclerView, ArrayList<CategoryItemModel> list, String mainCategory) {

        CategoryItemAdapter adapter = new CategoryItemAdapter(
                list,
                getContext(),
                mainCategory,
                (model, categoryName) -> openProductDetails(model, categoryName)   // Send both model + mainCategory
        );

        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    // 🔥 Open product details screen WITH category type
    private void openProductDetails(CategoryItemModel model, String mainCategory) {

        ProductDetailsFragment fragment = new ProductDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putString("name", model.getName());
        bundle.putInt("image", model.getImage());
        bundle.putString("categoryType", mainCategory); // ⭐ important for left subcategories
        fragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, fragment)
                .addToBackStack(null)
                .commit();
    }

    // ---- Dummy data lists ----

    private ArrayList<CategoryItemModel> getFruitsVegetables() {
        ArrayList<CategoryItemModel> list = new ArrayList<>();
        list.add(new CategoryItemModel(R.drawable.vegetables1, "Tomato"));
        list.add(new CategoryItemModel(R.drawable.vegetables1, "Potato"));
        list.add(new CategoryItemModel(R.drawable.vegetables1, "Onion"));
        list.add(new CategoryItemModel(R.drawable.vegetables1, "Carrot"));
        list.add(new CategoryItemModel(R.drawable.vegetables1, "Apple"));
        list.add(new CategoryItemModel(R.drawable.vegetables1, "Banana"));
        list.add(new CategoryItemModel(R.drawable.vegetables1, "Cabbage"));
        list.add(new CategoryItemModel(R.drawable.vegetables1, "Green Peas"));
        return list;
    }

    private ArrayList<CategoryItemModel> getBakeryDairy() {
        ArrayList<CategoryItemModel> list = new ArrayList<>();
        list.add(new CategoryItemModel(R.drawable.bread, "Milk"));
        list.add(new CategoryItemModel(R.drawable.bread, "Cheese"));
        list.add(new CategoryItemModel(R.drawable.bread, "Paneer"));
        list.add(new CategoryItemModel(R.drawable.bread, "Bread"));
        list.add(new CategoryItemModel(R.drawable.bread, "Brown Bread"));
        list.add(new CategoryItemModel(R.drawable.bread, "Butter"));
        list.add(new CategoryItemModel(R.drawable.bread, "Cup Cakes"));
        list.add(new CategoryItemModel(R.drawable.bread, "Cookies"));
        return list;
    }

    private ArrayList<CategoryItemModel> getSnacks() {
        ArrayList<CategoryItemModel> list = new ArrayList<>();
        list.add(new CategoryItemModel(R.drawable.fruit, "Chips"));
        list.add(new CategoryItemModel(R.drawable.fruit, "Nachos"));
        list.add(new CategoryItemModel(R.drawable.fruit, "Popcorn"));
        list.add(new CategoryItemModel(R.drawable.fruit, "Biscuits"));
        list.add(new CategoryItemModel(R.drawable.fruit, "Chocolate"));
        list.add(new CategoryItemModel(R.drawable.fruit, "Noodles"));
        list.add(new CategoryItemModel(R.drawable.fruit, "Dry Fruits"));
        list.add(new CategoryItemModel(R.drawable.fruit, "Sweets"));
        return list;
    }

    private ArrayList<CategoryItemModel> getHousehold() {
        ArrayList<CategoryItemModel> list = new ArrayList<>();
        list.add(new CategoryItemModel(R.drawable.vegetables1, "Detergent"));
        list.add(new CategoryItemModel(R.drawable.vegetables1, "Dishwash"));
        list.add(new CategoryItemModel(R.drawable.vegetables1, "Toilet Cleaner"));
        list.add(new CategoryItemModel(R.drawable.vegetables1, "Floor Cleaner"));
        list.add(new CategoryItemModel(R.drawable.vegetables1, "Tissue Paper"));
        list.add(new CategoryItemModel(R.drawable.vegetables1, "Garbage Bags"));
        list.add(new CategoryItemModel(R.drawable.vegetables1, "Fresheners"));
        list.add(new CategoryItemModel(R.drawable.vegetables1, "Bath Soap"));
        return list;
    }

    private ArrayList<CategoryItemModel> getBeauty() {
        ArrayList<CategoryItemModel> list = new ArrayList<>();
        list.add(new CategoryItemModel(R.drawable.bread, "Shampoo"));
        list.add(new CategoryItemModel(R.drawable.bread, "Face Wash"));
        list.add(new CategoryItemModel(R.drawable.bread, "Face Cream"));
        list.add(new CategoryItemModel(R.drawable.bread, "Body Lotion"));
        list.add(new CategoryItemModel(R.drawable.bread, "Deodorant"));
        list.add(new CategoryItemModel(R.drawable.bread, "Hair Oil"));
        list.add(new CategoryItemModel(R.drawable.bread, "Soap"));
        list.add(new CategoryItemModel(R.drawable.bread, "Hand Wash"));
        return list;
    }
}
