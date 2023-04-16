package com.meteor.jsherp.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.meteor.jsherp.constant.ErpAllConstant;
import com.meteor.jsherp.utils.CommonUtil;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @author 刘鑫
 * @version 1.0
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 新多租户插件配置,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存万一出现问题
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        List<String> ignoreList = Arrays.asList("jsh_material_property", "jsh_sequence", "jsh_function"
                , "jsh_user_business", "jsh_platform_config", "jsh_tenant");
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attributes == null){
                    return null;
                }
                HttpServletRequest request = attributes.getRequest();
                String token = request.getHeader(ErpAllConstant.REQUEST_TOKEN_KEY);
                Long tenantId = CommonUtil.getTenantIdByToken(token);
                return tenantId == 0L ? null : new LongValue(tenantId);
            }

            @Override
            public String getTenantIdColumn() {
                return "tenant_id";

            }

            // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
            @Override
            public boolean ignoreTable(String tableName) {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attributes == null){
                    return true;
                }
                HttpServletRequest request = attributes.getRequest();
                String token = request.getHeader(ErpAllConstant.REQUEST_TOKEN_KEY);
                Long tenantId = CommonUtil.getTenantIdByToken(token);
                if (tenantId != 0L){
                    return ignoreList.contains(tableName);
                }
                return true;
            }
        }));
        // 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
        // 用了分页插件必须设置 MybatisConfiguration#useDeprecatedExecutor = false
        // interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}
