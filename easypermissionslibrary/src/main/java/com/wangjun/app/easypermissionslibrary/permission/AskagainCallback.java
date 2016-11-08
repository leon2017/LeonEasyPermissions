package com.wangjun.app.easypermissionslibrary.permission;

/**
 * Author :LeonWang
 * Created  2016/11/8.13:11
 * Description:
 * E-mail:lijiawangjun@gmail.com
 */
public interface AskagainCallback {

    void showRequestPermission(UserResponse response);

    interface UserResponse {
        void result(boolean askagain);
    }

}
