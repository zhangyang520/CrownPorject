package com.jh.lottery.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jh.lottery.base.BaseActivity;
import com.jh.lottery.model.UserModel;
import com.jh.lottery.utils.GlideUtils;

import allen.com.rntestproject.R;

/**
 * Created by sangcixiang on 2018/8/7.
 */

public class UserInfoActivity extends BaseActivity {

    private ImageView avatar;


    @Override
    public int getLayoutId() {
        return R.layout.activity_userinfo;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {

        showBackButton();
        setTitle("用户信息");
        avatar = find(R.id.avatar);

    }

    @Override
    protected void onResume() {
        super.onResume();
        UserModel user = UserModel.getUserInfo();

        if (null != user.getMemberHeadImagePath() && user.getMemberHeadImagePath().isEmpty() && user.getMemberHeadImagePath().length() > 10){
            GlideUtils.loadAvatar(this,user.getMemberHeadImagePath(),avatar);
        }
    }

    public void clickAction(View v){
        switch (v.getId()){

            case R.id.avatarItem:
                JumpToActivity(AvatarActivity.class);
                break;

            case R.id.nickName:
                JumpToActivity(ChangeNicknameActivity.class);
                break;

            case R.id.password:
                JumpToActivity(ChangePasswordActivity.class);
                break;

            case R.id.vip:
                JumpToActivity(VipLevelActivity.class);
                break;

                default:break;

        }
    }



}
