package com.example.jkpvt.Core.SessionStorageData;

import com.example.jkpvt.UserManagement.AppUser.AppUserDTO;
import com.example.jkpvt.UserManagement.AppUserRoles.AppUserRolesDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SessionStorageUtil {

    private static final String APP_USER_SESSION_KEY = "appUser";
    private static final String APP_USER_ROLE_SESSION_KEY = "appUserRole";
    private static final String USER_NAME_SESSION_KEY = "userName";
    private static final String USER_EMAIL_SESSION_KEY = "userEmail";
    private static final String USER_GROUP_SESSION_KEY = "userGroup";

    public static AppUserDTO getAppUser() {
        HttpSession session = getCurrentSession();
        return (AppUserDTO) session.getAttribute(APP_USER_SESSION_KEY);
    }

    public static void setAppUser(AppUserDTO data) {
        HttpSession session = getCurrentSession();
        session.setAttribute(APP_USER_SESSION_KEY, data);
    }

    public static AppUserRolesDTO getAppUserRole() {
        HttpSession session = getCurrentSession();
        return (AppUserRolesDTO) session.getAttribute(APP_USER_ROLE_SESSION_KEY);
    }

    public static void setAppUserRole(AppUserRolesDTO data) {
        HttpSession session = getCurrentSession();
        session.setAttribute(APP_USER_ROLE_SESSION_KEY, data);
    }

    public static String getUserName() {
        HttpSession session = getCurrentSession();
        return (String) session.getAttribute(USER_NAME_SESSION_KEY);
    }

    public static void setUserName(String data) {
        HttpSession session = getCurrentSession();
        session.setAttribute(USER_NAME_SESSION_KEY, data);
    }

    public static String geUserEmail() {
        HttpSession session = getCurrentSession();
        return (String) session.getAttribute(USER_EMAIL_SESSION_KEY);
    }

    public static void setUserEmail(String data) {
        HttpSession session = getCurrentSession();
        session.setAttribute(USER_EMAIL_SESSION_KEY, data);
    }

    public static String getUserGroup() {
        HttpSession session = getCurrentSession();
        return (String) session.getAttribute(USER_GROUP_SESSION_KEY);
    }

    public static void setUserGroup(String data) {
        HttpSession session = getCurrentSession();
        session.setAttribute(USER_GROUP_SESSION_KEY, data);
    }

    // Helper method to get the current session
    private static HttpSession getCurrentSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession();
    }
}