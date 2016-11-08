package com.wangjun.app.easypermissionslibrary.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.wangjun.app.easypermissionslibrary.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Author :LeonWang
 * Created  2016/11/8.14:19
 * Description:权限管理工具类
 * E-mail:lijiawangjun@gmail.com
 */
public class PermissionManager {

    private static PermissionManager instance;

    private Context context;

    private FullCallback fullCallback;
    private AskagainCallback askagainCallback;

    private boolean askagain = false;

    private ArrayList<PermissionEnum> permissions;
    private ArrayList<PermissionEnum> permissionsGranted;
    private ArrayList<PermissionEnum> permissionsDenied;
    private ArrayList<PermissionEnum> permissionsDeniedForever;
    private ArrayList<PermissionEnum> permissionToAsk;

    private int key = PermissionConstant.KEY_PERMISSION;

    /**
     * @param context current context
     * @return current instance
     */
    public static PermissionManager with(Context context) {
        if (instance == null) {
            instance = new PermissionManager();
        }
        instance.init(context);
        return instance;
    }

    public static void handleResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (instance == null) return;
        if (requestCode == instance.key) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    instance.permissionsGranted.add(PermissionEnum.fromManifestPermission(permissions[i]));
                } else {
                    boolean permissionsDeniedForever = ActivityCompat.shouldShowRequestPermissionRationale((Activity) instance.context, permissions[i]);
                    if (!permissionsDeniedForever) {
                        instance.permissionsDeniedForever.add(PermissionEnum.fromManifestPermission(permissions[i]));
                    }
                    instance.permissionsDenied.add(PermissionEnum.fromManifestPermission(permissions[i]));
                    instance.permissionToAsk.add(PermissionEnum.fromManifestPermission(permissions[i]));
                }
            }
            if (instance.permissionToAsk.size() != 0 && instance.askagain) {
                instance.askagain = false;
                if (instance.askagainCallback != null && instance.permissionsDeniedForever.size() != instance.permissionsDenied.size()) {
                    instance.askagainCallback.showRequestPermission(new AskagainCallback.UserResponse() {
                        @Override
                        public void result(boolean askagain) {
                            if (askagain) {
                                instance.ask();
                            } else {
                                instance.showResult();
                            }
                        }
                    });
                } else {
                    instance.ask();
                }
            } else {
                instance.showResult();
            }
        }
    }

    private void init(Context context) {
        this.context = context;
    }

    /**
     * @param permissions an array of permission that you need to ask
     * @return current instance
     */
    public PermissionManager permissions(ArrayList<PermissionEnum> permissions) {
        this.permissions = new ArrayList<>();
        this.permissions.addAll(permissions);
        return this;
    }

    /**
     * @param permission permission you need to ask
     * @return current instance
     */
    public PermissionManager permission(PermissionEnum permission) {
        this.permissions = new ArrayList<>();
        this.permissions.add(permission);
        return this;
    }

    /**
     * @param permissions permission you need to ask
     * @return current instance
     */
    public PermissionManager permission(PermissionEnum... permissions) {
        this.permissions = new ArrayList<>();
        Collections.addAll(this.permissions, permissions);
        return this;
    }

    /**
     * @param askagain ask again when permission not granted
     * @return current instance
     */
    public PermissionManager askagain(boolean askagain) {
        this.askagain = askagain;
        return this;
    }

    public PermissionManager callback(FullCallback fullCallback) {
        this.fullCallback = fullCallback;
        return this;
    }

    public PermissionManager askagainCallback(AskagainCallback askagainCallback) {
        this.askagainCallback = askagainCallback;
        return this;
    }

    public PermissionManager key(int key) {
        this.key = key;
        return this;
    }

    public void ask() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initArray();
            String[] permissionToAsk = permissionToAsk();
            if (permissionToAsk.length == 0) {
                showResult();
            } else {
                ActivityCompat.requestPermissions((Activity) context, permissionToAsk, key);
            }
        } else {
            initArray();
            permissionsGranted.addAll(permissions);
            showResult();
        }
    }

    /**
     * @return permission that you realy need to ask
     */
    @NonNull
    private String[] permissionToAsk() {
        ArrayList<String> permissionToAsk = new ArrayList<>();
        for (PermissionEnum permission : permissions) {
            if (!PermissionHelper.isGranted(context, permission)) {
                permissionToAsk.add(permission.toString());
            } else {
                permissionsGranted.add(permission);
            }
        }
        return permissionToAsk.toArray(new String[permissionToAsk.size()]);
    }



    /**
     * init permissions ArrayList
     */
    private void initArray() {
        this.permissionsGranted = new ArrayList<>();
        this.permissionsDenied = new ArrayList<>();
        this.permissionsDeniedForever = new ArrayList<>();
        this.permissionToAsk = new ArrayList<>();
    }

    private void showResult() {
        if (fullCallback != null)
            if (permissionsDenied.size() > 0 ) {
                todoDenied(permissionsDenied);
            }else {
                //允许进行调用
                fullCallback.grated(permissionsGranted);
            }
    }

    private void todoDenied(final ArrayList<PermissionEnum> permissionsDenied) {
        StringBuilder msgCN = new StringBuilder();
        for (int i = 0; i < permissionsDenied.size(); i++) {

            if (i == permissionsDenied.size()-1) {
                msgCN.append(permissionsDenied.get(i).getName_cn());
            } else {
                msgCN.append(permissionsDenied.get(i).getName_cn()+",");
            }
        }
        if (context == null) {
            return;
        }
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setMessage(String.format(context.getResources().getString(R.string.permission_explain), msgCN.toString()))
                .setPositiveButton(R.string.per_setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fullCallback.deniedSetting(permissionsDenied);
                    }
                })
                .setNegativeButton(R.string.per_cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fullCallback.deniedCancle(permissionsDenied);
                    }
                }).create();
        alertDialog.show();
    }

}