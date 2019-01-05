package com.example.administrator.chengnian444.utils;

import android.app.Activity;
import com.example.administrator.chengnian444.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 *  app的管理類
 * @author Administrator
 * @Title ${name}
 * @ProjectName 444
 * @Description: TODO
 * @date 2019/1/516:12
 */
public class AppManager {

    /**
     * 构造函数 设置
     */
    private  AppManager(){

    }
    /**
     * activity集合類
     */
    private static List<Activity> activityList=new ArrayList<Activity>();


    public static List<Activity> getActivityList() {
        return activityList;
    }

    public static void setActivityList(List<Activity> activityList) {
        AppManager.activityList = activityList;
    }

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 删除其他的activity
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    /**
     * 消失所有的activity
     */
    public static void finishAll() {
        for(Activity activity:activityList){
            if(activity instanceof MainActivity){
                continue;
            }
            activity.finish();
        }
    }
}
