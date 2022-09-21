package com.xiwen.workload.service.impl;

import com.xiwen.common.core.utils.uuid.IdUtils;
import com.xiwen.common.datascope.annotation.DataScope;
import com.xiwen.workload.domain.Lcgl;
import com.xiwen.workload.mapper.LcglMapper;
import com.xiwen.workload.service.LcglService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @BelongsProject: xiwen
 * @BelongsPackage: com.xiwen.workload.service.impl
 * @Author: cuiqichao
 * @CreateTime: 2022-08-23  22:41
 * @Description: 流程管理
 * @Version: 1.0
 */
@Service
public class LcglServiceImpl implements LcglService {

    @Resource
    private LcglMapper lcglMapper;

    /**
     * @description:描述
     * @author: cuiqichao 
     * @param: lcgl
     * @return: java.util.List<com.xiwen.workload.domain.Lcgl>
    **/
    @Override
    public List<Lcgl> selectLcglList(Lcgl lcgl) {
        return lcglMapper.selectLcglList(lcgl);
    }
    @Override
    public List<Lcgl> listBySb(Lcgl lcgl) {
        return lcglMapper.listBySb(lcgl);
    }

    public Lcgl insertLcgl(Lcgl lcgl){
        lcgl.setId(IdUtils.getSnowflakeId());
        lcglMapper.insertLcgl(lcgl);
        List<Lcgl> jdList = lcgl.getJdList();
        int i = 1;
        for(Lcgl t:jdList){
            t.setId(IdUtils.getSnowflakeId());
            t.setLcid(lcgl.getId());
            t.setPx(i++);
            lcglMapper.insertLcjd(t);
        }
        return lcgl;
    }
    public Lcgl updateLcgl(Lcgl lcgl){
        lcglMapper.updateLcgl(lcgl);
        lcglMapper.deleteLcjd(lcgl);
        List<Lcgl> jdList = lcgl.getJdList();
        int i = 1;
        for(Lcgl t:jdList){
            t.setId(IdUtils.getSnowflakeId());
            t.setLcid(lcgl.getId());
            t.setPx(i++);
            lcglMapper.insertLcjd(t);
        }
        return lcgl;
    }
    public Lcgl updateLcglStatus(Lcgl lcgl){
        lcglMapper.updateLcglStatus(lcgl);
        return lcgl;
    }
    public Lcgl getLcxxById(String id){
        Lcgl lcgl = lcglMapper.getLcxxById(id);
        lcgl.setJdList(lcglMapper.getLcjdByLcid(id));
        return lcgl;
    }
    public void deleteLcgl(Lcgl lcgl){
        lcglMapper.deleteLcglById(lcgl);
        lcglMapper.deleteLcjdByLcid(lcgl);
    }
    public boolean checkedLc(Lcgl lcgl){
        boolean boo = false;
        //检查代码未完成
        return boo;
    }
}
