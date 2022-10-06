package com.xiwen.workload.domain;

import com.xiwen.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * @BelongsProject: xiwen
 * @BelongsPackage: com.xiwen.workload.domain
 * @Author: cuiqichao
 * @CreateTime: 2022-10-03  19:55
 * @Description: TODO
 * @Version: 1.0
 */
public class Tjgl extends BaseEntity {

    private String deptid;
    private String pbmid;
    private String bmmc;
    private String bmid;
    private BigDecimal zfs;
    private String nd;
    private String code;
    private String value;
    private String label;
    private String id;
    private String px;
    private String sjflx;
    private String fzlx;
    private String ysfz;
    private String khxmc;
    private String pid;
    private List<Tjgl> cList;

    public List<Tjgl> getcList() {
        return cList;
    }

    public void setcList(List<Tjgl> cList) {
        this.cList = cList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPx() {
        return px;
    }

    public void setPx(String px) {
        this.px = px;
    }

    public String getSjflx() {
        return sjflx;
    }

    public void setSjflx(String sjflx) {
        this.sjflx = sjflx;
    }

    public String getFzlx() {
        return fzlx;
    }

    public void setFzlx(String fzlx) {
        this.fzlx = fzlx;
    }

    public String getYsfz() {
        return ysfz;
    }

    public void setYsfz(String ysfz) {
        this.ysfz = ysfz;
    }

    public String getKhxmc() {
        return khxmc;
    }

    public void setKhxmc(String khxmc) {
        this.khxmc = khxmc;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public BigDecimal getZfs() {
        return zfs;
    }

    public void setZfs(BigDecimal zfs) {
        this.zfs = zfs;
    }

    public String getNd() {
        return nd;
    }

    public void setNd(String nd) {
        this.nd = nd;
    }

    public String getPbmid() {
        return pbmid;
    }

    public void setPbmid(String pbmid) {
        this.pbmid = pbmid;
    }

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    public String getBmid() {
        return bmid;
    }

    public void setBmid(String bmid) {
        this.bmid = bmid;
    }


    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }
}
