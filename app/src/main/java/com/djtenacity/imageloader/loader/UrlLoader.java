package com.djtenacity.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.djtenacity.imageloader.request.BitmapRequest;
import com.djtenacity.imageloader.utils.BitmapDecoder;
import com.djtenacity.imageloader.utils.ImageViewHelper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Comment:网络图片
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/11/2
 */
public class UrlLoader extends AbstractLoader {

    @Override
    protected Bitmap onLoad(final BitmapRequest request) {
        Log.w("loadImage","UrlLoader request");


        //先下载  后读取
        downloadImgByUrl(request.getImageUrl(),getCache(request.getImageUriMD5()));
        BitmapDecoder decoder=new BitmapDecoder() {
            @Override
            public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(getCache(request.getImageUriMD5()).getAbsolutePath(),options);
            }
        };
        return decoder.decodeBitmap(ImageViewHelper.getImageViewWidth(request.getImageView())
                ,ImageViewHelper.getImageViewHeight(request.getImageView()));
    }

    public static boolean downloadImgByUrl(String urlStr, File file)
    {
        FileOutputStream fos = null;
        InputStream is = null;
        try
        {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            is = conn.getInputStream();
            fos = new FileOutputStream(file);
            byte[] buf = new byte[512];
            int len = 0;
            while ((len = is.read(buf)) != -1)
            {
                fos.write(buf, 0, len);
            }
            fos.flush();
            return true;

        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (is != null)
                    is.close();
            } catch (IOException e)
            {
            }

            try
            {
                if (fos != null)
                    fos.close();
            } catch (IOException e)
            {
            }
        }

        return false;

    }
    private File getCache(String unipue)
    {
        File file=new File(Environment.getExternalStorageDirectory(),"ImageLoader");
        if(!file.exists())
        {
            file.mkdir();
        }
        return new File(file,unipue);
    }

}



/**
 *
 *   HttpsURLConnection connection = null;
 InputStream inputStream = null;

 try {
 connection = (HttpsURLConnection) (new URL(request.getImageUrl())).openConnection();

 //转化成bufferInputStream流
 inputStream = new BufferedInputStream(connection.getInputStream());
 //reset之后回到这里,,,BufferedInputStream重写了available方法
 inputStream.mark(inputStream.available());
 //解码图片
 final InputStream finalInputStream = inputStream;

 bitmapDecoder = new BitmapDecoder() {

 //options是入参出参对象
 @Override
 public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {

 Bitmap bitmap = BitmapFactory.decodeStream(finalInputStream, null, options);
 if (options.inJustDecodeBounds) {
 //第一次读,bitmap只有图片宽高的信息,,,在BitmapDecoder里面,设置inJustDecodeBounds=true
 try {
 //读完之后必须为第二次读图片做准备-->流的回位,将流重置
 finalInputStream.reset();
 } catch (IOException e) {
 e.printStackTrace();
 }
 } else {
 //在BitmapDecoder里面,设置inJustDecodeBounds=true
 try {
 finalInputStream.close();
 } catch (IOException e) {
 e.printStackTrace();
 }
 }
 return bitmap;
 }
 };

  * */