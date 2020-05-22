package www.manager.leke.com.lekemanager.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;


import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import www.manager.leke.com.lekemanager.bean.BaseEvent;
import www.manager.leke.com.lekemanager.utils.LogUtils;

/**
 * 功能：
 * 作者: YUAN_YE
 * 日期: 2019/5/6
 * 时间: 18:59
 */
public abstract class BaseFragment extends RxFragment {
    private String TAG = BaseFragment.class.getName();
    public String mChildTag = "childerTag";
    public View mRootView;
    private Unbinder mUnbind;
    public FragmentActivity mContext;
    public abstract void setChildTag() ;

    /***
     * onAttach:  拿到Activitys回力对象 ，但是UI 还没初始化完成。
     *
     * 官方描述中说到, 该方法会在 Fragment 第一次和它的 Context 建立关联的时候得到回调, 这个 context 便是 宿主 Activity,
     * 该方法的参数便是宿主 Activity, 我们可以在这里拿到 Activity 实例对象, 然后可以和 Activity 进行通信,
     * 最常见的便是在这里进行接口变量的初始化操作, 以便后续通过接口通信.
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setChildTag();
        LogUtils.e(mChildTag, "onAttach");
    }


    /**
     * onCreate: bundle获取数据 ,ActivityUI还未初始化
     * <p>
     * 该方法和 Activity 的回调很类似, 可以在这里做一些初始化操作, 如获取 Activity 通过 bundle 传递过来的数据.
     * 注意: 官方明确说明到, 当该方法回调时, Activity 可能还处于创建的过程中, 你不能在该方法内去操纵 Activity 的 UI 层级组件,
     * 如果你需要的话, 你应该在 onActivityCreate 方法内去干那些事情.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtils.e(mChildTag, "onCreate");
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    /**
     * onCreateView: 初始化fragment..UI
     * <p>
     * 该方法用来初始化 Fragment 用户视图界面, 官方描述中说到, 这是可选的, 我们可以在这里返回一个 null,
     * 代表着这个 Fragment 没有视图界面. 可能有同学会疑问, 既然没有视图界面, 那我们还要这个 Fragment 干嘛,
     * 在这里我简单提以下, 我们可以用一个没有视图的 Fragment 来监控 Activity 的生命周期, 从而可以封装一些很优美的操作,
     * 比如 Glide 的生命周期监控中就采用了这种思想.
     * 如果我们在这里返回了一个非空的视图, 那么他之后将会在 OnDestoryView 回调中得到销毁.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.e(mChildTag, "onCreateView");
        mRootView = getXmlView();
        mUnbind = ButterKnife.bind(this, mRootView);
        EventBus.getDefault().register(this);
        initXmlData();
        loadData();
        return mRootView;
    }



    /**
     * onActivityCreate: Activity 的 UI 控件.
     * <p>
     * 该方法会在宿主 Activity 被完全创建的时候得到回调, 我们可以在这里去操纵 Activity 的 UI 控件.
     * 官方描述中说到, 这是一个很好的地方去调用  setRetainInstance(boolean) 
     * 方法来保存 Fragment 实例对象当 Activity 被重建的时候 (如屏幕旋转).
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        LogUtils.e(mChildTag, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 当Fragment可见时调用。
     */
    @Override
    public void onStart() {
        LogUtils.e(mChildTag, "onStart");
        super.onStart();
    }

    /**
     * 当Fragment可见且可交互时调用
     */
    @Override
    public void onResume() {
        LogUtils.e(mChildTag, "onResume");
        super.onResume();
    }

    /**
     * Fragment不可交互但可见时调用
     */
    @Override
    public void onPause() {
        super.onPause();
        LogUtils.e(mChildTag, "onPause");
    }

    /**
     * 当Fragment不可见时调用
     */
    @Override
    public void onStop() {
        super.onStop();
        LogUtils.e(mChildTag, "onStop");
    }

    /**
     * onDestoryView:
     * <p>
     * 这个方法会在销毁之前由 onCreateView 方法创建的 view 的时候得到回调,
     * 在 Fragment 需要显示视图时, 一个新的 View 将会被创建.
     * 注意: 官方明确说明, 该方法的回调和 onCreateView 没有太多的关联,
     * 也就是说即使你在 onCreateView 方法中返回了 null, 该方法也会得到回调.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.e(mChildTag, "onDestroyView");
    }

    /**
     * onDestory:
     * <p>
     * 和 Activity 的 onDestory 类似, 销毁一个 fragment, 代表着这个 fragment 不会再使用.。
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e(mChildTag, "onDestroy");
        EventBus.getDefault().unregister(this);
        mUnbind.unbind();
    }

    /**
     * 当Fragment和Activity解除关联时调用
     * <p>
     * onDetach:
     * <p>
     * 表示该 fragment 和不在和宿主 Activity 有关联, 这是 fragment 生命周期的最后一个回调方法, 这个回调方法执行完之后,
     * 注意: 如果我们通过  setRetainInstance(boolean)  方法来保存 Fragment 实例对象当 Activity 被重现创建的时候, 该方法会在 onDestoryView 之后得到回调.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.e(mChildTag, "onDetach");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtils.e(mChildTag, "onHiddenChanged .hidden:" + hidden);
    }

    /////////////////////////////////////// ///////////////////////////////////////


    public void onEventMainThread(BaseEvent event) {
        try {
            LogUtils.e(TAG, "onEventMainThread+"+event.getType());
        }catch (Exception e){
            e.printStackTrace();
        }

        if (event == null) {
            return;
        }
    }


    /**
     * 获取XML View bind绑定View
     */
    protected abstract View getXmlView();

    /**
     * 初始化View的 XML 初始值 已经UI 等
     */
    protected abstract void initXmlData();

    /***
     * 加载数据 包含网络请求以及intent传递数据的处理
     */
    protected abstract void loadData();


    protected void startIntent(Context packageContext, Class<?> cls) {
        Intent intent = new Intent(packageContext, cls);
        startActivity(intent);
    }

    protected void startIntent(Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
    }

    public void startIntentWithExtras(Class<? extends Activity> cls, Bundle extras) {
        Intent intent = new Intent(mContext, cls);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void startIntentWithSpecificFlag(Class<?> cls, int flag) {
        Intent intent = new Intent(mContext, cls);
        intent.setFlags(flag);
        startActivity(intent);
    }


}
