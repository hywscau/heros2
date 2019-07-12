package com.example.heros.annotation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.heros.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AnnotationActivity extends Activity {

    //1、声明一些必要的 int 常量
    public static final int SUNDAY = 0;
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;

    //2、声明一个注解为 WeekDays
    @WeekDays
    int currentDay = SUNDAY;

    //3、使用 @IntDef 修饰 WeekDays,参数设置为待枚举的集合
    @IntDef({SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY})

    //4、使用 @Retention(RetentionPolicy.SOURCE) 指定注解仅存在与源码中,不加入到 class 文件中
    @Retention(RetentionPolicy.SOURCE)
    public @interface WeekDays {
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);

        setCurrentDay(WEDNESDAY);

        @WeekDays int today = getCurrentDay();
        switch (today) {
            case SUNDAY:
                break;
            case MONDAY:
                break;
            default:
                break;
        }

        setNullData(null);
        setNonNullData(null);
    }

    /**
     * 参数只能传入在声明范围内的整型，不然编译通不过
     * @param currentDay
     */
    public void setCurrentDay(@WeekDays int currentDay) {
        this.currentDay = currentDay;
    }

    @WeekDays
    public int getCurrentDay() {
        return currentDay;
    }

    public void setNullData(@Nullable  String data) {

    }

    public void setNonNullData(@NonNull String data) {

    }

    //指出一个方法在被混淆的时候应该被保留。
    @Keep
    public static int setMeathNotConfuse() {
        return 1;
    }
}
