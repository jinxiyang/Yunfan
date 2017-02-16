package com.yang.yunfan.model;

/**
 * Created by yang on 2017/2/16.
 */

public class Joke {

    /**
     * content : 去餐厅吃饭，正准备点餐，一个美女走过来羞羞地说：“那个。。。你看今天情侣套餐优惠只要120元，单人套餐没有优惠要80元，可是。。。我一个人。。。我身上只带了60元，你也是一个人，可不可以。。。” 我开心地说：“你是说我们一起拼个情侣套餐？” 她摇摇头：“可不可以借我20块钱？”
     * hashId : 31a5ec335c4c52184046d0cf9db92741
     * unixtime : 1487216630
     * updatetime : 2017-02-16 11:43:50
     */

    private String content;
    private String hashId;
    private int unixtime;
    private String updatetime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public int getUnixtime() {
        return unixtime;
    }

    public void setUnixtime(int unixtime) {
        this.unixtime = unixtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public Joke() {
    }
}
