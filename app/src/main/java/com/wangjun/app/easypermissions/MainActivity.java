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

import com.wangjun.app.easypermissionslibrary.permission.AskagainCallback;
import com.wangjun.app.easypermissionslibrary.permission.FullCallback;
import com.wangjun.app.easypermissionslibrary.permission.PermissionEnum;
import com.wangjun.app.easypermissionslibrary.permission.PermissionHelper;
import com.wangjun.app.easypermissionslibrary.permission.PermissionManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private Button askonepermission;
    private Button askmultipermission;
    private Button checkpermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        this.checkpermission = (Button) findViewById(R.id.check_permission);
        this.askmultipermission = (Button) findViewById(R.id.ask_multi_permission);
        this.askonepermission = (Button) findViewById(R.id.ask_one_permission);

        this.checkpermission.setOnClickListener(this);
        this.askmultipermission.setOnClickListener(this);
        this.askonepermission.setOnClickListener(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handleResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_permission:
                PermissionEnum permissionEnum = PermissionEnum.WRITE_EXTERNAL_STORAGE;
                boolean granted = PermissionHelper.isGranted(MainActivity.this, PermissionEnum.WRITE_EXTERNAL_STORAGE);
                Toast.makeText(MainActivity.this, permissionEnum.toString() + " -----是否被允许------ [" + granted + "]", Toast.LENGTH_SHORT).show();
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
        PermissionManager.with(MainActivity.this)
                .key(9000)
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
                        PermissionHelper.openApplicationSettings(MainActivity.this, R.class.getPackage().getName());
                    }

                    @Override
                    public void deniedCancle(ArrayList<PermissionEnum> permissionsDenied) {
                        finish();
                    }
                })
                .ask();
    }

    /**
     * 多个权限的检查
     */
    private void askMultiPermission() {
        PermissionManager.with(MainActivity.this)
                .key(800)
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
                        PermissionHelper.openApplicationSettings(MainActivity.this, R.class.getPackage().getName());
                    }

                    @Override
                    public void deniedCancle(ArrayList<PermissionEnum> permissionsDenied) {
                        finish();
                    }
                })
                .ask();
    }


    private void showDialog(final AskagainCallback.UserResponse response) {
        new AlertDialog.Builder(MainActivity.this)
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
