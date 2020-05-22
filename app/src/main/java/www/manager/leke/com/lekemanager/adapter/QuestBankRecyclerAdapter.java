package www.manager.leke.com.lekemanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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
import java.util.List;

import de.greenrobot.event.EventBus;
import rx.functions.Action1;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.activity.BookActiivty;
import www.manager.leke.com.lekemanager.activity.QuestionDetailActivity;
import www.manager.leke.com.lekemanager.bean.BaseEvent;
import www.manager.leke.com.lekemanager.bean.MainBookMessageBean;
import www.manager.leke.com.lekemanager.bean.SumbitQuestion;
import www.manager.leke.com.lekemanager.dialog.BaseDialogs;
import www.manager.leke.com.lekemanager.dialog.DialogUtils;
import www.manager.leke.com.lekemanager.http.ApiException;
import www.manager.leke.com.lekemanager.http.HttpManager;
import www.manager.leke.com.lekemanager.utils.CodeValueUtils;
import www.manager.leke.com.lekemanager.utils.Contacts;
import www.manager.leke.com.lekemanager.utils.GsonUitls;
import www.manager.leke.com.lekemanager.utils.ImageLoader;
import www.manager.leke.com.lekemanager.utils.NetUtils;
import www.manager.leke.com.lekemanager.utils.SpUtils;
import www.manager.leke.com.lekemanager.utils.ToastUtils;
import www.manager.leke.com.lekemanager.utils.UIUtils;

/**
 * Created by ypu
 * on 2020/5/6 0006
 * 题库适配器
 */
public class QuestBankRecyclerAdapter extends RecyclerView.Adapter<QuestBankRecyclerAdapter.MyViewHoder> {
    private List<MainBookMessageBean> mMainBookMessageBeanList;
    Context mContext;
    DialogUtils mDialogUtils;
    private List<String> mArrayList;

    public QuestBankRecyclerAdapter(List<MainBookMessageBean> mainBookMessageBeans, Context context) {
        this.mMainBookMessageBeanList = mainBookMessageBeans;
        this.mContext = context;
        if(mDialogUtils==null){
            mDialogUtils=new DialogUtils(context);
        }
    }

