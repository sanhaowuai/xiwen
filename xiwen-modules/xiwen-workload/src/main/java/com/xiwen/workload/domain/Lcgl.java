package com.xiwen.workload.domain;

import com.xiwen.common.core.web.domain.BaseEntity;

import java.util.List;

/**
 * @BelongsProject: xiwen
 * @BelongsPackage: com.xiwen.workload.domain
 * @Author: cuiqichao
 * @CreateTime: 2022-08-23  22:38
 * @Description: 流程管理实体类
 * @Version: 1.0
 */
public class Lcgl extends BaseEntity {
    private String lcmc;
    private int verss;
    private String isdel;
    private String kyf;
    private int px;
    private String lcsm;
    private String sfkbh;
    private String bhlx;
    private String id;
    private String lcid;
    private String jdmc;
    private String shjs;
    private List<Lcgl> jdList;

    public String getLcmc() {
        return lcmc;
    }

    public void setLcmc(String lcmc) {
        this.lcmc = lcmc;
    }

    public int getVerss() {
        return verss;
    }

    public void setVerss(int verss) {
        this.verss = verss;
    }

    public String getIsdel() {
        return isdel;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

    public String getKyf() {
        return kyf;
    }

    public void setKyf(String kyf) {
        this.kyf = kyf;
    }

    public int getPx() {
        return px;
    }

    public void setPx(int px) {
        this.px = px;
    }

    public String getLcsm() {
        return lcsm;
    }

    public void setLcsm(String lcsm) {
        this.lcsm = lcsm;
    }

    public String getSfkbh() {
        return sfkbh;
    }

    public void setSfkbh(String sfkbh) {
        this.sfkbh = sfkbh;
    }

    public String getBhlx() {
        return bhlx;
    }

    public void setBhlx(String bhlx) {
        this.bhlx = bhlx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLcid() {
        return lcid;
    }

    public void setLcid(String lcid) {
        this.lcid = lcid;
    }

    public String getJdmc() {
        return jdmc;
    }

    public void setJdmc(String jdmc) {
        this.jdmc = jdmc;
    }

    public String getShjs() {
        return shjs;
    }

    public void setShjs(String shjs) {
        this.shjs = shjs;
    }

    public List<Lcgl> getJdList() {
        return jdList;
    }

    public void setJdList(List<Lcgl> jdList) {
        this.jdList = jdList;
    }
}
