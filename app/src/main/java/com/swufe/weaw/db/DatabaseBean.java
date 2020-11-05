package com.swufe.weaw.db;

public class DatabaseBean {
    private int _id;//主键
    private String city;//城市
    private String content;//城市相关天气信息

    public DatabaseBean() //构造
    {
    }

    public DatabaseBean(int _id, String city, String content) //构造
    {
        this._id = _id;
        this.city = city;
        this.content = content;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
