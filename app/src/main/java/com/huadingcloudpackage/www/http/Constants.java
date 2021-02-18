package com.huadingcloudpackage.www.http;

public class Constants {

    public static int Loadmore = 1;
    public static int Refresh = 0;
    public static int TabDebounceTime = 666;//tab停止滑动后的时长（才可触发请求）



    public static class PageState {
        public static final String EMPTY = "EMPTY";//无数据
        public static final String ERROR = "ERROR";//网络异常
        public static final String NORMAL = "NORMAL";//返回数据正常
        public static final String LOADING = "LOADING";//请求数据中
        public static final String INIT = "INIT";//页面初始状态
    }
}
