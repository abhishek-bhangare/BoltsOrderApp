package com.example.shoppingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

/**
 * BaseFragment for fragments that want automatic bottom nav hide/show on scroll.
 * Just extend this fragment and override getScrollableView().
 */
public abstract class BaseFragment extends Fragment {

    /**
     * Child fragments must override this and return their scrollable view
     * (ScrollView or RecyclerView) for automatic scroll detection.
     */
    protected abstract View getScrollableView();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View scrollableView = getScrollableView();
        if (scrollableView != null && getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setupScrollListener(scrollableView);
        }
    }
}
