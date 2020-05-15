package www.manager.leke.com.lekemanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import rx.functions.Action1;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.activity.BookActiivty;
import www.manager.leke.com.lekemanager.activity.ReadPdfBookActivity;
import www.manager.leke.com.lekemanager.base.BaseFragmentActivity;
import www.manager.leke.com.lekemanager.bean.BaseEvent;
import www.manager.leke.com.lekemanager.bean.Ceshibean;
import www.manager.leke.com.lekemanager.bean.MainBookMessageBean;
import www.manager.leke.com.lekemanager.bean.OpenBookInfo;
import www.manager.leke.com.lekemanager.bean.SumbitBookStatus;
import www.manager.leke.com.lekemanager.configuration.GlobalConstant;
import www.manager.leke.com.lekemanager.dialog.BaseDialogs;
import www.manager.leke.com.lekemanager.dialog.DialogUtils;
import www.manager.leke.com.lekemanager.fragment.BookFragment;
import www.manager.leke.com.lekemanager.http.ApiException;
import www.manager.leke.com.lekemanager.http.HttpManager;
import www.manager.leke.com.lekemanager.manager.AppManager;
import www.manager.leke.com.lekemanager.manager.DownOssManager;
import www.manager.leke.com.lekemanager.manager.DownVoiceView;
import www.manager.leke.com.lekemanager.manager.OpenReaderUtils;
import www.manager.leke.com.lekemanager.manager.ThreadManager;
import www.manager.leke.com.lekemanager.utils.CodeValueUtils;
import www.manager.leke.com.lekemanager.utils.Contacts;
import www.manager.leke.com.lekemanager.utils.FileUtils;
import www.manager.leke.com.lekemanager.utils.GsonUitls;
import www.manager.leke.com.lekemanager.utils.ImageLoader;
import www.manager.leke.com.lekemanager.utils.LogUtils;
import www.manager.leke.com.lekemanager.utils.NetUtils;
import www.manager.leke.com.lekemanager.utils.SpUtils;
import www.manager.leke.com.lekemanager.utils.StringUtils;
import www.manager.leke.com.lekemanager.utils.ToastUtils;
import www.manager.leke.com.lekemanager.utils.UIUtils;

