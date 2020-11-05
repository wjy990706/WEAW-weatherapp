package com.swufe.weaw;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.swufe.weaw.R;
import com.swufe.weaw.city_manager.CityManagerActivity;
import com.swufe.weaw.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView addCityImageView;
    LinearLayout pointLayout;
    RelativeLayout outLayout;
    ViewPager mainViewPager;
//    ViewPager要显示的数据源
    List<Fragment>fragmentList;
//    需要显示的城市的集合
    List<String>cityList;
//    ViewPager的页数指数器显示集合
    List<ImageView>imgList;
    private CityFragmentPagerAdapter adapter;//适配器


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addCityImageView = findViewById(R.id.main_iv_add);

        pointLayout = findViewById(R.id.main_layout_point);
        outLayout = findViewById(R.id.main_out_layout);

        mainViewPager = findViewById(R.id.main_vp);
//        添加点击事件
        addCityImageView.setOnClickListener(this);
       // moreIv.setOnClickListener(this);

        fragmentList = new ArrayList<>();
        cityList = DBManager.queryAllCityName();  //获取数据库包含的城市信息列表
        imgList = new ArrayList<>();

        if (cityList.size()==0) {
            cityList.add("北京");
        }
        /* 因为可能搜索界面点击跳转此界面，会传值，所以此处获取一下*/
        try {
            Intent intent = getIntent();
            String city = intent.getStringExtra("city");
            if (!cityList.contains(city)&&!TextUtils.isEmpty(city)) {
                cityList.add(city);
            }
        }catch (Exception e){
            Log.i("animee","程序出现问题了！！");
        }
//        初始化ViewPager页面的方法
        initPager();
        adapter = new CityFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        mainViewPager.setAdapter(adapter);
//        创建小圆点指示器
        initPoint();
//        设置最后一个城市信息
        mainViewPager.setCurrentItem(fragmentList.size()-1);
//        设置ViewPager页面监听
        setPagerListener();
    }



    private void setPagerListener() //设置ViewPager页面监听事件
    {
        mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()//传入ViewPager中的Onpagelistener，有三个方法，分别代表滑动的信息
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) //遍历小圆点集合，获取每一个，设置为a1图片，当前小圆点设置为a2
            {
                for (int i = 0; i < imgList.size(); i++) {
                    imgList.get(i).setImageResource(R.mipmap.a1);
                }
                imgList.get(position).setImageResource(R.mipmap.a2);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initPoint() //创建圆点指示 ViewPager页面指示器的函数
    {
        for (int i = 0; i < fragmentList.size(); i++) {
            ImageView pIv = new ImageView(this);//小圆点显示对象
            pIv.setImageResource(R.mipmap.a1);//白色圆点
            pIv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));//传入高度宽度
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) pIv.getLayoutParams();//强转成layoutparams
            lp.setMargins(0,0,20,0);//设置外边距
            imgList.add(pIv);//添加到imglist
            pointLayout.addView(pIv);
        }
        imgList.get(imgList.size()-1).setImageResource(R.mipmap.a2);//设置最后一个（size-1）城市表示的小圆点为深色图片
    }

    private void initPager() // 创建Fragment对象，添加到ViewPager数据源当中
    {
        for (int i = 0; i < cityList.size(); i++) {
            CityWeatherFragment cwFrag = new CityWeatherFragment();
            Bundle bundle = new Bundle();
            bundle.putString("city",cityList.get(i));
            cwFrag.setArguments(bundle);
            fragmentList.add(cwFrag);
        }
    }
    @Override
    public void onClick(View v) //点击加号图片，做出反馈，跳转到新界面
    {
        Intent intent = new Intent();//打开新界面
        switch (v.getId()) {
            case R.id.main_iv_add:
                intent.setClass(this,CityManagerActivity.class);
                break;

        }
        startActivity(intent);
    }

    //当页面重写加载时会调用的函数，这个函数在页面获取焦点之前进行调用，此处完成ViewPager页数的更新
    @Override
    protected void onRestart() {
        super.onRestart();
//        获取数据库当中还剩下的城市集合
        List<String> list = DBManager.queryAllCityName();
        if (list.size()==0) {
            list.add("北京");
        }
        cityList.clear();    //重写加载之前，清空原本数据源
        cityList.addAll(list);
//        剩余城市也要创建对应的fragment页面
        fragmentList.clear();
        initPager();
        adapter.notifyDataSetChanged();
//        页面数量发生改变，指示器的数量也会发生变化，重写设置添加指示器
        imgList.clear();
        pointLayout.removeAllViews();   //将布局当中所有元素全部移除
        initPoint();
        mainViewPager.setCurrentItem(fragmentList.size()-1);
    }
}
