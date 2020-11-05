package com.swufe.weaw.city_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.swufe.weaw.R;
import com.swufe.weaw.bean.WeatherBean;
import com.swufe.weaw.db.DatabaseBean;
import com.google.gson.Gson;

import java.util.List;

public class CityManagerAdapter extends BaseAdapter{
    Context context;
    List<DatabaseBean>mDatas;//list泛型是数据库bean内容

    public CityManagerAdapter(Context context, List<DatabaseBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() //返回数量
    {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {//源码来自b站写bug的狐狸的教学视频，有修改
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_city_manager,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        DatabaseBean bean = mDatas.get(position);
        holder.cityTextView.setText(bean.getCity());
        WeatherBean weatherBean = new Gson().fromJson(bean.getContent(), WeatherBean.class);
//        获取今日天气情况
        WeatherBean.ResultsBean.WeatherDataBean dataBean = weatherBean.getResults().get(0).getWeather_data().get(0);
        holder.conTextView.setText("天气:"+dataBean.getWeather());
        String[] split = dataBean.getDate().split("：");
        String todayTemp = split[1].replace(")", "");
        holder.currentTempTextView.setText(todayTemp);
        holder.windTextView.setText(dataBean.getWind());
        holder.tempRangeTv.setText(dataBean.getTemperature());
        return convertView;
    }

    class ViewHolder
    {
        TextView cityTextView, conTextView, currentTempTextView, windTextView,tempRangeTv;
        public ViewHolder(View itemView){
            cityTextView = itemView.findViewById(R.id.item_city_tv_city);
            conTextView = itemView.findViewById(R.id.item_city_tv_condition);
            currentTempTextView = itemView.findViewById(R.id.item_city_tv_temp);
            windTextView = itemView.findViewById(R.id.item_city_wind);
            tempRangeTv = itemView.findViewById(R.id.item_city_temprange);

        }
    }
}
