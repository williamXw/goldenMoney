package com.loan.golden.cash.money.loan.app.util;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.BitmapCompat;
import androidx.core.widget.NestedScrollView;

import com.loan.golden.cash.money.loan.R;
import com.loan.golden.cash.money.loan.app.App;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URL;

import me.hgj.mvvmhelper.net.interception.logging.util.LogUtils;

public class ImgUtils {

    //将Bitmap转换成File
    static File outputFile;
    static String newImgName = System.currentTimeMillis() + ".jpg";

    public static File saveBitmapFile(Bitmap bitmap) {
        File file = new File("/sdcard/pic/+" + System.currentTimeMillis() + ".jpg");//将要保存图片的路径
        try {
            File dir = new File(App.Companion.getInstance().getExternalFilesDir(null).getPath());
            if (!dir.exists()) {
                dir.mkdirs();
            }
            outputFile = new File(App.Companion.getInstance().getFilesDir(), newImgName);
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
            }
            FileOutputStream out = App.Companion.getInstance().openFileOutput(newImgName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
//            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//            bos.flush();
//            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFile;
    }

    /**
     * 通过降低图片的质量来压缩图片
     *
     * @param bitmap  要压缩的图片位图对象
     * @param maxSize 压缩后图片大小的最大值,单位KB
     * @return 压缩后的图片位图对象
     */
    public static Bitmap compressByQuality(Bitmap bitmap, int maxSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        LogUtils.Companion.debugInfo("图片压缩前大小：" + baos.toByteArray().length + "byte");
        boolean isCompressed = false;
        while (baos.toByteArray().length / 1024 > maxSize) {
            quality -= 10;
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            System.out.println("质量压缩到原来的" + quality + "%时大小为："
                    + baos.toByteArray().length + "byte");
            isCompressed = true;
        }
        LogUtils.Companion.debugInfo("图片压缩后大小：" + baos.toByteArray().length + "byte");
        if (isCompressed) {
            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(
                    baos.toByteArray(), 0, baos.toByteArray().length);
            recycleBitmap(bitmap);
            return compressedBitmap;
        } else {
            return bitmap;
        }
    }

    /**
     * 回收位图对象
     *
     * @param bitmap
     */
    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            System.gc();
            bitmap = null;
        }
    }

    /**
     * 获得Bitmap的占用内存的大小
     * getAllocationByteCount() 、 getByteCount()
     *
     * @param bitmap
     * @return Bitmap占用内存大小 Byte
     */
    public static Integer getBitmapSize(Bitmap bitmap) {
        return BitmapCompat.getAllocationByteCount(bitmap);
    }


    /**
     * todo 将网络资源图片转换为Bitmap
     *
     * @param imgUrl 网络资源图片路径
     * @return Bitmap
     * 该方法调用时要放在子线程中
     */
    public static Bitmap netUrlToBitmap(String imgUrl) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new URL(imgUrl).openStream(), 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 1024);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

    /**
     * 照片转byte二进制
     *
     * @param imagepath 需要转byte的照片路径
     * @return 已经转成的byte
     * @throws Exception
     */
    public static byte[] readStream(String imagepath) throws Exception {
        FileInputStream fs = new FileInputStream(imagepath);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while (-1 != (len = fs.read(buffer))) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        fs.close();
        return outStream.toByteArray();
    }

    // 二进制转字符串
    public static String byte2hex(byte[] b) {
        StringBuffer sb = new StringBuffer();
        String tmp = "";
        for (int i = 0; i < b.length; i++) {
            tmp = Integer.toHexString(b[i] & 0XFF);
            if (tmp.length() == 1) {
                sb.append("0" + tmp);
            } else {
                sb.append(tmp);
            }
        }
        return sb.toString();
    }

    /**
     * 压缩图片之后保存为文件
     *
     * @param filePath     原始图片的完整路径
     * @param storeImgPath 压缩之后要存储的图片的完整路径
     * @return boolean
     * @author Doraemon
     * @time 2014年6月27日下午5:10:19
     */
    public static boolean saveCompressImg(String filePath, String storeImgPath) {
        Bitmap bm = getSmallBitmap(filePath);
        if (bm == null) return false;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(storeImgPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            return bm.compress(Bitmap.CompressFormat.JPEG, 40, stream);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @param filePath
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static byte[] bitmap2Byte(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //把bitmap100%高质量压缩 到 output对象里
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }

    public static String byte2Base64(byte[] imageByte) {
        if (null == imageByte) return null;
        return Base64.encodeToString(imageByte, Base64.DEFAULT);
    }


    /**
     * 判断是否包含某字段
     *
     * @param c
     * @param fieldName
     * @return
     */
    public static boolean hasField(Class c, String fieldName) {
        Field[] fields = c.getDeclaredFields();
        for (Field f : fields) {
            if (fieldName.equals(f.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 图片压缩-质量压缩
     *
     * @param filePath 源图片路径
     * @return 压缩后的路径
     */

    public static String compressImage(String filePath) {
        //原文件
        File oldFile = new File(filePath);
        //压缩文件路径 照片路径/
        String targetPath = oldFile.getPath();
        int quality = 50;//压缩比例0-100
        Bitmap bm = getSmallBitmap(filePath);//获取一定尺寸的图片
        int degree = getRotateAngle(filePath);//获取相片拍摄角度

        if (degree != 0) {//旋转照片角度，防止头像横着显示
            bm = setRotateAngle(degree, bm);
        }
        File outputFile = new File(targetPath);
        try {
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
            } else {
                outputFile.delete();
            }
            FileOutputStream out = new FileOutputStream(outputFile);
            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
            LogUtils.Companion.debugInfo(bm.getByteCount() + "");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return filePath;
        }
        return outputFile.getPath();
    }

    /**
     * 旋转图片角度
     *
     * @param angle
     * @param bitmap
     * @return
     */
    public static Bitmap setRotateAngle(int angle, Bitmap bitmap) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(angle);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;

    }

    /**
     * 获取图片的旋转角度
     *
     * @param filePath
     * @return
     */
    public static int getRotateAngle(String filePath) {
        int rotate_angle = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate_angle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate_angle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate_angle = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate_angle;
    }

    /**
     * Activity截屏
     */
    public static Bitmap takeScreenShot(Activity pActivity, Toolbar toolbar) {
        View view = pActivity.getWindow().getDecorView();
        // 设置是否可以进行绘图缓存
        view.setDrawingCacheEnabled(true);
        // 如果绘图缓存无法，强制构建绘图缓存
        view.buildDrawingCache();
        // 返回这个缓存视图
        Bitmap bitmap = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        // 测量屏幕宽和高
        view.getWindowVisibleDisplayFrame(frame);
        int stautsHeight = frame.top + toolbar.getHeight();
        Point point = new Point();
        pActivity.getWindowManager().getDefaultDisplay().getSize(point);
        int width = point.x;
        int height = point.y;
        // 根据坐标点和需要的宽和高创建bitmap
        bitmap = Bitmap.createBitmap(bitmap, 0, stautsHeight, width, height - stautsHeight);
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    /**
     * 保存图片到公共目录
     * 29 以下，需要提前申请文件读写权限
     * 29及29以上的，不需要权限
     * 保存的文件在 DCIM 目录下
     *
     * @param context 上下文
     * @param bitmap  需要保存的bitmap
     * @param format  图片格式
     * @param quality 压缩的图片质量
     * @param recycle 完成以后，是否回收Bitmap，建议为true
     * @return 文件的 uri
     */
    @Nullable
    public static Uri saveAlbum(Context context, Bitmap bitmap, Bitmap.CompressFormat format, int quality, boolean recycle) {
        String suffix;
        if (Bitmap.CompressFormat.JPEG == format)
            suffix = "JPG";
        else
            suffix = format.name();
        String fileName = System.currentTimeMillis() + "_" + quality + "." + suffix;
        if (Build.VERSION.SDK_INT < 29) {
            if (!isGranted(context)) {
                Log.e("ImageUtils", "save to album need storage permission");
                return null;
            }
            File picDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            File destFile = new File(picDir, fileName);
            if (!save(bitmap, destFile, format, quality, recycle))
                return null;
            Uri uri = null;
            if (destFile.exists()) {
                uri = Uri.parse("file://" + destFile.getAbsolutePath());
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(uri);
                context.sendBroadcast(intent);
            }
            return uri;
        } else {
            // Android 10 使用
            Uri contentUri;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else
                contentUri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM + "/");
            // 告诉系统，文件还未准备好，暂时不对外暴露
            contentValues.put(MediaStore.MediaColumns.IS_PENDING, 1);
            Uri uri = context.getContentResolver().insert(contentUri, contentValues);
            if (uri == null) return null;
            try (OutputStream os = context.getContentResolver().openOutputStream(uri)) {
                bitmap.compress(format, quality, os);
                // 告诉系统，文件准备好了，可以提供给外部了
                contentValues.clear();
                contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0);
                context.getContentResolver().update(uri, contentValues, null, null);
                return uri;
            } catch (Exception e) {
                e.printStackTrace();
                // 失败的时候，删除此 uri 记录
                context.getContentResolver().delete(uri, null, null);
                return null;
            }
            // ignore
        }
    }

    private static boolean save(Bitmap bitmap, File file, Bitmap.CompressFormat format, int quality, boolean recycle) {
        if (isEmptyBitmap(bitmap)) {
            Log.e("ImageUtils", "bitmap is empty.");
            return false;
        }
        if (bitmap.isRecycled()) {
            Log.e("ImageUtils", "bitmap is recycled.");
            return false;
        }
        if (!createFile(file, true)) {
            Log.e("ImageUtils", "create or delete file <$file> failed.");
            return false;
        }
        OutputStream os = null;
        boolean ret = false;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            ret = bitmap.compress(format, quality, os);
            if (recycle && !bitmap.isRecycled()) bitmap.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null)
                    os.close();
            } catch (IOException e) {
                // ignore
            }
        }
        return ret;
    }

    private static boolean isEmptyBitmap(Bitmap bitmap) {
        return bitmap == null || bitmap.isRecycled() || bitmap.getWidth() == 0 || bitmap.getHeight() == 0;
    }

    private static boolean createFile(File file, boolean isDeleteOldFile) {
        if (file == null) return false;
        if (file.exists()) {
            if (isDeleteOldFile) {
                if (!file.delete()) return false;
            } else
                return file.isFile();
        }
        if (!createDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean createDir(File file) {
        if (file == null) return false;
        if (file.exists())
            return file.isDirectory();
        else
            return file.mkdirs();
    }

    private static boolean isGranted(Context context) {
        return (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }

    //根据uri获取真实路径
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 截取scrollview的屏幕
     **/
    public static Bitmap getScrollViewBitmap(NestedScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取listView实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundResource(R.color.colorWhite);
        }

        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        // 测试输出
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("/sdcard/screen_test.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (null != out) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            // TODO: handle exception
        }
        return bitmap;
    }

    /**
     * 加载本地大图片
     *
     * @param localPath
     * @param context
     * @return
     */
    public static Bitmap decodeBitmap(String localPath, Context context) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 置为true,仅仅返回图片的分辨率
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(localPath, opts);
        // 得到原图的分辨率;
        int srcHeight = opts.outHeight;
        int srcWidth = opts.outWidth;
        // 得到设备的分辨率
        int screenHeight = getScreenHeight(context);
        int screenWidth = getScreenWidth(context);
        // 通过比较得到合适的比例值;
        // 屏幕的 宽320 高 480 ,图片的宽3000 ,高是2262  3000/320=9  2262/480=5,,使用大的比例值
        int scale = 1;
        int sx = srcWidth / screenWidth;
        int sy = srcHeight / screenHeight;
        if (sx >= sy && sx > 1) {
            scale = sx;
        }
        if (sy >= sx && sy > 1) {
            scale = sy;
        }
        // 根据比例值,缩放图片,并加载到内存中;
        // 置为false,让BitmapFactory.decodeFile()返回一个图片对象
        opts.inJustDecodeBounds = false;
        // 可以把图片缩放为原图的1/scale * 1/scale
        opts.inSampleSize = scale;
        // 得到缩放后的bitmap
//        Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/lp.jpg", opts);
        Bitmap bm = BitmapFactory.decodeFile(localPath, opts);
        return bm;
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }
}
