////
////package com.example.shoppingapp.CategoriesProductDetails;
////
////import android.os.Bundle;
////
////import androidx.fragment.app.Fragment;
////import androidx.recyclerview.widget.GridLayoutManager;
////import androidx.recyclerview.widget.LinearLayoutManager;
////import androidx.recyclerview.widget.RecyclerView;
////
////import android.view.LayoutInflater;
////import android.view.View;
////import android.view.ViewGroup;
////import android.widget.ImageView;
////import android.widget.LinearLayout;
////import android.widget.TextView;
////
////import com.example.shoppingapp.CartScreen.CartFragment;
////import com.example.shoppingapp.R;
////import com.example.shoppingapp.utils.CartStorage;
////
////import java.util.ArrayList;
////
////public class ProductDetailsFragment extends Fragment {
////
////    TextView productName, productDesc, txtCartCount;
////    LinearLayout btnViewCart;
////
////    RecyclerView rvLeftCategories, rvRightCategories, rvProducts;
////
////    String categoryType = "";
////
////    public ProductDetailsFragment() {}
////
////    @Override
////    public View onCreateView(LayoutInflater inflater, ViewGroup container,
////                             Bundle savedInstanceState) {
////
////        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
////
////        // FIND VIEWS
////        productName = view.findViewById(R.id.productName);
////        productDesc = view.findViewById(R.id.productDesc);
////
////        rvLeftCategories = view.findViewById(R.id.rvLeftCategories);
////        rvRightCategories = view.findViewById(R.id.rvRightCategories);
////        rvProducts = view.findViewById(R.id.rvProducts);
////
////        // Floating Cart Button
////        btnViewCart = view.findViewById(R.id.btnViewCart);
////        txtCartCount = view.findViewById(R.id.txtCartCount);
////
////        // Navigate to cart page
////        btnViewCart.setOnClickListener(v -> {
////            requireActivity().getSupportFragmentManager()
////                    .beginTransaction()
////                    .replace(R.id.main, new CartFragment())
////                    .addToBackStack(null)
////                    .commit();
////        });
////
////        // Back Button
////        ImageView btnBack = view.findViewById(R.id.btnBack);
////        btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
////
////
////        // RECEIVE DATA
////        Bundle bundle = getArguments();
////        if (bundle != null) {
////            categoryType = bundle.getString("categoryType", "");
////            productName.setText(bundle.getString("name"));
////        }
////
////        // SET LEFT SIDE
////        setupLeftCategories(categoryType);
////
////        // Update cart button on start
////        updateCartButton();
////
////        return view;
////    }
////
////    // ---------------------- Update Go To Cart Button ----------------------
////    private void updateCartButton() {
////        int count = CartStorage.getTotalItems(getContext());
////
////        if (count > 0) {
////            btnViewCart.setVisibility(View.VISIBLE);
////            txtCartCount.setText(count + " items | Go to Cart");
////        } else {
////            btnViewCart.setVisibility(View.GONE);
////        }
////    }
////
////    // ========================= LEFT SIDE CATEGORIES ===========================
////    private void setupLeftCategories(String categoryType) {
////
////        ArrayList<LeftCategoryModel> list = new ArrayList<>();
////
////        switch (categoryType) {
////
////            case "Fruits":
////                list.add(new LeftCategoryModel(R.drawable.fruit, "Fresh Fruits"));
////                list.add(new LeftCategoryModel(R.drawable.fruit, "Vegetables"));
////                list.add(new LeftCategoryModel(R.drawable.fruit, "Organic"));
////                list.add(new LeftCategoryModel(R.drawable.fruit, "Exotic"));
////                list.add(new LeftCategoryModel(R.drawable.fruit, "Leafy Greens"));
////                break;
////
////            case "Bakery":
////                list.add(new LeftCategoryModel(R.drawable.bread, "Bread"));
////                list.add(new LeftCategoryModel(R.drawable.bread, "Cakes"));
////                list.add(new LeftCategoryModel(R.drawable.bread, "Dairy"));
////                list.add(new LeftCategoryModel(R.drawable.bread, "Butter & Cheese"));
////                break;
////
////            default:
////                list.add(new LeftCategoryModel(R.drawable.fruit, "Category 1"));
////                list.add(new LeftCategoryModel(R.drawable.fruit, "Category 2"));
////                break;
////        }
////
////        LeftCategoryAdapter adapter = new LeftCategoryAdapter(
////                list, getContext(), this::onLeftCategoryClick
////        );
////
////        rvLeftCategories.setLayoutManager(new LinearLayoutManager(getContext()));
////        rvLeftCategories.setAdapter(adapter);
////
////        if (list.size() > 0) onLeftCategoryClick(list.get(0).getName());
////    }
////
////    // ================= LEFT CATEGORY CLICK ====================
////    private void onLeftCategoryClick(String categoryName) {
////
////        productName.setText(categoryName);
////        productDesc.setText("Explore best items in " + categoryName);
////
////        setupRightCategories(categoryName);
////        loadProducts(categoryName);
////    }
////
////    // ================= RIGHT SUB CATEGORY ====================
////    private void setupRightCategories(String parentCategory) {
////
////        ArrayList<RightCategoryModel> list = new ArrayList<>();
////
////        switch (parentCategory) {
////            case "Fresh Fruits":
////                list.add(new RightCategoryModel(R.drawable.fruit, "Apple"));
////                list.add(new RightCategoryModel(R.drawable.fruit, "Banana"));
////                break;
////        }
////
////        RightCategoryAdapter adapter = new RightCategoryAdapter(
////                list, getContext(), this::onRightCategoryClick
////        );
////
////        rvRightCategories.setLayoutManager(
////                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
////        );
////        rvRightCategories.setAdapter(adapter);
////    }
////
////    private void onRightCategoryClick(String subCategory) {
////        productName.setText(subCategory);
////        productDesc.setText("Best quality " + subCategory);
////
////        loadProducts(subCategory);
////    }
////
////    // ========================= LOAD PRODUCTS ==========================
////    private void loadProducts(String category) {
////
////        ArrayList<ProductModel> list = new ArrayList<>();
////
////        switch (category) {
////
////            case "Apple":
////                list.add(new ProductModel("APPLE_1KG", R.drawable.veg_img, "Apple 1kg", "₹120"));
////                list.add(new ProductModel("APPLE_500G", R.drawable.veg_img, "Apple 500g", "₹70"));
////                break;
////
////            case "Banana":
////                list.add(new ProductModel("BANANA_12", R.drawable.veg_img, "Banana 1 dozen", "₹50"));
////                list.add(new ProductModel("BANANA_6", R.drawable.veg_img, "Banana 6 pcs", "₹30"));
////                break;
////
////            default:
////                list.add(new ProductModel(category + "_A", R.drawable.veg_img, "Item A", "₹80"));
////                list.add(new ProductModel(category + "_B", R.drawable.veg_img, "Item B", "₹120"));
////                break;
////        }
////
////        // Pass callback to update cart button
////        ProductAdapter adapter = new ProductAdapter(list, getContext(), this::updateCartButton);
////
////        rvProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));
////        rvProducts.setAdapter(adapter);
////    }
////}
////
//
//package com.example.shoppingapp.CategoriesProductDetails;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.example.shoppingapp.CartScreen.CartFragment;
//import com.example.shoppingapp.R;
//import com.example.shoppingapp.utils.CartStorage;
//
//import java.util.ArrayList;
//
//public class ProductDetailsFragment extends Fragment {
//
//    TextView productName, productDesc, txtCartCount;
//    LinearLayout btnViewCart;
//
//    RecyclerView rvLeftCategories, rvProducts;
//
//    String categoryType = "";
//
//    public ProductDetailsFragment() {}
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
//
//        // FIND VIEWS
//        productName = view.findViewById(R.id.productName);
//        productDesc = view.findViewById(R.id.productDesc);
//
//        rvLeftCategories = view.findViewById(R.id.rvLeftCategories);
//        rvProducts = view.findViewById(R.id.rvProducts);
//
//        // ❌ Right category RecyclerView not used
//        // rvRightCategories = view.findViewById(R.id.rvRightCategories);
//
//        // Floating Cart Button
//        btnViewCart = view.findViewById(R.id.btnViewCart);
//        txtCartCount = view.findViewById(R.id.txtCartCount);
//
//        // Go to Cart
//        btnViewCart.setOnClickListener(v -> {
//            requireActivity().getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.main, new CartFragment())
//                    .addToBackStack(null)
//                    .commit();
//        });
//
//        // Back Button
//        ImageView btnBack = view.findViewById(R.id.btnBack);
//        btnBack.setOnClickListener(v ->
//                requireActivity().getSupportFragmentManager().popBackStack()
//        );
//
//        // RECEIVE DATA
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            categoryType = bundle.getString("categoryType", "");
//            productName.setText(bundle.getString("name"));
//        }
//
//        // ✅ ONLY FIRST CATEGORY DETAILS
//        setupLeftCategories();
//
//        updateCartButton();
//
//        return view;
//    }
//
//    // ---------------------- CART BUTTON ----------------------
//    private void updateCartButton() {
//        int count = CartStorage.getTotalItems(getContext());
//
//        if (count > 0) {
//            btnViewCart.setVisibility(View.VISIBLE);
//            txtCartCount.setText(count + " items | Go to Cart");
//        } else {
//            btnViewCart.setVisibility(View.GONE);
//        }
//    }
//
//    // ================= LEFT CATEGORIES (ONLY ONE SET) =================
//    private void setupLeftCategories() {
//
//        ArrayList<LeftCategoryModel> list = new ArrayList<>();
//
//        // ✅ FIRST CATEGORY ONLY (Vehicle Parts / Main Category)
//        list.add(new LeftCategoryModel(R.drawable.partsimg, "Engine"));
//        list.add(new LeftCategoryModel(R.drawable.partsimg, "Brakes"));
//        list.add(new LeftCategoryModel(R.drawable.partsimg, "Suspension"));
//        list.add(new LeftCategoryModel(R.drawable.partsimg, "Electrical"));
//        list.add(new LeftCategoryModel(R.drawable.partsimg, "Battery"));
//        list.add(new LeftCategoryModel(R.drawable.partsimg, "Tyres"));
//
//        LeftCategoryAdapter adapter = new LeftCategoryAdapter(
//                list, getContext(), this::onLeftCategoryClick
//        );
//
//        rvLeftCategories.setLayoutManager(new LinearLayoutManager(getContext()));
//        rvLeftCategories.setAdapter(adapter);
//
//        // Auto select first
//        if (!list.isEmpty()) {
//            onLeftCategoryClick(list.get(0).getName());
//        }
//    }
//
//    // ================= LEFT CATEGORY CLICK =================
//    private void onLeftCategoryClick(String categoryName) {
//
//        productName.setText(categoryName);
//        productDesc.setText("Explore best " + categoryName + " parts");
//
//        loadProducts(categoryName);
//    }
//
//    // ================= LOAD PRODUCTS (SIMPLE) =================
//    private void loadProducts(String category) {
//
//        ArrayList<ProductModel> list = new ArrayList<>();
//
//        // ✅ Sample products (can be API later)
//        list.add(new ProductModel(
//                category + "_1",
//                R.drawable.partsimg,
//                category + "  1",
//                "₹999"
//        ));
//
//        list.add(new ProductModel(
//                category + "_2",
//                R.drawable.partsimg,
//                category + "  2",
//                "₹1499"
//        ));
//        list.add(new ProductModel(
//                category + "_3",
//                R.drawable.partsimg,
//                category + "  3",
//                "₹500"
//        ));
//
//        list.add(new ProductModel(
//                category + "_4",
//                R.drawable.partsimg,
//                category + "  4",
//                "₹499"
//        ));
//        list.add(new ProductModel(
//                category + "_5",
//                R.drawable.partsimg,
//                category + "  5",
//                "₹900"
//        ));
//
//        list.add(new ProductModel(
//                category + "_6",
//                R.drawable.partsimg,
//                category + "  6",
//                "₹799"
//        ));
//        list.add(new ProductModel(
//                category + "_7",
//                R.drawable.partsimg,
//                category + "  7",
//                "₹399"
//        ));
//
//        list.add(new ProductModel(
//                category + "_8",
//                R.drawable.partsimg,
//                category + "  8",
//                "₹499"
//        ));
//        list.add(new ProductModel(
//                category + "_9",
//                R.drawable.partsimg,
//                category + "  9",
//                "₹999"
//        ));
//
//        list.add(new ProductModel(
//                category + "_10",
//                R.drawable.partsimg,
//                category + "  10",
//                "₹599"
//        ));
//
//        ProductAdapter adapter =
//                new ProductAdapter(list, getContext(), this::updateCartButton);
//
//        rvProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        rvProducts.setAdapter(adapter);
//    }
//}
