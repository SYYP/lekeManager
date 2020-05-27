package www.manager.leke.com.lekemanager.read;

import android.graphics.Bitmap;
import android.view.View;

import com.onyx.android.sdk.reader.api.ReaderDocumentTableOfContent;
import com.onyx.android.sdk.reader.host.wrapper.Reader;


import www.manager.leke.com.lekemanager.base.BasePresenter;
import www.manager.leke.com.lekemanager.base.BaseView;
import www.manager.leke.com.lekemanager.reading.ReadingBean;

/**
 * Created by ming on 2017/4/1.
 */

public interface ReaderContract {

    interface ReaderView extends BaseView<ReaderPresenter> {
        void updatePage(final int page, final Bitmap bitmap, ReadingBean readInfo);
        View getContentView();
        void showThrowable(final Throwable throwable);
        void openDocumentFinsh();
        void updateDirectory(ReaderDocumentTableOfContent content);
    }

    interface ReaderPresenter extends BasePresenter {
        void openDocument(final String documentPath, final String booId);
        void setDocumentViewRect(final int width, final int height);
        void gotoPage(final int page);
        Reader getReader();
        void getDirectory();
        void close();
    }
}
