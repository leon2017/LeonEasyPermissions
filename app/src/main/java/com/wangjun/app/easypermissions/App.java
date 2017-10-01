package com.wangjun.app.easypermissions;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * 当前类注释:
 * <p>
 * Author : junwang <p>
 * Created: 2017/10/1.下午10:21 <P>
 * Description:
 * <p>
 * E-mail:lijiawangjun@gmail.com
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
