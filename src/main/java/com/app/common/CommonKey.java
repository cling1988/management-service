package com.app.common;

import java.util.ArrayList;
import java.util.List;

public class CommonKey {
    public static final String EXCEPTION_MESSAGE = "exception.message.key";

    //Permission
    public static final String PERMISSION_USER_VIEW = "USER.MANAGEMENT.VIEW";
    public static final String PERMISSION_USER_EDIT = "USER.MANAGEMENT.EDIT";
    public static final String PERMISSION_PRODUCT_VIEW = "PRODUCT.MANAGEMENT.VIEW";
    public static final String PERMISSION_PRODUCT_EDIT = "PRODUCT.MANAGEMENT.EDIT";
    public static final String PERMISSION_OUTLET_VIEW = "OUTLET.MANAGEMENT.VIEW";
    public static final String PERMISSION_OUTLET_EDIT = "OUTLET.MANAGEMENT.EDIT";
    public static final String PERMISSION_PRODUCTION_VIEW = "PRODUCTION.MANAGEMENT.VIEW";
    public static final String PERMISSION_PRODUCTION_EDIT = "PRODUCTION.MANAGEMENT.EDIT";
    public static final String PERMISSION_EVENT_VIEW = "EVENT.MANAGEMENT.VIEW";
    public static final String PERMISSION_EVENT_EDIT = "EVENT.MANAGEMENT.EDIT";
    public static final String PERMISSION_PLANNING_VIEW = "PLANNING.MANAGEMENT.VIEW";
    public static final String PERMISSION_PLANNING_EDIT = "PLANNING.MANAGEMENT.EDIT";
    public static final String PERMISSION_REPORT = "REPORT.MANAGEMENT.ALLOW";

    //Base URL
    public static final String URL_AUTHORIZATION="/api/service/authorization";
    public static final String URL_USER_VIEW ="/api/service/management/v/users";
    public static final String URL_USER_EDIT ="/api/service/management/e/users";
    public static final String URL_PRODUCT_VIEW ="/api/service/management/v/products";
    public static final String URL_PRODUCT_EDIT ="/api/service/management/e/products";
    public static final String URL_OUTLET_VIEW ="/api/service/management/v/outlets";
    public static final String URL_OUTLET_EDIT ="/api/service/management/e/outlets";
    public static final String URL_PRODUCTION_VIEW ="/api/service/management/v/production";
    public static final String URL_PRODUCTION_EDIT ="/api/service/management/e/production";
    public static final String URL_EVENT_VIEW ="/api/service/management/v/events";
    public static final String URL_EVENT_EDIT ="/api/service/management/e/events";
    public static final String URL_PLANNING_VIEW ="/api/service/management/v/planning";
    public static final String URL_PLANNING_EDIT ="/api/service/management/e/planning";
    public static final String URL_REPORT ="/api/service/management/report";



    public static List<String> getAllPermission(){
        List<String> permission = new ArrayList<>();
        permission.add(PERMISSION_USER_VIEW);
        permission.add(PERMISSION_USER_EDIT);
        permission.add(PERMISSION_PRODUCT_VIEW);
        permission.add(PERMISSION_PRODUCT_EDIT);
        permission.add(PERMISSION_OUTLET_VIEW);
        permission.add(PERMISSION_OUTLET_EDIT);
        permission.add(PERMISSION_PRODUCTION_VIEW);
        permission.add(PERMISSION_PRODUCTION_EDIT);
        permission.add(PERMISSION_EVENT_VIEW);
        permission.add(PERMISSION_EVENT_EDIT);
        permission.add(PERMISSION_PLANNING_VIEW);
        permission.add(PERMISSION_PLANNING_EDIT);
        permission.add(PERMISSION_REPORT);
        return permission;
    }

}
