package com.gordarg.messageforwarder;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;

public class SmsSender {
    private static String SENDING = "SMS_SENDING";
    private static String RECIEVED = "SMS_SENDING";
    private static String SENT = "SMS_SENT";
    private static String DELIVERED = "SMS_DELIVERED";
    private static int MAX_SMS_MESSAGE_LENGTH = 160;
    static public void sendSMS(Context context, String phoneNumber, String message) {
        PendingIntent piSent = PendingIntent.getBroadcast(context,
                0, new Intent(SENT), 0);
        PendingIntent piDelivered = PendingIntent.getBroadcast(context,
                0, new Intent(DELIVERED), 0);
        SmsManager smsManager = SmsManager.getDefault();
        int length = message.length();

//        Toast.makeText(context, RECIEVED, Toast.LENGTH_LONG).show();

        try {
            if (length > MAX_SMS_MESSAGE_LENGTH) {
                ArrayList<String> messagelist = smsManager.divideMessage(message);
                smsManager.sendMultipartTextMessage(phoneNumber, null,
                        messagelist, null, null);
            } else {
                smsManager.sendTextMessage(phoneNumber, null, message,
                        piSent, piDelivered);
            }

//            Toast.makeText(context, SENDING, Toast.LENGTH_LONG).show();

        } catch (Exception exp) {
            Toast.makeText(context, exp.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}
