package com.xiwen.workload.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xiwen.common.core.annotation.Excel;
import com.xiwen.common.core.web.domain.BaseEntity;

/**
 * 人员考核分数详情对象 wf_rykhfsxqb
 *
 * @author xiwen
 * @date 2022-09-16
 */
public class Khfsxqb extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;
    private String xqb;
    private String createtime;
    private String updatetime;
    private String fslxmc;
    private String createUserName;
    private String updateUserName;
    private String yhid;
    private String nd;
    private String updatetimeg;
    private String createtimeg;
    private BigDecimal zfs;
    private String yhxm;
    private String bmmc;
    private String maxfs;
    private String minfs;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getYhxm() {
        return yhxm;
    }

    public void setYhxm(String yhxm) {
        this.yhxm = yhxm;
    }

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    public String getMaxfs() {
        return maxfs;
    }

    public void setMaxfs(String maxfs) {
        this.maxfs = maxfs;
    }

    public String getMinfs() {
        return minfs;
    }

    public void setMinfs(String minfs) {
        this.minfs = minfs;
    }

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

    /** 考核分数表ID wf_rykhfsb.id */
    @Excel(name = "考核分数表ID wf_rykhfsb.id")
    private String rykhfsbid;

    /** 分数类型  1加2减3初始化 */
    @Excel(name = "分数类型  1加2减3初始化")
    private String fslx;

    /** 分数 */
    @Excel(name = "分数")
    private BigDecimal fs;

    /** 业务ID */
    @Excel(name = "业务ID")
    private String ywid;

    /** 备注 */
    @Excel(name = "备注")
    private String bz;

    public String getUpdatetimeg() {
        return updatetimeg;
    }

    public void setUpdatetimeg(String updatetimeg) {
        this.updatetimeg = updatetimeg;
    }

    public String getCreatetimeg() {
        return createtimeg;
    }

    public void setCreatetimeg(String createtimeg) {
        this.createtimeg = createtimeg;
    }

    public String getYhid() {
        return yhid;
    }

    public void setYhid(String yhid) {
        this.yhid = yhid;
    }

    public String getNd() {
        return nd;
    }

    public void setNd(String nd) {
        this.nd = nd;
    }

    public BigDecimal getZfs() {
        return zfs;
    }

    public void setZfs(BigDecimal zfs) {
        this.zfs = zfs;
    }

    public String getXqb() {
        return xqb;
    }

    public void setXqb(String xqb) {
        this.xqb = xqb;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getFslxmc() {
        return fslxmc;
    }

    public void setFslxmc(String fslxmc) {
        this.fslxmc = fslxmc;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
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
    public void setRykhfsbid(String rykhfsbid)
    {
        this.rykhfsbid = rykhfsbid;
    }

    public String getRykhfsbid()
    {
        return rykhfsbid;
    }
    public void setFslx(String fslx)
    {
        this.fslx = fslx;
    }

    public String getFslx()
    {
        return fslx;
    }
    public void setFs(BigDecimal fs)
    {
        this.fs = fs;
    }

    public BigDecimal getFs()
    {
        return fs;
    }
    public void setYwid(String ywid)
    {
        this.ywid = ywid;
    }

    public String getYwid()
    {
        return ywid;
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
                .append("rykhfsbid", getRykhfsbid())
                .append("fslx", getFslx())
                .append("fs", getFs())
                .append("ywid", getYwid())
                .append("bz", getBz())
                .toString();
    }
}
