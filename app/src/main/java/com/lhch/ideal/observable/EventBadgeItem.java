package com.lhch.ideal.observable;

import java.util.Observer;

/**
 * @author wenjun
 * @date 2017/9/21.
 * @description
 */

public class EventBadgeItem {
    private static EventBadgeItem sInstance;
    private BadgeItemObservable biObservable;

    //双锁单例
    public static EventBadgeItem getInstance() {
        if (sInstance == null) {
            synchronized (EventBadgeItem.class) {
                if (sInstance == null) {
                    sInstance = new EventBadgeItem();
                }
            }
        }
        return sInstance;
    }

    public EventBadgeItem() {
        this.biObservable = new BadgeItemObservable();
    }

    //注册
    public void register(Observer o) {
        biObservable.addObserver(o);
    }

    //注销
    public void unRegister(Observer o) {
        biObservable.deleteObserver(o);
    }

    //发送
    public void post(Object object) {
        biObservable.postNewObservable(object);
    }
}
