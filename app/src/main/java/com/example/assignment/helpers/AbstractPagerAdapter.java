package com.example.assignment.helpers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.assignment.i002.view.adapter.SimpleFragment;

import java.util.List;

abstract public class AbstractPagerAdapter<T> extends FragmentPagerAdapter {
    private static final String TAG = AbstractPagerAdapter.class.getSimpleName();
    public static final String BUNDLE_KEY = AbstractPagerAdapter.class.getSimpleName();
    private final Bundle mBundle;
    private final List<T> mList;
    private final Integer size;


    public AbstractPagerAdapter(FragmentManager manager, Integer fragmentNumbers, Bundle bundle) {
        super(manager);
        this.mList = null;
        this.mBundle = bundle;
        this.size = fragmentNumbers;
    }


    public AbstractPagerAdapter(FragmentManager manager, List<T> list, Bundle bundle) {
        super(manager);
        this.mList = list;
        this.mBundle = bundle;
        size = mList.size();
    }

    @Override
    public int getCount() {
        if(null!=mList){
            return mList.size();
        }else {
            return size;
        }
    }

    public abstract Bundle putItemToBundle(Bundle bundle, T item);

    public abstract T getItemFromBundle(Bundle bundle);

    @Override
    @SuppressWarnings("unchecked")
    public Fragment getItem(int position) {
        if(null!=mList) {
            return SimpleFragment.Companion.newInstance(
                putItemToBundle(mBundle, mList.get(position))
            );
        }else{
            return SimpleFragment.Companion.newInstance(
                putItemToBundle(mBundle, (T) String.valueOf(position))
            );
        }
    }
}