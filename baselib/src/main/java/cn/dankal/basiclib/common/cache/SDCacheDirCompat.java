package cn.dankal.basiclib.common.cache;

import android.os.AsyncTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Created by Dylan on 2016/10/12.
 * 初始化
 * SDCacheDirCompat.getInstance().setRootCacheFile(
 * new SDCacheDir(getContext()).filesDir);
 */
public class SDCacheDirCompat {

    private SDCacheDirCompat() {
        // throw new UnsupportedOperationException("can invoke this Constructor Method.");
    }


    public static class Holder {
        volatile static SDCacheDirCompat mInstance = new SDCacheDirCompat();
    }

    public static SDCacheDirCompat getInstance() {
        return Holder.mInstance;
    }

    private File rootCacheFile;

    public File getRootCacheFile() {
        return rootCacheFile;
    }

    public void setRootCacheFile(File f) {
        this.rootCacheFile = f;
    }

    public void setRootCacheFile(String f) {
        setRootCacheFile(new File(f));
    }

    public static <T> void writeObject(String fileName, T obj) {
        if (SDCacheDirCompat.getInstance().getRootCacheFile() == null) {
            throw new NullPointerException("must set rootCacheFile path.");
        }
        File file = new File(getInstance().getRootCacheFile() + File.separator + fileName);
        SDCacheDirCompat.writeObject(file, obj);
    }

    public static <T> T readObject(String fileName) {
        if (SDCacheDirCompat.getInstance().getRootCacheFile() == null) {
            throw new NullPointerException("must set rootCacheFile path.");
        }
        File file = new File(getInstance().getRootCacheFile() + File.separator + fileName);
        return SDCacheDirCompat.readObject(file);
    }


    public static <T> void writeObject(final File file, final T obj) {
        new AsyncTask<Void, Long, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                ObjectOutputStream oos = null;
                try {
                    if (!file.exists()) {
                        file.getParentFile().mkdirs();
                        file.createNewFile();
                    }
                    FileOutputStream fos = new FileOutputStream(file);
                    oos = new ObjectOutputStream(fos);
                    oos.writeObject(obj);
                    oos.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    closeIO(oos);
                }
                return null;
            }
        }.execute();
    }

    public static <T> T readObject(File file) {
        ObjectInputStream ois = null;
        Object obj = null;
        try {
            if (!file.exists()) {
                return null;
            }
            FileInputStream fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            //noinspection unchecked
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeIO(ois);
        return (T) obj;
    }

    public static void closeIO(InputStream io) {
        try {
            if (io != null) {
                io.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeIO(OutputStream io) {
        try {
            if (io != null) {
                io.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
