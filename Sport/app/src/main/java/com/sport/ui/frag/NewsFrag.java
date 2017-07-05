package com.sport.ui.frag;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.sport.R;
import com.sport.bean.News;
import com.sport.common.BaseFrag;
import com.sport.ui.WebViewAct;
import com.sport.utils.DialogUtils;
import com.sport.widget.TopView;
import com.squareup.picasso.Picasso;

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


public class NewsFrag extends BaseFrag {
    @BindView(R.id.mTopView)
    TopView mTopView;

    @BindView(R.id.mRecycler)
    RecyclerView mRecycler;

    @BindView(R.id.mRefresh)
    SwipeRefreshLayout mRefresh;

    private String url;
    private int page = 1;
    private int num = 10;

    private News news;
    private List<News.NewslistEntity> datas=new ArrayList<>();
    private NewsAdapter adapter;

    @Override
    public int setContentView(Bundle savedInstanceState) {
        return R.layout.f_news;
    }

    @Override
    public void initView(View view) {
        mTopView.init("新闻");
        DialogUtils.showLoadingDialog(getActivity());
        getData();

        //下拉刷新
        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(mRefresh.isRefreshing()){
                    datas.clear();
                    page=1;
                    getData();
                }

            }
        });

        //加载更多
        mRecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager)recyclerView.getLayoutManager();
                int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                int totalItemCount = manager.getItemCount();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem == (totalItemCount -1)) {
                    if(news.newslist.size()==10){
                        page++;
                        DialogUtils.showLoadingDialog(getActivity());
                        getData();
                    }else if(news.newslist.size()<10){
                        toast("没有更多数据");
                    }

                }
            }



            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                if(adapter==null){
                    mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                    adapter=new NewsAdapter();
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
     * 访问接口获取新闻数据
     */
    private void getData() {
        url = "http://apis.baidu.com/txapi/tiyu/tiyu?num=" + num + "&page=" + page;
        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(url)
                .header("apikey", "0edeabaafc3bae597d30623254fb92a4")
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
                    news = JSON.parseObject(response.body().string(), News.class);
                    if(news!=null&&news.newslist!=null){
                        datas.addAll(news.newslist);
                    }
                    handler.sendEmptyMessageDelayed(0,1000);
                }
            }
        });
    }

    /**
     * 新闻列表Adapter
     */
    private class NewsAdapter extends RecyclerView.Adapter<ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.i_news, parent, false);
            return new ViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final News.NewslistEntity entity = datas.get(position);
            String picUrl = entity.picUrl;
            holder.mTitle.setText(entity.title);
            holder.mContent.setText(entity.description);
            holder.mDate.setText(entity.ctime);

            if (!TextUtils.isEmpty(picUrl)) {
                Picasso.with(getActivity()).load(picUrl).into(holder.mImg);
            } else {
                holder.mImg.setImageResource(R.mipmap.error_img);
            }

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), WebViewAct.class);
                    intent.putExtra("url",entity.url);
                    intent.putExtra("title","新闻详情");
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mImg)
        ImageView mImg;
        @BindView(R.id.mTitle)
        TextView mTitle;
        @BindView(R.id.mContent)
        TextView mContent;

        @BindView(R.id.mDate)
        TextView mDate;

        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            ButterKnife.bind(this, itemView);
        }
    }

}
