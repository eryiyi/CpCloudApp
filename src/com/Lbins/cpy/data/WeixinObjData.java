package com.Lbins.cpy.data;

import com.Lbins.cpy.module.WeixinObj;

import java.util.List;

/**
 * Created by zhanghailong on 2016/3/4.
 */
public class WeixinObjData extends Data {
    private List<WeixinObj> data;

    public List<WeixinObj> getData() {
        return data;
    }

    public void setData(List<WeixinObj> data) {
        this.data = data;
    }
}
