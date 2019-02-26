package com.example.root.graduation_app.bean;

import java.io.Serializable;

/**
 * author:Jiwenjie
 * email:278630464@qq.com
 * time:2019/02/24
 * desc:
 * version:1.0
 */
public class LoginUser implements Serializable {
   private String userid;
   private String userphone;
   private String username;
   private String password;
   private int continuesigndays;        // indicate(表示, 表明) how many days have singed
   private boolean signintoday;  // 表示今天是否签到
   private String signintime;     // 表示今天签到时间
   private int signintotaldays;    // 表示一共签到了几天
   private String logintime;     // 表示第一次登陆时间 any day's the first login time
   private String logouttime;     // 表示登出时间
   private Long totaltime;       // 使用 App 的总时间
   private String avatar;     // 用户头像在服务器存储的地址
   private boolean signout;    // 标记是否退出账号
   private String collectioncount; // 收藏了几篇文章
   private String profile;

   public String getUserid() {
      return userid;
   }

   public void setUserid(String userid) {
      this.userid = userid;
   }

   public String getUserphone() {
      return userphone;
   }

   public void setUserphone(String userphone) {
      this.userphone = userphone;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public int getContinuesigndays() {
      return continuesigndays;
   }

   public void setContinuesigndays(int continuesigndays) {
      this.continuesigndays = continuesigndays;
   }

   public boolean isSignintoday() {
      return signintoday;
   }

   public void setSignintoday(boolean signintoday) {
      this.signintoday = signintoday;
   }

   public String getSignintime() {
      return signintime;
   }

   public void setSignintime(String signintime) {
      this.signintime = signintime;
   }

   public int getSignintotaldays() {
      return signintotaldays;
   }

   public void setSignintotaldays(int signintotaldays) {
      this.signintotaldays = signintotaldays;
   }

   public String getLogintime() {
      return logintime;
   }

   public void setLogintime(String logintime) {
      this.logintime = logintime;
   }

   public String getLogouttime() {
      return logouttime;
   }

   public void setLogouttime(String logouttime) {
      this.logouttime = logouttime;
   }

   public Long getTotaltime() {
      return totaltime;
   }

   public void setTotaltime(Long totaltime) {
      this.totaltime = totaltime;
   }

   public String getAvatar() {
      return avatar;
   }

   public void setAvatar(String avatar) {
      this.avatar = avatar;
   }

   public boolean isSignout() {
      return signout;
   }

   public void setSignout(boolean signout) {
      this.signout = signout;
   }

   public String getCollectioncount() {
      return collectioncount;
   }

   public void setCollectioncount(String collectioncount) {
      this.collectioncount = collectioncount;
   }

   public String getProfile() {
      return profile;
   }

   public void setProfile(String profile) {
      this.profile = profile;
   }
}
