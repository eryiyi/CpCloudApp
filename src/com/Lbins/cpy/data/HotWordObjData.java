package com.Lbins.cpy.data;

import com.Lbins.cpy.module.HotWordObj;

import java.util.List;

/**
 * Created by zhl on 2016/8/3.
 */
public class HotWordObjData extends Data {
    private List<HotWordObj> data;

    public List<HotWordObj> getData() {
        return data;
    }

    public void setData(List<HotWordObj> data) {
        this.data = data;
    }
}