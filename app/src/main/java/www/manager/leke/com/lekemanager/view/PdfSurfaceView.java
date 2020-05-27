package www.manager.leke.com.lekemanager.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;

import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.api.device.epd.UpdateMode;

import www.manager.leke.com.lekemanager.configuration.HtppConfiguration;
import www.manager.leke.com.lekemanager.utils.DeviceTypeGlobal;
import www.manager.leke.com.lekemanager.utils.LogUtils;
import www.manager.leke.com.lekemanager.utils.RefreshUtils;
import www.manager.leke.com.lekemanager.utils.UIUtils;

/**
 * 功能：教材加载PDF的surfaceView
 * 作者: Cao ZH
 * 日期: 2020/4/22 0020
 * 时间: 下午 15:21
 */
public class PdfSurfaceView extends SurfaceView {


    public PdfSurfaceView(Context context) {
        super(context);
    }

    public PdfSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PdfSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int i = 0;

    public void setSurfaceViewPdfBitmap(Bitmap bitmap, View rootView) {
        //1
        Bitmap imageBitmap = getImageBitmap(bitmap);
        //2
        Rect region = new Rect(0, 0, imageBitmap.getWidth(), imageBitmap.getHeight());
        Bitmap dst = Bitmap.createBitmap(region.width(), region.height(), Bitmap.Config.ARGB_8888);
        renderToBitmap(imageBitmap, dst);
        Canvas canvas = getCanVas();
        if (canvas == null) {
            return;
        }

        try {
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
            if (dst == null) {
                return;
            }
            paint.setDither(true);
            canvas.drawBitmap(dst, 0, 0, new Paint());
        } finally {
            i++;
            if (i == 5) {
                i = 0;
                RefreshUtils.invalidate(rootView);
            }
            getHolder().unlockCanvasAndPost(canvas);
            afterRestModel();
        }
    }

    private Canvas getCanVas() {
        Canvas canvas;
        if (HtppConfiguration.DEVICE_MODEL.equalsIgnoreCase(DeviceTypeGlobal.DEVICE_TYPE_EDU)) {
            canvas = getHolder().lockCanvas();
            //EDU 位置
            beforRegalModel();
        } else {
            //107 note位置
            beforRegalModel();
            canvas = getHolder().lockCanvas();
        }
        return canvas;
    }

    private void beforRegalModel() {
        EpdController.enableRegal();
        EpdController.setViewDefaultUpdateMode(this, UpdateMode.REGAL);
        EpdController.enablePost(this, 1);
        LogUtils.e("袁野... ... mode ...............11111111111111111111111111111111..");
    }

    private void afterRestModel() {
        EpdController.resetUpdateMode(this);
        LogUtils.e("袁野... ... mode ...............222222..");
    }

    private void renderToBitmap(Bitmap viewportBitmap, Bitmap dst) {
        Canvas canvas = new Canvas(dst);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(viewportBitmap, 0, 0, paint);
    }

    private Bitmap getImageBitmap(Bitmap bitmap) {
        Rect region = new Rect(0, 0, UIUtils.getScreenWidth(), UIUtils.getScreenHeight());
        Bitmap renderBitmap = Bitmap.createBitmap(region.width(), region.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(renderBitmap);
        canvas.drawBitmap(bitmap,
                region,
                new Rect(0, 0, region.width(), region.height()),
                new Paint(Paint.FILTER_BITMAP_FLAG));
        return renderBitmap;
    }


}
