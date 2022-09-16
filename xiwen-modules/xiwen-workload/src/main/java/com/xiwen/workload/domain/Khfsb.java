package com.xiwen.workload.domain;

import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xiwen.common.core.annotation.Excel;
import com.xiwen.common.core.web.domain.BaseEntity;

/**
 * 人员考核分数对象 wf_rykhfsb
 *
 * @author xiwen
 * @date 2022-09-16
 */
public class Khfsb extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 乐观锁 */
    @Excel(name = "乐观锁")
    private Long verss;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private String isdel;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createuse;

    /** 修改人 */
    @Excel(name = "修改人")
    private String updateuse;

    /** 用户ID */
    @Excel(name = "用户ID")
    private String yhid;

    /** 年度 */
    @Excel(name = "年度")
    private String nd;

    /** 总分数 */
    @Excel(name = "总分数")
    private BigDecimal zfs;

    /** 人员考核分数详情信息 */
    private List<Khfsxqb> khfsxqbList;

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
    public void setYhid(String yhid)
    {
        this.yhid = yhid;
    }

    public String getYhid()
    {
        return yhid;
    }
    public void setNd(String nd)
    {
        this.nd = nd;
    }

    public String getNd()
    {
        return nd;
    }
    public void setZfs(BigDecimal zfs)
    {
        this.zfs = zfs;
    }

    public BigDecimal getZfs()
    {
        return zfs;
    }

    public List<Khfsxqb> getKhfsxqbList()
    {
        return khfsxqbList;
    }

    public void setKhfsxqbList(List<Khfsxqb> khfsxqbList)
    {
        this.khfsxqbList = khfsxqbList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("verss", getVerss())
                .append("isdel", getIsdel())
                .append("createuse", getCreateuse())
                .append("updateuse", getUpdateuse())
                .append("yhid", getYhid())
                .append("nd", getNd())
                .append("zfs", getZfs())
                .append("khfsxqbList", getKhfsxqbList())
                .toString();
    }
}
