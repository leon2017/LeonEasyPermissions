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
     * 限被拒绝在权限管理页面进行调用
     * @param permissionsDenied
     */
    void deniedSetting(ArrayList<PermissionEnum> permissionsDenied);

    /**
     * 权限被拒绝--取消设置权限调用
     * @param permissionsDenied
     */
    void deniedCancle(ArrayList<PermissionEnum> permissionsDenied);
}
