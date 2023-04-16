package com.meteor.jsherp.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 单据编号表
 * @TableName jsh_sequence
 */
@TableName(value ="jsh_sequence")
@Data
public class Sequence implements Serializable {
    /**
     * 序列名称
     */
    @TableId
    private String seqName;

    /**
     * 最小值
     */
    private Long minValue;

    /**
     * 最大值
     */
    private Long maxValue;

    /**
     * 当前值
     */
    private Long currentVal;

    /**
     * 增长步数
     */
    private Integer incrementVal;

    /**
     * 备注
     */
    private String remark;

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
        Sequence other = (Sequence) that;
        return (this.getSeqName() == null ? other.getSeqName() == null : this.getSeqName().equals(other.getSeqName()))
            && (this.getMinValue() == null ? other.getMinValue() == null : this.getMinValue().equals(other.getMinValue()))
            && (this.getMaxValue() == null ? other.getMaxValue() == null : this.getMaxValue().equals(other.getMaxValue()))
            && (this.getCurrentVal() == null ? other.getCurrentVal() == null : this.getCurrentVal().equals(other.getCurrentVal()))
            && (this.getIncrementVal() == null ? other.getIncrementVal() == null : this.getIncrementVal().equals(other.getIncrementVal()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSeqName() == null) ? 0 : getSeqName().hashCode());
        result = prime * result + ((getMinValue() == null) ? 0 : getMinValue().hashCode());
        result = prime * result + ((getMaxValue() == null) ? 0 : getMaxValue().hashCode());
        result = prime * result + ((getCurrentVal() == null) ? 0 : getCurrentVal().hashCode());
        result = prime * result + ((getIncrementVal() == null) ? 0 : getIncrementVal().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", seqName=").append(seqName);
        sb.append(", minValue=").append(minValue);
        sb.append(", maxValue=").append(maxValue);
        sb.append(", currentVal=").append(currentVal);
        sb.append(", incrementVal=").append(incrementVal);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}