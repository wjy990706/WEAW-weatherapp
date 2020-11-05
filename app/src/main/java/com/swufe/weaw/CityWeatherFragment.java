package com.swufe.weaw;




import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.swufe.weaw.R;
import com.swufe.weaw.base.BaseFragment;
import com.swufe.weaw.bean.WeatherBean;
import com.swufe.weaw.db.DBManager;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class CityWeatherFragment extends BaseFragment
{//继承basefragment，
    TextView temp,city,condition,wind,tempRange,date;
    ImageView dayImagview;
    LinearLayout futureLayout;
    ScrollView outLayout;
    String url1 = "http://api.map.baidu.com/telematics/v3/weather?location=";
    String url2 = "&output=json&ak=FkPhtMBK0HTIQNh7gG4cNUttSTyr0nzo";//网址是http://api.map.baidu.com/telematics/v3/weather?location=北京&output=json&ak=FkPhtMBK0HTIQNh7gG4cNUttSTyr0nzo

    String cityName;



    //初始化组件
    private void initView(View view)
    {
        temp = view.findViewById(R.id.frag_tv_currenttemp);
        city = view.findViewById(R.id.frag_tv_city);
        condition = view.findViewById(R.id.frag_tv_condition);
        wind = view.findViewById(R.id.frag_tv_wind);
        tempRange = view.findViewById(R.id.frag_tv_temprange);
        date = view.findViewById(R.id.frag_tv_date);

        dayImagview = view.findViewById(R.id.frag_iv_today);
        futureLayout = view.findViewById(R.id.frag_center_layout);
        outLayout = view.findViewById(R.id.out_layout);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_city_weather, container, false);
        initView(view);

//        可以通过activity传值获取到当前fragment加载的是那个地方的天气情况
        Bundle bundle = getArguments();
        cityName = bundle.getString("city");
        String url = url1+cityName+url2;
//      调用父类的basefragment的方法获取数据
        loadData(url);
        return view;
    }
    @Override
    public void onSuccess(String result)//重写basefragment中的成功方法
    {
//        解析并展示数据
         parseShowData(result);
//         更新数据
        int i = DBManager.updateInfoByCity(cityName, result);
        if (i<=0) {
//            更新数据库失败，说明没有这条城市信息，增加这个城市记录
            DBManager.addCityInfo(cityName,result);
        }
    }
    @Override
    public void onError(Throwable ex, boolean isOnCallback) //重写basefragment中的失败的方法
    {
//        数据库当中查找上一次信息显示在Fragment当中
        String s = DBManager.queryInfoByCity(cityName);
        if (!TextUtils.isEmpty(s)) {
            parseShowData(s);
        }

    }
    private void parseShowData(String result)//源码来自b站up主写bug的狐狸的教学视频
    {
// 使用gson解析数据
        WeatherBean weatherBean = new Gson().fromJson(result, WeatherBean.class);
        WeatherBean.ResultsBean resultsBean = weatherBean.getResults().get(0);
// 设置TextView
        date.setText(weatherBean.getDate());
        city.setText(resultsBean.getCurrentCity());
// 获取今日天气情况
        WeatherBean.ResultsBean.WeatherDataBean todayDataBean = resultsBean.getWeather_data().get(0);
        wind.setText(todayDataBean.getWind());
        tempRange.setText(todayDataBean.getTemperature());
        condition.setText(todayDataBean.getWeather());
// 获取实时天气温度情况，需要处理字符串。可以用在线json解析工具查看，发现实时温度在Date里，需要用中文冒号分割出来
        String[] split = todayDataBean.getDate().split("：");
        String todayTemp = split[1].replace(")", "");
        temp.setText(todayTemp);
// 设置显示的天气情况图片
        Picasso.with(getActivity()).load(todayDataBean.getDayPictureUrl()).into(dayImagview);
//  获取未来三天的天气情况，加载到layout当中
        List<WeatherBean.ResultsBean.WeatherDataBean> futureList = resultsBean.getWeather_data();
        futureList.remove(0);//删除第一天的天气，只显示三天
        for (int i = 0; i < futureList.size(); i++) {
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.item_main_center, null);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            futureLayout.addView(itemView);
            TextView idateTv = itemView.findViewById(R.id.item_center_tv_date);
            TextView iconTv = itemView.findViewById(R.id.item_center_tv_con);
            TextView itemprangeTv = itemView.findViewById(R.id.item_center_tv_temp);
            ImageView iIv = itemView.findViewById(R.id.item_center_iv);
//          获取对应的位置的天气情况
            WeatherBean.ResultsBean.WeatherDataBean dataBean = futureList.get(i);
            idateTv.setText(dataBean.getDate());
            iconTv.setText(dataBean.getWeather());
            itemprangeTv.setText(dataBean.getTemperature());
            Picasso.with(getActivity()).load(dataBean.getDayPictureUrl()).into(iIv);
        }
    }




}
