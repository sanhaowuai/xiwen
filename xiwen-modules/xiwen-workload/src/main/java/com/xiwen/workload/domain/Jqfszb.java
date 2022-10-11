package com.xiwen.workload.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xiwen.common.core.annotation.Excel;
import com.xiwen.common.core.web.domain.BaseEntity;

/**
 * 加权分设置对象 wf_jqfszb
 *
 * @author xiwen
 * @date 2022-10-10
 */
public class Jqfszb extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 乐观锁 */
    private Long verss;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private String isdel;

    /** 创建人 */
    private String createuse;

    /** 修改人 */
    private String updateuse;

    /** 类型1男2女 */
    @Excel(name = "类型1男2女")
    private String lx;

    /** 年龄 */
    @Excel(name = "年龄")
    private String nl;

    /** 加权倍数 */
    @Excel(name = "加权倍数")
    private BigDecimal jqbs;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setVerss(Long verss)
    {
        this.verss = verss;
    }

    public Long getVerss()
    {
        return verss;
    }
    public void setIsdel(String isdel)
    {
        this.isdel = isdel;
    }

    public String getIsdel()
    {
        return isdel;
    }
    public void setCreateuse(String createuse)
    {
        this.createuse = createuse;
    }

    public String getCreateuse()
    {
        return createuse;
    }
    public void setUpdateuse(String updateuse)
    {
        this.updateuse = updateuse;
    }

    public String getUpdateuse()
    {
        return updateuse;
    }
    public void setLx(String lx)
    {
        this.lx = lx;
    }

    public String getLx()
    {
        return lx;
    }
    public void setNl(String nl)
    {
        this.nl = nl;
    }

    public String getNl()
    {
        return nl;
    }
    public void setJqbs(BigDecimal jqbs)
    {
        this.jqbs = jqbs;
    }

    public BigDecimal getJqbs()
    {
        return jqbs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("verss", getVerss())
                .append("isdel", getIsdel())
                .append("createuse", getCreateuse())
                .append("updateuse", getUpdateuse())
                .append("lx", getLx())
                .append("nl", getNl())
                .append("jqbs", getJqbs())
                .toString();
    }
}
