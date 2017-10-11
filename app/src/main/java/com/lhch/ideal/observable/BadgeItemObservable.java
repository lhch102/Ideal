package com.lhch.ideal.observable;

import java.util.Observable;

/**
 * @author wenjun
 * @date 2017/9/21.
 * @description
 */

public class BadgeItemObservable extends Observable {

    public void postNewObservable(Object object){
        //标识内容发生改变，必须调用，否则notif无效
        setChanged();
        //通知所有观察者
        notifyObservers(object);
    }

}
