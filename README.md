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





