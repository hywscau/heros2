package com.example.heros.process;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.heros.MyApplication;
import com.example.heros.R;
import com.example.heros.process.models.AndroidAppProcess;
import com.example.heros.process.models.ProcessManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FrontProcessActivity extends Activity implements View.OnClickListener {
    private ScheduledExecutorService scheduledThreadPool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_process);
        initViews();
        scheduledThreadPool = Executors.newScheduledThreadPool(5);

    }

    private void initViews() {
       findViewById(R.id.btn_running_task).setOnClickListener(this);
        findViewById(R.id.btn_running_process).setOnClickListener(this);
        findViewById(R.id.btn_lifestyle).setOnClickListener(this);
        findViewById(R.id.btn_usage_state_manager).setOnClickListener(this);
        findViewById(R.id.btn_accessibility).setOnClickListener(this);
        findViewById(R.id.btn_proc).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        scheduledThreadPool = null;
        scheduledThreadPool = Executors.newScheduledThreadPool(5);
        switch (v.getId()) {
            case R.id.btn_running_task:
                scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        getRunningTask(FrontProcessActivity.this,"com.example.heros");
                    }
                }, 1, 3,TimeUnit.SECONDS);

                break;
            case R.id.btn_running_process:
                scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        getRunningAppProcesses(FrontProcessActivity.this,"com.example.heros");
                    }
                }, 1,3, TimeUnit.SECONDS);
                break;
            case R.id.btn_lifestyle:
                getApplicationValue((MyApplication) getApplication());
                break;
            case R.id.btn_usage_state_manager:
                scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        queryUsageStats(FrontProcessActivity.this,"com.example.heros");
                    }
                }, 1,3, TimeUnit.SECONDS);
                break;
            case R.id.btn_accessibility:
                scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        getFromAccessibilityService(FrontProcessActivity.this,"com.example.heros");
                    }
                }, 1,3, TimeUnit.SECONDS);

                break;
            case R.id.btn_proc:
                scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("hyw","scheduleAtFixedRate:");
                        getLinuxCoreInfo(FrontProcessActivity.this,"com.example.heros");
                    }
                }, 1,3, TimeUnit.SECONDS);

                break;
        }
    }

    /**
     * 方法1：通过getRunningTasks判断App是否位于前台，此方法在5.0以上失效
     *
     * @param context     上下文参数
     * @param packageName 需要检查是否位于栈顶的App的包名
     * @return
     */
    public static boolean getRunningTask(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        Log.e("hyw","cn.getPackageName():"+cn.getPackageName());
        return !TextUtils.isEmpty(packageName) && packageName.equals(cn.getPackageName());
    }


    /**
     * 方法2：通过getRunningAppProcesses的IMPORTANCE_FOREGROUND属性判断是否位于前台，当service需要常驻后台时候，此方法失效,
     * 在小米 Note上此方法无效，在Nexus上正常
     *
     * @param context     上下文参数
     * @param packageName 需要检查是否位于栈顶的App的包名
     * @return
     */
    public static boolean getRunningAppProcesses(Context context, String packageName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND ) {
                Log.e("hyw"," appProcess.processName:"+ appProcess.processName);
                return true;
            }
        }
        return false;
    }

    /**
     * 方法3：通过ActivityLifecycleCallbacks来批量统计Activity的生命周期，来做判断，此方法在API 14以上均有效，但是需要在Application中注册此回调接口
     * 必须：
     * 1. 自定义Application并且注册ActivityLifecycleCallbacks接口
     * 2. AndroidManifest.xml中更改默认的Application为自定义
     * 3. 当Application因为内存不足而被Kill掉时，这个方法仍然能正常使用。虽然全局变量的值会因此丢失，但是再次进入App时候会重新统计一次的
     * @param myApplication
     * @return
     */

    public static boolean getApplicationValue(MyApplication myApplication) {
        myApplication.registerLifecycle();
        return myApplication.getAppCount() > 0;
    }

    /**
     * 方法4：通过使用UsageStatsManager获取，此方法是ndroid5.0A之后提供的API
     * 必须：
     * 1. 此方法只在android5.0以上有效
     * 2. AndroidManifest中加入此权限<uses-permission xmlns:tools="http://schemas.android.com/tools" android:name="android.permission.PACKAGE_USAGE_STATS"
     * tools:ignore="ProtectedPermissions" />
     * 3. 打开手机设置，点击安全-高级，在有权查看使用情况的应用中，为这个App打上勾
     *
     * @param context     上下文参数
     * @param packageName 需要检查是否位于栈顶的App的包名
     * @return
     */

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static boolean queryUsageStats(Context context, String packageName) {
        class RecentUseComparator implements Comparator<UsageStats> {
            @Override
            public int compare(UsageStats lhs, UsageStats rhs) {
                return (lhs.getLastTimeUsed() > rhs.getLastTimeUsed()) ? -1 : (lhs.getLastTimeUsed() == rhs.getLastTimeUsed()) ? 0 : 1;
            }
        }
        RecentUseComparator mRecentComp = new RecentUseComparator();
        long ts = System.currentTimeMillis();
        UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService("usagestats");
        List<UsageStats> usageStats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, ts - 1000 * 10, ts);
        if (usageStats == null || usageStats.size() == 0) {
            if (!HavaPermissionForTest(context)) {
                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                Toast.makeText(context, "权限不够\n请打开手机设置，点击安全-高级，在有权查看使用情况的应用中，为这个App打上勾", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        Collections.sort(usageStats, mRecentComp);
        String currentTopPackage = usageStats.get(0).getPackageName();
        Log.e("hyw","currentTopPackage:"+currentTopPackage);
        if (currentTopPackage.equals(packageName)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否有用权限
     *
     * @param context 上下文参数
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static boolean HavaPermissionForTest(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
            return (mode == AppOpsManager.MODE_ALLOWED);
        } catch (PackageManager.NameNotFoundException e) {
            return true;
        }
    }

    /**
     * 方法5：通过Android自带的无障碍功能，监控窗口焦点的变化，进而拿到当前焦点窗口对应的包名
     * 必须：
     * 1. 创建ACCESSIBILITY SERVICE INFO 属性文件
     * 2. 注册 DETECTION SERVICE 到 ANDROIDMANIFEST.XML
     * AccessibilityService 不再需要轮询的判断当前的应用是不是在前台，系统会在窗口状态发生变化的时候主动回调，耗时和资源消耗都极小
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean getFromAccessibilityService(Context context, String packageName) {
        if (DetectService.isAccessibilitySettingsOn(context) == true) {
            DetectService detectService = DetectService.getInstance();
            String foreground = detectService.getForegroundPackage();
            Log.e("hyw", "**方法五** 当前窗口焦点对应的包名为： =" + foreground);
            return packageName.equals(foreground);
        } else {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            Toast.makeText(context, R.string.accessbiliityNo, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * 方法6：无意中看到乌云上有人提的一个漏洞，Linux系统内核会把process进程信息保存在/proc目录下，使用Shell命令去获取的他，再根据进程的属性判断是否为前台
     * 7.0以下，不能获取系统应用状态
     * @param packageName 需要检查是否位于栈顶的App的包名
     */
    public static boolean getLinuxCoreInfo(Context context, String packageName) {
        List<AndroidAppProcess> processes = ProcessManager.getRunningForegroundApps(context);
        for (AndroidAppProcess appProcess : processes) {
            if ( appProcess.foreground) {
              Log.e("hyw","getLinuxCoreInfo:"+appProcess.getPackageName());
            }
        }
        return false;
    }
}
