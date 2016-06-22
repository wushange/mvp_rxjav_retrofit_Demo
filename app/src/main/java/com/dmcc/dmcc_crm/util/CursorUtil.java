package com.dmcc.dmcc_crm.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.dmcc.dmcc_crm.bean.User;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wushange on 2016/06/14.
 */
public class CursorUtil {
    /**
     * 获取库Phon表字段
     **/
    private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Photo.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_LAST_UPDATED_TIMESTAMP};
    /**
     * 联系人显示名称
     **/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;

    /**
     * 电话号码
     **/
    private static final int PHONES_NUMBER_INDEX = 1;

    /**
     * 头像ID
     **/
    private static final int PHONES_PHOTO_ID_INDEX = 2;

    /**
     * 联系人的ID
     **/
    private static final int PHONES_CONTACT_ID_INDEX = 3;

    /**
     * 得到手机通讯录联系人信息
     **/
    public static List<User> getPhoneContacts(Context context) {
        List<User> users = new ArrayList<User>();
        ContentResolver resolver = context.getContentResolver();

        // 获取手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);

        LogUtil.e("联系人" + phoneCursor.getCount());
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {

                //得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                //当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;

                //得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);


              LogUtil.e( "最后修改时间-====="+ phoneCursor.getString(4));
                User user = new User();
                user.setUserName(contactName);
                user.setUserPhone(phoneNumber);
                users.add(user);
            }

            phoneCursor.close();
        }
        LogUtil.e("联系人" + users.size() + users.toString());// 根据a-z进行排序源数据

        return users;
    }
}
