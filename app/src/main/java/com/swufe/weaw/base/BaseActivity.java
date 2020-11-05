package com.swufe.weaw.base;

import android.support.v7.app.AppCompatActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
//源码来自b站@写bug的狐狸的视频，有修改
/*
* xutils加载网络数据的步骤
* 1.整体声明模块，新建类uniteapp继承application，在uniteapp中声明，并在androidmanifest中写入，让程序一运行就自动加载uniteapp
* 2.执行网络请求*/

public class BaseActivity extends AppCompatActivity implements Callback.CommonCallback<String>{//继承回调

    public void loadData(String url){
        RequestParams params = new RequestParams(url);
        x.http().get(params,this);
    }
    @Override
    //获取数据成功时回调的接口
    public void onSuccess(String result) {

    }

    @Override
//获取数据失败时回调的接口
    public void onError(Throwable ex, boolean isOnCallback) {

    }

    @Override
    //取消请求时回调的接口
    public void onCancelled(CancelledException cex) {

    }

    @Override
    //请求完成时回调的接口
    public void onFinished() {

    }
}
