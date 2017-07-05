package com.sport.utils;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * 自定义Activity堆栈管理
 *
 */
public class ActManager {
    private static Stack<Activity> mActivityStack;
    private static ActManager mInstance;

    private ActManager() {

    }

    /**
     * 单一实例
     */
    public static ActManager getInstance() {
        if (mInstance == null) {
            mInstance = new ActManager();
        }
        return mInstance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void add(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<Activity>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity current() {
        Activity activity = mActivityStack.lastElement();
        return activity;
    }

    /**
     * 移除当前Activity（堆栈中最后一个压入的）
     */
    public void remove() {
        Activity activity = mActivityStack.lastElement();
        remove(activity);
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void pop() {
        Activity activity = mActivityStack.lastElement();
        pop(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void pop(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 移除指定的Activity
     */
    public void remove(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void pop(Class<?> cls) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                pop(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void popAll() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
    }

    /**
     * 退出应用程序
     *
     * @param context      上下文
     * @param isBackground 是否开开启后台运行
     */
    public void exit(Context context, Boolean isBackground) {
        popAll();
        // 注意，如果您有后台程序运行，请不要支持此句子
        if (!isBackground) {
            System.exit(0);
        }
    }
}
