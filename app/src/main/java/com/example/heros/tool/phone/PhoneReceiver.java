package com.example.heros.tool.phone;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneReceiver extends BroadcastReceiver {

    String TAG = "PhoneReceiver";
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        TelephonyManager tm = (TelephonyManager) context
//                .getSystemService(Service.TELEPHONY_SERVICE);
//        switch (tm.getCallState()) {
//            case TelephonyManager.CALL_STATE_OFFHOOK:// 电话打进来接通状态；电话打出时首先监听到的状态。
//                Log.e("onCallStateChanged", "CALL_STATE_OFFHOOK");
//                break;
//            case TelephonyManager.CALL_STATE_RINGING:// 电话打进来状态
//                Log.e("onCallStateChanged", "CALL_STATE_RINGING");
//                PhoneUtils.autoAnswerPhone(context,tm);
//                break;
//            case TelephonyManager.CALL_STATE_IDLE:// 不管是电话打出去还是电话打进来都会监听到的状态。
//                Log.e("onCallStateChanged", "CALL_STATE_IDLE");
//                break;
//        }
//    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e("PhoneListener",action);
        if (action.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {

        } else {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Service.TELEPHONY_SERVICE);
            String incoming_number = "";
            switch (tm.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING:
                    incoming_number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    try {
                        //3s后再开启activity，是为了挡在系统的接听界面之前
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent tmpI = new Intent(context, IncomingCallActivity.class);
                    tmpI.putExtra(IncomingCallActivity.INCOMING_CALL_NAME,incoming_number);
//                    tmpI.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(tmpI);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
            }
        }
    }
}
