package com.example.lyfuelgas.common.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

public class CallPhone {

    public static void call(Activity activity,String phone) {
        if(TextUtils.isEmpty(phone))
            return;
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
}
