package com.meteor.jsherp.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 平台参数
 * @TableName jsh_platform_config
 */
@TableName(value ="jsh_platform_config")
@Data
public class PlatformConfig implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 关键词
     */
    private String platformKey;

    /**
     * 关键词名称
     */
    private String platformKeyInfo;

    /**
     * 值
     */
    private String platformValue;

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
        PlatformConfig other = (PlatformConfig) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlatformKey() == null ? other.getPlatformKey() == null : this.getPlatformKey().equals(other.getPlatformKey()))
            && (this.getPlatformKeyInfo() == null ? other.getPlatformKeyInfo() == null : this.getPlatformKeyInfo().equals(other.getPlatformKeyInfo()))
            && (this.getPlatformValue() == null ? other.getPlatformValue() == null : this.getPlatformValue().equals(other.getPlatformValue()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPlatformKey() == null) ? 0 : getPlatformKey().hashCode());
        result = prime * result + ((getPlatformKeyInfo() == null) ? 0 : getPlatformKeyInfo().hashCode());
        result = prime * result + ((getPlatformValue() == null) ? 0 : getPlatformValue().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", platformKey=").append(platformKey);
        sb.append(", platformKeyInfo=").append(platformKeyInfo);
        sb.append(", platformValue=").append(platformValue);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}