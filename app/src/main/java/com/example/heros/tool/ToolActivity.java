package com.example.heros.tool;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.heros.R;
import com.example.heros.tool.phone.IncomingCallActivity;

public class ToolActivity extends Activity  implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);
        initViews();

    }

    private void initViews() {
        findViewById(R.id.btn_link_reverse).setOnClickListener(this);
        findViewById(R.id.btn_home).setOnClickListener(this);
        findViewById(R.id.btn_force_stop).setOnClickListener(this);
        findViewById(R.id.btn_phone).setOnClickListener(this);
        findViewById(R.id.btn_download).setOnClickListener(this);
    }

    private void printnode(Node node) {
        while (node.next != null) {
            Log.e("hyw","node:"+node.index);
            node = node.next;
        }
        Log.e("hyw","node:"+node.index);
    }

    public Node reverse(Node node) {
        Node prev = null;
        Node now = node;
        while (now != null) {
            Node next = now.next;
            now.next = prev;
            prev = now;
            now = next;
        }

        return prev;
    }

    public Node reverse2(Node node, Node prev) {
        if (node.next == null) {
            node.next = prev;
            return node;
        } else {
            Node re = reverse2(node.next, node);
            node.next = prev;
            return re;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_link_reverse:
                Node node5 = new Node(5,null);
                Node node4 = new Node(4,node5);
                Node node3 = new Node(3,node4);
                Node node2 = new Node(2,node3);
                Node node1 = new Node(1,node2);
                //printnode(node1);
                //printnode(reverse(node1));
                printnode(reverse2(node1,null));
                break;
            case R.id.btn_home:
                backToHome();
                break;
            case R.id.btn_force_stop:
//                ActivityManager am = (ActivityManager) getSystemService(Activity.ACTIVITY_SERVICE);
//                am.killBackgroundProcesses("com.example.runoob");
                try {
                    SuUtil.kill("com.example.runoob");
                } catch (Exception e) {
                    Log.e("hyw","Exception:"+e.getMessage());
                }

                break;
            case R.id.btn_phone:
                Intent tmpI = new Intent(this, IncomingCallActivity.class);
                tmpI.putExtra(IncomingCallActivity.INCOMING_CALL_NAME,"188");
//                    tmpI.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivity(tmpI);
                break;
            case R.id.btn_download:
                DownloadUtils download = new DownloadUtils(ToolActivity.this);
//                download.download("http://gdown.baidu.com/data/wisegame/55dc62995fe9ba82/jinritoutiao_448.apk","testAPk");
                download.download("http://apk.wsdl.vivo.com.cn/appstore/developer/soft/20190123/201901231140356079733.apk","testAPk");
                break;
        }
    }

    public static class Node {

        int index;
        Node next;

        public Node(int index, Node next) {
            this.index = index;
            this.next = next;
        }
    }

    private void backToHome() {
        Intent intent = new Intent();
        // 为Intent设置Action、Category属性
        intent.setAction(Intent.ACTION_MAIN);// "android.intent.action.MAIN"
        intent.addCategory(Intent.CATEGORY_HOME); //"android.intent.category.HOME"
        startActivity(intent);
    }
}
