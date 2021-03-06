package www.manager.leke.com.lekemanager.utils;

/**
 * Created by ypu
 * on 2020/5/13 0013
 */
public class Contacts {
    public static final String URL = "http:";
    public static final String OSSPATH="http://";
    public static final String OSSASFFIX=".aliyuncs.com";
    public static final String TOKEN = "token";
    public static final String INFORBEAN = "inforbean";
    public static final String ACCOUNT = "account";
    public static final String PASSWORD = "password";
    //权限集合
    public static final String PERMISSIOMIDS = "permissionIds";
    public static final String BA02 = "待编辑";
    public static final String BA03 = "编辑中";
    public static final String BA05 = "待校对";
    public static final String BA09 = "待审核";
    //点读
    public static final String BT02 = "BT02";
    public static final String BT03 = "BT03";
    public static final String BT05 = "BT05";
    public static final String BT09 = "BT09";
    //------------------------------提交点读图书状态 http://192.168.12.13:8888/zentao/doc-view-40.html
    public static final String BT04 = "BT04"; //提交点读
    public static final String BT07 = "BT07";//校对通过
    public static final String BT11 = "BT11";//审核通过
    //图书模块按钮提交时候状态值
    public static final String BA04 = "BA04";//图书提交按钮上传的状态
    public static final String BA07 = "BA07";//图书校对按钮；
    public static final String BA11 = "BA11";//图书审核按钮提交
    public static final String Xiaodui = "BA05";//校对
    public static final String EXAMINE = "BA09";//审核
    //登录时候用户自带角色
    public static final String BOOKJURISDICTION = "7-1-1";//用户操作图书图书权限192.168.12.13:8888/zentao/doc-view-541.html
    public static final String AUTODIOBOOK = "7-2-1";//用户处理点读的权限       192.168.12.13:8888/zentao/doc-view-541.html
    public static final String QUESTIONBANK = "7-3-1";//用户处理题库的权限
    //Eventbus
    public static final String BOOKFRAGMENT = "bookfragment";//刷新图书页面数据
    public static final String BOOKREAD = "audiobook";//刷新点读模块
    public static final String QUESTIONBANKS = "questionbank";//刷新题库列表

    //下载更新时间dialog展示，根据谁来取
    public static final String READ = "diandu";

    public static final String BOOKID = "bookId";
    //---------------------------------------------图书详情-----
    public static final String KEBEN = "BB01";
    public static final String KEWAISHU = "BB03";
    public static final String BOOKDETAIL = "bookMessageDetail";
    //----------------------------------题库--------------------
    public static final String STATUSCODE = "qStatusCode";
    public static final String IB01 = "IB01"; //isModifier=true时候传
    public static final String IB02 = "IB02"; //isInspector=true时候传
    public static final String IB03 = "IB03";//isApprover=true时候传
    public static final String POSITION="position";

}
