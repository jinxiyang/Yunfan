package com.yang.yunfan.ui.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yang.yunfan.R;
import com.yang.yunfan.model.News;
import com.yang.yunfan.utils.FrescoUtil;

import java.util.List;

/**
 * Created by yang on 2017/1/8.
 */

public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String NETWORK_ERROR = "network_orror";

    private static final int VIEW_TYPE_NETWORK_ERROR = 0;
    private static final int VIEW_TYPE_ONE_IMAGE = 1;
    private static final int VIEW_TYPE_THREE_IMAGE = 2;

    private Context context;
    private List<News> datas;
    private OnItemClickListener onItemClickListener;

    public NewsListAdapter(Context context, List<News> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        View view = null;
        switch (viewType){
            case VIEW_TYPE_NETWORK_ERROR:
                view = LayoutInflater.from(context).inflate(R.layout.layout_neterror_top, parent, false);
                vh = new NeterrorViewHolder(view);
                break;
            case VIEW_TYPE_ONE_IMAGE:
                view = LayoutInflater.from(context).inflate(R.layout.item_news_list_2, parent, false);
                vh = new ViewHolder(view);
                break;
            case VIEW_TYPE_THREE_IMAGE:
                view = LayoutInflater.from(context).inflate(R.layout.item_news_list, parent, false);
                vh = new ViewHolder(view);
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == VIEW_TYPE_NETWORK_ERROR){
            NeterrorViewHolder vh = (NeterrorViewHolder) holder;
            vh.llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.onClickNeterorItem(v, position);
                    }
                }
            });
            return;
        }


        ViewHolder viewHolder = (ViewHolder) holder;
        News news = datas.get(position);
        viewHolder.tvTitle.setText(news.getTitle());
        FrescoUtil.setImageUri(viewHolder.sdv_1, news.getThumbnail_pic_s());
        if (itemViewType == VIEW_TYPE_THREE_IMAGE){
            FrescoUtil.setImageUri(viewHolder.sdv_2, news.getThumbnail_pic_s02());
            FrescoUtil.setImageUri(viewHolder.sdv_3, news.getThumbnail_pic_s03());
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
        if (position == 0 && news != null && NETWORK_ERROR.equals(news.getCategory())){
            return VIEW_TYPE_NETWORK_ERROR;
        }

        if (TextUtils.isEmpty(news.getThumbnail_pic_s02()) || TextUtils.isEmpty(news.getThumbnail_pic_s03())){
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
    public static class NeterrorViewHolder extends RecyclerView.ViewHolder{
        LinearLayout llItem;

        public NeterrorViewHolder(View itemView) {
            super(itemView);
            llItem = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int position);

        void onClickNeterorItem(View v, int position);
    }

}
