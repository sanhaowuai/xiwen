package com.xiwen.workload.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xiwen.common.core.annotation.Excel;
import com.xiwen.common.core.web.domain.BaseEntity;

/**
 * 年度管理对象 wf_ndb
 *
 * @author xiwen
 * @date 2022-09-17
 */
public class Ndb extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 乐观锁 */
    private Long verss;

    /** 是否删除 */
    private String isdel;

    /** 年度 */
    @Excel(name = "年度")
    private String nd;


    private String sfdqnd;
    /** 是否当前年度（1是0否） */
    @Excel(name = "是否当前年度")
    private String sfdqndmc;

    public String getSfdqndmc() {
        return sfdqndmc;
    }

    public void setSfdqndmc(String sfdqndmc) {
        this.sfdqndmc = sfdqndmc;
    }

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
    public void setNd(String nd)
    {
        this.nd = nd;
    }

    public String getNd()
    {
        return nd;
    }
    public void setSfdqnd(String sfdqnd)
    {
        this.sfdqnd = sfdqnd;
    }

    public String getSfdqnd()
    {
        return sfdqnd;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("verss", getVerss())
                .append("isdel", getIsdel())
                .append("nd", getNd())
                .append("sfdqnd", getSfdqnd())
                .toString();
    }
}
