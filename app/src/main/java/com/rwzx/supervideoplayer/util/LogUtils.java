package com.rwzx.supervideoplayer.util;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志输出控制类。
 * 1、可以控制日志是否输出，是否记录到sd卡中。
 * 2、默认不输出日志，不记录到SD卡中，调用setDebugMode控制输出日志。
 * 3、添加json字符串格式输出。
 *
 * @author lilei
 * @version 1.8.3
 */

public class LogUtils {

    private static final String DEFAULT_TAG = "LogUtils";
    private static boolean mIsDebugMode = false;
    private static String mTag;
    private static String path;
    private static File file;
    private static FileOutputStream outputStream;
    private static String pattern = "yyyy-MM-dd HH:mm:ss";
    private static final int JSON_INDENT = 2;
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * 设置为调试模式，调试模式时输出log信息。
     *
     * @param tag Log标签
     */
    public static void setDebugMode(String tag) {
        mIsDebugMode = true;
        mTag = tag;
    }

    /**
     * 设置为调试模式，调试模式时输出log信息。使用默认的Tag。
     */
    public static void setDebugMode() {
        mIsDebugMode = true;
        mTag = DEFAULT_TAG;
    }

    /**
     * 输出log信息。
     *
     * @param tag
     * @param msg
     */
    public static void log(String tag, String msg) {
        printLog(tag, msg);
    }

    /**
     * json数据格式化后输出。
     */
    private static void logJson(Object object) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        log(gson.toJson(object));
    }

    /**
     * 输出log信息，Tag为固定值。
     *
     * @param msg
     */
    public static void log(String msg) {
        printLog(mTag, msg);
    }

    private static void printLog(String tag, String msg) {
        if (mIsDebugMode) {
            String formatMsg = formatJson(msg);
            if (TextUtils.isEmpty(formatMsg)) {
                String logMsg = getLogAddress() + msg;
                Log.e(tag, logMsg);
            } else {
                String logMsg = getLogAddress() + formatMsg;
                Log.e(tag, logMsg);
            }
        }
    }

    private static String formatJson(String json) {
        String result = null;
        if (!TextUtils.isEmpty(json)) {
            try {
                json = json.trim();
                if (json.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(json);
                    result = LINE_SEPARATOR + jsonObject.toString(JSON_INDENT);
                } else if (json.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(json);
                    result = LINE_SEPARATOR + jsonArray.toString(JSON_INDENT);
                }
            } catch (JSONException e) {
            }
        }
        return result;
    }

    /**
     * 获取Log输出的位置，类名、行号、方法名。
     *
     * @return
     */
    private static String getLogAddress() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int size = stackTrace.length;
        int index = 5;
        if (index <= size) {
            String className = stackTrace[index].getFileName();
            String methodName = stackTrace[index].getMethodName();
            int lineNumber = stackTrace[index].getLineNumber();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[(").append(className).append(":").append(lineNumber).append(")#").append(methodName).append("] ");
            return stringBuilder.toString();
        } else {
            return "";
        }
    }

    /**
     * 将Log信息保存到SD卡中。
     */
    public static void logToSdCard(String msg) {
        if (mIsDebugMode && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            path = externalStorageDirectory.getAbsolutePath() + "/DebugLog/";
            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            file = new File(new File(path), "log.txt");
            String logMsg = getLogAddress() + msg;
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String time = simpleDateFormat.format(date);
            try {
                outputStream = new FileOutputStream(file, true);
                outputStream.write(time.getBytes());
                outputStream.write(("    " + mTag + "\r\n").getBytes());
                outputStream.write(logMsg.getBytes());
                outputStream.write("\r\n".getBytes());
                outputStream.flush();
            } catch (Exception e) {
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}