package com.app.nytimes.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.nytimes.ParentFragment;
import com.app.nytimes.R;
import com.app.nytimes.data.ArticleDataModel;
import com.app.nytimes.imageLoader.ImageLoader;
import com.app.nytimes.utils.AppHelper;
import com.app.nytimes.utils.Constants;
import com.google.gson.Gson;

public class NewsArticleDetailsFragment extends ParentFragment {
    public static final String TAG = NewsArticleDetailsFragment.class.getSimpleName();
    private static final String KEY_DATA_ITEM = "com.app.nytimes.fragments.KEY_DATA_ITEM";

    ArticleDataModel.ResultsBean item;
    TextView tv_article_title, tv_article_abstract, tv_article_author, tv_read_more;
    ImageView iv_news_image;

    public static NewsArticleDetailsFragment newInstance(ArticleDataModel.ResultsBean item) {

        Bundle args = new Bundle();
        args.putString(KEY_DATA_ITEM, new Gson().toJson(item));

        NewsArticleDetailsFragment fragment = new NewsArticleDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setArgumentData(getArguments());
    }

    private void setArgumentData(Bundle args) {
        if (args != null) {
            String itemString = args.getString(KEY_DATA_ITEM);
            if (!TextUtils.isEmpty(itemString)) {
                item = new Gson().fromJson(itemString, ArticleDataModel.ResultsBean.class);
            } else {
                showErrorMsg();
            }
        } else {
            showErrorMsg();
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_article_details;
    }

    @Override
    protected void initializeViews() {
        iv_news_image = fView.findViewById(R.id.iv_news_image);
        tv_article_title = fView.findViewById(R.id.tv_article_title);
        tv_article_abstract = fView.findViewById(R.id.tv_article_abstract);
        tv_article_author = fView.findViewById(R.id.tv_article_author);
        tv_read_more = fView.findViewById(R.id.tv_read_more);
    }

    @Override
    protected void initializeListener() {
        tv_read_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppHelper.openUrl(context, item.getUrl());
            }
        });
    }

    @Override
    protected void setData() {
        if (item != null) {

            ConstraintLayout.LayoutParams params =
                    (ConstraintLayout.LayoutParams) iv_news_image.getLayoutParams();

            params.width = Constants.screenWidth;
            params.height = (Constants.screenWidth * 2) / 3;

            if (item.getMultimedia().size() > 0 &&
                    !TextUtils.isEmpty(item.getMultimedia()
                            .get(item.getMultimedia().size() - 2).getUrl())) {
                new ImageLoader(context).DisplayImage(
                        item.getMultimedia().get(item.getMultimedia().size() - 1).getUrl(),
                        iv_news_image,
                        R.mipmap.ic_launcher);
            } else {
                iv_news_image.setImageResource(R.mipmap.ic_launcher);
            }

            if (!TextUtils.isEmpty(item.getTitle())) {
                tv_article_title.setText(item.getTitle());
            } else {
                tv_article_title.setText("");
            }

            if (!TextUtils.isEmpty(item.getAbstractX())) {
                tv_article_abstract.setText(item.getAbstractX());
            } else {
                tv_article_abstract.setText("");
            }

            if (!TextUtils.isEmpty(item.getByline())) {
                tv_article_author.setText(item.getByline());
            } else {
                tv_article_author.setText("");
            }

            if (!TextUtils.isEmpty(item.getUrl())) {
                tv_read_more.setVisibility(View.VISIBLE);
            } else {
                tv_read_more.setVisibility(View.GONE);
            }
        } else {
            showErrorMsg();
        }
    }

    @Override
    protected void initializeCallbacks(Context context) {

    }

    @Override
    protected void destroyCallbacks() {

    }

    private void showErrorMsg() {
        AppHelper.showMsg(context, R.string.text_something_went_wrong);
    }
}