    @Override
    public MyViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = UIUtils.inflate(R.layout.item_question_bank);
        return new MyViewHoder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHoder holder, final int position) {
        MainBookMessageBean mainBookMessageBean = mMainBookMessageBeanList.get(position);
        holder.text_title.setText(mainBookMessageBean.getBookTitle());//大标题
        holder.text_title_two.setText(mainBookMessageBean.getBookDisplayTitle());//副标题
        String codeValues = CodeValueUtils.getInstance().getCodeValues(mainBookMessageBean.getBookStatus());
        holder.text_type.setText(codeValues);//状态值
         if(mainBookMessageBean.isIsApprover()) {

             holder.text_name.setText(UIUtils.getString(R.string.string_examine_ok));
         }
         if(mainBookMessageBean.isIsInspector()){

             holder.text_name.setText(UIUtils.getString(R.string.string_proofread));
         }
         if(mainBookMessageBean.isIsModifier()){
             holder.text_name.setText(UIUtils.getString(R.string.string_ok));
         }
        holder.liner_type_ground.setBackground(mContext.getResources().getDrawable(R.drawable.shape_background_black));
        ImageLoader.getInstance().loadTeachBook(mContext, holder.img_book, mainBookMessageBean.getCoverLAtchRemotePath(), true);
        //判断最后一个按钮是否可以点击
        if (mArrayList == null) {
            String string = SpUtils.getString(Contacts.PERMISSIOMIDS);
            mArrayList = GsonUitls.fromJson(string, ArrayList.class);
        }
        if (mArrayList.contains(Contacts.QUESTIONBANK)) {
            //包含待处理题库权限
            if (holder.text_name.getText().toString().equals(UIUtils.getString(R.string.string_ok))) {
                if (Contacts.BA02.equals(mainBookMessageBean.getBookStatus())) {
                    //取得hasInsp=false时候可以点击
                    holder.fram_ok.setBackground(UIUtils.getDrawable(R.drawable.shape_background_hui));
                } else {
                    holder.fram_ok.setBackground(UIUtils.getDrawable(R.drawable.img_back_ground_button));
                    holder.fram_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (mDialogUtils == null) {
                                mDialogUtils = new DialogUtils(mContext);
                            }
                            mDialogUtils.TijiaoDialog();
                            mDialogUtils.setSunmissionOnClick(new DialogUtils.SunmissionOnClick() {
                                @Override
                                public void OnClickListener(BaseDialogs mDialogs, String mEditText) {
                                    Log.d("TAG", "------内容：" + mEditText);
                                    getQuestionSumbit(mainBookMessageBean, mEditText);
                                    mDialogs.dismiss();

                                }
                            });
                        }
                    });
                }
            }
            if (holder.text_name.getText().toString().equals(UIUtils.getString(R.string.string_proofread))) {
                if (!mainBookMessageBean.isHasInsp()) {
                    holder.fram_ok.setBackground(UIUtils.getDrawable(R.drawable.shape_background_hui));
                } else {
                    holder.fram_ok.setBackground(UIUtils.getDrawable(R.drawable.img_back_ground_button));
                    holder.fram_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mDialogUtils == null) {
                                mDialogUtils = new DialogUtils(mContext);
                            }
                            mDialogUtils.setTitle("校对通过 ?");
                            //校对通过弹窗
                            mDialogUtils.XiaoduiDialog();

                            mDialogUtils.setXiaodOnClick(new DialogUtils.XiaodOnClick() {
                                @Override
                                public void OnClickListener(BaseDialogs mDialogs) {
                                    getQuestionInspect(mainBookMessageBean);
                                    mDialogs.dismiss();
                                }
                            });
                        }
                    });
                }
            } //审核通过
            if (holder.text_name.getText().toString().equals(UIUtils.getString(R.string.string_examine_ok))) {
                if (mainBookMessageBean.isHasAppr()) {
                    holder.fram_ok.setBackground(UIUtils.getDrawable(R.drawable.img_back_ground_button));
                    holder.fram_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDialogUtils.setTitle("审核通过 ?");
                            mDialogUtils.XiaoduiDialog();

                            mDialogUtils.setXiaodOnClick(new DialogUtils.XiaodOnClick() {
                                @Override
                                public void OnClickListener(BaseDialogs mDialogs) {
                                    getQuestionApprove(mainBookMessageBean);
                                    mDialogs.dismiss();
                                }
                            });
                        }
                    });
                }
            }else {
                holder.fram_ok.setBackground(UIUtils.getDrawable(R.drawable.shape_background_hui));
            }
        }

        //题库信息
        holder.fram_look_question_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BookActiivty.class);
                intent.putExtra(Contacts.BOOKID, mainBookMessageBean.getBookId());
                mContext.startActivity(intent);
            }
        });
        //查看习题
        holder.string_look_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, QuestionDetailActivity.class);
                intent.putExtra(Contacts.BOOKID, mainBookMessageBean.getBookId());
                if (mainBookMessageBean.isIsModifier()) {
                    intent.putExtra(Contacts.STATUSCODE, Contacts.IB01);
                }
                if (mainBookMessageBean.isIsInspector()) {
                    intent.putExtra(Contacts.STATUSCODE, Contacts.IB02);
                }
                if (mainBookMessageBean.isIsApprover()) {
                    intent.putExtra(Contacts.STATUSCODE, Contacts.IB03);
                }
                mContext.startActivity(intent);

            }
        });

    }

    //提交审核
    private void getQuestionApprove(MainBookMessageBean mainBookMessageBean) {
        // appr //0:审核不通过，1：审核通过，必传
        SumbitQuestion sumbitQuestion = new SumbitQuestion(mainBookMessageBean.getBookId(), null, 1);
        if (NetUtils.isWifiConnected()) {
            HttpManager.getInstace().getQuestionApprove(sumbitQuestion)
                    .subscribe(new Action1<Object>() {
                        @Override
                        public void call(Object o) {
                            ToastUtils toastUtil2 = new ToastUtils(mContext, R.layout.toast_layouts, "提交成功");
                            toastUtil2.show();
                            BaseEvent baseEvent = new BaseEvent();
                            baseEvent.setType(Contacts.QUESTIONBANKS);
                            EventBus.getDefault().postSticky(baseEvent);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            if (throwable instanceof ApiException) {
                                UIUtils.showToastSafe(throwable.getMessage());
                            }
                        }
                    });
        } else {
            UIUtils.showToastSafe(R.string.no_net);
        }
    }

    //校对通过提交
    private void getQuestionInspect(MainBookMessageBean mainBookMessageBean) {
        SumbitQuestion sumbitQuestion = new SumbitQuestion(mainBookMessageBean.getBookId(), 1, null);
        if (NetUtils.isWifiConnected()) {
            HttpManager.getInstace().getQuestionInspect(sumbitQuestion)
                    .subscribe(new Action1<Object>() {
                        @Override
                        public void call(Object o) {
                            ToastUtils toastUtil2 = new ToastUtils(mContext, R.layout.toast_layouts, "提交成功");
                            toastUtil2.show();
                            BaseEvent baseEvent = new BaseEvent();
                            baseEvent.setType(Contacts.QUESTIONBANKS);
                            EventBus.getDefault().postSticky(baseEvent);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            if (throwable instanceof ApiException) {
                                UIUtils.showToastSafe(throwable.getMessage());
                            }
                        }
                    });
        } else {
            UIUtils.showToastSafe(R.string.no_net);
        }
    }

    //提交
    private void getQuestionSumbit(MainBookMessageBean mainBookMessageBean, String mEditText) {
        if (NetUtils.isWifiConnected()) {
            HttpManager.getInstace().getQuestionSumbit(mainBookMessageBean.getBookId(), mEditText)
                    .subscribe(new Action1<Object>() {
                        @Override
                        public void call(Object o) {
                            ToastUtils toastUtil2 = new ToastUtils(mContext, R.layout.toast_layouts, "提交成功");
                            toastUtil2.show();
                            BaseEvent baseEvent = new BaseEvent();
                            baseEvent.setType(Contacts.QUESTIONBANKS);
                            EventBus.getDefault().postSticky(baseEvent);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            if (throwable instanceof ApiException) {
                                UIUtils.showToastSafe(throwable.getMessage());
                            }
                        }
                    });
        } else {
            UIUtils.showToastSafe(R.string.no_net);
        }
    }


    @Override
    public int getItemCount() {
        return mMainBookMessageBeanList.size() > 0 ? mMainBookMessageBeanList.size() : 0;
    }

    class MyViewHoder extends RecyclerView.ViewHolder {
        ImageView img_book;
        TextView text_title, text_title_two, text_type, text_name;
        LinearLayout liner_type_ground;
        ProgressBar progress_bar;
        FrameLayout fram_update, string_look_question, fram_look_question_bank, fram_ok;

        public MyViewHoder(View itemView) {
            super(itemView);
            img_book = itemView.findViewById(R.id.img_book);
            text_title = itemView.findViewById(R.id.text_title);
            text_title_two = itemView.findViewById(R.id.text_title_two);
            liner_type_ground = itemView.findViewById(R.id.liner_type_ground);
            text_type = itemView.findViewById(R.id.text_type);
            progress_bar = itemView.findViewById(R.id.progress_bar);
            string_look_question = itemView.findViewById(R.id.string_look_question);
            fram_look_question_bank = itemView.findViewById(R.id.fram_look_question_bank);
            fram_ok = itemView.findViewById(R.id.fram_ok);
            text_name = itemView.findViewById(R.id.text_name);
        }


    }
}
