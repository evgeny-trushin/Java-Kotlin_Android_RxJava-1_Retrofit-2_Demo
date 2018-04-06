package com.example.assignment.helpers;

import android.support.v7.widget.RecyclerView;
import java.util.List;

/**
 * @param <ItemType>
 * @param <ViewHolderType>
 */
public abstract class AbstractRecyclerListAdapter<ItemType, ViewHolderType extends RecyclerView.ViewHolder,ActionType> extends RecyclerView.Adapter<ViewHolderType> {
    protected String TAG = this.getClass().getName();
    List<ItemType> items;
    ReturnPositionCallbackToReact mReturnPositionCallbackToReact;
    private ReturnPositionCallbackForAction mCallbackForAction;

    AbstractRecyclerListAdapter(List<ItemType> items) {
        this.items = items;
    }

    public List<ItemType> getItems() {
        return items;
    }

    public void addItems(List<ItemType> itemsToAdd) {
        this.items.addAll(itemsToAdd);
    }

    public void setItems(List<ItemType> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setCallbackForActions(ReturnPositionCallbackForAction callback) {
        this.mCallbackForAction = callback;
    }

    public void setPositionToReactCallback(ReturnPositionCallbackToReact mReturnPositionCallback){
        this.mReturnPositionCallbackToReact = mReturnPositionCallback;
    }

    public interface ReturnPositionCallbackForAction<ActionType> {
        void call(int position, ActionType action);
    }

    public interface ReturnPositionCallbackToReact {
        void call(int position, int previousPosition);
    }
}