package com.Lbins.cpy.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.Lbins.cpy.R;
import com.Lbins.cpy.UniversityApplication;
import com.Lbins.cpy.adapter.ItemFourFuwuAdapter;
import com.Lbins.cpy.adapter.OnClickContentItemListener;
import com.Lbins.cpy.base.BaseFragment;
import com.Lbins.cpy.base.InternetURL;
import com.Lbins.cpy.data.FuwuObjData;
import com.Lbins.cpy.library.internal.PullToRefreshBase;
import com.Lbins.cpy.library.internal.PullToRefreshListView;
import com.Lbins.cpy.module.FuwuObj;
import com.Lbins.cpy.ui.FourFuwuActivity;
import com.Lbins.cpy.ui.GPSNaviActivity;
import com.Lbins.cpy.ui.LoginActivity;
import com.Lbins.cpy.ui.WebViewActivity;
import com.Lbins.cpy.util.StringUtil;
import com.Lbins.cpy.widget.SelectTelPopWindow;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2016/7/17.
 */
public class FuwuFragmentTwo extends BaseFragment implements View.OnClickListener,OnClickContentItemListener {
    private View view;
    private Resources res;

    private List<FuwuObj> lists = new ArrayList<FuwuObj>();
    private PullToRefreshListView gridView;
    private ItemFourFuwuAdapter adapter;

    private ImageView no_data;

    private int pageIndex = 1;
    private static boolean IS_REFRESH = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.record_one_fragment, null);
        res = getActivity().getResources();
        initView();
        InitViewPager();
        initData();
        return view;
    }

    public void initData() {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                InternetURL.GET_FUWU_BY_LOCATION_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if (StringUtil.isJson(s)) {
                            try {
                                JSONObject jo = new JSONObject(s);
                                String code1 = jo.getString("code");
                                if (Integer.parseInt(code1) == 200) {
                                    FuwuObjData data = getGson().fromJson(s, FuwuObjData.class);
                                    if (IS_REFRESH) {
                                        lists.clear();
                                    }
                                    lists.addAll(data.getData());
                                    gridView.onRefreshComplete();
                                    adapter.notifyDataSetChanged();
                                } else if (Integer.parseInt(code1) == 9) {
                                    Toast.makeText(getActivity(), R.string.login_out, Toast.LENGTH_SHORT).show();
                                    save("password", "");
                                    Intent loginV = new Intent(getActivity(), LoginActivity.class);
                                    startActivity(loginV);
                                    getActivity().finish();
                                } else {
                                    Toast.makeText(getActivity(), R.string.get_data_error, Toast.LENGTH_SHORT).show();
                                }
                                if(lists.size() == 0){
                                    no_data.setVisibility(View.VISIBLE);
                                    gridView.setVisibility(View.GONE);
                                }else{
                                    no_data.setVisibility(View.GONE);
                                    gridView.setVisibility(View.VISIBLE);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getActivity(), R.string.get_data_error, Toast.LENGTH_SHORT).show();
                        }
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(getActivity(), R.string.get_data_error, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                if (!StringUtil.isNullOrEmpty(FourFuwuActivity.mm_fuwu_type)) {
                    params.put("mm_fuwu_type", FourFuwuActivity.mm_fuwu_type);
                }
                if (!StringUtil.isNullOrEmpty(UniversityApplication.lat)) {
                    params.put("lat", UniversityApplication.lat);
                }
                if (!StringUtil.isNullOrEmpty(UniversityApplication.lng)) {
                    params.put("lng", UniversityApplication.lng);
                }
                params.put("index", String.valueOf(pageIndex));
                params.put("size", "10");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        getRequestQueue().add(request);
    }

    private void initView() {
        no_data = (ImageView) view.findViewById(R.id.no_data);
    }

    @Override
    public void onClick(View view) {

    }
    private void InitViewPager() {
        gridView = (PullToRefreshListView) view.findViewById(R.id.lstv);
        adapter = new ItemFourFuwuAdapter(lists, getActivity(), FourFuwuActivity.mm_fuwu_type);
        gridView.setMode(PullToRefreshBase.Mode.BOTH);
        gridView.setAdapter(adapter);
        gridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                IS_REFRESH = true;
                pageIndex = 1;
                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                IS_REFRESH = false;
                pageIndex++;
                initData();
            }
        });
        adapter.setOnClickContentItemListener(this);
    }


    @Override
    public void onClickContentItem(int position, int flag, Object object) {
        switch (flag) {
            case 1: {
                FuwuObj fuwuObj = lists.get(position);
                String mm_fuwu_url = fuwuObj.getMm_fuwu_url();
                if (!StringUtil.isNullOrEmpty(mm_fuwu_url)) {
                    Intent webV = new Intent(getActivity(), WebViewActivity.class);
                    webV.putExtra("strurl", mm_fuwu_url);
                    startActivity(webV);
                } else {
                    Toast.makeText(getActivity(),res.getString(R.string.zanwu_www),Toast.LENGTH_SHORT ).show();
                }

            }
            break;
            case 2: {
                FuwuObj fuwuObj = lists.get(position);
                if (fuwuObj != null) {
                    showTel(fuwuObj.getMm_fuwu_cover(), fuwuObj.getMm_fuwu_tel(), fuwuObj.getMm_fuwu_nickname(), "");
                }
            }
            break;
            case 3: {
                FuwuObj fuwuObj = lists.get(position);
                if (fuwuObj != null && !StringUtil.isNullOrEmpty(UniversityApplication.lat) && !StringUtil.isNullOrEmpty(UniversityApplication.lng) && !StringUtil.isNullOrEmpty(fuwuObj.getLat())&& !StringUtil.isNullOrEmpty(fuwuObj.getLng())) {
                    //开始导航
                    Intent naviV = new Intent(getActivity(), GPSNaviActivity.class);
                    naviV.putExtra("lat_end", fuwuObj.getLat());
                    naviV.putExtra("lng_end", fuwuObj.getLng());
                    startActivity(naviV);
                } else {
                    Toast.makeText(getActivity(), res.getString(R.string.no_location_lat_lng),Toast.LENGTH_SHORT ).show();
                }
            }
            break;
        }
    }

    private SelectTelPopWindow telphonePop;
    private String tmpTel = "";
    private void showTel(String cover, String tel, String nickname,String company) {
        tmpTel = tel;
        telphonePop = new SelectTelPopWindow(getActivity(), itemsOnClick, nickname, company, cover);
        telphonePop.showAtLocation(getActivity().findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            telphonePop.dismiss();
            switch (v.getId()) {
                case R.id.btn_sure: {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tmpTel));
                    startActivity(intent);
                }
                break;
                case R.id.btn_cancel: {}
                break;
                default:
                    break;
            }
        }
    };


}
