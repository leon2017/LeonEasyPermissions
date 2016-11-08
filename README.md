# LeonEasyPermissions
android6.0权限请求工具类的简单封装


# 权限检查工具类的封装 #
---
>本项目主要业务逻辑参考：[https://github.com/rebus007/PermissionUtils](https://github.com/rebus007/PermissionUtils)
>
>我只是针对个人的爱好对源码进行改动，如有不妥之处，请联系我删除

## 演示效果图 ##

![](https://github.com/leon2017/LeonEasyPermissions/blob/master/screenshot/screen.gif)

## 部分代码 ##

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


---

    /**
     * 多个权限的检查
     */
    private void askMtutiPermission() {
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
