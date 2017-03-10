package com.wangjun.app.easypermissions;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wangjun.app.easypermissionslibrary.permission.PerUtils;
import com.wangjun.app.easypermissionslibrary.permission.PerimissionsCallback;
import com.wangjun.app.easypermissionslibrary.permission.PermissionEnum;
import com.wangjun.app.easypermissionslibrary.permission.PermissionManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Button checkpermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        this.checkpermission = (Button) findViewById(R.id.check_permission);
        this.checkpermission.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_permission:
                checkPermissions();
                break;
        }
    }

    /**
     * 权限检查
     */
    private void checkPermissions() {
        PermissionManager
                .with(MainActivity.this)
                .tag(1000)
                .permission(PermissionEnum.READ_EXTERNAL_STORAGE, PermissionEnum.WRITE_EXTERNAL_STORAGE, PermissionEnum.CAMERA)
                .callback(new PerimissionsCallback() {
                    @Override
                    public void onGranted(ArrayList<PermissionEnum> grantedList) {
                        Toast.makeText(mContext, "权限被允许", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDenied(ArrayList<PermissionEnum> deniedList) {
                        Toast.makeText(mContext, "权限被拒绝", Toast.LENGTH_SHORT).show();
                        PermissionDenied(deniedList);
                    }
                })
                .checkAsk();
    }

    private void PermissionDenied(final ArrayList<PermissionEnum> permissionsDenied) {
        StringBuilder msgCN = new StringBuilder();
        for (int i = 0; i < permissionsDenied.size(); i++) {

            if (i == permissionsDenied.size() - 1) {
                msgCN.append(permissionsDenied.get(i).getName_cn());
            } else {
                msgCN.append(permissionsDenied.get(i).getName_cn() + ",");
            }
        }
        if (mContext == null) {
            return;
        }

        AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                .setMessage(String.format(mContext.getResources().getString(R.string.permission_explain), msgCN.toString()))
                .setCancelable(false)
                .setPositiveButton(R.string.per_setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PerUtils.openApplicationSettings(mContext, R.class.getPackage().getName());
                    }
                })
                .setNegativeButton(R.string.per_cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mContext, "点击了取消", Toast.LENGTH_SHORT).show();
                    }
                }).create();
        alertDialog.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handleResult(requestCode, permissions, grantResults);
    }


}
