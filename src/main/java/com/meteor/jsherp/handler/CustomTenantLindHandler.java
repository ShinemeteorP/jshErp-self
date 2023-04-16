package com.meteor.jsherp.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import net.sf.jsqlparser.expression.Expression;

/**
 * @author 刘鑫
 * @version 1.0
 */
public class CustomTenantLindHandler implements TenantLineHandler {
    @Override
    public Expression getTenantId() {
        return null;
    }

    @Override
    public String getTenantIdColumn() {
        return TenantLineHandler.super.getTenantIdColumn();
    }

    @Override
    public boolean ignoreTable(String tableName) {
        return TenantLineHandler.super.ignoreTable(tableName);
    }
}
