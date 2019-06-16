package com.app.nytimes;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.app.nytimes.utils.ProgressDialog;

/**
 * This is a parent activity: All activity in this app should extend this activity so that code
 * structure of the app remain same.
 * <p>
 * This activity contains all the basic method include fragment operations
 * <p>
 * This activity is designed to used fragment.
 * <p>
 * To use this activity, you must have initialize fragmentContainerId in method
 * setFragmentContainerId()
 */

public abstract class ParentActivity extends AppCompatActivity implements ParentFragment.Callbacks {

    protected AppCompatActivity activity;
    protected Context context;

    protected int fragmentContainerId = 0;
    protected Dialog dialog;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        context = this;
        int layoutResId = getLayoutResId();
        if (layoutResId != 0) {
            setContentView(getLayoutResId());
            setFragmentContainerId();
            initializeViews();
            initializeListener();
            setData();
            setBackStackChangeListener();
        }
    }

    protected abstract int getLayoutResId();

    protected abstract void setFragmentContainerId();

    protected abstract void initializeViews();

    protected abstract void initializeListener();

    protected abstract void setData();

    protected abstract void onFragmentChange(Fragment fragment);

    private void setBackStackChangeListener() {
        final FragmentManager fm = getSupportFragmentManager();
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                if (fm.getBackStackEntryCount() > 1) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }

                onFragmentChange(getCurrentFragment());
            }
        });
    }

    protected Fragment getCurrentFragment() {
        FragmentManager fm = getSupportFragmentManager();
        return fm.findFragmentById(fragmentContainerId);
    }

    protected void setFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right_slow, R.anim.exit_to_left_slow,
                R.anim.enter_from_left_slow, R.anim.exit_to_right_slow);
        transaction.replace(fragmentContainerId, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commitAllowingStateLoss();
    }

    protected void addFragment(Fragment fragment) {
        addFragment(fragment, null);
    }

    protected void addFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right_slow, R.anim.exit_to_left_slow,
                R.anim.enter_from_left_slow, R.anim.exit_to_right_slow);
        transaction.add(fragmentContainerId, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commitAllowingStateLoss();
    }

    protected void setFragment(Fragment fragment) {
        setFragment(fragment, null);
    }

    protected void popAllFragments() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    protected void popCurrentFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();

        if (fm.getBackStackEntryCount() > 1) {
            super.onBackPressed();
        } else {
            finish();
        }
    }

    @Override
    public void showProgressDialog(boolean cancelable) {
        dialog = ProgressDialog.getProgressDialog(context, cancelable);
        dialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            if (item.getItemId() == android.R.id.home ) {
                onBackPressed();
                return true;
            } else {
                return super.onOptionsItemSelected(item);
            }
        } catch (IllegalStateException e) {
            return super.onOptionsItemSelected(item);
        }
    }
}
