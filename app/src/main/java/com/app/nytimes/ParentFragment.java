package com.app.nytimes;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * This is a ParentFragment: All fragment in this app should extend this fragment so that code
 * structure will remain same.
 *
 * This fragment contains all basic methods and callback commonly used.
 *
 * You can override method onActivityCreated(@Nullable Bundle savedInstanceState) {} but remember to
 * initialize activity and call setData()
 * */

public abstract class ParentFragment extends Fragment {
    protected Context context;
    protected View fView;
    protected Activity activity;
    protected Callbacks parentCallback;

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container,
                                   final Bundle savedInstanceState) {
        int layoutResId = getLayoutResId();

        if (layoutResId != 0) {
            fView = inflater.inflate(layoutResId, container, false);
            initializeViews();
            initializeListener();
        }
        return fView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        setData();
    }

    protected abstract int getLayoutResId();

    protected abstract void initializeViews();

    protected abstract void initializeListener();

    protected abstract void setData();

    protected abstract void initializeCallbacks(Context context);

    protected abstract void destroyCallbacks();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof Callbacks) {
            parentCallback = (Callbacks) context;
        }
        initializeCallbacks(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parentCallback = null;
        destroyCallbacks();
    }

    public interface Callbacks {
        void showProgressDialog(boolean cancelable);
        void hideProgressDialog();
    }

}
