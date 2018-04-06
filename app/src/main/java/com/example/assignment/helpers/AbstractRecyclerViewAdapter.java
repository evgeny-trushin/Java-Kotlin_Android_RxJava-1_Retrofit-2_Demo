package com.example.assignment.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public abstract class AbstractRecyclerViewAdapter<TypeOfItems, GenericAdapter extends RecyclerView.ViewHolder, ActionType>
    extends AbstractRecyclerListAdapter<TypeOfItems, GenericAdapter, ActionType> {

    private Integer mMaxItemsForRequest = null;
    private Integer mNextPreLoadItem = null;
    private int mPreviousPosition = 0;

    public AbstractRecyclerViewAdapter(Context context, List<TypeOfItems> items) {
        super(items);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericAdapter holder, @SuppressLint("RecyclerView") int position) {
        if (null != mMaxItemsForRequest) {
            if (mNextPreLoadItem == position % mMaxItemsForRequest) {
                if (holder.getAdapterPosition() != RecyclerView.NO_POSITION && mReturnPositionCallbackToReact != null) {
                    mReturnPositionCallbackToReact.call(holder.getAdapterPosition(), mPreviousPosition);
                }
            }
            mPreviousPosition = position;
        }
        try {
            final TypeOfItems item = items.get(position);
            bindItem(holder, item, position);
        } catch (Exception e) {
//            cp.eTestFailedReport(TAG, e);
        }
    }

    public abstract void bindItem(GenericAdapter holder, TypeOfItems item, int position);

    public void setPositionToReact(Integer maxItemsForRequest, Integer nextItemsPreLoadedCounter) {
        this.mMaxItemsForRequest = maxItemsForRequest - 1;
        this.mNextPreLoadItem = nextItemsPreLoadedCounter;
    }
}