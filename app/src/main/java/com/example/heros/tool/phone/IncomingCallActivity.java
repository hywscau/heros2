package com.example.heros.tool.phone;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import com.example.heros.R;

public class IncomingCallActivity extends Activity{

    public static final String INCOMING_CALL_NAME = "INCOMING_CALL_NAME";
    private TextView number;
    TelephonyManager tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_call);
        number = findViewById(R.id.tv_phone_number);
        number.setText(getIntent().getStringExtra("INCOMING_CALL_NAME"));
        tm = (TelephonyManager) this
                .getSystemService(Service.TELEPHONY_SERVICE);
        findViewById(R.id.btn_end_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneUtils.endPhone(IncomingCallActivity.this,tm);
            }
        });

        findViewById(R.id.btn_accept_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneUtils.autoAnswerPhone(IncomingCallActivity.this,tm);
            }
        });

    }
}
