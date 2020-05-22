package www.manager.leke.com.lekemanager.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import www.manager.leke.com.lekemanager.R;

/**
 * Created by ypu
 * on 2020/5/14 0014
 */
public class ImageLoader {
    private static ImageLoader instance;

    private ImageLoader() {

    }

    public static synchronized ImageLoader getInstance() {
        if (instance == null) {
            instance = new ImageLoader();
        }
        return instance;
    }
    public void loadTeachBook(Context context, ImageView imageView, String url, boolean isSmall) {
        String newUrl = "";
        if (!TextUtils.isEmpty(url) && !url.contains(Contacts.URL)) {
            newUrl = Contacts.URL + url;
        } else {
            newUrl = url;
        }
        DrawableRequestBuilder builder = Glide.with(context)
                .load(newUrl)
                .placeholder(R.drawable.img_book_default)
                .error(R.drawable.img_book_default)
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        if (isSmall) {
            builder.override(UIUtils.getDimension(R.dimen.x200), UIUtils.getDimension(R.dimen.y150)).into(imageView);
        } else {
            builder.override(UIUtils.getDimension(R.dimen.x320), UIUtils.getDimension(R.dimen.y426)).into(imageView);
        }

    }
    public void loadBookDetail(Context context, ImageView imageView, String url, String isBookType) {
        String newUrl = "";
        if (!TextUtils.isEmpty(url) && !url.contains(Contacts.URL)) {
            newUrl = Contacts.URL + url;
        } else {
            newUrl = url;
        }
        DrawableRequestBuilder builder = Glide.with(context)
                .load(newUrl)
                .placeholder(R.drawable.img_book_default)
                .error(R.drawable.img_book_default)
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        if (Contacts.KEBEN.equals(isBookType)) {  //课本 214*274
            builder.override(UIUtils.getDimension(R.dimen.x214), UIUtils.getDimension(R.dimen.y274)).into(imageView);
        } else { //课外书 190*250
            builder.override(UIUtils.getDimension(R.dimen.x190), UIUtils.getDimension(R.dimen.y252)).into(imageView);
        }

    }
}
