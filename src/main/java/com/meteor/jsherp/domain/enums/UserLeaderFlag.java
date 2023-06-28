package com.meteor.jsherp.domain.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author 刘鑫
 * @version 1.0
 */
public enum UserLeaderFlag implements IEnum<String> {
    IS("1", "是"),
    NOT("0", "否");

    private String value;
    private String desc;

    UserLeaderFlag(String value, String desc) {
        this.desc = desc;
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