/**
 * Created by ypu
 * on 2020/5/6 0006
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MyViewHoder> {

    private List<MainBookMessageBean> mMainBookMessageBeanListnew;
    BaseDialogs.Builder mBuilder;
    private BaseDialogs mDialogs;
    Context mContext;
    DialogUtils mDialogUtils;
    private List<String> mArrayList;

    public MainRecyclerAdapter(List<MainBookMessageBean> mainBookMessageBeanListnew, Context context) {
        this.mMainBookMessageBeanListnew = mainBookMessageBeanListnew;
        this.mContext = context;
        mBuilder = new BaseDialogs.Builder(mContext);
        addMap();

    }

    private void addMap() {

    }

    @Override
    public MyViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = UIUtils.inflate(R.layout.item_main_booklist);
        return new MyViewHoder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHoder holder, final int position) {
        MainBookMessageBean mainBookMessageBean = mMainBookMessageBeanListnew.get(position);
        holder.text_title.setText(mainBookMessageBean.getBookTitle());//大标题
        holder.text_title_two.setText(mainBookMessageBean.getBookDisplayTitle());//副标题
        String codeValues = CodeValueUtils.getInstance().getCodeValues(mainBookMessageBean.getBookStatus());
        holder.text_type.setText(codeValues);//状态值
        if (codeValues.equals(Contacts.BA02) || codeValues.equals(Contacts.BA03)) {
            holder.text_type.setBackground(mContext.getResources().getDrawable(R.drawable.img_cyan));
            //最后一个按钮的字体显示
            holder.text_name.setText(UIUtils.getString(R.string.string_ok));
        } else if (codeValues.equals(Contacts.BA05)) {
            holder.liner_type_ground.setBackground(mContext.getResources().getDrawable(R.drawable.img_orange));
            holder.text_name.setText(UIUtils.getString(R.string.string_proofread));
        } else {
            holder.liner_type_ground.setBackground(mContext.getResources().getDrawable(R.drawable.img_blue));
            holder.text_name.setText(UIUtils.getString(R.string.string_examine_ok));
        }
        ImageLoader.getInstance().loadTeachBook(mContext, holder.img_book, mainBookMessageBean.getCoverLAtchRemotePath(), true);
        //最后一个按钮是否可以点击
        if (mArrayList == null) {
            String string = SpUtils.getString(Contacts.PERMISSIOMIDS);
            mArrayList = GsonUitls.fromJson(string, ArrayList.class);
        }
        if (mArrayList.contains(Contacts.BOOKJURISDICTION)) {
            if ("BA02".equals(mainBookMessageBean.getBookStatus())) {
                holder.fram_ok.setBackground(UIUtils.getDrawable(R.drawable.shape_background_hui));
            } else {
                holder.fram_ok.setBackground(UIUtils.getDrawable(R.drawable.img_back_ground_button));
                holder.fram_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mDialogUtils == null) {
                            mDialogUtils = new DialogUtils(mContext);
                        }
                        if (holder.text_name.getText().toString().equals(UIUtils.getString(R.string.string_ok))) {
                            //提交按钮
                            mDialogUtils.TijiaoDialog();
                            mDialogUtils.setSunmissionOnClick(new DialogUtils.SunmissionOnClick() {
                                @Override
                                public void OnClickListener(BaseDialogs mDialogs, String mEditText) {
                                    Log.d("TAG", "------内容：" + mEditText);
                                    NetworkSubmit(mainBookMessageBean, Contacts.BA04, mEditText);
                                    mDialogs.dismiss();

                                }
                            });

                        }
                        //校对通过弹窗
                        if (holder.text_name.getText().toString().equals(UIUtils.getString(R.string.string_proofread))) {
                            mDialogUtils.setTitle("校对通过 ?");
                            mDialogUtils.XiaoduiDialog();

                            mDialogUtils.setXiaodOnClick(new DialogUtils.XiaodOnClick() {
                                @Override
                                public void OnClickListener(BaseDialogs mDialogs) {
                                    NetworkSubmit(mainBookMessageBean, Contacts.BA07, null);
                                    mDialogs.dismiss();
                                }
                            });
                        } else if (holder.text_name.getText().toString().equals(UIUtils.getString(R.string.string_examine_ok))) {
                            mDialogUtils.setTitle("审核通过 ?");
                            mDialogUtils.XiaoduiDialog();
                            mDialogUtils.setXiaodOnClick(new DialogUtils.XiaodOnClick() {
                                @Override
                                public void OnClickListener(BaseDialogs mDialogs) {
                                    NetworkSubmit(mainBookMessageBean, Contacts.BA11, null);
                                    mDialogs.dismiss();
                                }
                            });
                        }
                    }

                });
            }
        }


        //设置监听
        holder.fram_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateBook(position);

            }
        });
        holder.fram_lookpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ReadPdfBookActivity.class);
                mContext.startActivity(intent);
            }
        });
        holder.fram_bookmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BookActiivty.class);
                mContext.startActivity(intent);
            }
        });

    }


    //提交请求/校对请求，审核请求
    private void NetworkSubmit(MainBookMessageBean mainBookMessageBean, String booStatus, String nEditText) {
        SumbitBookStatus sumbitBookStatus = new SumbitBookStatus(mainBookMessageBean.getBookId(), booStatus, nEditText);
        if (NetUtils.isWifiConnected()) {
            HttpManager.getInstace().getUpdateStatus(sumbitBookStatus).subscribe(new Action1<Object>() {
                @Override
                public void call(Object o) {
                    ToastUtils toastUtil2 = new ToastUtils(mContext, R.layout.toast_layouts, "提交成功");
                    toastUtil2.show();
                    BaseEvent baseEvent = new BaseEvent();
                    baseEvent.setType(Contacts.BOOKFRAGMENT);
                    EventBus.getDefault().postSticky(baseEvent);

                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    if (throwable instanceof ApiException) {
                        UIUtils.showToastSafe(throwable.getMessage(), 0);
                    }
                }
            });
        } else {
            UIUtils.showToastSafe(UIUtils.getString(R.string.no_net));
        }

    }

    @Override
    public int getItemCount() {
        return mMainBookMessageBeanListnew.size() > 0 ? mMainBookMessageBeanListnew.size() : 0;
    }

    class MyViewHoder extends RecyclerView.ViewHolder {
        ImageView img_book;
        TextView text_title, text_title_two, text_type, text_name;
        LinearLayout liner_type_ground;
        ProgressBar progress_bar;
        FrameLayout fram_update, fram_bookmessage, fram_lookpdf, fram_ok;

        public MyViewHoder(View itemView) {
            super(itemView);
            img_book = itemView.findViewById(R.id.img_book);
            text_title = itemView.findViewById(R.id.text_title);
            text_title_two = itemView.findViewById(R.id.text_title_two);
            liner_type_ground = itemView.findViewById(R.id.liner_type_ground);
            text_type = itemView.findViewById(R.id.text_type);
            progress_bar = itemView.findViewById(R.id.progress_bar);
            fram_update = itemView.findViewById(R.id.fram_update);
            fram_bookmessage = itemView.findViewById(R.id.fram_bookmessage);
            fram_lookpdf = itemView.findViewById(R.id.fram_lookpdf);
            fram_ok = itemView.findViewById(R.id.fram_ok);
            text_name = itemView.findViewById(R.id.text_name);
        }
    }

    private void showUpdateBook(final int pos) {
        //设置触摸dialog外围是否关闭
        mDialogs = mBuilder.setViewId(R.layout.dialog_updatebook)
                .setGravity(Gravity.CENTER)
                .setAnimation(R.style.Alpah_aniamtion)
                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
                //设置触摸dialog外围是否关闭
                .isOnTouchCanceled(true)
                .builder();
        mDialogs.show();
        mDialogs.getView(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   itemClick(pos);
//                new OpenReaderUtils(new OpenBookInfo()
//                        .setProgressg(true)
//                        .setBookId(bookId+"")
//
//                ).openReader();
                //更新
                mDialogs.dismiss();
            }
        });
        mDialogs.getView(R.id.img_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogs.dismiss();
            }
        });
    }


}
