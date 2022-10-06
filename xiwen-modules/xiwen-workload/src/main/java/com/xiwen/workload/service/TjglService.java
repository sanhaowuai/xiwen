package com.xiwen.workload.service;

import com.xiwen.system.api.domain.SysDept;
import com.xiwen.workload.domain.Tjgl;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: xiwen
 * @BelongsPackage: com.xiwen.workload.service
 * @Author: cuiqichao
 * @CreateTime: 2022-10-03  19:55
 * @Description: TODO
 * @Version: 1.0
 */
public interface TjglService {

    public Tjgl getMrbmxx();
    public Map<String,Object> getGrzfList(String nd,String yhid);
    public Map<String,Object> getRyxxList();
    public Map<String,Object> getBmzfList(String bmid, String nd);
    public Map<String,Object> getTableHeader(String nd);



}
