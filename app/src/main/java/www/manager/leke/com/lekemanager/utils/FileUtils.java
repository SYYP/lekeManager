package www.manager.leke.com.lekemanager.utils;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;

public class FileUtils {

    private static final String APP_ROOT_DIR = "EduMediaFile";

    private static final String BOOK_DIR = "book";

    private static final String MEDIA_DIR = "media";

    private static final String MEDIA_JSON = "json";
    private static final String MEDIA_MP3 = "mp3";

    private  static  final  String EXERCISES_DIR = "qinfo" ;

    public static boolean isSDcardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 判断文件是否存在
     *
     * @param pathName 文件全路径名称
     * @return 有返回TRUE，否则FALSE
     */
    public static boolean exists(String pathName) {
        try {
            return !isEmpty(pathName) && new File(pathName).exists();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 创建目录
     *
     * @param dirPath 目录全路径名
     * @return 是否创建成功
     */
    public static boolean createDir(String dirPath) {
        LogUtils.e("dirPath " + dirPath);
        File file = new File(dirPath);
        if (!file.exists() || !file.isDirectory()) {
            return file.mkdirs();
        }
        return true;
    }

    /**
     * 判断字符串是否为空
     */
    private static boolean isEmpty(String value) {
        return !(value != null && !"".equalsIgnoreCase(value.trim()) && !"null".equalsIgnoreCase(value.trim()));
    }

    public static boolean delFileOrFolder(String path) {
        return delFileOrFolder(new File(path));
    }

    public static boolean delFileOrFolder(File file) {
        if (file == null || !file.exists()) {
        } else if (file.isFile())
            file.delete();
        else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null)
                for (File sonFile : files)
                    delFileOrFolder(sonFile);
            file.delete();
        }
        return true;
    }

    /**
     * 删除文件
     *
     * @param path 被删除的文件路径
     * @return 是否删除成功
     */
    public static boolean deleteFile(String path) {
        return new File(path).delete();
    }

    /**
     * 获取安装在用户手机上的sdcard/下的chianandroid目录
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
    }

    public static String getAppRootFileDir() {
        return getSDCardPath() + APP_ROOT_DIR + "/";
    }


    public static boolean creatBookDir() {
        return createDir(getAppBookFileDirPath());
    }

    public static boolean creatMediaDir() {
        return createDir(getAppMediaFileDirPath());
    }


    public static String getAppMediaFileDirPath() {
        return getAppRootFileDir() + MEDIA_DIR + "/";
    }

    public static String getAppBookFileDirPath() {
        return getAppRootFileDir() + BOOK_DIR + "/";
    }

    public static String getExercisesPath() {
        return getAppRootFileDir() + EXERCISES_DIR + "/";
    }

    public static boolean creatExercisesDir() {
        return createDir(getExercisesPath());
    }

    public static boolean unZip(String srcPath, String destPath) {
        return unZip(new File(srcPath), new File(destPath));
    }

    public static boolean unZip(File srcFile, File destFile) {
        boolean res = false;
        if (null == srcFile || null == destFile || !srcFile.exists() || !srcFile.canRead()) {
            return false;
        }
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            java.util.zip.ZipFile zipFile = new java.util.zip.ZipFile(srcFile);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            byte[] buffer = new byte[1024 * 8];
            int len = 0;
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                String entryName = zipEntry.getName();
                String unZipFileName = destFile.getAbsolutePath() + File.separator + entryName;
                if (zipEntry.isDirectory()) { // 没有执行此代码
                    new File(unZipFileName).mkdirs();
                } else { // 总是执行该代码.因为压缩的时候是对每个文件进行压缩的.
                    new File(unZipFileName).getParentFile().mkdirs();
                }
                File unZipedFile = new File(unZipFileName);
                if (unZipedFile.isDirectory()) {
                    File[] files = unZipedFile.listFiles();
                    for (File file : files) {
                        fos = new FileOutputStream(file);
                        is = zipFile.getInputStream(zipEntry);
                        while ((len = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                        }
                    }
                } else {
                    fos = new FileOutputStream(unZipedFile);
                    is = zipFile.getInputStream(zipEntry);
                    while ((len = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                }
            }
            fos.flush();
            res = true;
        } catch (ZipException e) {
            LogUtils.e(e);
        } catch (IOException e) {
            LogUtils.e(e);
        } finally {
            close(fos);
            close(is);
        }
        return res;
    }

    private static void close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getMediaJsonPath() {
        return getAppMediaFileDirPath() + MEDIA_JSON + "/";
    }

    public static String getMediaMp3Path() {
        return getAppMediaFileDirPath() + MEDIA_MP3 + "/";
    }

    public static boolean creatMediaJsonDir() {
        return createDir(getMediaJsonPath());
    }

    public static boolean creatMediaMp3Dir() {
        return createDir(getMediaMp3Path());
    }

    public static String readerFile(String filePath) {
        String result = "";
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            isr.close();
            result = builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
