package com.sport.ui.frag;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sport.R;
import com.sport.bean.Team;
import com.sport.bean.UserInfo;
import com.sport.common.BaseFrag;
import com.sport.ui.LoginAct;
import com.sport.ui.PublishAct;
import com.sport.ui.TeamDetailAct;
import com.sport.utils.DialogUtils;
import com.sport.widget.TopView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


public class TeamFrag extends BaseFrag {
    @BindView(R.id.mTopView)
    TopView mTopView;
    @BindView(R.id.mRecycler)
    RecyclerView mRecycler;
    @BindView(R.id.mRefresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.mType)
    Spinner mType;

    private List<Team> datas = new ArrayList<>();
    private TeamAdapter adapter;

    private String[] places = {"全部","篮球场", "羽毛球", "游泳馆","其他"};

    private UserInfo userInfo;
    @Override
    public int setContentView(Bundle savedInstanceState) {
        return R.layout.f_team;
    }

    @Override
    public void initView(View view) {
        mTopView.init("组队");
        userInfo = BmobUser.getCurrentUser(getActivity(), UserInfo.class);
        //Spinner设置
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, places);
        mType.setAdapter(adapter);
        mType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DialogUtils.showLoadingDialog(getActivity());
                switch (position) {
                    case 0:
                        getData();
                        break;
                    case 1:
                        getData("篮球场");
                        break;
                    case 2:
                        getData("羽毛球");
                        break;
                    case 3:
                        getData("游泳馆");
                        break;
                    case 4:
                        getData("其他");
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //下拉刷新
        mRefresh.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    /**
     * Bmob获取数据
     * @param type
     */
    private void getData(String type){
        BmobQuery<Team> query = new BmobQuery<>();
        query.setLimit(1000);
        if(!TextUtils.isEmpty(type)){
            query.addWhereEqualTo("type",type);
        }
        query.findObjects(getActivity(), new FindListener<Team>() {
            @Override
            public void onSuccess(List<Team> list) {
                datas.clear();
                //反转集合
                Collections.reverse(list);
                if(list!=null&&list.size()>0){
                    datas=list;
                }else {
                    toast("没有查询到消息");
                    return;
                }

                if (adapter == null) {
                    mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                DialogUtils.dismiss();
                Log.e("error--->",s);
            }
        });
    }

    private void getData() {
        getData("");
    }

    private class TeamAdapter extends RecyclerView.Adapter<ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.i_team, parent, false);
            return new ViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final Team team = datas.get(position);
            holder.mTitle.setText(team.getTitle());
            holder.mDate.setText(team.getCreatedAt());
            holder.mType.setText(team.getType());
            if (team.getComment() != null && team.getComment().size() > 0) {
                holder.mCommentNum.setText(team.getComment().size() + "");
            } else {
                holder.mCommentNum.setText("0");
            }

            //点击
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), TeamDetailAct.class);
                    intent.putExtra("team", team);
                    startActivity(intent);

                }
            });

            //长按
            holder.view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    deleteDialog(team.getObjectId(),position);
                    return false;
                }
            });

            //收藏
            final String flag = team.getFlag();
            if(flag.equals("1")){
                holder.mLike.setImageResource(R.mipmap.collect2);
            }else {
                holder.mLike.setImageResource(R.mipmap.collect1);
            }

            holder.mLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(flag.equals("1")){
                        team.setFlag("0");
                        holder.mLike.setImageResource(R.mipmap.collect1);
                        collect("0",team.getObjectId());
                    }else {
                        team.setFlag("1");
                        holder.mLike.setImageResource(R.mipmap.collect2);
                        collect("1",team.getObjectId());
                    }
                    adapter.notifyDataSetChanged();
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
        @BindView(R.id.mLike)
        ImageView mLike;

        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    private void collect(String flag,String id){
        Team team = new Team();
        team.setFlag(flag);
        team.update(getActivity(),id, new UpdateListener() {
            @Override
            public void onSuccess() {
                Log.e("success--->","success");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("error--->",s);
            }
        });
    }

    /**
     * 提示弹框
     */
    private void deleteDialog(final String id, final int pos){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
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
        team.delete(getActivity(), id, new DeleteListener() {
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

    /**
     * 发新帖按钮
     */
    @OnClick(R.id.mPublish)
    public void onClick(){
        if (userInfo != null) {
            Intent intent = new Intent(getActivity(), PublishAct.class);
            startActivityForResult(intent,111);
        } else {
            startActivity(new Intent(getActivity(), LoginAct.class));
        }

    }

    /**
     * 发帖成功回来重新刷新数据
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==111&&resultCode==getActivity().RESULT_OK){
            DialogUtils.showLoadingDialog(getActivity());
            getData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
