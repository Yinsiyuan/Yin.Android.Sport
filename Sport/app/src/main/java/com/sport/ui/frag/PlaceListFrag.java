package com.sport.ui.frag;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.sport.R;
import com.sport.bean.Places;
import com.sport.common.BaseFrag;
import com.sport.event.PlaceEvent;
import com.sport.ui.WebViewAct;
import com.sport.utils.DialogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PlaceListFrag extends BaseFrag {

    @BindView(R.id.mList)
    ListView mList;

    @BindView(R.id.mRefresh)
    SwipeRefreshLayout mRefresh;

    private List<Places.ResultsBean> datas = new ArrayList<>();
    private List<Places.ResultsBean> results = new ArrayList<>();
    private PlaceAdapter adapter;
    private String url;
    private String type;
    private String city;
    private int page = 0;

    private int lastVisiable;

    @Override
    public int setContentView(Bundle savedInstanceState) {
        return R.layout.f_place_list;
    }

    @Override
    public void initView(View view) {
        EventBus.getDefault().register(this);

        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(mRefresh.isRefreshing()){
                    datas.clear();
                    page=0;
                    getData(type,city);
                }

            }
        });

        mList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(adapter!=null&& scrollState==AbsListView.OnScrollListener.SCROLL_STATE_IDLE &&lastVisiable==adapter.getCount()-1){
                    if(results.size()==10){
                        page++;
                        DialogUtils.showLoadingDialog(getActivity());
                        getData(type,city);
                    }else if(results.size()<10){
                        toast("没有更多数据了");
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastVisiable=firstVisibleItem+visibleItemCount-1;
            }
        });

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebViewAct.class);
                intent.putExtra("url",datas.get(position).getDetail_info().getDetail_url());
                intent.putExtra("title","场地详情");
                startActivity(intent);
            }
        });
    }

    @Subscribe
    public void onPlaceEvent(PlaceEvent event){
        datas.clear();
        results.clear();
        type=event.getType();
        city=event.getCity();
        DialogUtils.showLoadingDialog(getActivity());
        getData(type,city);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                if(adapter==null){
                    adapter=new PlaceAdapter();
                    mList.setAdapter(adapter);
                }else {
                    mRefresh.setRefreshing(false);
                    adapter.notifyDataSetChanged();
                }
                DialogUtils.dismiss();
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 访问接口获取新闻数据
     */
    private void getData(String type,String city) {
        url = "http://api.map.baidu.com/place/v2/search?query="+type+"&page_size=10&page_num="+page+"&scope=2&region="+city+"&output=json&ak=OxEa9ehZo1UKvuFpVplPjnlvnjTQej8C";
        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mRefresh.setRefreshing(false);
                DialogUtils.dismiss();
                Log.e("error--->", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Places places = JSON.parseObject(response.body().string(), Places.class);
                    results = places.getResults();
                    datas.addAll(results);
                    handler.sendEmptyMessageDelayed(0,1000);
                }
            }
        });
    }

    /**
     * 寻常列表Adapter
     */
    public class PlaceAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.i_place, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final Places.ResultsBean place = datas.get(position);

            holder.mTitle.setText(place.getName());
            holder.mContent.setText("地址:"+place.getAddress());

            if(place.getTelephone()==null){
                holder.mTel.setText("该场没留电话");
                holder.mTelImg.setVisibility(View.INVISIBLE);
            }else {
                holder.mTel.setText("电话:"+place.getTelephone());
                holder.mTelImg.setVisibility(View.VISIBLE);
            }


            holder.mTelImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+place.getTelephone()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

            return convertView;

        }

        //复用条目节约内存
        public class ViewHolder {
            @BindView(R.id.mTitle)
            TextView mTitle;
            @BindView(R.id.mContent)
            TextView mContent;
            @BindView(R.id.mTel)
            TextView mTel;
            @BindView(R.id.mTelImg)
            ImageView mTelImg;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
