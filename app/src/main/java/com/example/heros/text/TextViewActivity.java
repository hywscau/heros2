package com.example.heros.text;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.example.heros.R;

import org.greenrobot.eventbus.EventBus;

public class TextViewActivity extends Activity implements View.OnClickListener {

    TextView mText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text2);

        mText = findViewById(R.id.tv_text);
        mText.setText("         中国 ");
        mText.setText(getString(R.string.Chinese2));

        findViewById(R.id.btn_text_color).setOnClickListener(this);
        findViewById(R.id.btn_bg_color).setOnClickListener(this);
        findViewById(R.id.btn_text_size).setOnClickListener(this);
        findViewById(R.id.btn_text_style).setOnClickListener(this);
        findViewById(R.id.btn_delete_line).setOnClickListener(this);
        findViewById(R.id.btn_under_line).setOnClickListener(this);
        findViewById(R.id.btn_drawable).setOnClickListener(this);
        EventBus.getDefault();
    }

    private void changeTextColor() {
        //1、字体颜色设置（ForegroundColorSpan）
        //先构造SpannableString
        SpannableString spanString = new SpannableString("欢迎光临Harvic的博客");
        //再构造一个改变字体颜色的Span
        ForegroundColorSpan span = new ForegroundColorSpan(Color.BLUE);
        //将这个Span应用于指定范围的字体
        spanString.setSpan(span, 1, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置给EditText显示出来
        mText.setText(spanString);
    }

    private void changeTextBgColor() {
        SpannableString spanString = new SpannableString("欢迎光临Harvic的博客");
        BackgroundColorSpan span = new BackgroundColorSpan(Color.YELLOW);
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mText.setText(spanString);
    }

    private void changeTextSize() {
        SpannableString spanString = new SpannableString("欢迎光临Harvic的博客");
        AbsoluteSizeSpan span = new AbsoluteSizeSpan(16);
        spanString.setSpan(span, 2, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mText.setText(spanString);
    }

    private void chageTextStyle() {
        SpannableString spanString = new SpannableString("欢迎光临Harvic的博客");
        StyleSpan span = new StyleSpan(Typeface.BOLD_ITALIC);
        spanString.setSpan(span, 1, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mText.setText(spanString);

    }

    private void setDeleteLine() {
        SpannableString spanString = new SpannableString("欢迎光临Harvic的博客");
        StrikethroughSpan span = new StrikethroughSpan();
        spanString.setSpan(span, 2, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mText.setText(spanString);
    }

    private void setUnderLine() {
        SpannableString spanString = new SpannableString("欢迎光临Harvic的博客");
        UnderlineSpan span = new UnderlineSpan();
        spanString.setSpan(span, 1, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mText.setText(spanString);

    }

    private void setDrawable() {
        SpannableString spanString = new SpannableString("欢迎光临Harvic的博客");
        Drawable d = getResources().getDrawable(R.drawable.ic_launcher);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        spanString.setSpan(span, 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mText.setText(spanString);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_text_color:
                changeTextColor();
                break;
            case R.id.btn_bg_color:
                changeTextBgColor();
                break;
            case R.id.btn_text_size:
                changeTextSize();
                break;
            case R.id.btn_text_style:
                chageTextStyle();
                break;
            case R.id.btn_delete_line:
                setDeleteLine();
                break;
            case R.id.btn_under_line:
                setUnderLine();
                break;
            case R.id.btn_drawable:
                setDrawable();
                break;
        }
    }
}
