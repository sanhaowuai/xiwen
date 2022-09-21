package com.xiwen.workload.domain;

import com.xiwen.common.core.utils.uuid.IdUtils;

/**
 * @BelongsProject: xiwen
 * @BelongsPackage: com.xiwen.workload.domain
 * @Author: cuiqichao
 * @CreateTime: 2022-09-19  21:42
 * @Description: 审核记录表
 * @Version: 1.0
 */
public class Shjlb {
    private String id;
    private String lcid;
    private String sqid;
    private String shzt;
    private String shyj;
    private String shnr;
    private String xyjdid;

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

    public String getSqid() {
        return sqid;
    }

    public void setSqid(String sqid) {
        this.sqid = sqid;
    }

    public String getShzt() {
        return shzt;
    }

    public void setShzt(String shzt) {
        this.shzt = shzt;
    }

    public String getShyj() {
        return shyj;
    }

    public void setShyj(String shyj) {
        this.shyj = shyj;
    }

    public String getShnr() {
        return shnr;
    }

    public void setShnr(String shnr) {
        this.shnr = shnr;
    }

    public String getXyjdid() {
        return xyjdid;
    }

    public void setXyjdid(String xyjdid) {
        this.xyjdid = xyjdid;
    }
}
