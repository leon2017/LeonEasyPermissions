package com.wangjun.app.easypermissions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.wangjun.app.easypermissionslibrary.permission.AskagainCallback;
import com.wangjun.app.easypermissionslibrary.permission.FullCallback;
import com.wangjun.app.easypermissionslibrary.permission.PermissionEnum;
import com.wangjun.app.easypermissionslibrary.permission.PermissionHelper;
import com.wangjun.app.easypermissionslibrary.permission.PermissionManager;

import java.util.ArrayList;

/**
 * 当前类注释：permission for fragment
 * Author :LeonWang
 * Created  2016/11/17.9:23
 * Description:
 * E-mail:lijiawangjun@gmail.com
 */

public class SampleFragment extends Fragment implements View.OnClickListener{

    private Button askonepermission;
    private Button askmultipermission;
    private Button checkpermission;
    private Context mContext;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.sample_fragment,null);

        mContext = getActivity();
        checkpermission = (Button) view.findViewById(R.id.check_permission);
        askmultipermission = (Button) view.findViewById(R.id.ask_multi_permission);
        askonepermission = (Button) view.findViewById(R.id.ask_one_permission);

        this.checkpermission.setOnClickListener(this);
        this.askmultipermission.setOnClickListener(this);
        this.askonepermission.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_permission:
                PermissionEnum permissionEnum = PermissionEnum.WRITE_EXTERNAL_STORAGE;
                boolean granted = PermissionHelper.isGranted(mContext, PermissionEnum.WRITE_EXTERNAL_STORAGE);
                Toast.makeText(mContext, permissionEnum.toString() + " -----是否被允许------ [" + granted + "]", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ask_multi_permission:
                askMultiPermission();
                break;
            case R.id.ask_one_permission:
                askSinglePermission();
                break;
        }
    }

    /**
     * 单个权限的检查
     */
    private void askSinglePermission() {
        PermissionManager.with(getActivity())
                .key(900)
                .permission(PermissionEnum.WRITE_EXTERNAL_STORAGE)
                .askagain(true)
                .askagainCallback(new AskagainCallback() {
                    @Override
                    public void showRequestPermission(UserResponse response) {
                        showDialog(response);
                    }
                })
                .callback(new FullCallback() {
                    @Override
                    public void grated(ArrayList<PermissionEnum> permissionsGranted) {
                        Toast.makeText(mContext, "权限被允许了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void deniedSetting(ArrayList<PermissionEnum> permissionsDenied) {
                        PermissionHelper.openApplicationSettings(mContext, R.class.getPackage().getName());
                    }

                    @Override
                    public void deniedCancle(ArrayList<PermissionEnum> permissionsDenied) {
                        Toast.makeText(mContext, "权限被取消了", Toast.LENGTH_SHORT).show();
                    }
                })
                .ask();
    }

    /**
     * 多个权限的检查
     */
    private void askMultiPermission() {
        PermissionManager.with(getActivity())
                .key(801)
                .permission(PermissionEnum.GET_ACCOUNTS, PermissionEnum.ACCESS_FINE_LOCATION, PermissionEnum.READ_SMS)
                .askagain(true)
                .askagainCallback(new AskagainCallback() {
                    @Override
                    public void showRequestPermission(UserResponse response) {
                        showDialog(response);
                    }
                })
                .callback(new FullCallback() {
                    @Override
                    public void grated(ArrayList<PermissionEnum> permissionsGranted) {
                        Toast.makeText(mContext, "权限被允许了", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void deniedSetting(ArrayList<PermissionEnum> permissionsDenied) {
                        PermissionHelper.openApplicationSettings(mContext, R.class.getPackage().getName());
                    }

                    @Override
                    public void deniedCancle(ArrayList<PermissionEnum> permissionsDenied) {
                        Toast.makeText(mContext, "权限被取消了", Toast.LENGTH_SHORT).show();
                    }
                })
                .ask();
    }


    private void showDialog(final AskagainCallback.UserResponse response) {
        new AlertDialog.Builder(mContext)
                .setTitle("请求权限")
                .setMessage("需要调用系统权限，否则APP部分功能无法使用")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        response.result(true);
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        response.result(false);
                    }
                })
                .show();
    }
}
