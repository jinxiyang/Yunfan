package com.yang.yunfan.source.jsoup;

import java.util.List;

public class WeipaiVideosPage {
    private List<WeipaiVideo> videos;
    private int currPageNo;
    private boolean hasNextPage;

    public WeipaiVideosPage() {
        super();
    }

    public List<WeipaiVideo> getVideos() {
        return videos;
    }

    public void setVideos(List<WeipaiVideo> videos) {
        this.videos = videos;
    }

    public int getCurrPageNo() {
        return currPageNo;
    }

    public void setCurrPageNo(int currPageNo) {
        this.currPageNo = currPageNo;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}
