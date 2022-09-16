package com.xiwen.workload.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xiwen.common.core.annotation.Excel;
import com.xiwen.common.core.web.domain.BaseEntity;

/**
 * 申报管理对象 wf_sqb_sjfb
 *
 * @author xiwen
 * @date 2022-09-05
 */
public class Sbgl extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 乐观锁 */
    private Long verss;

    /** 是否删除 */
    private String isdel;

    /** 创建人 */
    private String createuse;
    /** 创建人 */
    private List<SbCyry> cyryTList;

    /** 修改人 */
    private String updateuse;
    /** 流程名称 */
    private String lcmc;
    /** 考核项名称 */
    private String khxmc;
    /** 申请人姓名 */
    private String sqrxm;
    /** 审核状态名称 */
    private String shztmc;
    /** 申请类型名称 */
    private String sqlxmc;
    /** 申请时间 */
    private String sbsj;


    /** 流程id wf_ywlcb.id */
    @Excel(name = "流程id wf_ywlcb.id")
    private String lcid;

    /** 申请人 sys_user.user_id */
    @Excel(name = "申请人 sys_user.user_id")
    private String sqr;

    /** 申请类型 字典表three_method_status */
    @Excel(name = "申请类型 字典表three_method_status")
    private String sqlx;

    /** 考核项id wf_khxb.id */
    @Excel(name = "考核项id wf_khxb.id")
    private String ksxid;

    /** 工作开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "工作开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date gzkssj;

    /** 工作结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "工作结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date gzjssj;

    /** 工作时长 */
    @Excel(name = "工作时长")
    private Long gzsc;

    /** 工作简述 */
    @Excel(name = "工作简述")
    private String gzjs;

    /** 申请分值 */
    @Excel(name = "申请分值")
    private Long sqfz;

    /** 审核状态 字典表flow_status */
    @Excel(name = "审核状态 字典表flow_status")
    private String shzt;

    public String getSbsj() {
        return sbsj;
    }

    public void setSbsj(String sbsj) {
        this.sbsj = sbsj;
    }

    public String getLcmc() {
        return lcmc;
    }

    public String getKhxmc() {
        return khxmc;
    }

    public String getSqrxm() {
        return sqrxm;
    }

    public String getShztmc() {
        return shztmc;
    }

    public String getSqlxmc() {
        return sqlxmc;
    }
    public void setLcmc(String lcmc) {
        this.lcmc = lcmc;
    }

    public void setKhxmc(String khxmc) {
        this.khxmc = khxmc;
    }

    public void setSqrxm(String sqrxm) {
        this.sqrxm = sqrxm;
    }

    public void setShztmc(String shztmc) {
        this.shztmc = shztmc;
    }

    public void setSqlxmc(String sqlxmc) {
        this.sqlxmc = sqlxmc;
    }

    public List<SbCyry> getCyryTList() {
        return cyryTList;
    }

    public void setCyryTList(List<SbCyry> cyryTList) {
        this.cyryTList = cyryTList;
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
    public void setLcid(String lcid)
    {
        this.lcid = lcid;
    }

    public String getLcid()
    {
        return lcid;
    }
    public void setSqr(String sqr)
    {
        this.sqr = sqr;
    }

    public String getSqr()
    {
        return sqr;
    }
    public void setSqlx(String sqlx)
    {
        this.sqlx = sqlx;
    }

    public String getSqlx()
    {
        return sqlx;
    }
    public void setKsxid(String ksxid)
    {
        this.ksxid = ksxid;
    }

    public String getKsxid()
    {
        return ksxid;
    }
    public void setGzkssj(Date gzkssj)
    {
        this.gzkssj = gzkssj;
    }

    public Date getGzkssj()
    {
        return gzkssj;
    }
    public void setGzjssj(Date gzjssj)
    {
        this.gzjssj = gzjssj;
    }

    public Date getGzjssj()
    {
        return gzjssj;
    }
    public void setGzsc(Long gzsc)
    {
        this.gzsc = gzsc;
    }

    public Long getGzsc()
    {
        return gzsc;
    }
    public void setGzjs(String gzjs)
    {
        this.gzjs = gzjs;
    }

    public String getGzjs()
    {
        return gzjs;
    }
    public void setSqfz(Long sqfz)
    {
        this.sqfz = sqfz;
    }

    public Long getSqfz()
    {
        return sqfz;
    }
    public void setShzt(String shzt)
    {
        this.shzt = shzt;
    }

    public String getShzt()
    {
        return shzt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("verss", getVerss())
                .append("isdel", getIsdel())
                .append("createuse", getCreateuse())
                .append("updateuse", getUpdateuse())
                .append("lcid", getLcid())
                .append("sqr", getSqr())
                .append("sqlx", getSqlx())
                .append("ksxid", getKsxid())
                .append("gzkssj", getGzkssj())
                .append("gzjssj", getGzjssj())
                .append("gzsc", getGzsc())
                .append("gzjs", getGzjs())
                .append("sqfz", getSqfz())
                .append("shzt", getShzt())
                .toString();
    }
}
