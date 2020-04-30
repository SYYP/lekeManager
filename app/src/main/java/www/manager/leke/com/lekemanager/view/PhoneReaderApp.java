package www.manager.leke.com.lekemanager.view;

import android.content.Context;

import com.onyx.android.sdk.data.DataManager;
import com.onyx.android.sdk.reader.ReaderBaseApp;
import com.raizlabs.android.dbflow.config.DatabaseHolder;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ming on 2017/3/31.
 */

public class PhoneReaderApp extends ReaderBaseApp {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = PhoneReaderApp.this;
        DataManager.init(this, databaseHolderList());
        initContentProvider(this);
    }
    private List<Class<? extends DatabaseHolder>> databaseHolderList() {
        List<Class<? extends DatabaseHolder>> list = new ArrayList<>();
        // add your databases need to be initialized
        return list;
    }
    static public void initContentProvider(final Context context) {
        try {
            FlowConfig.Builder builder = new FlowConfig.Builder(context);
            FlowManager.init(builder.build());
        } catch (Exception e) {
            if (com.onyx.android.sdk.dataprovider.BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }
    }

    public static Context getContext() {
        return mContext;
    }
}
