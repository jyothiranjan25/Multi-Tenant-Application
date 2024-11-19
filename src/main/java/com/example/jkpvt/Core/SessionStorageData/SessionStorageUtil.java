package com.example.jkpvt.Core.SessionStorageData;

import com.example.jkpvt.UserManagement.AppUser.AppUserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SessionStorageUtil {

    private static final String APP_USER_SESSION_KEY = "appUser";
    private static final String USER_NAME_SESSION_KEY = "userName";
    private static final String USER_EMAIL_SESSION_KEY = "userEmail";
    private static final String USER_GROUP_SESSION_KEY = "userGroup";
    private static final String ROLE_SESSION_KEY = "roleId";

    public static AppUserDTO getUserLoginDetails() {
        HttpSession session = getCurrentSession();
        return (AppUserDTO) session.getAttribute(APP_USER_SESSION_KEY);
    }

    public static void setUserLoginDetails(AppUserDTO appUser) {
        HttpSession session = getCurrentSession();
        session.setAttribute(APP_USER_SESSION_KEY, appUser);
    }

    public static String getUserName() {
        HttpSession session = getCurrentSession();
        return (String) session.getAttribute(USER_NAME_SESSION_KEY);
    }

    public static void setUserName(String userName) {
        HttpSession session = getCurrentSession();
        session.setAttribute(USER_NAME_SESSION_KEY, userName);
    }

    public static String geUserEmail() {
        HttpSession session = getCurrentSession();
        return (String) session.getAttribute(USER_EMAIL_SESSION_KEY);
    }

    public static void setUserEmail(String userEmail) {
        HttpSession session = getCurrentSession();
        session.setAttribute(USER_EMAIL_SESSION_KEY, userEmail);
    }

    public static String getUserGroup() {
        HttpSession session = getCurrentSession();
        return (String) session.getAttribute(USER_GROUP_SESSION_KEY);
    }

    public static void setUserGroup(String userGroup) {
        HttpSession session = getCurrentSession();
        session.setAttribute(USER_GROUP_SESSION_KEY, userGroup);
    }

    public static Long getRoleId() {
        HttpSession session = getCurrentSession();
        return (Long) session.getAttribute(ROLE_SESSION_KEY);
    }

    public static void setRoleId(Long roleId) {
        HttpSession session = getCurrentSession();
        session.setAttribute(ROLE_SESSION_KEY, roleId);
    }

    // Helper method to get the current session
    private static HttpSession getCurrentSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession();
    }
}