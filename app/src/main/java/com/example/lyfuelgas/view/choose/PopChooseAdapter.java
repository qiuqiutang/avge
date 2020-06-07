package com.example.lyfuelgas.view.choose;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.lyfuelgas.R;
import com.example.lyfuelgas.common.utils.DensityUtils;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PopChooseAdapter extends RecyclerView.Adapter<PopChooseAdapter.ViewHolder> {
    private final List<SingleObject> mList;

    public PopChooseAdapter(List<SingleObject> data) {
        this.mList = data;
    }

    private OnItemClickListener<SingleObject> mListener;

    public void setOnItemClickListener(OnItemClickListener<SingleObject> listener) {
        this.mListener = listener;
    }

    private int drawableLeftResId = 0;

    public void setDrawableLeft(int resId){
        drawableLeftResId = resId;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_pop, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SingleObject obj = mList.get(position);
        holder.tvName.setText(obj.value);
        if(0 != drawableLeftResId){
            Drawable drawableLeft = holder.itemView.getResources().getDrawable(
                    drawableLeftResId);
            holder.tvName.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                    null, null, null);
            holder.tvName.setCompoundDrawablePadding(DensityUtils.dp2px(10));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mListener) {
                    mListener.onItemClick(obj, view, position);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}