package com.example.heros.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class utils {


    public void drawTextToBitmap(Context gContext, String filePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath); // 此时返回bm为空
        if (bitmap == null) {
            Log.e("hyw", "hyw" + "bitmap==null  filePath:"+filePath);
            return;
        }
        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
//        paint.setColor(gContext.getResources().getColor(R.color.Blue));
        int textSize = 18;
        paint.setTextSize(textSize * 2);
        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤一些
        Rect bounds = new Rect();
        int bitmapHeight = bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();
        int msgPadding = 10;
        Log.e("photo", "bitmapHeight:" + bitmapHeight);
        int timeX =(int)( bitmapWidth/2-paint.measureText("何勇文")/2);
        int timeY = (int) (bounds.height()) + msgPadding * 4;
        canvas.drawText("何勇文", timeX, timeY+50, paint);
        Log.e("photo", "timeX:" + timeX + " timeY:" + timeY);
        saveBitmap(bitmap, filePath);
    }



    public void saveBitmap(Bitmap bm, String fileStrPath) {
        File f = new File(fileStrPath);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            // bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
