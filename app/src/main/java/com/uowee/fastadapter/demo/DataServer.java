package com.uowee.fastadapter.demo;


import java.util.ArrayList;
import java.util.List;

public class DataServer {

    public static List<Status> getSampleData(int lenth) {
        List<Status> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
            Status status = new Status();
            status.setUserName("XXX" + i);
            status.setCreatedAt("04/05/" + i);
            status.setRetweet(i % 2 == 0);
            status.setUserAvatar("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460");
            status.setText(" https://www.recyclerview.org");
            list.add(status);
        }
        return list;
    }
}
