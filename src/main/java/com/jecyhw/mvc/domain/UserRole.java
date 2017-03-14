package com.jecyhw.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by jecyhw on 16-11-9.
 */
@AllArgsConstructor
public enum  UserRole {
    SUPER_ADMIN(0, "SUPER_ADMIN", "超级管理人员(市级管理人员)"),
    ADMIN(1, "ADMIN", "管理人员(县级管理人员)"),
    MONITOR(2, "MONITOR", "一级跟踪监测员"),
    OBSERVER(3, "OBSERVER", "二级观察员"),
    GENERAL(4, "GENERAL", "一般用户"),
    NONE(-1, "NONE", "没有权限");


    @Getter
    private Integer id;
    @Getter
    private String name;
    @Getter
    private String desc;

    static public UserRole roleById(Integer id) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.getId().equals(id)) {
                return userRole;
            }
        }
        return NONE;
    }
}
