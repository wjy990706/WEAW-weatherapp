package com.swufe.weaw.city_manager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.swufe.weaw.DeleteCityActivity;
import com.swufe.weaw.R;
import com.swufe.weaw.SearchCityActivity;
import com.swufe.weaw.db.DBManager;
import com.swufe.weaw.db.DatabaseBean;

import java.util.ArrayList;
import java.util.List;

public class CityManagerActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView addImageView, backImageView, deleteImageView;
    ListView cityListView;
    List<DatabaseBean>mDatas;  //显示列表数据源
    private CityManagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manager);
        addImageView = findViewById(R.id.city_iv_add);
        backImageView = findViewById(R.id.city_iv_back);
        deleteImageView = findViewById(R.id.city_iv_delete);
        cityListView = findViewById(R.id.city_lv);
        mDatas = new ArrayList<>();
//        添加点击监听事件
        addImageView.setOnClickListener(this);
        deleteImageView.setOnClickListener(this);
        backImageView.setOnClickListener(this);
//        设置适配器
        adapter = new CityManagerAdapter(this, mDatas);//mdatas数据源

        cityListView.setAdapter(adapter);
    }
// 获取数据库当中真实数据源，添加到原有数据源当中，提示适配器更新
    @Override
    protected void onResume() {
        super.onResume();
        List<DatabaseBean> list = DBManager.queryAllInfo();//表的全部信息
        mDatas.clear();//先清空原来的
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();//提示更新
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.city_iv_add:
                //打开搜索界面
                Intent intent = new Intent(this, SearchCityActivity.class);
                startActivity(intent);
                break;
            case R.id.city_iv_back:
                //返回上一页
                finish();
                break;
            case R.id.city_iv_delete:
             //删除，打开编辑界面
                Intent intent1 = new Intent(this, DeleteCityActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
