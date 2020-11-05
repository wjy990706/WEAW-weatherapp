package com.swufe.weaw.base;

import android.support.v4.app.Fragment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
//源码来自b站@写bug的狐狸的视频，有修改
/*
 * xutils加载网络数据的步骤
 * 1.整体声明模块，新建类uniteapp继承application，在uniteapp中声明，并在androidmanifest中写入，让程序一运行就自动加载uniteapp
 * 2.执行网络请求*/
public class BaseFragment extends Fragment implements Callback.CommonCallback<String>{

    public void loadData(String path){
        RequestParams params = new RequestParams(path);
        x.http().get(params,this);
    }
//    获取数据成功时，会回调的接口
    @Override
    public void onSuccess(String result) {

    }
//  获取数据失败时，会调用的接口
    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

    }
//  取消请求时，会调用的接口
    @Override
    public void onCancelled(CancelledException cex) {

    }
//  请求完成后，会回调的接口
    @Override
    public void onFinished() {

    }
}
