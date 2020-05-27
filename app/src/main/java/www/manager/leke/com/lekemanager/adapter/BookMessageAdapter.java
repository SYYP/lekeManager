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

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.bean.Artist;
import www.manager.leke.com.lekemanager.bean.BookMessageDetail;
import www.manager.leke.com.lekemanager.utils.UIUtils;
import www.manager.leke.com.lekemanager.view.HeaderViewHolder;
import www.manager.leke.com.lekemanager.view.ItemViewHolder;

/**
 * Created by ypu
 * on 2020/5/7 0007
 */
public class BookMessageAdapter extends RecyclerView.Adapter<BookMessageAdapter.MyViewHolder> {

    List<BookMessageDetail.BookContentsBean.NodesBeanX> nodes;
    private Context mContext;


    public BookMessageAdapter(List<BookMessageDetail.BookContentsBean.NodesBeanX> nodes, Context context) {
        this.nodes = nodes;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = UIUtils.inflate(R.layout.item_xqchild);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (nodes != null && !nodes.isEmpty()) { //第一阶级
            holder.mtextTitle.setText(nodes.get(position).getName());//添加主title
            List<BookMessageDetail.BookContentsBean.NodesBeanX.NodesBean> nodes = this.nodes.get(position).getNodes();
            if (position == 0) {
                holder.mLinearLayout.setVisibility(View.VISIBLE);
                holder.mImgLeft.setImageDrawable(UIUtils.getDrawable(R.drawable.img_icon_xia));
            }
            if (nodes != null && nodes.size() > 0) {//添加页数
                holder.mTextpage.setText(nodes.size() + "页");
                for (int i = 0; i < nodes.size(); i++) {//第二级
                    View inflate = LayoutInflater.from(mContext).inflate(R.layout.add_xq_view, null, false);
                    TextView mTextTitle = inflate.findViewById(R.id.text_title);
                    TextView mTextpage = inflate.findViewById(R.id.text_page);
                    LinearLayout linearLayout = inflate.findViewById(R.id.liner_adds);
                    //加载数据
                    mTextTitle.setText(nodes.get(i).getName());
                    holder.mLinearLayout.addView(inflate);
                    List<BookMessageDetail.BookContentsBean.NodesBeanX.NodesBean> nodes1 = nodes.get(i).getNodes();
                    if (nodes1 != null && nodes1.size() > 0) {
                        mTextpage.setText(nodes1.size() + "页");
                        for (int j = 0; j < nodes1.size(); j++) {//第三级
                            View inflate1 = LayoutInflater.from(mContext).inflate(R.layout.add_xq_view, linearLayout, false);
                            TextView mTextTitle1 = inflate1.findViewById(R.id.text_title);
                            TextView mTextpage1 = inflate1.findViewById(R.id.text_page);
                            LinearLayout linearLayout1 = inflate1.findViewById(R.id.liner_adds);
                            //加载数据
                            mTextTitle1.setText(nodes1.get(i).getName());
                            linearLayout.addView(inflate1);
                            List<BookMessageDetail.BookContentsBean.NodesBeanX.NodesBean> nodes2 = nodes1.get(j).getNodes();
                            if (nodes2 != null && nodes2.size() > 0) {
                                mTextpage1.setText(nodes2.size() + "页");
                                for (int k = 0; k < nodes2.size(); k++) {//第四级
                                    View inflate2 = LayoutInflater.from(mContext).inflate(R.layout.add_xq_view, linearLayout1, false);
                                    TextView mTextTitle2 = inflate2.findViewById(R.id.text_title);
                                    TextView mTextpage2 = inflate2.findViewById(R.id.text_page);
                                    LinearLayout linearLayout2 = inflate2.findViewById(R.id.liner_adds);
                                    //加载数据
                                    mTextTitle2.setText(nodes2.get(i).getName());
                                    linearLayout1.addView(inflate2);
                                    List<BookMessageDetail.BookContentsBean.NodesBeanX.NodesBean> nodes3 = nodes2.get(k).getNodes();
                                    if (nodes3 != null && nodes3.size() > 0) {
                                        mTextpage2.setText(nodes3.size() + "页");

                                    }
                                }
                            }
                        }

                    }
                }
            }
            holder.mImgLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if ((holder.mLinearLayout.getVisibility() == View.VISIBLE)) {
                        holder.mLinearLayout.setVisibility(View.GONE);
                        holder.mImgLeft.setImageDrawable(UIUtils.getDrawable(R.drawable.img_icon_left));
                    } else {
                        holder.mLinearLayout.setVisibility(View.VISIBLE);
                        holder.mImgLeft.setImageDrawable(UIUtils.getDrawable(R.drawable.img_icon_xia));
                    }
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return nodes.size() > 0 ? nodes.size() : 0;
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
}
