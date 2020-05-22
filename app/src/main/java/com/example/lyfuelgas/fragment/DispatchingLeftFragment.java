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
import com.example.lyfuelgas.common.utils.LyLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DispatchingLeftFragment extends Fragment {
    private static final String TAG="DispatchingLeftFragment";

    @BindView(R.id.order_fm_left_rv)
    RecyclerView DpFmLeftRv;

    private View view;
    private List<String> DpLeft=new ArrayList<>();
    private BaseRecyclerAdapter<String> DpLeftRecyclyer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order_left, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DpLeft.add(new String());
        DpLeft.add(new String());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        DpFmLeftRv.setLayoutManager(layoutManager);
        setOrderFmLeftRv();
    }

    private void setOrderFmLeftRv() {
        LyLog.i(TAG,"初始化rv");
        DpLeftRecyclyer = new BaseRecyclerAdapter<String>(getActivity(), DpLeft, R.layout.rv_dispatching) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, String s, int position) {

            }
        };
        DpFmLeftRv.setAdapter(DpLeftRecyclyer);
    }
}
