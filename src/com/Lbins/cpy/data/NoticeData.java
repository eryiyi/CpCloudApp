package com.Lbins.cpy.data;

import com.Lbins.cpy.module.Notice;

import java.util.List;

/**
 * Created by Administrator on 2016/3/26 0026.
 */
public class NoticeData extends Data {
    private List<Notice> data;

    public List<Notice> getData() {
        return data;
    }

    public void setData(List<Notice> data) {
        this.data = data;
    }
}
