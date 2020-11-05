package com.swufe.weaw;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.swufe.weaw.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class DeleteCityActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView errorImageView, rightImageView;
    ListView deleteListView;
    List<String>mDatas;   //listview的数据源
    List<String>deleteCitys;  //表示存储了删除的城市信息
    private DeleteCityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_city);
        errorImageView = findViewById(R.id.delete_iv_error);
        rightImageView = findViewById(R.id.delete_iv_right);
        deleteListView = findViewById(R.id.delete_lv);
        mDatas = DBManager.queryAllCityName();
        deleteCitys = new ArrayList<>();
           // 设置点击监听事件
        errorImageView.setOnClickListener(this);
        rightImageView.setOnClickListener(this);
          //设置适配器
        adapter = new DeleteCityAdapter(this, mDatas, deleteCitys);
        deleteListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_iv_error:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示信息").setMessage("您确定要舍弃更改么？").setPositiveButton("舍弃更改", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        finish();   //关闭当前的activity
                            }
                        });
                builder.setNegativeButton("取消",null);
                builder.create().show();
                break;
            case R.id.delete_iv_right:
                for (int i = 0; i < deleteCitys.size(); i++) {
                    String city = deleteCitys.get(i);
                   // 调用删除城市的函数
                    int i1 = DBManager.deleteInfoByCity(city);
                }

                finish();//删除成功返回上一级页面
                break;
        }
    }
}
