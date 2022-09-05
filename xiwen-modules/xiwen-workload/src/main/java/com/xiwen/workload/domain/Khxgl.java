package com.xiwen.workload.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xiwen.common.core.annotation.Excel;
import com.xiwen.common.core.web.domain.TreeEntity;

/**
 * 考核项管理对象 wf_khxb
 *
 * @author xiwen
 * @date 2022-09-04
 */
public class Khxgl extends TreeEntity
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
    @Excel(name = "修改人")
    private String updateuse;

    /** 可用否 */
    @Excel(name = "可用否")
    private String kyf;

    /** 排序 */
    private Integer px;

    /** 三计法类型 字典类型three_method_status */
    @Excel(name = "三计法类型 字典类型three_method_status")
    private String sjflx;

    /** 分值类型（加分或者减分） 字典表score_type */
    @Excel(name = "分值类型", readConverterExp = "加=分或者减分")
    private String fzlx;

    /** 预设分值 */
    @Excel(name = "预设分值")
    private Long ysfz;

    /** 考核项名称 */
    @Excel(name = "考核项名称")
    private String khxmc;

    /** 流程id */
    private String lcid;

    /** 父考核项id */
    @Excel(name = "父考核项id")
    private String pid;

    /** 考核细则说明 */
    @Excel(name = "考核细则说明")
    private String ksxz;

    /** 备注 */
    @Excel(name = "备注")
    private String bz;

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
    public void setKyf(String kyf)
    {
        this.kyf = kyf;
    }

    public String getKyf()
    {
        return kyf;
    }
    public void setPx(Integer px)
    {
        this.px = px;
    }

    public Integer getPx()
    {
        return px;
    }
    public void setSjflx(String sjflx)
    {
        this.sjflx = sjflx;
    }

    public String getSjflx()
    {
        return sjflx;
    }
    public void setFzlx(String fzlx)
    {
        this.fzlx = fzlx;
    }

    public String getFzlx()
    {
        return fzlx;
    }
    public void setYsfz(Long ysfz)
    {
        this.ysfz = ysfz;
    }

    public Long getYsfz()
    {
        return ysfz;
    }
    public void setKhxmc(String khxmc)
    {
        this.khxmc = khxmc;
    }

    public String getKhxmc()
    {
        return khxmc;
    }
    public void setLcid(String lcid)
    {
        this.lcid = lcid;
    }

    public String getLcid()
    {
        return lcid;
    }
    public void setPid(String pid)
    {
        this.pid = pid;
    }

    public String getPid()
    {
        return pid;
    }
    public void setKsxz(String ksxz)
    {
        this.ksxz = ksxz;
    }

    public String getKsxz()
    {
        return ksxz;
    }
    public void setBz(String bz)
    {
        this.bz = bz;
    }

    public String getBz()
    {
        return bz;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("verss", getVerss())
                .append("isdel", getIsdel())
                .append("createuse", getCreateuse())
                .append("updateuse", getUpdateuse())
                .append("kyf", getKyf())
                .append("px", getPx())
                .append("sjflx", getSjflx())
                .append("fzlx", getFzlx())
                .append("ysfz", getYsfz())
                .append("khxmc", getKhxmc())
                .append("lcid", getLcid())
                .append("pid", getPid())
                .append("ksxz", getKsxz())
                .append("bz", getBz())
                .toString();
    }
}
