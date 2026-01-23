
package com.example.shoppingapp.CartScreen;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingapp.AddressBottomSheet.AddressBottomSheet;
import com.example.shoppingapp.AddressBottomSheet.AddressModel;
import com.example.shoppingapp.R;
import com.example.shoppingapp.utils.CartStorage;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    RecyclerView rvCartItems;
    TextView txtItemTotal, txtDeliveryFee, txtTaxes, txtGrandTotal;
    MaterialToolbar toolbarCart;
    LinearLayout layoutSelectAddress;
    TextView txtAddress;

    List<CartModel> cartList = new ArrayList<>();

    int deliveryFee = 30;
    int taxes = 12;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_cart, container, false);

        // Bind Views
        rvCartItems = v.findViewById(R.id.rvCartItems);
        txtItemTotal = v.findViewById(R.id.txtItemTotal);
        txtDeliveryFee = v.findViewById(R.id.txtDeliveryFee);
        txtTaxes = v.findViewById(R.id.txtTaxes);
        txtGrandTotal = v.findViewById(R.id.txtGrandTotal);
        toolbarCart = v.findViewById(R.id.toolbarCart);
        layoutSelectAddress = v.findViewById(R.id.layoutSelectAddress);
        txtAddress = v.findViewById(R.id.txtAddress);


        // ⭐ BACK ARROW CLICK LOGIC
        toolbarCart.setNavigationOnClickListener(v1 -> {
            requireActivity().onBackPressed();
        });
        // ---------- ADDRESS CLICK ----------
        layoutSelectAddress.setOnClickListener(v1 -> openAddressBottomSheet());
        // ⭐ Load cart from SharedPreferences instead of dummy data
        cartList = CartStorage.getCart(getActivity());

        setupCartRecycler();   // Load into RecyclerView
        calculateTotal();      // Calculate total amounts

        return v;
    }

    private void setupCartRecycler() {
        CartAdapter adapter = new CartAdapter(
                getActivity(),
                cartList,
                this::calculateTotal // callback updates totals when qty changes
        );

        rvCartItems.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCartItems.setAdapter(adapter);
    }

    // ⭐ Recalculate total dynamically
    private void calculateTotal() {

        int itemTotal = 0;

        for (CartModel model : cartList) {
            itemTotal += model.getPrice() * model.getQty();
        }

        int grandTotal = itemTotal + deliveryFee + taxes;

        txtItemTotal.setText("₹" + itemTotal);
        txtDeliveryFee.setText("₹" + deliveryFee);
        txtTaxes.setText("₹" + taxes);
        txtGrandTotal.setText("₹" + grandTotal);

    }
    // ================== ADDRESS BOTTOM SHEET ==================

    private void openAddressBottomSheet() {

        List<AddressModel> addressList = new ArrayList<>();
        addressList.add(new AddressModel("Home", "Street 12, Near ABC Chowk, Pune"));
        addressList.add(new AddressModel("Office", "IT Park, Hinjawadi Phase 2"));
        addressList.add(new AddressModel("Other", "Katraj Bus Stand, Pune"));

        AddressBottomSheet sheet = new AddressBottomSheet(
                requireContext(),
                addressList,
                new AddressBottomSheet.OnAddressSelected() {

                    @Override
                    public void onSelect(AddressModel model) {
                        txtAddress.setText("Deliver to: " + model.getTitle());
                    }

                    @Override
                    public void onAddNew() {
                        Toast.makeText(
                                requireContext(),
                                "Add New Address Clicked",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );
        sheet.show();
    }
}
