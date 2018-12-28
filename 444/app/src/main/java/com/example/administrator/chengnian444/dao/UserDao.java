package com.example.administrator.chengnian444.dao;

import com.example.administrator.chengnian444.base.MyApplication;
import com.example.administrator.chengnian444.bean.UserBean;
import com.example.administrator.chengnian444.exception.ContentException;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.List;

/**
 * 用户的相关的dao
 * @author Administrator
 * @Title ${name}
 * @ProjectName 444
 * @Description: TODO
 * @date 2018/12/2815:20
 */
public class UserDao {

    /**
     * 获取所有的用户
     * @return
     */
    public List<UserBean> getAllUser() throws ContentException {
        try {
            List<UserBean> datss=MyApplication.dbUtils.findAll(Selector.from(UserBean.class));
            if(datss!=null && datss.size()>0){
                return datss;
            }
            throw new ContentException("");
        } catch (Exception e) {
            throw new ContentException("");
        }
    }


    /**
     * 进行获取本地用户
     * @return
     */
    public static UserBean getLocalUser() throws ContentException
    {
        try {
            List<UserBean> datas = MyApplication.dbUtils.findAll(Selector.from(UserBean.class)
                                                .where("isLocalUser", "=", true));
             if (datas != null && datas.size() > 0) {
                 return datas.get(0);
            } else {
                throw new ContentException("");
            }
        } catch (DbException e) {
             throw new  ContentException("");
        }
    }

    /**
     * 删除 对象
     */
    public void  deleteUser(UserBean user) {
        try {
            MyApplication.dbUtils.delete(user);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
    /**
     * 保存所有数据
     * @param user
     */
    public void  saveAll(UserBean user) {
        try {
            MyApplication.dbUtils.save(user);
        } catch ( DbException e) {
            e.printStackTrace();
        }
    }


    /**
     * 保存更新数据
     * @param user
     */
    public void  saveUpDate(UserBean user) {
        try {
            MyApplication.dbUtils.saveOrUpdate(user);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到User对象的UserId
     * @param userId
     * @return
     */
    public UserBean getUserId(String userId) throws ContentException{
        try {
            List<UserBean> datas = MyApplication.dbUtils.findAll(Selector.from(UserBean.class).where("id", "=", userId));
            if (datas != null && datas.size() > 0) {
                 return datas.get(0);
            }
            throw new ContentException("");
        } catch (DbException e) {
            e.printStackTrace();
            throw new ContentException("");
        }
    }

    /**
     * 进行更新所有用户的本地身份状态
     * @param flag
     */
    public void  updateAllUserLocalState(Boolean flag) {
        try {
            List<UserBean> userList = getAllUser();
            for (UserBean user:userList) {
                user.isLocalUser = flag;
            }
            MyApplication.dbUtils.updateAll(userList, "isLocalUser");
        } catch (ContentException e) {
            //没有用户
            e.printStackTrace();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 进行更新除了该用户所有用户的本地身份状态
     * @param flag
     */
    public void  updateExceptUserLocalState(int userId, Boolean flag) {
        try {
            List<UserBean> userList = getAllUser();
            for (UserBean user: userList) {
                if (!(user.id ==(userId))) {
                    //设置其他用户为false
                    user.isLocalUser = false;
                } else {
                    //对应的userId用户为flag
                    user.isLocalUser = flag;
                }
            }
            MyApplication.dbUtils.updateAll(userList);
        } catch (ContentException e) {
            //没有用户
            e.printStackTrace();
        } catch (DbException e) {
        }
    }

}
