package com.Lbins.cpy.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.Lbins.cpy.R;
import com.Lbins.cpy.UniversityApplication;
import com.Lbins.cpy.module.CountryObj;
import com.Lbins.cpy.util.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by Administrator on 2015/5/27.
 */
public class ItemCountryAdapter extends BaseAdapter {
    private ViewHolder holder;
    private List<CountryObj> lists;
    private Context mContect;

    ImageLoader imageLoader = ImageLoader.getInstance();//图片加载类
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private OnClickContentItemListener onClickContentItemListener;

    public void setOnClickContentItemListener(OnClickContentItemListener onClickContentItemListener) {
        this.onClickContentItemListener = onClickContentItemListener;
    }

    public ItemCountryAdapter(List<CountryObj> lists, Context mContect) {
        this.lists = lists;
        this.mContect = mContect;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContect).inflate(R.layout.item_area, null);
            holder.title = (TextView) convertView.findViewById(R.id.title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final CountryObj cell = lists.get(position);
        if (cell != null) {
            holder.title.setText((cell.getCityName() == null ? "" : cell.getCityName()) + (cell.getArea() == null ? "" : cell.getArea()));
            if (!StringUtil.isNullOrEmpty(UniversityApplication.fontSize)) {
                holder.title.setTextSize(Float.valueOf(UniversityApplication.fontSize));
            }
            if (!StringUtil.isNullOrEmpty(UniversityApplication.fontColor)) {
                if ("black".equals(UniversityApplication.fontColor)) {
                    holder.title.setTextColor(Color.BLACK);
                }
                if ("gray".equals(UniversityApplication.fontColor)) {
                    holder.title.setTextColor(Color.GRAY);
                }
                if ("blue".equals(UniversityApplication.fontColor)) {
                    holder.title.setTextColor(Color.BLUE);
                }
                if ("orange".equals(UniversityApplication.fontColor)) {
                    holder.title.setTextColor(Color.YELLOW);
                }
                if ("red".equals(UniversityApplication.fontColor)) {
                    holder.title.setTextColor(Color.RED);
                }
            }
        }
        return convertView;
    }

    class ViewHolder {
        TextView title;
    }
}
