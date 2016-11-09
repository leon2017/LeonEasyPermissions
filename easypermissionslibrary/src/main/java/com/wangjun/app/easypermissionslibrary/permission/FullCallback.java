package com.wangjun.app.easypermissionslibrary.permission;

import java.util.ArrayList;

/**
 * Author :LeonWang
 * Created  2016/11/8.17:28
 * Description:
 * E-mail:lijiawangjun@gmail.com
 */

public interface FullCallback {

    /**
     * 权限被允许
     * @param permissionsGranted
     */
    void grated(ArrayList<PermissionEnum> permissionsGranted);

    /**
     * dialog----权限被拒绝--设置进入权限管理页面
     * @param permissionsDenied
     */
    void deniedSetting(ArrayList<PermissionEnum> permissionsDenied);

    /**
     * dialog----权限被拒绝--取消设置权限调用
     * @param permissionsDenied
     */
    void deniedCancle(ArrayList<PermissionEnum> permissionsDenied);
}
