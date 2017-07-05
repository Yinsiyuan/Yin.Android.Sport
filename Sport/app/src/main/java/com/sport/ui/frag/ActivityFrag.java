package com.sport.ui.frag;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.sport.R;
import com.sport.bean.Shop;
import com.sport.common.BaseFrag;
import com.sport.ui.WebViewAct;
import com.sport.utils.DialogUtils;
import com.sport.widget.city.CityPickerActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ActivityFrag extends BaseFrag {
    @BindView(R.id.mRecycler)
    RecyclerView mRecycler;
    @BindView(R.id.mRefresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.mCity)
    TextView mCity;

    private String url;
    private int page = 0;
    private String type = "体育用品";
    private String city="天津";

    private List<Shop.ResultsEntity> datas = new ArrayList<>();
    private List<Shop.ResultsEntity> results = new ArrayList();
    private ShopAdapter adapter;

    @Override
    public int setContentView(Bundle savedInstanceState) {
        return R.layout.f_activity;
    }

    @Override
    public void initView(View view) {
        //下拉刷新
        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(mRefresh.isRefreshing()){
                    datas.clear();
                    page=0;
                    getData(city);
                }
            }
        });

//        监听列表滑动加载更多
        mRecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager)recyclerView.getLayoutManager();
                int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                int totalItemCount = manager.getItemCount();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem == (totalItemCount -1)) {
                    if(results.size()==10){
                        page++;
                        DialogUtils.showLoadingDialog(getActivity());
                        getData(city);
                    }else if(results.size()<10){
                        toast("没有更多数据");
                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });

        DialogUtils.showLoadingDialog(getActivity());
        getData(city);
    }

    /**
     * 选择城市
     */
    @OnClick(R.id.mSelectCity)
    public void onClick() {
        startActivityForResult(new Intent(getActivity(), CityPickerActivity.class),SELECT_CITY);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                if(adapter==null){
                    adapter=new ShopAdapter();
                    mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecycler.setAdapter(adapter);
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
     * 访问接口获取活动列表
     */
    private void getData(String city) {
        url = "http://api.map.baidu.com/place/v2/search?query=" + type + "&page_size=10&page_num=" + page + "&scope=2&region=" + city + "&output=json&ak=OxEa9ehZo1UKvuFpVplPjnlvnjTQej8C";
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
                    Shop shop = JSON.parseObject(response.body().string(), Shop.class);
                    results = shop.results;
                    datas.addAll(results);
                    handler.sendEmptyMessageDelayed(0, 1000);
                }
            }
        });
    }

    /**
     * 商铺Adapter
     */
    private class ShopAdapter extends RecyclerView.Adapter<ShopHolder> {
        @Override
        public ShopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.i_shop, parent, false);
            return new ShopHolder(inflate);
        }

        @Override
        public void onBindViewHolder(ShopHolder holder, int position) {
            final Shop.ResultsEntity entity = datas.get(position);
            holder.mTitle.setText(entity.name);
            holder.mAddress.setText("地址："+entity.address);
            if(entity.detail_info.comment_num!=null){
                holder.mComment.setText(entity.detail_info.comment_num+"个评价");
            }

            holder.mRatingBar.setRating(Float.parseFloat(entity.detail_info.overall_rating));
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), WebViewAct.class);
                    Log.e("url--->",entity.detail_info.detail_url);
                    intent.putExtra("url",entity.detail_info.detail_url);
                    intent.putExtra("title","商铺详情");
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    public class ShopHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mTitle)
        TextView mTitle;
        @BindView(R.id.mAddress)
        TextView mAddress;
        @BindView(R.id.mComment)
        TextView mComment;
        @BindView(R.id.mRatingBar)
        RatingBar mRatingBar;


        View view;
        public ShopHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            view=itemView;
        }
    }

    /**
     * 接受选择的城市
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==getActivity().RESULT_OK&&requestCode==SELECT_CITY){
            city = data.getStringExtra("city");
            mCity.setText(city);
            DialogUtils.showLoadingDialog(getActivity());
            datas.clear();
            page=0;
            getData(city);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
