package com.yang.yunfan.ui.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yang.yunfan.R;
import com.yang.yunfan.source.jsoup.WeipaiVideo;

import java.util.List;

/**
 * Created by yang on 2017/1/13.
 */

public class WeipaiVideoAdapter extends RecyclerView.Adapter<WeipaiVideoAdapter.ViewHolder> {

    private Context context;
    private List<WeipaiVideo> videos;

    private OnItemClickListener onItemClickListener;

    public WeipaiVideoAdapter(Context context, List<WeipaiVideo> videos) {
        this.context = context;
        this.videos = videos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        WeipaiVideo video = videos.get(position);
        holder.sdvVideo.setImageURI(video.getImageUrl());
        holder.sdvUserIcon.setImageURI(video.getUserIconUrl());
        holder.tvTitle.setText(video.getTitle());
        holder.tvUserName.setText(video.getUserName());

        holder.rlVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onClickVideo(v, position);
                }
            }
        });
        holder.ivShareVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onClickShare(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView sdvVideo;
        TextView tvTitle;
        ImageView ivVideoAction;
        RelativeLayout rlVideo;
        SimpleDraweeView sdvUserIcon;
        TextView tvUserName;
        ImageView ivShareVideo;

        public ViewHolder(View itemView) {
            super(itemView);
            sdvVideo = (SimpleDraweeView) itemView.findViewById(R.id.sdv_video);
            sdvUserIcon = (SimpleDraweeView) itemView.findViewById(R.id.sdv_user_icon);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
            ivVideoAction = (ImageView) itemView.findViewById(R.id.iv_video_action);
            ivShareVideo = (ImageView) itemView.findViewById(R.id.iv_share_video);
            rlVideo = (RelativeLayout) itemView.findViewById(R.id.rl_video);
        }
    }

    public interface OnItemClickListener {
        void onClickVideo(View v, int position);
        void onClickShare(View v, int position);
    }
}
