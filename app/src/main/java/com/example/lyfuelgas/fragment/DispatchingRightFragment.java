package com.example.lyfuelgas.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lyfuelgas.R;
import com.example.lyfuelgas.adapter.BaseRecyclerAdapter;
import com.example.lyfuelgas.adapter.BaseRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DispatchingRightFragment extends Fragment {
    private static final String TAG="DispatchingRightFragment";
    @BindView(R.id.order_fm_right_rv)
    RecyclerView Dp_fm_right_rv;

    private View view;
    private List<String> DprLeft=new ArrayList<>();
    private BaseRecyclerAdapter<String> DpRightRecyclyer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order_right, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DprLeft.add(new String());
        DprLeft.add(new String());
        DprLeft.add(new String());
        DprLeft.add(new String());
        DprLeft.add(new String());
        DprLeft.add(new String());
        DprLeft.add(new String());
        DprLeft.add(new String());
        DprLeft.add(new String());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        Dp_fm_right_rv.setLayoutManager(layoutManager);
        setOrderFmLeftRv();
    }

    private void setOrderFmLeftRv() {
        DpRightRecyclyer = new BaseRecyclerAdapter<String>(getActivity(), DprLeft, R.layout.rv_dispatching) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, String s, int position) {

            }
        };
        Dp_fm_right_rv.setAdapter(DpRightRecyclyer);
    }
}
