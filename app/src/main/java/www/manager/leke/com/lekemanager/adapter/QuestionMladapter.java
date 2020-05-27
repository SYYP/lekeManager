package www.manager.leke.com.lekemanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.bean.BookMessageDetail;
import www.manager.leke.com.lekemanager.interfaces.OnItemClickListener;
import www.manager.leke.com.lekemanager.utils.CodeValueUtils;
import www.manager.leke.com.lekemanager.utils.UIUtils;

/**
 * Created by ypu
 * on 2020/5/20 0020
 * 题库的目录
 */
public class QuestionMladapter extends RecyclerView.Adapter<QuestionMladapter.MyViewHolder> {
    List<BookMessageDetail.BookContentsBean.NodesBeanX> mNodesBeanXList;
    private Context mContext;
    private HashMap<String, Integer> mMap;

    public QuestionMladapter(List<BookMessageDetail.BookContentsBean.NodesBeanX> nodes, Context context, HashMap<String, Integer> map) {
        this.mNodesBeanXList = nodes;
        mContext = context;
        mMap = map;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = UIUtils.inflate(R.layout.item_xqchild);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (mNodesBeanXList != null && !mNodesBeanXList.isEmpty()) {//第一阶级
            holder.mtextTitle.setText(mNodesBeanXList.get(position).getName());
            List<BookMessageDetail.BookContentsBean.NodesBeanX.NodesBean> nodes = mNodesBeanXList.get(position).getNodes();
            holder.mImgLeft.setVisibility(View.GONE);
            holder.mLinearLayout.setVisibility(View.VISIBLE);
            holder.mTextpage.setText(CodeValueUtils.getInstance().getQestionNumber(String.valueOf(mNodesBeanXList.get(position).getId()), mMap) == null ? "" : CodeValueUtils.getInstance().getQestionNumber(String.valueOf(mNodesBeanXList.get(position).getId()), mMap) + "");
            holder.realOneTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick1(mNodesBeanXList.get(position).getId());
                }
            });
            if (nodes != null && nodes.size() > 0) {//添加页数
                for (int i = 0; i < nodes.size(); i++) {//第二级
                    View inflate = LayoutInflater.from(mContext).inflate(R.layout.add_xq_view, null, false);
                    TextView mTextTitle = inflate.findViewById(R.id.text_title);
                    TextView mTextpage = inflate.findViewById(R.id.text_page);
                    LinearLayout linearLayout = inflate.findViewById(R.id.liner_adds);
                    int finalI = i;
                    inflate.findViewById(R.id.real_title).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onItemClickListener.onItemClick1(nodes.get(finalI).getId());
                        }
                    });
                    //加载数据
                    mTextTitle.setText(nodes.get(i).getName());
                    mTextpage.setText(CodeValueUtils.getInstance().getQestionNumber(String.valueOf(nodes.get(i).getId()), mMap) == null ? "" : CodeValueUtils.getInstance().getQestionNumber(String.valueOf(nodes.get(i).getId()), mMap) + "");
                    holder.mLinearLayout.addView(inflate);
                    List<BookMessageDetail.BookContentsBean.NodesBeanX.NodesBean> nodes1 = nodes.get(i).getNodes();
                    if (nodes1 != null && nodes1.size() > 0) {
                        for (int j = 0; j < nodes1.size(); j++) {//第三级
                            View inflate1 = LayoutInflater.from(mContext).inflate(R.layout.add_xq_view, null, false);
                            TextView mTextTitle1 = inflate1.findViewById(R.id.text_title);
                            TextView mTextpage1 = inflate1.findViewById(R.id.text_page);
                            LinearLayout linearLayout1 = inflate1.findViewById(R.id.liner_adds);
                            int finalJ = j;
                            inflate1.findViewById(R.id.real_title).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    onItemClickListener.onItemClick1(nodes1.get(finalJ).getId());
                                }
                            });
                            //加载数据
                            mTextTitle1.setText(nodes1.get(j).getName());
                            mTextpage1.setText(CodeValueUtils.getInstance().getQestionNumber(String.valueOf(nodes1.get(j).getId()), mMap) == null ? "" : CodeValueUtils.getInstance().getQestionNumber(String.valueOf(nodes1.get(j).getId()), mMap) + "");
                            linearLayout.addView(inflate1);
                            List<BookMessageDetail.BookContentsBean.NodesBeanX.NodesBean> nodes2 = nodes1.get(j).getNodes();
                            if (nodes2 != null && nodes2.size() > 0) {
                                for (int k = 0; k < nodes2.size(); k++) {//第四级
                                    View inflate2 = LayoutInflater.from(mContext).inflate(R.layout.add_xq_view,linearLayout1 , false);
                                    TextView mTextTitle2 = inflate2.findViewById(R.id.text_title);
                                    TextView mTextpage2 = inflate2.findViewById(R.id.text_page);
                                    LinearLayout linearLayout2 = inflate2.findViewById(R.id.liner_adds);
                                    int finalK = k;
                                    inflate2.findViewById(R.id.real_title).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            onItemClickListener.onItemClick1(nodes2.get(finalK).getId());}
                                    });
                                    //加载数据
                                    mTextTitle2.setText(nodes2.get(k).getName());
                                    mTextpage2.setText(CodeValueUtils.getInstance().getQestionNumber(String.valueOf(nodes2.get(k).getId()), mMap) == null ? "" : CodeValueUtils.getInstance().getQestionNumber(String.valueOf(nodes2.get(k).getId()), mMap) + "");
                                    linearLayout1.addView(inflate2);
                                    List<BookMessageDetail.BookContentsBean.NodesBeanX.NodesBean> nodes3 = nodes2.get(k).getNodes();
                                    if (nodes3 != null && nodes3.size() > 0) {


                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mNodesBeanXList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout realOneTitle;
        TextView mtextTitle, mTextpage;
        ImageView mImgLeft;
        LinearLayout mLinearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            realOneTitle = itemView.findViewById(R.id.real_onetitle);
            mtextTitle = itemView.findViewById(R.id.text_title);
            mTextpage = itemView.findViewById(R.id.text_page);
            mImgLeft = itemView.findViewById(R.id.img_left);
            mLinearLayout = itemView.findViewById(R.id.add_liner);
        }
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
