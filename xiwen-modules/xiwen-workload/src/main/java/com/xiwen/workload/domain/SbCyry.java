package com.xiwen.workload.domain;

/**
 * @BelongsProject: xiwen
 * @BelongsPackage: com.xiwen.workload.domain
 * @Author: cuiqichao
 * @CreateTime: 2022-09-12  19:53
 * @Description: 申报参与人员
 * @Version: 1.0
 */
public class SbCyry {

    private String sqid;
    private String id;
    private String yhid;
    private String ryxm;
    private String yhxm;
    private String xbmc;
    private String nl;
    private String df;
    private String gznr;
    private String bmmc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSqid() {
        return sqid;
    }

    public void setSqid(String sqid) {
        this.sqid = sqid;
    }

    public String getDf() {
        return df;
    }

    public void setDf(String df) {
        this.df = df;
    }

    public String getGznr() {
        return gznr;
    }

    public void setGznr(String gznr) {
        this.gznr = gznr;
    }

    public String getYhid() {
        return yhid;
    }

    public void setYhid(String yhid) {
        this.yhid = yhid;
    }

    public String getRyxm() {
        return ryxm;
    }

    public void setRyxm(String ryxm) {
        this.ryxm = ryxm;
    }

    public String getYhxm() {
        return yhxm;
    }

    public void setYhxm(String yhxm) {
        this.yhxm = yhxm;
    }

    public String getXbmc() {
        return xbmc;
    }

    public void setXbmc(String xbmc) {
        this.xbmc = xbmc;
    }

    public String getNl() {
        return nl;
    }

    public void setNl(String nl) {
        this.nl = nl;
    }

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }
}
