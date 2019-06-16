package com.app.nytimes;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.nytimes.apis.MyObservable;
import com.app.nytimes.data.ArticleDataModel;
import com.app.nytimes.fragments.NewsArticleDetailsFragment;
import com.app.nytimes.fragments.NewsListFragment;
import com.app.nytimes.utils.AppHelper;

import io.reactivex.Observer;

public class MainActivity extends ParentActivity implements
        NewsListFragment.Callback {

    Observer newsList;
    TextView toolbar_title;
    ImageButton btn_refresh, iv_select_category;
    Toolbar toolbar_main_activity;

    String selectedSection = "home";

    MyObservable.NetworkCallback callback;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setFragmentContainerId() {
        fragmentContainerId = R.id.container;
    }

    @Override
    protected void initializeViews() {
        toolbar_title = findViewById(R.id.toolbar_title);
        btn_refresh = findViewById(R.id.btn_refresh);
        toolbar_main_activity = findViewById(R.id.toolbar_main_activity);
        iv_select_category = findViewById(R.id.iv_select_category);
        setSupportActionBar(toolbar_main_activity);
    }

    @Override
    protected void initializeListener() {
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog(false);
                newsList = MyObservable.getArticleObservable(selectedSection)
                        .subscribeWith(MyObservable.getObserver(callback));
            }
        });

        iv_select_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppHelper.showMsg(context, "Not implemented yet");
            }
        });


    }

    @Override
    protected void setData() {
        setFragment(NewsListFragment.newInstance(), NewsListFragment.TAG);
    }

    @Override

    protected void onFragmentChange(Fragment fragment) {
        handleActionBarItems(fragment);
    }

    private void handleActionBarItems(Fragment fragment) {
        if (fragment instanceof NewsListFragment) {
            btn_refresh.setVisibility(View.VISIBLE);
            iv_select_category.setVisibility(View.VISIBLE);
        } else {
            btn_refresh.setVisibility(View.GONE);
            iv_select_category.setVisibility(View.GONE);
        }
    }

    @Override
    public void setNetworkCallback(MyObservable.NetworkCallback callback) {
        this.callback = callback;
    }

    @Override
    public void getNewsData() {
        newsList = MyObservable.getArticleObservable(selectedSection)
                .subscribeWith(MyObservable.getObserver(callback));
    }

    @Override
    public void launchArticleDetailsPage(ArticleDataModel.ResultsBean item) {
        addFragment(NewsArticleDetailsFragment.newInstance(item), NewsArticleDetailsFragment.TAG);
    }
}
