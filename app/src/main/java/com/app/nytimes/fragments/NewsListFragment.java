package com.app.nytimes.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.app.nytimes.ParentFragment;
import com.app.nytimes.R;
import com.app.nytimes.adapters.NewsListAdapter;
import com.app.nytimes.apis.MyObservable;
import com.app.nytimes.data.ArticleDataModel;
import com.app.nytimes.utils.AppHelper;
import com.app.nytimes.utils.MyPreference;
import com.google.gson.Gson;

import io.reactivex.Observable;

public class NewsListFragment extends ParentFragment implements NewsListAdapter.AdapterCallback,
        MyObservable.NetworkCallback {
    public static final String TAG = NewsListFragment.class.getSimpleName();

    RecyclerView rv_news_list;
    Callback callback;
    NewsListAdapter adapter;

    public static NewsListFragment newInstance() {

        Bundle args = new Bundle();

        NewsListFragment fragment = new NewsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected void initializeViews() {
        rv_news_list = fView.findViewById(R.id.rv_news_list);
        adapter = new NewsListAdapter(this);
        rv_news_list.setLayoutManager(new LinearLayoutManager(context));
        rv_news_list.setAdapter(adapter);
    }

    @Override
    protected void initializeListener() {

    }

    @Override
    protected void setData() {
        callback.setNetworkCallback(this);
        String articleDataString = MyPreference.getNewsArticle(context, "home", null);
        if(articleDataString != null){
            ArticleDataModel articleDataModel = new Gson().fromJson(articleDataString, ArticleDataModel.class);
            updateData(articleDataModel);
        }else {
            if (callback != null) {
                if(AppHelper.isNetworkAvailable(context)) {
                    parentCallback.showProgressDialog(false);
                    callback.getNewsData();
                }else {
                    AppHelper.showMsg(context, R.string.no_internet);
                }
            }
        }

    }

    @Override
    protected void initializeCallbacks(Context context) {
        if (context instanceof Callback) {
            callback = (Callback) context;
        }
    }

    @Override
    protected void destroyCallbacks() {
        callback = null;
    }

    @Override
    public void onItemClick(int position, ArticleDataModel.ResultsBean item) {
        callback.launchArticleDetailsPage(item);
    }

    @Override
    public void onSuccess(Object response) {
        parentCallback.hideProgressDialog();
        if (response instanceof ArticleDataModel) {
            ArticleDataModel articleDataModel = (ArticleDataModel) response;
            updateData(articleDataModel);
            MyPreference.setNewsArticle(context, articleDataModel.getSection() ,
                    new Gson().toJson(articleDataModel) );
        }
    }

    private void updateData(ArticleDataModel articleDataModel){
        adapter.setResults(articleDataModel.getResults());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(String errorMessage) {
        parentCallback.hideProgressDialog();
        AppHelper.showMsg(context, errorMessage);
    }

    public interface Callback {
        void setNetworkCallback(MyObservable.NetworkCallback callback);
        void getNewsData();
        void launchArticleDetailsPage(ArticleDataModel.ResultsBean item);
    }
}
