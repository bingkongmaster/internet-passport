package com.example.administrator.internetpassport;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Reference : https://bonoogi.postype.com/post/597
 */
public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("in onReceive", intent.getAction());
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Log.i("sms received", "sms received");
            StringBuilder sms = new StringBuilder();
            Bundle bundle = intent.getExtras();

            if (bundle != null) {
                Object[] pdusObj = (Object[]) bundle.get("pdus");

                SmsMessage[] messages = new SmsMessage[pdusObj.length];
                for (int i = 0; i<messages.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                }

                for (SmsMessage smsMessage : messages) {
                    sms.append(smsMessage.getMessageBody());
                }

                Log.i("sms contents", sms.toString());

                String sms_contents = sms.toString();

                Pattern six_digit = Pattern.compile("\\d{6}");

                Matcher matcher = six_digit.matcher(sms_contents);

                String verification_number = null;
                if (matcher.find()) {
                    verification_number = matcher.group(0);
                }

                Log.i("verification number", verification_number);
            }
        }
    }
}