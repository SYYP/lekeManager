package www.manager.leke.com.lekemanager.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ypu
 * on 2020/5/13 0013
 */
public class LoginBean {


    /**
     * userRoleIds : [1]
     * userId : 1
     * userName : root-update
     * userGenderCode : UA01
     * userStatusCode : UC01
     * account : root
     * userToken : BwAcYbUGm5wM5cPo
     * roleSystemIds : [7]
     *  permissionIds : ["7","7-1","7-1-1"]
     */

    private int userId;
    private String userName;
    private String userGenderCode;
    private String userStatusCode;
    private String account;
    private String userToken;
    private List<Integer> userRoleIds;
    private List<Integer> roleSystemIds;
    private ArrayList<String> permissionIds;

    public List<Integer> getRoleSystemIds() {
        return roleSystemIds;
    }

    public void setRoleSystemIds(List<Integer> roleSystemIds) {
        this.roleSystemIds = roleSystemIds;
    }

    public ArrayList<String> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(ArrayList<String> permissionIds) {
        this.permissionIds = permissionIds;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGenderCode() {
        return userGenderCode;
    }

    public void setUserGenderCode(String userGenderCode) {
        this.userGenderCode = userGenderCode;
    }

    public String getUserStatusCode() {
        return userStatusCode;
    }

    public void setUserStatusCode(String userStatusCode) {
        this.userStatusCode = userStatusCode;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public List<Integer> getUserRoleIds() {
        return userRoleIds;
    }

    public void setUserRoleIds(List<Integer> userRoleIds) {
        this.userRoleIds = userRoleIds;
    }
}
