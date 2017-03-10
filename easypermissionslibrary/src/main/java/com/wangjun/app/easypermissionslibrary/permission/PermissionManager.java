package com.wangjun.app.easypermissionslibrary.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 当前类注释：权限统一管理类
 * <p>
 * Author :LeonWang
 * <p>
 * Created  2017/3/9.16:38
 * <p>
 * Description:
 * <p>
 * E-mail:lijiawangjun@gmail.com
 */

public class PermissionManager {

    private Context mContext;
    private PerimissionsCallback  mPerimissionsCallback;
    private ArrayList<PermissionEnum> mPermissions;
    private ArrayList<PermissionEnum> mPermissionsGranted;
    private ArrayList<PermissionEnum> mPermissionsDenied;
    private int mTag = 100;

    private PermissionManager() {
    }

    private static PermissionManager _instance;

    public static PermissionManager with(Context context) {
        if (_instance == null) {
            _instance = new PermissionManager();
        }
        _instance.init(context);
        return _instance;
    }

    private void init(Context context) {
        mContext = context;
    }


    public static void handleResult(int requestCode,
                                    @NonNull String[] permissions,
                                    @NonNull int[] grantResults) {

        if (_instance == null) return;
        if (requestCode == _instance.mTag) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    _instance.mPermissionsGranted.add(PermissionEnum.onResultPermissions(permissions[i]));
                } else {
                    _instance.mPermissionsDenied.add(PermissionEnum.onResultPermissions(permissions[i]));
                }
            }
            _instance.showResult();
        }

    }
    /**
     * 权限请求的返回状态区分
     *
     * @param tag
     * @return
     */
    public PermissionManager tag(int tag) {
        this.mTag = tag;
        return this;
    }

    public void checkAsk() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initArrayList();
            String[] permissionToAsk = permissionToAsk();
            if (permissionToAsk.length == 0) {
                showResult();
            } else {
                // TODO: 2017/3/9 检查权限---此处尚有一个bug
                // TODO: 2017/3/9  e.g.     ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                // TODO: 2017/3/9  Manifest.permission.READ_PHONE_STATE)
                ActivityCompat.requestPermissions((Activity) mContext, permissionToAsk, mTag);
            }
        } else {
            initArrayList();
            mPermissionsGranted.addAll(mPermissions);
            showResult();
        }
    }


    /**
     * @param permissions an array of permission that you need to ask
     * @return current instance
     */
    public PermissionManager permissions(ArrayList<PermissionEnum> permissions) {
        this.mPermissions = new ArrayList<>();
        this.mPermissions.addAll(permissions);
        return this;
    }


    /**
     * @param permissionEnum permission you need to ask
     * @return current instance
     */
    public PermissionManager permission(PermissionEnum permissionEnum) {
        this.mPermissions = new ArrayList<>();
        this.mPermissions.add(permissionEnum);
        return this;
    }


    /**
     * @param permissions permission you need to ask
     * @return current instance
     */
    public PermissionManager permission(PermissionEnum... permissions) {
        this.mPermissions = new ArrayList<>();
        Collections.addAll(this.mPermissions, permissions);
        return this;
    }

    public PermissionManager callback(PerimissionsCallback callback) {
        this.mPerimissionsCallback = callback;
        return this;
    }


    private void initArrayList() {
        this.mPermissionsGranted = new ArrayList<>();
        this.mPermissionsDenied = new ArrayList<>();
    }


    /**
     * 检查是否拥有权限
     * @return permission that you realy need to ask
     */
    @NonNull
    private String[] permissionToAsk() {
        ArrayList<String> permissionToAsk = new ArrayList<>();
        for (PermissionEnum permission : mPermissions) {
            if (!isGranted(permission)) {
                permissionToAsk.add(permission.getPermisson());
            } else {
                mPermissionsGranted.add(permission);
            }
        }
        return permissionToAsk.toArray(new String[permissionToAsk.size()]);
    }

    private boolean isGranted(PermissionEnum permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                ContextCompat.checkSelfPermission(mContext, permission.getPermisson()) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isGranted(Context context, PermissionEnum... permission) {
        for (PermissionEnum permissionEnum : permission) {
            if (!isGranted(context, permissionEnum)) {
                return false;
            }
        }
        return true;
    }

    private void showResult() {
        if (mPerimissionsCallback != null) {
            if (mPermissionsDenied.size() > 0) {
                mPerimissionsCallback.onDenied(mPermissionsDenied);
            } else {
                mPerimissionsCallback.onGranted(mPermissionsGranted);
            }
        }
    }


}
