package com.jh.lottery.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jh.lottery.AppConfig;
import allen.com.rntestproject.R;
import com.jh.lottery.view.XLoadingDialog;
import com.jh.lottery.view.XToast;

import com.jh.lottery.base.BaseActivity;
import com.jh.lottery.model.TopicModel;
import com.jh.lottery.model.UserModel;
import com.jh.lottery.utils.HttpTool;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sangcixiang on 2018/8/6.
 */

public class CommunityReplyActivity extends BaseActivity {

    private EditText editText;
    private Button submit;
    private UserModel userModel;
    private TopicModel model;
    @Override
    public int getLayoutId() {
        return R.layout.activity_community_reply;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        userModel = UserModel.getUserInfo();
        model = (TopicModel) getIntent().getSerializableExtra("model");
    }

    @Override
    public void initView() {
        showBackButton();
        setTitle("回复");

        editText = find(R.id.editText);
        submit = find(R.id.submit);

    }

    public void submitAction(View v){

        String content = editText.getText().toString();
        if (content.isEmpty()){
            XToast.warning("回复内容不能为空!");
            return;
        }

        Map<String,String> map = new HashMap<>();
        map.put("invitationId",model.getId()+"");
        map.put("content",content);
        map.put("acctBaseId",userModel.getAcctBaseId());
        XLoadingDialog.with(this).setMessage("登录中...").show();
        HttpTool.POST(AppConfig.postReplyUrl, map, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                XLoadingDialog.with(CommunityReplyActivity.this).dismiss();
                finish();
                XToast.success("发表成功!");
            }

            @Override
            public void onError(String msg) {
                XLoadingDialog.with(CommunityReplyActivity.this).dismiss();
            }
        });

    }
}
