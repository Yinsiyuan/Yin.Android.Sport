package com.sport.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sport.R;
import com.sport.bean.Team;
import com.sport.bean.UserInfo;
import com.sport.common.BaseAct;
import com.sport.utils.DialogUtils;
import com.sport.widget.TopView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;


public class PublishDetailAct extends BaseAct {
    @BindView(R.id.mTopView)
    TopView mTopView;
    @BindView(R.id.mRecycler)
    RecyclerView mRecycler;
    @BindView(R.id.mRefresh)
    SwipeRefreshLayout mRefresh;

    private List<Team> datas = new ArrayList<>();
    private TeamAdapter adapter;

    @Override
    public int setContentView(Bundle savedInstanceState) {
        return R.layout.a_publish_detail;
    }

    @Override
    public void initView() {
        mTopView.init("我的发布", new TopView.OnClickTopListener() {
            @Override
            public void onLeft() {
                finish();
            }
        });

        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(mRefresh.isRefreshing()){
                    getData();
                }

            }
        });
        DialogUtils.showLoadingDialog(this);
        getData();
    }

    private void getData(){
        UserInfo userInfo= BmobUser.getCurrentUser(this, UserInfo.class);
        BmobQuery<Team> query = new BmobQuery<>();
        query.setLimit(1000);
        query.addWhereEqualTo("nick",userInfo.getNick());
        query.findObjects(this, new FindListener<Team>() {
            @Override
            public void onSuccess(List<Team> list) {
                datas.clear();
                //反转集合
                Collections.reverse(list);
                if(list!=null&&list.size()>0){
                    datas=list;
                }else {
                    toast("没有查询到消息");
                }

                if (adapter == null) {
                    mRecycler.setLayoutManager(new LinearLayoutManager(PublishDetailAct.this));
                    adapter = new TeamAdapter();
                    mRecycler.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                    mRefresh.setRefreshing(false);
                }
                DialogUtils.dismiss();

            }

            @Override
            public void onError(int i, String s) {
                Log.e("error-->",s);
                DialogUtils.dismiss();
            }
        });
    }

    private class TeamAdapter extends RecyclerView.Adapter<ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(PublishDetailAct.this).inflate(R.layout.i_team, parent, false);
            return new ViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final Team team = datas.get(position);
            holder.mTitle.setText(team.getTitle());
            holder.mDate.setText(team.getCreatedAt());
            holder.mType.setText(team.getType());
            if (team.getComment() != null && team.getComment().size() > 0) {
                holder.mCommentNum.setText(team.getComment().size() + "");
            } else {
                holder.mCommentNum.setText("0");
            }

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PublishDetailAct.this, TeamDetailAct.class);
                    intent.putExtra("team", team);
                    startActivity(intent);

                }
            });

            holder.view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    deleteDialog(team.getObjectId(),position);
                    return false;
                }
            });

        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mTitle)
        TextView mTitle;
        @BindView(R.id.mDate)
        TextView mDate;
        @BindView(R.id.mCommentNum)
        TextView mCommentNum;
        @BindView(R.id.mType)
        TextView mType;

        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 提示弹框
     */
    private void deleteDialog(final String id, final int pos){
        AlertDialog.Builder builder=new AlertDialog.Builder(PublishDetailAct.this);
        builder.setTitle("提示");
        builder.setMessage("确定删除此消息吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteData(id,pos);
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
    }

    private void deleteData(String id, final int pos){
        Team team = new Team();
        team.delete(PublishDetailAct.this, id, new DeleteListener() {
            @Override
            public void onSuccess() {
                datas.remove(pos);
                adapter.notifyDataSetChanged();
                toast("删除成功");
            }

            @Override
            public void onFailure(int i, String s) {
                toast("删除失败");
            }
        });
    }
}
