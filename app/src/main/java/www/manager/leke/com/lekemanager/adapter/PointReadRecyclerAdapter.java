//package www.manager.leke.com.lekemanager.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import java.util.List;
//
//import www.manager.leke.com.lekemanager.R;
//import www.manager.leke.com.lekemanager.activity.BookActiivty;
//import www.manager.leke.com.lekemanager.bean.MainBookMessageBean;
//import www.manager.leke.com.lekemanager.dialog.BaseDialogs;
//import www.manager.leke.com.lekemanager.dialog.DialogUtils;
//import www.manager.leke.com.lekemanager.utils.UIUtils;
//
///**
// * Created by ypu
// * on 2020/5/6 0006
// * 点读适配器
// */
//public class PointReadRecyclerAdapter extends RecyclerView.Adapter<PointReadRecyclerAdapter.MyViewHoder> {
//
//
//    private List<MainBookMessageBean> mMainBookMessageBeanList;
//    BaseDialogs.Builder mBuilder;
//    private BaseDialogs mDialogs;
//    Context mContext;
//    DialogUtils mDialogUtils;
//
//    public PointReadRecyclerAdapter(List<MainBookMessageBean> mainBookMessageBeans, Context context) {
//        mMainBookMessageBeanList = mainBookMessageBeans;
//        this.mContext = context;
//        mBuilder = new BaseDialogs.Builder(mContext);
//
//    }
//
//    @Override
//    public MyViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = UIUtils.inflate(R.layout.item_point_read);
//        return new MyViewHoder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(final MyViewHoder holder, final int position) {
//        holder.text_title.setText(mStringList.get(position).toString());
//
//          if(position==0||position==2||position==4){
//              holder.text_name.setText("校对通过");
//          }
//          else  if (position==1){
//              holder.text_name.setText("审核通过");
//          }
//        //检查更新
//        holder.fram_update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showUpdateBook();
//
//            }
//        });
//          //跳转到点读
//        holder.fram_prview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//         //位置查询
//        holder.fram_findpositon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//        holder.fram_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mDialogUtils == null) {
//                    mDialogUtils = new DialogUtils(mContext);
//                }
//                if (position == 0 || position == 2 || position == 4) {
//                    mDialogUtils.XiaoduiDialog();
//                    mDialogUtils.setXiaodOnClick(new DialogUtils.XiaodOnClick() {
//                        @Override
//                        public void OnClickListener(BaseDialogs mDialogs) {
//                            mDialogs.dismiss();
//                        }
//                    });
//                }else if(position==1){
//
//                }else {
//                       mDialogUtils.TijiaoDialog();
//                       mDialogUtils.setSunmissionOnClick(new DialogUtils.SunmissionOnClick() {
//                           @Override
//                           public void OnClickListener(BaseDialogs mDialogs, String mEditText) {
//                               Log.d("TAG","------内容："+mEditText);
//                               mDialogs.dismiss();
//                           }
//                       });
//                }
//            }
//
//        });
//    }
//
//    private void showUpdateBook() {
//        //设置触摸dialog外围是否关闭
//        mDialogs = mBuilder.setViewId(R.layout.dialog_updatebook)
//                .setGravity(Gravity.CENTER)
//                .setAnimation(R.style.Alpah_aniamtion)
//                .setWidthHeightpx(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
//                //设置触摸dialog外围是否关闭
//                .isOnTouchCanceled(true)
//                .builder();
//        mDialogs.show();
//        mDialogs.getView(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //更新
//                mDialogs.dismiss();
//            }
//        });
//        mDialogs.getView(R.id.img_pause).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDialogs.dismiss();
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return mStringList.size() > 0 ? mStringList.size() : 0;
//    }
//
//    class MyViewHoder extends RecyclerView.ViewHolder {
//        ImageView img_book;
//        TextView text_title, text_title_two, text_type,text_name;
//        LinearLayout liner_type_ground;
//        ProgressBar progress_bar;
//        FrameLayout fram_update, fram_findpositon, fram_prview, fram_ok;
//
//        public MyViewHoder(View itemView) {
//            super(itemView);
//            img_book = itemView.findViewById(R.id.img_book);
//            text_title = itemView.findViewById(R.id.text_title);
//            text_title_two = itemView.findViewById(R.id.text_title_two);
//            liner_type_ground = itemView.findViewById(R.id.liner_type_ground);
//            text_type = itemView.findViewById(R.id.text_type);
//            progress_bar = itemView.findViewById(R.id.progress_bar);
//            fram_update = itemView.findViewById(R.id.fram_update);
//            fram_findpositon = itemView.findViewById(R.id.fram_findpositon);
//            fram_prview = itemView.findViewById(R.id.fram_prview);
//            fram_ok = itemView.findViewById(R.id.fram_ok);
//            text_name= itemView.findViewById(R.id.text_name);
//        }
//    }
//}
