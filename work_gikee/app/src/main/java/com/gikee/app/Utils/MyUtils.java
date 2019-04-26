package com.gikee.app.Utils;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LruCache;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gikee.app.R;
import com.gikee.app.base.GikeeApplication;
import com.gikee.app.datas.Commons;
import com.gikee.app.language.LanguageType;
import com.gikee.app.language.MultiLanguageUtil;
import com.gikee.app.preference_config.PreferenceUtil;
import com.lcodecore.tkrefreshlayout.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by THINKPAD on 2018/3/13.
 */

public class MyUtils {

    public static String CACHE_ROOT;
    public static String EMoneyFund_ROOT;
    public static final String CACHE_FUND_FILES_PATH = "fund_files/";
    public static final String CACHE_HTTP_DATA_PATH = "data/";
    public static final String CACHE_IMAGE_PATH = "images/";
    public static final String CACHE_INFOR_COLLECT_PATH = "infors/";
    public static boolean isAppActive = false;
    private static Context mContext;

    private static NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager cm = (ConnectivityManager) GikeeApplication.getMyApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static void init(Context context) {

        mContext = context;
        isAppActive = true;
        createAppCacheDir();

    }


    private static void createAppCacheDir() {
        String caString = MyUtils.getSdCardDir(mContext);
        if (caString == null) {
            caString = mContext.getFilesDir().getAbsolutePath().toString();
        }

        String dir = "/gikee/";

        String cache = caString + dir;//mContext.getString(MResource.getResourceIdByName(mContext.getPackageName(), "string", "app_name")); /*mContext.getPackageName()  "/PETKIT_MATE/"*/;

        CACHE_ROOT = cache + "cache/";
        EMoneyFund_ROOT = cache;
        File file = new File(cache);
        if (!file.isDirectory()) {
            file.delete();
        }
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(CACHE_ROOT);
        if (!file.isDirectory()) {
            file.delete();
        }
        if (!file.exists()) {
            file.mkdirs();
        }


        file = new File(CACHE_ROOT + CACHE_HTTP_DATA_PATH);
        if (!file.isDirectory()) {
            file.delete();
        }
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(EMoneyFund_ROOT + CACHE_FUND_FILES_PATH);
        if (!file.isDirectory()) {
            file.delete();
        }
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(CACHE_ROOT + CACHE_IMAGE_PATH);
        if (!file.isDirectory()) {
            file.delete();
        }
        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(CACHE_ROOT + CACHE_INFOR_COLLECT_PATH);
        if (!file.isDirectory()) {
            file.delete();
        }
        if (!file.exists()) {
            file.mkdirs();
        }

    }


    /**
     * 获取数据目录
     *
     * @return
     */
    public static String getAppDirPath() {
        File file = new File(EMoneyFund_ROOT);
        if (!file.exists())
            file.mkdirs();
        return EMoneyFund_ROOT;
    }

    /*
     * 获取sd卡路径
     *
     * @param context
     * @return
     */
    public static String getSdCardDir(Context context) {
        if (!isSD()) {
            return null;
        }
        String sdDir = Environment.getExternalStorageDirectory()
                .getAbsolutePath().toString();
        return sdDir;
    }

