package com.meteor.jsherp.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户表
 * @TableName jsh_user
 */
@TableName(value ="jsh_user")
@Data
public class User implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户姓名--例如张三
     */
    private String username;

    /**
     * 登录用户名
     */
    private String loginName;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 是否经理，0否，1是
     */
    private String leaderFlag;

    /**
     * 职位
     */
    private String position;

    /**
     * 所属部门
     */
    private String department;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phonenum;

    /**
     * 是否为管理者 0==管理者 1==员工
     */
    private Integer ismanager;

    /**
     * 是否系统自带数据 
     */
    private Integer isystem;

    /**
     * 状态，0：正常，1：删除，2封禁
     */
    private Integer status;

    /**
     * 用户描述信息
     */
    private String description;

    /**
     * 备注
     */
    private String remark;

    /**
     * 租户id
     */
    private Long tenantId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getLoginName() == null ? other.getLoginName() == null : this.getLoginName().equals(other.getLoginName()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getLeaderFlag() == null ? other.getLeaderFlag() == null : this.getLeaderFlag().equals(other.getLeaderFlag()))
            && (this.getPosition() == null ? other.getPosition() == null : this.getPosition().equals(other.getPosition()))
            && (this.getDepartment() == null ? other.getDepartment() == null : this.getDepartment().equals(other.getDepartment()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getPhonenum() == null ? other.getPhonenum() == null : this.getPhonenum().equals(other.getPhonenum()))
            && (this.getIsmanager() == null ? other.getIsmanager() == null : this.getIsmanager().equals(other.getIsmanager()))
            && (this.getIsystem() == null ? other.getIsystem() == null : this.getIsystem().equals(other.getIsystem()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getLoginName() == null) ? 0 : getLoginName().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getLeaderFlag() == null) ? 0 : getLeaderFlag().hashCode());
        result = prime * result + ((getPosition() == null) ? 0 : getPosition().hashCode());
        result = prime * result + ((getDepartment() == null) ? 0 : getDepartment().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getPhonenum() == null) ? 0 : getPhonenum().hashCode());
        result = prime * result + ((getIsmanager() == null) ? 0 : getIsmanager().hashCode());
        result = prime * result + ((getIsystem() == null) ? 0 : getIsystem().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", loginName=").append(loginName);
        sb.append(", password=").append(password);
        sb.append(", leaderFlag=").append(leaderFlag);
        sb.append(", position=").append(position);
        sb.append(", department=").append(department);
        sb.append(", email=").append(email);
        sb.append(", phonenum=").append(phonenum);
        sb.append(", ismanager=").append(ismanager);
        sb.append(", isystem=").append(isystem);
        sb.append(", status=").append(status);
        sb.append(", description=").append(description);
        sb.append(", remark=").append(remark);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}