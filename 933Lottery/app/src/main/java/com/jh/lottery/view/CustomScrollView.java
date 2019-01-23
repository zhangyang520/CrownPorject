package com.jh.lottery.view;

/**
 * Created by sangcixiang on 2018/7/26.
 */

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;


public class CustomScrollView extends ScrollView {

    private static final String TAG ="MyScrollView" ;
    private static final long RESTORE_TIME = 300;//回弹的时间
    public static final int DRAG_RATE=2;//mChildView移动的距离=手指移动的的距离/DRAG_RATE
    private View mChildView;
    private float mDownY;
    private Rect mRect=new Rect();//用于保存子View的位置
    private boolean isRestoring=false;//是否正在回弹

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount()>0){//保证要有子View
            mChildView = getChildAt(0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isRestoring){
            handleTouchEvent(ev);//监听触摸事件
        }
        //因为我们要做的是当scrollView下滑或上滑到尽头还能继续滑动
        //所以只有这两种情况我们才自己处理
        //其他情况交由父类默认实现
        return super.onTouchEvent(ev);
    }

    private void handleTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();
                int deltaY= (int) (moveY-mDownY);
                if (isNeedLoadMore()){
                    if (mRect.isEmpty()){//如果没有记录子view位置，那么我们记录
                        mRect.set(mChildView.getLeft(), mChildView.getTop()
                                , mChildView.getRight(), mChildView.getBottom());
                    }
                    mChildView.layout(mChildView.getLeft(), mChildView.getTop()+deltaY/DRAG_RATE,
                            mChildView.getRight(), mChildView.getBottom()+deltaY/DRAG_RATE);
                }
                mDownY=moveY;//更新位置
                break;
            case MotionEvent.ACTION_UP://当手指释放的时候，需要回弹到原来的位置
                mDownY=0;
                if (isNeedRestore()){
                    restoreState();
                }
                break;
        }
    }

    /**
     * 恢复到原来的位置
     */
    private void restoreState() {
        //TranslateAnimation移动的相对位置
        TranslateAnimation ta=new TranslateAnimation(0,0,0,mRect.top- mChildView.getTop());
        ta.setDuration(RESTORE_TIME);
        mChildView.startAnimation(ta);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isRestoring=true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isRestoring=false;
                mChildView.clearAnimation();
                //保证回到原来的位置
                mChildView.layout(mRect.left,mRect.top,mRect.right,mRect.bottom);
                mRect.setEmpty();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    /**
     * 判断是否需要回弹
     * @return
     */
    private boolean isNeedRestore() {
        return !mRect.isEmpty();//如果保存过位置，说明需要回弹
    }

    /**
     * 是否已经滑动尽头了
     * @return
     */
    private boolean isNeedLoadMore() {
        int offset = mChildView.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        if (scrollY==0||offset==scrollY){//当scrollY==0表示向下滑滑到尽头了
            return true;            // offset==scrollY 表示向上滑滑到尽头了
        }
        return  false;
    }


}