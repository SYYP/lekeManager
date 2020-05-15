package www.manager.leke.com.lekemanager.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.zip.ZipEntry;

public class FileUtil {
    /**
     * 自定义APP 文件目录  名称
     */
    private static String mRootName;

    private static boolean isEmpty(String value) {
        return !(value != null && !"".equalsIgnoreCase(value.trim()) && !"null".equalsIgnoreCase(value.trim()));
    }

    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
    }

    public static String getAppFilesDirByData() {
        return UIUtils.getContext().getFilesDir().getAbsolutePath() + "/";
    }


    public static boolean isSDcardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean exists(String pathName) {
        try {
            return !isEmpty(pathName) && new File(pathName).exists();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean createDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists() || !file.isDirectory()) {
            return file.mkdirs();
        }
        return true;
    }

    //删除文件及文件夹
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

    public static boolean delFileOrFolder(String path) {
        return delFileOrFolder(new File(path));
    }

    //删除文件夹内所有文件，而不删除该文件夹
    public static void delFileNoFolder(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null)
                for (File sonFile : files)
                    delFileOrFolder(sonFile);
        }
    }

    //---------------------------------------------------------------------------
    public static void setRootFileName(String rootName) {

    }

    /**
     * 获取SD卡 应用的 root 目录
     *
     * @return
     */
    public static String getAppFilesDir() {
        if (isSDcardExist()) {
            if (StringUtils.isEmpty(mRootName)) {
                return getSDCardPath() + FileRoot.fileRoot + "/";
            } else {
                return getSDCardPath() + mRootName + "/";
            }

        } else {
            return getAppFilesDirByData();
        }
    }


    public static void setRootName(String rootName) {
        FileUtil.mRootName = rootName;
    }

    /***
     *  获取 图书 文件 路径
     * @return
     */
    public static String getReaderPath() {
        return getAppFilesDir() + FileRoot.reader + "/";
    }

    /**
     * 获取 试读图书文件夹
     *
     * @return
     */
    public static String getTrialPath() {
        return getAppFilesDir() + FileRoot.trial + "/";
    }

    public static String getHomeworkPath() {
        return getAppFilesDir() + FileRoot.homeworkResult + "/";
    }

    public static String getTaskCacheDir() {
        return getAppFilesDir() + FileRoot.task + "/";
    }

    private static String getVoiceRootPath() {
        return getAppFilesDir() + FileRoot.voice + "/";
    }

    /**
     * 点读音频文件
     *
     * @return
     */
    public static String getAudioPath() {
        return getVoiceRootPath() + FileRoot.audio + "/";
    }

    /***
     * 点读配置文件
     * @return
     */
    public static String getConfigurePath() {
        return getVoiceRootPath() + FileRoot.configure + "/";
    }


    /***
     * 练习题
     * @return
     */
    public static String getExercisesPath() {
        return getAppFilesDir() + FileRoot.exercises + "/";
    }

    /***
     * apk
     * @return
     */
    public static String getApkPath() {
        return getAppFilesDir() + FileRoot.apk + "/";
    }

    /***
     * Screenshot 图片保存路径
     * @return
     */
    public static String getScreenShotImagePath() {
        return getAppFilesDir() + FileRoot.screenShot + "/";
    }


    public static void creatUserDir() {
        createDir(getConfigurePath());
        createDir(getAudioPath());
        createDir(getReaderPath());
        createDir(getTrialPath());
        createDir(getHomeworkPath());
        createDir(getExercisesPath());
        createDir(getScreenShotImagePath());
        createDir(getTaskCacheDir());
    }


    public static class FileRoot {
        //应用的包名作为目录
        public static String fileRoot = UIUtils.getContext().getPackageName();
        public static String reader = "reader";
        public static String trial = "trial";
        public static String homeworkResult = "homework_result";
        public static String voice = "voice";
        public static String audio = "audio";
        public static String configure = "configure";
        public static String exercises = "exercises";
        public static String apk = "apk";
        public static String task = "task";
        public static String screenShot = "screenshot";
    }

    public static File ifNotExistCreateFile(String filePath) throws IOException {
        if (TextUtils.isEmpty(filePath)) {
            throw new IOException("filePath is empty");
        }
        String saveFileDir = filePath.substring(0, filePath.lastIndexOf("/"));
        if (TextUtils.isEmpty(saveFileDir) || !createDirs(saveFileDir)) {
            throw new IOException("create saveFileDir fail");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     * 创建多级目录，如果上级目录不存在，会先创建上级目录
     *
     * @param dirPath 目录全路径名
     * @return 是否创建成功
     */
    public static boolean createDirs(String dirPath) {
        LogUtils.e(FileUtil.class.getName(), " path == " + dirPath);
        File file = new File(dirPath);
        if (!file.exists() || !file.isDirectory()) {
            return file.mkdirs();
        }
        return true;
    }

    /**
     * 获取下载文件后缀
     *
     * @param filePath
     * @return .pdf
     */
    public static String getDownBookSuffix(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return "";
        }
        return filePath.substring(filePath.lastIndexOf("."));
    }

    public static final String pdf = ".pdf";
    public static final String epub = ".epub";


    public static byte[] file2byte(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
    public static void byte2File(byte[] buf, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdirs();
            }
            file = new File(filePath + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String formatFileSizeDotOne(long fileS)
    {
        DecimalFormat df = new DecimalFormat("#");
        String fileSizeString = "";
        String wrongSize="0B";
        if(fileS==0){
            return wrongSize;
        }
        if (fileS < 1024){
            fileSizeString = df.format((double) fileS) + "B";
        }
        else if (fileS < 1048576){
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        }
        else if (fileS < 1073741824){
            df = new DecimalFormat("#.0");
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        }
        else{
            df = new DecimalFormat("#.0");
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小
     * @param fileS
     * @return
     */
    public static String formatFileSize(long fileS)
    {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize="0B";
        if(fileS==0){
            return wrongSize;
        }
        if (fileS < 1024){
            fileSizeString = df.format((double) fileS) + "B";
        }
        else if (fileS < 1048576){
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        }
        else if (fileS < 1073741824){
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        }
        else{
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 解压缩zip文件
     *
     * @param srcPath  需要被解压的文件路径
     * @param destPath 被解压后的文件存放的目录路径
     * @return 是否解压成功
     */
    public static boolean unZip3(String srcPath, String destPath) {
        return unZip3(new File(srcPath), new File(destPath));
    }


    /**
     * 解压缩zip文件
     *
     * @param srcFile  需要被解压的文件
     * @param destFile 被解压后的文件存放的目录
     * @return 是否解压成功
     */
    @SuppressWarnings("resource")
    public static boolean unZip3(File srcFile, File destFile) {
        boolean res = false;
        if (null == srcFile || null == destFile || !srcFile.exists() || !srcFile.canRead()) {
            return false;
        }
        FileOutputStream fos = null;
        InputStream is = null;
        java.util.zip.ZipFile zipFile = null;
        try {
            zipFile = new java.util.zip.ZipFile(srcFile);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            byte[] buffer = new byte[1024 * 8];
            int len = 0;
            while (entries.hasMoreElements()) {
                java.util.zip.ZipEntry zipEntry = entries.nextElement();
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
                        try {
                            fos = new FileOutputStream(file);
                            is = zipFile.getInputStream(zipEntry);
                            while ((len = is.read(buffer)) != -1) {
                                fos.write(buffer, 0, len);
                            }
                            fos.flush();
                        } catch (IOException e) {
                            LogUtils.e(e);
                        } finally {
                            IOUtils.close(fos);
                            IOUtils.close(is);
                        }
                    }
                } else {
                    try {
                        fos = new FileOutputStream(unZipedFile);
                        is = zipFile.getInputStream(zipEntry);
                        while ((len = is.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                        }
                        fos.flush();
                    } catch (IOException e) {
                        LogUtils.e(e);
                    } finally {
                        IOUtils.close(fos);
                        IOUtils.close(is);
                    }
                }
            }
            res = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
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

    /**
     * 复制单个文件
     *
     * @param oldPath$Name String 原文件路径+文件名 如：data/user/0/com.test/files/abc.txt
     * @param newPath$Name String 复制后路径+文件名 如：data/user/0/com.test/cache/abc.txt
     * @return <code>true</code> if and only if the file was copied;
     *         <code>false</code> otherwise
     */
    public static boolean copyFile(String oldPath$Name, String newPath$Name) {
        try {
            File oldFile = new File(oldPath$Name);
            if (!oldFile.exists()) {
                Log.e("--Method--", "copyFile:  oldFile not exist.");
                return false;
            } else if (!oldFile.isFile()) {
                Log.e("--Method--", "copyFile:  oldFile not file.");
                return false;
            } else if (!oldFile.canRead()) {
                Log.e("--Method--", "copyFile:  oldFile cannot read.");
                return false;
            }

            /* 如果不需要打log，可以使用下面的语句
            if (!oldFile.exists() || !oldFile.isFile() || !oldFile.canRead()) {
                return false;
            }
            */

            FileInputStream fileInputStream = new FileInputStream(oldPath$Name);
            FileOutputStream fileOutputStream = new FileOutputStream(newPath$Name);
            byte[] buffer = new byte[1024];
            int byteRead;
            while (-1 != (byteRead = fileInputStream.read(buffer))) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            fileInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
