package com.dmcc.dmcc_crm.bean;

import com.dmcc.dmcc_crm.util.HanYuPinYin;
import com.dmcc.dmcc_crm.util.StringUtil;

import java.io.Serializable;

/**
 * 用户实体类
 *
 * @author zhangyunbo
 * @date 2016-3-18 下午3:18:47
 * @name User.java
 */
public class User implements Serializable {

    public static final long serialVersionUID = 1L;
    public String userId;// 用户 id
    public String userPhone;// 手机号，注册号
    public String password;// 密码
    public AuthContext authContext;// 服务器返回数据 用于安全验证
    public String userName;// 用户姓名
    public String userEmail;// 邮箱
    public String birthYear;// 出生年
    public String birthMonth;// 出生月日
    public String userCompany;// 公司
    public String userPosition;// 职务
    public String userIndustry;// 行业
    public String userHead;// 用户头像名称

    public String firstLetter = (StringUtil.isNotBlank(userName) ? HanYuPinYin
            .cn2py(userName) : StringUtil.isNotBlank(userName) ? HanYuPinYin
            .cn2py(userName) : "");

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthContext getAuthContext() {
        return authContext;
    }

    public void setAuthContext(AuthContext authContext) {
        this.authContext = authContext;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(String birthMonth) {
        this.birthMonth = birthMonth;
    }

    public String getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public String getUserIndustry() {
        return userIndustry;
    }

    public void setUserIndustry(String userIndustry) {
        this.userIndustry = userIndustry;
    }

    public String getFirstLetter() {
        if (StringUtil.isBlank(firstLetter)) {
            firstLetter = StringUtil.isNotBlank(userName) ? HanYuPinYin.cn2py(userName)
                    : StringUtil.isNotBlank(userName) ? HanYuPinYin.cn2py(userName)
                    : "";
        }
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", firstLetter='" + firstLetter + '\'' +
                '}';
    }

}