package com.xiwen.workload.domain;

/**
 * @BelongsProject: xiwen
 * @BelongsPackage: com.xiwen.workload.domain
 * @Author: cuiqichao
 * @CreateTime: 2022-09-18  22:44
 * @Description: 系统参数表
 * @Version: 1.0
 */
public class WfConfig {
    private String id;
    private String name;
    private String key;
    private String value;
    private String type;
    private String bz;
    private String dqsj;
    private String sfky;

    public String getSfky() {
        return sfky;
    }

    public void setSfky(String sfky) {
        this.sfky = sfky;
    }

    public String getDqsj() {
        return dqsj;
    }

    public void setDqsj(String dqsj) {
        this.dqsj = dqsj;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }
}
