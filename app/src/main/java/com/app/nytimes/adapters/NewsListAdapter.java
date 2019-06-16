package com.app.nytimes.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.nytimes.R;
import com.app.nytimes.data.ArticleDataModel;
import com.app.nytimes.imageLoader.ImageLoader;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ItemViewHolder> {

    private List<ArticleDataModel.ResultsBean> results;
    private ImageLoader imageLoader;
    private AdapterCallback callback;

    public NewsListAdapter(AdapterCallback callback) {
        this.callback = callback;
    }

    public void setResults(List<ArticleDataModel.ResultsBean> results) {
        this.results = results;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        imageLoader = new ImageLoader(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        final ArticleDataModel.ResultsBean item = results.get(position);

        if(item.getMultimedia().size() > 0 &&
                !TextUtils.isEmpty(item.getMultimedia().get(0).getUrl())){
            imageLoader.DisplayImage(item.getMultimedia().get(0).getUrl(),
                    holder.iv_news_thumbnail,
                    R.mipmap.ic_launcher_round);
        }else {
            holder.iv_news_thumbnail.setImageResource(R.mipmap.ic_launcher_round);
        }

        if(!TextUtils.isEmpty(item.getByline())) {
            holder.tv_author_name.setText(item.getByline());
        }else {
            holder.tv_author_name.setText("");
        }

        if(!TextUtils.isEmpty(item.getTitle())){
            holder.tv_article_title.setText(item.getTitle());
        }else {
            holder.tv_article_title.setText("");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    callback.onItemClick(holder.getAdapterPosition(), item);
                }else {
                    throw new RuntimeException("NewsListAdapter callback is null");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (results == null)
            return 0;
        return results.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_news_thumbnail;
        TextView tv_article_title, tv_author_name;
        View itemView;

        ItemViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            iv_news_thumbnail = itemView.findViewById(R.id.iv_news_thumbnail);
            tv_article_title = itemView.findViewById(R.id.tv_article_title);
            tv_author_name = itemView.findViewById(R.id.tv_author_name);
        }
    }

    public interface AdapterCallback{
        void onItemClick(int position, ArticleDataModel.ResultsBean item);
    }
}
