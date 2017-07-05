package com.sport.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sport.R;
import com.sport.bean.Comment;
import com.sport.bean.Team;
import com.sport.bean.UserInfo;
import com.sport.common.BaseAct;
import com.sport.utils.DialogUtils;
import com.sport.utils.KeyBoardUtils;
import com.sport.widget.CircleTransform;
import com.sport.widget.NoScrollListView;
import com.sport.widget.TopView;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;


public class TeamDetailAct extends BaseAct {
    @BindView(R.id.mTopView)
    TopView mTopView;
    @BindView(R.id.mTitle)
    TextView mTitle;
    @BindView(R.id.mType)
    TextView mType;
    @BindView(R.id.mDate)
    TextView mDate;
    @BindView(R.id.mNick)
    TextView mNick;
    @BindView(R.id.mImg)
    ImageView mImg;
    @BindView(R.id.mContent)
    TextView mContent;
    @BindView(R.id.mList)
    NoScrollListView mList;
    @BindView(R.id.input)
    EditText input;
    @BindView(R.id.isShowInputComment)
    RelativeLayout isShowInputComment;
    @BindView(R.id.mCommentLayout)
    LinearLayout mCommentLayout;

    private Team team;

    List<Comment> comments = new ArrayList<>();

    private Comment cm;
    private UserInfo userInfo;
    private CommentAdapter adapter;

    @Override
    public int setContentView(Bundle savedInstanceState) {
        return R.layout.a_team_detail;
    }

    @Override
    public void initView() {
        userInfo = BmobUser.getCurrentUser(this, UserInfo.class);
        mTopView.init("组队详情", new TopView.OnClickTopListener() {
            @Override
            public void onLeft() {
                finish();
            }
        });
        team = (Team) getIntent().getSerializableExtra("team");
        mTitle.setText(team.getTitle());
        mType.setText(team.getType());
        mDate.setText(team.getCreatedAt());
        mNick.setText(team.getNick());
        mContent.setText(team.getContent());

        if (team.getHeadImg() != null && !TextUtils.isEmpty(team.getHeadImg())) {
            Picasso.with(this).load(team.getHeadImg()).transform(new CircleTransform()).into(mImg);
        } else {
            mImg.setImageResource(R.mipmap.item_user_default);
        }
        if(team.getComment()!=null&&team.getComment().size()>0){
            comments = team.getComment();
            //反转集合
            Collections.reverse(comments);
            adapter=new CommentAdapter();
            mList.setAdapter(adapter);
        }

        /**
         * 点击屏幕评论消失
         */
        isShowInputComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShowInputComment.setVisibility(View.GONE);
                mCommentLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    public class CommentAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return comments.size();
        }

        @Override
        public Object getItem(int position) {
            return comments.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.i_comment, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final Comment comment = comments.get(position);

            if (comment.getHeadImg() != null && !TextUtils.isEmpty(comment.getHeadImg())) {
                Picasso.with(TeamDetailAct.this).load(comment.getHeadImg()).transform(new CircleTransform()).into(holder.mUserImg);
            } else {
                holder.mUserImg.setImageResource(R.mipmap.item_user_default);
            }
            holder.mNick.setText(comment.getNick());
            holder.mDate.setText(comment.getCommentDate());
            holder.mContent.setText(comment.getComment());

            if (TextUtils.isEmpty(comment.getToNick())) {
                holder.mReplyLayout.setVisibility(View.GONE);
            } else {
                holder.mReplyLayout.setVisibility(View.VISIBLE);
                holder.mReplyMan.setText("回复：" + comment.getToNick());
                holder.mReplyContent.setText(comment.getToComment());
            }

            holder.mReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (userInfo != null) {
                        isShowInputComment.setVisibility(View.VISIBLE);
                        input.setHint("回复：" + comment.getNick());
                        mCommentLayout.setVisibility(View.GONE);
                        cm = comment;
                    } else {
                        startActivity(new Intent(TeamDetailAct.this, LoginAct.class));
                    }
                }
            });

            return convertView;
        }

        public class ViewHolder {
            @BindView(R.id.mUserImg)
            ImageView mUserImg;
            @BindView(R.id.mNick)
            TextView mNick;
            @BindView(R.id.mDate)
            TextView mDate;
            @BindView(R.id.mContent)
            TextView mContent;
            @BindView(R.id.mReplyMan)
            TextView mReplyMan;
            @BindView(R.id.mReplyContent)
            TextView mReplyContent;
            @BindView(R.id.mReplyLayout)
            LinearLayout mReplyLayout;
            @BindView(R.id.mReply)
            LinearLayout mReply;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


    @OnClick({R.id.sendButton, R.id.mCommentLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sendButton://评论发送按钮
                String s = input.getText().toString();
                input.setText("");
                if (TextUtils.isEmpty(s)) {
                    KeyBoardUtils.show(TeamDetailAct.this, isShowInputComment);
                    Toast.makeText(TeamDetailAct.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                publishComment(s);
                break;
            case R.id.mCommentLayout:
                if (userInfo != null) {
                    isShowInputComment.setVisibility(View.VISIBLE);
                    input.setHint("评论");
                    mCommentLayout.setVisibility(View.GONE);
                } else {
                    startActivity(new Intent(TeamDetailAct.this, LoginAct.class));
                }
                break;
        }
    }

    /**
     * 发送评论
     *
     * @param s
     */
    private void publishComment(String s) {
        DialogUtils.showLoadingDialog(this);
        final Team teamTemp = new Team();
        final Comment commentTemp = new Comment();
        commentTemp.setNick(userInfo.getNick());
        commentTemp.setHeadImg(userInfo.getHeadImgUrl());
        commentTemp.setComment(s);

        Date d = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        commentTemp.setCommentDate(format.format(d));

        if (cm == null) {
            commentTemp.setToNick("");
            commentTemp.setToHeadImg("");
            commentTemp.setToComment("");
        } else {
            commentTemp.setToNick(cm.getNick());
            commentTemp.setToHeadImg(cm.getHeadImg());
            commentTemp.setToComment(cm.getComment());
        }

        List<Comment> c = new ArrayList<>();
        c.add(commentTemp);

        teamTemp.addAll("comment", c);

        teamTemp.update(this, team.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                if (team.getComment() == null) {
                    List<Comment> comment = new ArrayList<Comment>();
                    comment.add(commentTemp);
                    team.setComment(comment);

                    comments = comment;
                    adapter = new CommentAdapter();
                    mList.setAdapter(adapter);
                } else {
                    team.getComment().add(0, commentTemp);
                    adapter.notifyDataSetChanged();
                }

                isShowInputComment.setVisibility(View.GONE);
                mCommentLayout.setVisibility(View.VISIBLE);
                cm = null;
                DialogUtils.dismiss();
            }

            @Override
            public void onFailure(int i, String s) {
                Log.e("error--->", s);
                isShowInputComment.setVisibility(View.GONE);
                mCommentLayout.setVisibility(View.VISIBLE);
                cm = null;
                DialogUtils.dismiss();
            }
        });
    }
}
