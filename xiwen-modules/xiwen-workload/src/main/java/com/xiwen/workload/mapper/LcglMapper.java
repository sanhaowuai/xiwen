package com.xiwen.workload.mapper;

import com.xiwen.workload.domain.Lcgl;

import java.util.List;

/**
 * @BelongsProject: xiwen
 * @BelongsPackage: com.xiwen.workload.mapper
 * @Author: cuiqichao
 * @CreateTime: 2022-08-23  22:47
 * @Description: 流程管理mapper
 * @Version: 1.0
 */
public interface LcglMapper {

    public List<Lcgl> selectLcglList(Lcgl lcgl);
    public void insertLcgl(Lcgl lcgl);
    public void insertLcjd(Lcgl lcgl);
    public void updateLcgl(Lcgl lcgl);
    public void deleteLcjd(Lcgl lcgl);
    public void deleteLcglById(Lcgl lcgl);
    public void deleteLcjdByLcid(Lcgl lcgl);
    public void updateLcglStatus(Lcgl lcgl);
    public Lcgl getLcxxById(String id);
    public List<Lcgl> getLcjdByLcid(String id);

}
