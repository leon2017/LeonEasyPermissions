package com.wangjun.app.easypermissionslibrary.permission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

/**
 * Author :LeonWang
 * Created  2016/11/8.14:30
 * Description:
 * E-mail:lijiawangjun@gmail.com
 */
@SuppressLint("InlinedApi")
public enum PermissionEnum {

    BODY_SENSORS(Manifest.permission.BODY_SENSORS,"传感器"),
    READ_CALENDAR(Manifest.permission.READ_CALENDAR,"日历"),
    WRITE_CALENDAR(Manifest.permission.WRITE_CALENDAR,"日历"),
    READ_CONTACTS(Manifest.permission.READ_CONTACTS,"联系人"),
    WRITE_CONTACTS(Manifest.permission.WRITE_CONTACTS,"联系人"),
    GET_ACCOUNTS(Manifest.permission.GET_ACCOUNTS,"手机账户列表"),
    READ_EXTERNAL_STORAGE(Manifest.permission.READ_EXTERNAL_STORAGE,"内存卡"),
    WRITE_EXTERNAL_STORAGE(Manifest.permission.WRITE_EXTERNAL_STORAGE,"内存卡"),
    ACCESS_FINE_LOCATION(Manifest.permission.ACCESS_FINE_LOCATION,"定位"),
    ACCESS_COARSE_LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION,"定位"),
    RECORD_AUDIO(Manifest.permission.RECORD_AUDIO,"录音"),
    READ_PHONE_STATE(Manifest.permission.READ_PHONE_STATE,"电话"),
    CALL_PHONE(Manifest.permission.CALL_PHONE,"电话"),
    READ_CALL_LOG(Manifest.permission.READ_CALL_LOG,"电话"),
    WRITE_CALL_LOG(Manifest.permission.WRITE_CALL_LOG,"电话"),
    ADD_VOICEMAIL(Manifest.permission.ADD_VOICEMAIL,"语音信箱"),
    USE_SIP(Manifest.permission.USE_SIP,"视频"),
    PROCESS_OUTGOING_CALLS(Manifest.permission.PROCESS_OUTGOING_CALLS,"电话"),
    CAMERA(Manifest.permission.CAMERA,"相机"),
    SEND_SMS(Manifest.permission.SEND_SMS,"发送短信"),
    RECEIVE_SMS(Manifest.permission.RECEIVE_SMS,"接收短信"),
    READ_SMS(Manifest.permission.READ_SMS,"读取短信"),
    RECEIVE_WAP_PUSH(Manifest.permission.RECEIVE_WAP_PUSH,"接收Wap Push"),
    RECEIVE_MMS(Manifest.permission.RECEIVE_MMS,"接收彩信"),

    GROUP_CALENDAR(Manifest.permission_group.CALENDAR,"日历"),
    GROUP_CAMERA(Manifest.permission_group.CAMERA,"相机"),
    GROUP_CONTACTS(Manifest.permission_group.CONTACTS,"联系人"),
    GROUP_LOCATION(Manifest.permission_group.LOCATION,"位置"),
    GROUP_MICROPHONE(Manifest.permission_group.MICROPHONE,"麦克风"),
    GROUP_PHONE(Manifest.permission_group.PHONE,"电话"),
    GROUP_SENSORS(Manifest.permission_group.SENSORS,"传感器"),
    GROUP_SMS(Manifest.permission_group.SMS,"短信"),
    GROUP_STORAGE(Manifest.permission_group.STORAGE,"内存卡");

    private final String permission;
    private String name_cn;

    PermissionEnum(String permission,String name_cn) {
        this.permission = permission;
        this.name_cn = name_cn;
    }

    public static PermissionEnum fromManifestPermission(@NonNull String value) {
        for (PermissionEnum permissionEnum : PermissionEnum.values()) {
            if (value.equalsIgnoreCase(permissionEnum.permission)) {
                return permissionEnum;
            }
        }
        return null;
    }

    public String getName_cn(){
        return name_cn;
    }

    @Override
    public String toString() {
        return permission;
    }

}
