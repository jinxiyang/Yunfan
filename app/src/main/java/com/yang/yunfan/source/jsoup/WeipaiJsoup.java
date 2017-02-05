package com.yang.yunfan.source.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class WeipaiJsoup {

    public static final String VIDEO_LIST_URL = "http://www.dbmeinv.com/dbgroup/videos.htm?pager_offset=";

    public static final int TIME_OUT = 10000;

    /**
     * 根据页码获取一页微拍视频列表数据
     *
     * @param pageNo
     * @return
     */
    public static Observable<WeipaiVideosPage> getWeipaiVideosPageByPageNo(final int pageNo) {
        return Observable.create(new Observable.OnSubscribe<WeipaiVideosPage>() {
            @Override
            public void call(Subscriber<? super WeipaiVideosPage> subscriber) {
                try {
                    WeipaiVideosPage videosPage = WeipaiJsoup._getWeipaiVideosPageByPageNo(pageNo);
                    subscriber.onNext(videosPage);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });
    }

    /**
     * 根据微拍视频网页地址，获取视频资源MP4的URL
     *
     * @param videoHtmlUrl
     * @return
     */
    public static Observable<String> getWeipaiVideoUrl(final String videoHtmlUrl) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String videoUrl = WeipaiJsoup._getWeipaiVideoUrl(videoHtmlUrl);
                    subscriber.onNext(videoUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });
    }

    /**
     * 根据页码获取一页微拍视频列表数据
     *
     * @param pageNo
     * @return
     */
    private static WeipaiVideosPage _getWeipaiVideosPageByPageNo(int pageNo) throws Exception {
        if (pageNo < 1) {
            pageNo = 1;
        }
        Document document = Jsoup.connect(VIDEO_LIST_URL + pageNo).timeout(TIME_OUT).get();
        if (document == null) {
            return null;
        }

        WeipaiVideosPage page = new WeipaiVideosPage();
        List<WeipaiVideo> videos = new ArrayList<>();

        Elements elements = document.getElementsByClass("thumbnail");
        for (int i = 0; i < elements.size(); i++) {
            WeipaiVideo video = new WeipaiVideo();
            Element element = elements.get(i);
            Elements imgs = element.getElementsByTag("img");
            String imageUrl = imgs.get(0).attr("src");
            String title = element.getElementsByClass("fl p5 meta").get(0).text().replaceAll("\"", "");
            String userIconUrl = imgs.get(1).attr("src");
            String userName = element.getElementsByClass("avatar").get(0).text();
            String videoHtmlUrl = element.getElementsByTag("a").get(0).attr("href");

            video.setImageUrl(imageUrl);
            video.setTitle(title);
            video.setUserIconUrl(userIconUrl);
            video.setUserName(userName);
            video.setVideoHtmlUrl(videoHtmlUrl);
            videos.add(video);
        }
        String currPageNo = document.getElementsByClass("active").get(0).getElementsByTag("span").get(0).text();
        boolean hasNextPage = document.getElementsByClass("next next_page") != null;
        page.setVideos(videos);
        page.setCurrPageNo(Integer.parseInt(currPageNo));
        page.setHasNextPage(hasNextPage);
        return page;
    }

    /**
     * 根据微拍视频网页地址，获取视频资源MP4的URL
     *
     * @param videoHtmlUrl
     * @return
     */
    private static String _getWeipaiVideoUrl(String videoHtmlUrl) throws Exception {
        if (videoHtmlUrl == null || videoHtmlUrl.trim().equals("")) {
            return null;
        }
        Document document = Jsoup.connect(videoHtmlUrl).timeout(TIME_OUT).get();
        if (document == null) {
            return null;
        }
        String html = document.getElementsByClass("panel-body markdown").get(0).getElementsByTag("script").get(0)
                .html();
        String videoUrl = html.split("<source")[1].split("\"")[1];
        return videoUrl;
    }
}
