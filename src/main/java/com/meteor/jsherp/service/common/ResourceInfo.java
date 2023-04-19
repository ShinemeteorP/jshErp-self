package com.meteor.jsherp.service.common;

import java.lang.annotation.*;

/**
 * @author 刘鑫
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ResourceInfo {
    String value();
}
