package com.xiwen.workload.service;

import com.xiwen.workload.domain.Lcgl;

import java.util.List;

/**
 * @BelongsProject: xiwen
 * @BelongsPackage: com.xiwen.workload.service
 * @Author: cuiqichao
 * @CreateTime: 2022-08-23  22:40
 * @Description: 流程管理service
 * @Version: 1.0
 */
public interface LcglService {

    /**
     * @description:查询流程列表 分页
     * @author: cuiqichao
     * @param: lcgl
     * @return: java.util.List<com.xiwen.workload.domain.Lcgl>
    **/
    public List<Lcgl> selectLcglList(Lcgl lcgl);
    public List<Lcgl> listBySb(Lcgl lcgl);

    public Lcgl insertLcgl(Lcgl lcgl);
    public Lcgl updateLcgl(Lcgl lcgl);
    public Lcgl updateLcglStatus(Lcgl lcgl);
    public Lcgl getLcxxById(String id);
    public boolean checkedLc(Lcgl lcgl);
    public void deleteLcgl(Lcgl lcgl);

}
