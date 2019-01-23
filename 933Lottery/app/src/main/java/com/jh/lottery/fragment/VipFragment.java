package com.jh.lottery.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jh.lottery.activity.AboutActivity;
import com.jh.lottery.activity.LoginActivity;
import com.jh.lottery.activity.MessageActivity;
import com.jh.lottery.activity.UserInfoActivity;
import com.jh.lottery.base.BaseFragement;
import com.jh.lottery.model.UserModel;
import com.jh.lottery.utils.GlideUtils;

import allen.com.rntestproject.R;


/**
 * Created by sangcixiang on 2018/7/26.
 */

public class VipFragment extends BaseFragement implements View.OnClickListener{

    private LinearLayout userInfoItem;
    private LinearLayout vipLevelLayout;
    private TextView loginOrOutBtn;
    private TextView name;
    private ProgressBar progressBar;
    private TextView vipLevel;
    private TextView score;
    private UserModel user;
    private ImageView avatar;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_vip;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {
        userInfoItem = find(R.id.userInfoItem);
        userInfoItem.setOnClickListener(this);
        loginOrOutBtn = find(R.id.loginOrOutBtn);
        loginOrOutBtn.setOnClickListener(this);

        avatar = find(R.id.avatar);
        name = find(R.id.name);
        progressBar = find(R.id.progressBar);
        vipLevel = find(R.id.level);
        score = find(R.id.score);
        vipLevelLayout = find(R.id.vipLevelLayout);

        find(R.id.message).setOnClickListener(this);
        find(R.id.about).setOnClickListener(this);
    }



    @Override
    public void resume(){
        if (isResume){
            user = UserModel.getUserInfo();
            if (null != user){
                loginOrOutBtn.setText("退出登录");
                loginOrOutBtn.setTextColor(getResources().getColor(R.color.springview_text_color));
                name.setText(user.getNickName());
                score.setText("用户积分："+user.getMemberScore());
                vipLevel.setText("用户等级："+user.getMemberLever());
                score.setVisibility(View.VISIBLE);
                vipLevelLayout.setVisibility(View.VISIBLE);
                progressBar.setProgress(Integer.parseInt(user.getMemberLever()));
                if (null != user.getMemberHeadImagePath() && user.getMemberHeadImagePath().isEmpty()){
                    GlideUtils.loadAvatar(getContext(),user.getMemberHeadImagePath(),avatar);
                }else {
                    Glide.with(getContext()).load(getResources().getDrawable(R.mipmap.def_header));
                }
            }else {
                loginOrOutBtn.setText("用户登录");
                loginOrOutBtn.setTextColor(getResources().getColor(R.color.mainColor));
                name.setText("尚未登录");
                score.setVisibility(View.GONE);
                vipLevelLayout.setVisibility(View.GONE);
                Glide.with(getContext()).load(getResources().getDrawable(R.mipmap.def_header));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        resume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.userInfoItem:
                if (null != user){
                    pushActivity(UserInfoActivity.class);
                }else {
                    pushActivity(LoginActivity.class);
                }

                break;
            case R.id.loginOrOutBtn:{
                if (null != user){

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("确定退出登录?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            UserModel.removeUserInfo();
                            resume();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    });
                    builder.create().show();
                }else {
                    pushActivity(LoginActivity.class);
                }
            }
            break;
            case R.id.message:
                pushActivity(MessageActivity.class);
                break;
            case R.id.about:
                pushActivity(AboutActivity.class);
                break;
            default:
                break;
        }
    }




    private void pushActivity(Class<?> cls){
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }
}
