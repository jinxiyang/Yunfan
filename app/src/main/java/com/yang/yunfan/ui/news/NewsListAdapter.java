package com.yang.yunfan.ui.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yang.yunfan.R;
import com.yang.yunfan.model.News;

import java.util.List;

/**
 * Created by yang on 2017/1/8.
 */

public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ONE_IMAGE = 0;
    private static final int VIEW_TYPE_THREE_IMAGE = 1;

    private Context context;
    private List<News> datas;
    private OnItemClickListener onItemClickListener;

    public NewsListAdapter(Context context, List<News> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){
            case VIEW_TYPE_ONE_IMAGE:
                view = LayoutInflater.from(context).inflate(R.layout.item_news_list_2, parent, false);
                break;
            case VIEW_TYPE_THREE_IMAGE:
                view = LayoutInflater.from(context).inflate(R.layout.item_news_list, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        News news = datas.get(position);
        viewHolder.tvTitle.setText(news.getTitle());
        viewHolder.sdv_1.setImageURI(news.getThumbnail_pic_s());
        if (getItemViewType(position) == VIEW_TYPE_THREE_IMAGE){
            viewHolder.sdv_2.setImageURI(news.getThumbnail_pic_s02());
            viewHolder.sdv_3.setImageURI(news.getThumbnail_pic_s03());
        }

        viewHolder.tvAuthor.setText(news.getAuthor_name());
        viewHolder.tvDate.setText(news.getDate());
        viewHolder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        News news = datas.get(position);
        if (TextUtils.isEmpty(news.getThumbnail_pic_s02())){
            return VIEW_TYPE_ONE_IMAGE;
        }else {
            return VIEW_TYPE_THREE_IMAGE;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout rlItem;
        TextView tvTitle;
        SimpleDraweeView sdv_1;
        SimpleDraweeView sdv_2;
        SimpleDraweeView sdv_3;
        TextView tvAuthor;
        TextView tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            rlItem = (RelativeLayout) itemView.findViewById(R.id.rl_item);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            sdv_1 = (SimpleDraweeView) itemView.findViewById(R.id.sdv_1);
            sdv_2 = (SimpleDraweeView) itemView.findViewById(R.id.sdv_2);
            sdv_3 = (SimpleDraweeView) itemView.findViewById(R.id.sdv_3);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_author_name);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

}