    public static boolean isSD() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }


    public static String getAppVersionName(Context context) {

        String versionName = null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;

    }


    /**
     * 添加系统缓存信息
     *
     * @param context
     * @param key
     * @param value
     */
    public static void addSysMap(Context context, String key, String value) {
        SharedPreferences.Editor sysEdit = getSysShare(context).edit();
        sysEdit.putString(key, value);
        sysEdit.commit();
    }


    /**
     * 得到系统级别的缓存对象
     *
     * @param context
     * @return
     */
    public static SharedPreferences getSysShare(Context context) {
        if (context != null)
            return context.getSharedPreferences(Commons.APP_CACHE_PATH, Context.MODE_PRIVATE);
        return null;
    }

    /**
     * 根据手机分辨率从DP转成PX
     *
     * @param dpValue
     * @return
     */
    public static int dip2px(float dpValue) {
        float scale = GikeeApplication.getMyApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //获取用户昵称
    public static boolean isLogin() {
        if (getUserId().equals(""))
            return false;
        else return true;
    }

    //获取用户Id
    public static String getUserId() {
        return PreferenceUtil.getString( "uuid", "");
    }

    //获取用户昵称
    public static String getUserName() {
        return PreferenceUtil.getString("username", "");
    }

    //获取用户手机号
    public static String getUserPhone() {
        return PreferenceUtil.getString( "userphone", "");
    }

    //获取计价单位
    public static String getMonetarySymbol() {
        return PreferenceUtil.getString( "monetarySymbol", "CNY");
    }

    //获取计价单位
    public static String getYuYan() {
        return PreferenceUtil.getString( "yuyan", "简体中文");
    }

    /**
     * sp转px
     *
     * @param spVal
     */

    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, GikeeApplication.getMyApplicationContext().getResources().getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率PX(像素)转成DP
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(float pxValue) {
        float scale = GikeeApplication.getMyApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 判断网络是否可用
     * <p>需添加权限 android.permission.ACCESS_NETWORK_STATE</p>
     */
    public static boolean isAvailable() {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }

    public static void setGridViewHeightBasedOnChildren(GridView gridView) {
        // 获取GridView对应的Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int rows;
        int columns = 0;
        int horizontalBorderHeight = 0;
        Class<?> clazz = gridView.getClass();
        try {
            //利用反射，取得每行显示的个数
            Field column = clazz.getDeclaredField("mRequestedNumColumns");
            column.setAccessible(true);
            columns = (Integer) column.get(gridView);
            //利用反射，取得横向分割线高度
            Field horizontalSpacing = clazz.getDeclaredField("mRequestedHorizontalSpacing");
            horizontalSpacing.setAccessible(true);
            horizontalBorderHeight = (Integer) horizontalSpacing.get(gridView);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        //判断数据总数除以每行个数是否整除。不能整除代表有多余，需要加一行
        if (listAdapter.getCount() % columns > 0) {
            rows = listAdapter.getCount() / columns + 1;
        } else {
            rows = listAdapter.getCount() / columns;
        }
        int totalHeight = 0;
        for (int i = 0; i < rows; i++) { //只计算每项高度*行数
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight + horizontalBorderHeight * (rows - 1);//最后加上分割线总高度
        gridView.setLayoutParams(params);
    }

    /*
     * 压缩图片
     * */

    public static Bitmap setBitmapToByteArray(Bitmap bmp, int size) {
        // 首先进行一次大范围的压缩
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, output);
        float zoom = (float) Math.sqrt(size * 1024 / (float) output.toByteArray().length); //获取缩放比例
        // 设置矩阵数据
        Matrix matrix = new Matrix();
        matrix.setScale(zoom, zoom);
        // 根据矩阵数据进行新bitmap的创建
        Bitmap resultBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        output.reset();
        resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        // 如果进行了上面的压缩后，依旧大于400K，就进行小范围的微调压缩
        while (output.toByteArray().length > size * 1024) {
            matrix.setScale(0.9f, 0.9f);//每次缩小 1/10
            resultBitmap = Bitmap.createBitmap(
                    resultBitmap, 0, 0,
                    resultBitmap.getWidth(), resultBitmap.getHeight(), matrix, true);
            output.reset();
            resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        }
        Bitmap outB = resultBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(outB);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(resultBitmap, 0, 0, null);

        return outB;
    }

    //获取网络图片bitmap
    public static Bitmap getBitmap(final String Url) {
        try {
            URL url = null;
            url = new URL(Url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 计算listview高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
        listView.invalidate();
    }

    private static final double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    // 返回单位是:千米
    public static double getDistance(double longitude1, double latitude1,
                                     double longitude2, double latitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        //有小数的情况;注意这里的10000d中的“d”
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;//单位：米
        s = Math.round(s / 10d) / 100d;//单位：千米 保留两位小数
//        s = Math.round(s / 100d) / 10d;//单位：千米 保留一位小数
        return s;
    }

    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName 要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            LogUtils.showLog("删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param dir 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        try {
            // 如果dir不以文件分隔符结尾，自动添加文件分隔符
            if (!dir.endsWith(File.separator))
                dir = dir + File.separator;
            File dirFile = new File(dir);
            // 如果dir对应的文件不存在，或者不是一个目录，则退出
            if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
                System.out.println("删除目录失败：" + dir + "不存在！");
                return false;
            }
            boolean flag = true;
            // 删除文件夹中的所有文件包括子目录
            File[] files = dirFile.listFiles();
            if (files == null)
                return false;
            for (int i = 0; i < files.length; i++) {
                // 删除子文件
                if (files[i].isFile()) {
                    flag = deleteFile(files[i].getAbsolutePath());
                    if (!flag)
                        break;
                }
                // 删除子目录
                else if (files[i].isDirectory()) {
                    flag = deleteDirectory(files[i]
                            .getAbsolutePath());
                    if (!flag)
                        break;
                }
            }
            if (!flag) {
                System.out.println("删除目录失败！");
                return false;
            }
            // 删除当前目录
            if (dirFile.delete()) {
                System.out.println("删除目录" + dir + "成功！");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 获取屏幕宽度
     */
    public static int getWidth() {
        WindowManager wm = (WindowManager) GikeeApplication.getMyApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * 获取屏幕宽度
     */
    public static int getHieght() {
        WindowManager wm = (WindowManager) GikeeApplication.getMyApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    /**
     * 获取版本名
     */
    public static String getLocalVersionName() {
        String localVersion = "";
        try {
            PackageInfo packageInfo = GikeeApplication.getMyApplicationContext().getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(GikeeApplication.getMyApplicationContext().getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return Commons.isDebug?"测试 "+localVersion:localVersion;
    }


    public static String getOldDate(Date beginDate, int distanceDay) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dft.format(endDate);
    }

    public static String getOldDateNoTime(Date beginDate, int distanceDay) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dft.format(endDate);
    }


    public static String getFormatTime(Date date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss


        return simpleDateFormat.format(date);
    }

    public static String getRemindTime() {

        Date dt = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss

        return simpleDateFormat.format(dt);
    }


    /**
     * String转时间戳
     */
    public static long getTimes(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dt1;
            dt1 = sdf.parse(date);
            long ts1 = dt1.getTime();
            return ts1;
        } catch (ParseException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 两个时间相差天数
     */
    public static long dateDiff(String startTime, String endTime, String format) {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        try {
            // 获得两个时间的毫秒时间差异
            diff = sd.parse(endTime).getTime()
                    - sd.parse(startTime).getTime();
            day = diff / nd;// 计算差多少天
            long hour = diff % nd / nh;// 计算差多少小时
            long min = diff % nd % nh / nm;// 计算差多少分钟
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒
            // 输出结果
            System.out.println("时间相差：" + day + "天" + hour + "小时" + min
                    + "分钟" + sec + "秒。");
            if (day >= 1) {
                return day;
            } else {
                if (day == 0) {
                    return 1;
                } else {
                    return 0;
                }

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static String getCurrectDate() {

        String temp_str = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

        Date dt = new Date();

        temp_str = sdf.format(dt);

        return temp_str;

    }

    public static String getCurrectDateNoTime() {

        String temp_str = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date dt = new Date();

        temp_str = sdf.format(dt);

        return temp_str;

    }


    public static String getRandomString(int length) {
        //定义一个字符串（A-Z，a-z，0-9）即62位；
        String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        //由Random生成随机数
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        //长度为几就循环几次
        for (int i = 0; i < length; ++i) {
            //产生0-61的数字
            int number = random.nextInt(62);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }


    public static Bitmap shotActivityNoBar(Activity activity) {
        // 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();

        // 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        Display display = activity.getWindowManager().getDefaultDisplay();

        // 获取屏幕宽和高
        int widths = display.getWidth();
        int heights = display.getHeight();

        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);

        if(statusBarHeights<=0){
            statusBarHeights=15;
        }

        // 去掉状态栏
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
                statusBarHeights, widths, heights - statusBarHeights);
//        File   filePic=null;
//        try {
//               filePic = new File(CACHE_ROOT + CACHE_IMAGE_PATH + getRemindTime() + ".jpg");
//            if (!filePic.exists()) {
//                filePic.getParentFile().mkdirs();
//                filePic.createNewFile();
//            }
//            FileOutputStream fos = new FileOutputStream(filePic);
//            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//            fos.flush();
//            fos.close();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return null;
//        }

        // 销毁缓存信息
        view.destroyDrawingCache();

        return bmp;

    }

    /**
     * 验证邮箱
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
        boolean flag = false;
        try{
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }





    /**
     * @Title: fmtMicrometer
     * @Description: 格式化数字为千分位
     * @return    设定文件
     * @return String    返回类型
     */
    public static String fmtMicrometer(String text) {



        DecimalFormat df = null;
        if (text.indexOf(".") > 0) {
            if (text.length() - text.indexOf(".") - 1 == 0) {
                df = new DecimalFormat("###,##0.");
            } else if (text.length() - text.indexOf(".") - 1 == 1) {
                df = new DecimalFormat("###,##0.0");
            } else if(text.length() - text.indexOf(".") - 1 == 2){
                df = new DecimalFormat("###,##0.00");
            }else{
                df = new DecimalFormat("###,##0.00");

            }

        } else {
            df = new DecimalFormat("###,##0");
        }
        double number = 0.0;
        try {
            number = Double.parseDouble(text);
        } catch (Exception e) {
            number = 0.0;
        }
        return df.format(number);
    }


    /**
     * @Title: fmtMicrometer
     * @Description: 格式化数字为千分位
     * @return    设定文件
     * @return String    返回类型
     */
    public static String fmtMicrometer1(String text) {
        DecimalFormat df = null;
        if (text.indexOf(".") > 0) {
            if (text.length() - text.indexOf(".") - 1 == 0) {
                df = new DecimalFormat("###,##0.");
            } else if (text.length() - text.indexOf(".") - 1 == 1) {
                df = new DecimalFormat("###,##0.0");
            } else if(text.length() - text.indexOf(".") - 1 == 2){
                df = new DecimalFormat("###,##0.00");
            }else if(text.length() - text.indexOf(".") - 1 == 3){
                df = new DecimalFormat("###,##0.000");
            }else if(text.length() - text.indexOf(".") - 1 == 4){
                df = new DecimalFormat("###,##0.0000");
            }else if(text.length() - text.indexOf(".") - 1 == 5){
                df = new DecimalFormat("###,##0.00000");
            }else  if(text.length() - text.indexOf(".") - 1 == 6){
                df = new DecimalFormat("###,##0.000000");
            }else  if(text.length() - text.indexOf(".") - 1 == 7){
                df = new DecimalFormat("###,##0.0000000");
            }else  if(text.length() - text.indexOf(".") - 1 == 8){
                df = new DecimalFormat("###,##0.00000000");
            }else{
                df = new DecimalFormat("###,##0.00000000");
            }

        } else {
            df = new DecimalFormat("###,##0");
        }
        double number = 0.0;
        try {
            number = Double.parseDouble(text);
        } catch (Exception e) {
            number = 0.0;
        }
        return df.format(number);
    }

    public static String fmtMicrometer2(String text) {
        DecimalFormat df = null;

        df = new DecimalFormat("###,##0");

        double number = 0.0;
        try {
            number = Double.parseDouble(text);
        } catch (Exception e) {
            number = 0.0;
        }
        return df.format(number);
    }


    //正则过滤出字母
    public static String filterAlphabet(String alph)
    {
        alph = alph.replaceAll("[^(A-Za-z)]", "");
        return alph;
    }

    /**
     * 过滤汉字
     * @param chin
     * @return
     */
    public static String filterChinese(String chin){
        return chin.replaceAll("[\\u4e00-\\u9fa5]", "");
    }

    public static String filterNumber(String number)
    {
        number = number.replaceAll("[^(0-9-.)]", "");
        return number;
    }

    /**
     * 生成某个LinearLayout的图片
     *
     * @author gengqiquan
     * @date 2017/3/20 上午10:34
     */
    public static Bitmap getLinearLayoutBitmap(RelativeLayout linearLayout) {
        int h = 0;
        // 获取LinearLayout实际高度
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            linearLayout.getChildAt(i).measure(0, 0);
            h += linearLayout.getChildAt(i).getMeasuredHeight();
        }
        linearLayout.measure(0, 0);
        // 创建对应大小的bitmap
        Bitmap bitmap = Bitmap.createBitmap(linearLayout.getMeasuredWidth(), h,
                Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        linearLayout.draw(canvas);
        return bitmap;
    }


    public static Bitmap ImageCompress(Bitmap bitmap) {
        // 图片允许最大空间 单位：KB
        double maxSize = 32.00;
        // 将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        // 将字节换成KB
        double mid = b.length / 1024;
        // 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
        if (mid > maxSize) {
            // 获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            // 开始压缩 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
            bitmap = zoomImage(bitmap, bitmap.getWidth() / Math.sqrt(i),
                    bitmap.getHeight() / Math.sqrt(i));
        }
        return bitmap;
    }

    /***
     * 图片压缩方法二
     *
     * @param bgimage
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }



    /**
     * https://gist.github.com/PrashamTrivedi/809d2541776c8c141d9a
     */
    public static Bitmap shotRecyclerView(RecyclerView view) {
        RecyclerView.Adapter adapter = view.getAdapter();
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int size = adapter.getItemCount();
            int height = 0;
            Paint paint = new Paint();
            int iHeight = 0;
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;
            LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
            for (int i = 0; i < size; i++) {
                RecyclerView.ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);
                holder.itemView.measure(
                        View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(),
                        holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                Bitmap drawingCache = holder.itemView.getDrawingCache();
                if (drawingCache != null) {

                    bitmaCache.put(String.valueOf(i), drawingCache);
                }
                height += holder.itemView.getMeasuredHeight();
            }

            bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);
            Drawable lBackground = view.getBackground();
            if (lBackground instanceof ColorDrawable) {
                ColorDrawable lColorDrawable = (ColorDrawable) lBackground;
                int lColor = lColorDrawable.getColor();
                bigCanvas.drawColor(lColor);
            }

            for (int i = 0; i < size; i++) {
                Bitmap bitmap = bitmaCache.get(String.valueOf(i));
                bigCanvas.drawBitmap(bitmap, 0f, iHeight, paint);
                iHeight += bitmap.getHeight();
                bitmap.recycle();
            }
        }
        return bigBitmap;
    }


    /**
     * 拼接图片

     * @return 返回拼接后的Bitmap
     */
//    public static Bitmap  newBitmap(Bitmap bit1,Bitmap bit2){
//
//        int width = bit1.getWidth()+1000;
//        int height = bit1.getHeight() + bit2.getHeight();
//
//
//        Bitmap mBitmap = Bitmap.createBitmap(width, bit2.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas1 = new Canvas(mBitmap);
//        canvas1.drawBitmap(bit2, 0, 0, null);
//        //创建一个空的Bitmap(内存区域),宽度等于第一张图片的宽度，高度等于两张图片高度总和
//        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        //将bitmap放置到绘制区域,并将要拼接的图片绘制到指定内存区域
//        Canvas canvas = new Canvas(bitmap);
//        canvas.drawBitmap(bit1, 0, 0, null);
//        canvas.drawBitmap(mBitmap, 0, bit1.getHeight(), null);
//
//        Bitmap bitmap1 = createWatermark(bitmap, "Gikee");
//
//
//        return bitmap1;
//    }


    public static Bitmap newBitmap(Bitmap bmp1, Bitmap bmp2) {
        Bitmap retBmp;
        int width = bmp1.getWidth();
        if (bmp2.getWidth() != width) {
            //以第一张图片的宽度为标准，对第二张图片进行缩放。

            int h2 = bmp2.getHeight() * width / bmp2.getWidth();
            retBmp = Bitmap.createBitmap(width, bmp1.getHeight() + h2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(retBmp);
            Bitmap newSizeBmp2 = resizeBitmap(bmp2, width, h2);
            canvas.drawBitmap(bmp1, 0, 0, null);
            canvas.drawBitmap(newSizeBmp2, 0, bmp1.getHeight(), null);
        } else {
            //两张图片宽度相等，则直接拼接。

            retBmp = Bitmap.createBitmap(width, bmp1.getHeight() + bmp2.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(retBmp);
            canvas.drawBitmap(bmp1, 0, 0, null);
            canvas.drawBitmap(bmp2, 0, bmp1.getHeight(), null);
        }

        Bitmap bitmap1 = createWatermark(retBmp, GikeeApplication.getMyApplicationContext().getString(R.string.share_title));

        return bitmap1;
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        float scaleWidth = ((float) newWidth) / bitmap.getWidth();
        float scaleHeight = ((float) newHeight) / bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bmpScale = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bmpScale;
    }




    public static Bitmap createWatermark(Bitmap target, String mark) {
        int w = target.getWidth();
        int h = target.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint p = new Paint();        // 水印的颜色
        p.setColor(GikeeApplication.getMyApplicationContext().getResources().getColor(R.color.home_navite));
        p.setTextSize(30);//水印的字体大小
        p.setAntiAlias(true);// 去锯齿
       // p.setAlpha(60);
        canvas.drawBitmap(target, 0, 0, p);        // 在左边的中间位置开始添加水印
        canvas.drawText(mark, w/4, h/2, p);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return bmp;
    }

    //判断地址类型
    public static String[] getBaseLine(String params){
        String[] symbol=new String[2];
        if(params.length()==66){
            symbol[0]="ETH";
            symbol[1]="hash";
        }else if(params.length()==64){
            //BTC hash和EOS hash都是64位
            symbol[0]="BTC";
            if(params.startsWith("0000000000")){
                //BTC块hash
                symbol[1]="hash";
            }else{
                //BTC交易hash
                symbol[1]="hash";
            }
        }else if(params.length()==42&&params.startsWith("0x")){
            //ETH地址
            symbol[0]="ETH";
            symbol[1]="address";
        }else if(params.length()==34){
            //BTC地址
            symbol[0]="BTC";
            symbol[1]="address";
        }else if(params.length()==42&&params.startsWith("bc1")){
            symbol[0]="BTC";
            symbol[1]="address";
        }else if(params.length()==12){
            symbol[0]="EOS";
            symbol[1]="address";
        }else{
            symbol[0]="project";
            symbol[1]="symbol";
        }
        return symbol;
    }



    /**
     * ETH 爬虫时间格式  Nov-05-2018 09:55:02 AM
     *
     * EOS 2018-11-16T03:34:58.500
     * */

    public static String timeChange(String time,String baseline,String type){

        String new_time="";


      if(!TextUtils.isEmpty(time)) {

          // String dt="Nov-05-2018 09:55:02 PM";
          SimpleDateFormat sdf1;
          SimpleDateFormat sdf2;
          if (baseline.equals(Commons.ETH)) {
              sdf1 = new SimpleDateFormat("MMM-dd-yyyy hh:mm:ss a", Locale.ENGLISH);
              sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
              Date d = null;
              try {
                  d = sdf1.parse(time);
              } catch (ParseException e) {
                  e.printStackTrace();
              }
              long rightTime = (long) (d.getTime() + 8 * 60 * 60 * 1000); //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
              new_time = sdf2.format(rightTime);
          }else if(type.equals("eth")){
              sdf1 = new SimpleDateFormat("MMM-dd-yyyy hh:mm:ss a", Locale.ENGLISH);
              sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
              Date d = null;
              try {
                  d = sdf1.parse(time);
              } catch (ParseException e) {
                  e.printStackTrace();
              }
              long rightTime = (long) (d.getTime() + 8 * 60 * 60 * 1000); //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
              new_time = sdf2.format(rightTime);
          }else {
              sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:sss", Locale.ENGLISH);
              sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

              Date d = null;
              try {
                  d = sdf1.parse(time);
              } catch (ParseException e) {
                  e.printStackTrace();
              }
              long rightTime = (long) (d.getTime() + 8 * 60 * 60 * 1000); //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
              new_time = sdf2.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式

          }


      }


        return new_time;
    }



    public static void numberTimer(final TextView view, int start, int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setText(fmtMicrometer(valueAnimator.getAnimatedValue() + ""));
            }
        });
        valueAnimator.setDuration(1500);
        valueAnimator.start();
    }


    public static int getVersionCode(Context context){

        int versioncode = 0;

        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versioncode= packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
       return versioncode;
    }

    /**
     * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9-.]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }


    public static String getLocalLanguage(){
        String language = "zh_CN";
        Locale savedLanguageType = MultiLanguageUtil.getInstance().getLanguageLocale();
        if (savedLanguageType == Locale.ENGLISH) {
            language = "en";
        } else if (savedLanguageType == Locale.SIMPLIFIED_CHINESE) {
            language = "zh_CN";
        }else if (savedLanguageType == Locale.CHINESE) {
            language = "zh_CN";
        }else
            language=savedLanguageType.getLanguage();
        if("zh".equals(language)){
            language="zh_CN";
        }
        return language;
    }



    /**
     * 保留两位有效数字区别于保留小数点后两位
     */
    public static String getNumber(String d){
        BigDecimal b = new BigDecimal(d,new MathContext(2, RoundingMode.DOWN));
        String number = b.toPlainString();
        return  number;// 输出为0.0037
    }


    /**
     * 获取价格单位
     */
    public static boolean  getUnit(){
        int type_unit =  Commons.lanuage_type;
        if(type_unit!=-1){
            if(type_unit==LanguageType.UNIT_USD){
                return true;
            }else
                return false;
        }else
            return true;
    }

    public static void  setUnit(){
        int type_unit =  PreferenceUtil.getInt(LanguageType.SAVE_UNIT,-1);
        Commons.lanuage_type =  type_unit;

    }



    /**
     *获取价格标志
     */
    public static String  getUnitSymbol(){
        int type_unit =  Commons.lanuage_type;
        if(type_unit!=-1){
            if(type_unit==LanguageType.UNIT_USD){
                return "$";
            }else
                return "¥";
        }else
            return "$";

    }

    /**
     *涨跌幅标志
     */
    public static boolean getQuateSymbol(){

        boolean ischeck = Commons.quate_color;

        return ischeck;
    }

    public static void  setQuateSymbol(){

        Commons.quate_color = PreferenceUtil.getBoolean(LanguageType.SAVE_QUATE,false);


    }

    /**
     * K:10^3 M:10^6 B:10^9 BT:10^12
     **/

    public static String getBigNumber(double value){

        String language = getLocalLanguage();

        String str_value =null;

        if(language=="zh_CN") {
            if (value >= 100000000) {
                str_value =  MyUtils.fmtMicrometer(value / 100000000 + "") + "亿";
            } else if (value >= 10000) {
                str_value =  MyUtils.fmtMicrometer(value / 10000 + "") + "万";
            }else
                str_value = MyUtils.fmtMicrometer(value+"");
        }else{
            if(value>100000){
                str_value =  MyUtils.fmtMicrometer(value/1000000+"")+"M";
            }else if(value>=1000){
                str_value = MyUtils.fmtMicrometer(value/1000+"")+"K";
            }else
                str_value = MyUtils.fmtMicrometer(value+"");
        }

        return str_value;
    }



    /**
     * 计算根布局的的底部空隙，从而判断软键盘的显示和隐藏.
     * 判断根布局的可视区域与屏幕底部的差值，如果这个差大于某个值，可以认定键盘弹起了。
     * 得到的Rect就是根布局的可视区域， 而rootView.bottom是其本应的底部坐标值，如果差值大于我们预设的值，就可以认定键盘弹起了。
     * 这个预设值是键盘的高度的最小值。
     *
     * @param rootView 实际上就是DecorView，通过任意一个View再getRootView就能获得
     * @return 软键盘当前状态.
     */
    private static boolean isKeyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }

    /**
     * 隐藏软键盘.
     */
    public static void hideKeyBoard(Activity activity) {
        if (isKeyboardShown(activity.getWindow().getDecorView().getRootView())) {
            InputMethodManager inputMgr = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMgr.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
        }
    }

    /**
     * 显示软键盘.
     */
    public static void showKeyBoard(Activity activity) {
        if (!isKeyboardShown(activity.getWindow().getDecorView().getRootView())) {
            InputMethodManager inputMgr = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMgr.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
        }
    }


    public static void chooseDate(TextView tx_end, FragmentActivity activity) {





    }
}
