package com.xiwen.workload.service.impl;

import java.util.List;

import com.xiwen.common.core.utils.uuid.IdUtils;
import com.xiwen.workload.domain.SbCyry;
import com.xiwen.workload.service.SbglService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiwen.workload.mapper.SbglMapper;
import com.xiwen.workload.domain.Sbgl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 申报管理Service业务层处理
 *
 * @author xiwen
 * @date 2022-09-05
 */
@Service
@Transactional
public class SbglServiceImpl implements SbglService
{
    @Resource
    private SbglMapper sbglMapper;

    /**
     * 查询申报管理
     *
     * @param id 申报管理主键
     * @return 申报管理
     */
    @Override
    public Sbgl selectSbglById(String id)
    {
        return sbglMapper.selectSbglById(id);
    }

    /**
     * 查询申报管理列表
     *
     * @param sbgl 申报管理
     * @return 申报管理
     */
    @Override
    public List<Sbgl> selectSbglList(Sbgl sbgl)
    {
        return sbglMapper.selectSbglList(sbgl);
    }
    @Override
    public List<SbCyry> querySbcyryList(Sbgl sbgl)
    {
        return sbglMapper.querySbcyryList(sbgl);
    }
    @Override
    public List<SbCyry> queryCyryList(SbCyry sbCyry)
    {
        return sbglMapper.queryCyryList(sbCyry);
    }

    @Override
    public SbCyry getUserByDlr(SbCyry sbCyry)
    {
        return sbglMapper.getUserByDlr(sbCyry);
    }


    /**
     * 新增申报管理
     *
     * @param sbgl 申报管理
     * @return 结果
     */
    @Override
    public int insertSbgl(Sbgl sbgl)
    {
        return sbglMapper.insertSbgl(sbgl);
    }

    /**
     * 新增、修改申报管理
     *
     * @param sbgl 申报管理
     * @return 结果
     */
    @Override
    public int updateSbgl(Sbgl sbgl)
    {
        if(sbgl.getCyryTList() != null && sbgl.getCyryTList().size()>0){
            if(StringUtils.isNotBlank(sbgl.getId())){//id不为空  修改
                sbglMapper.updateSbgl(sbgl);
                sbglMapper.deleteSbcyry(sbgl);
            }else{ //新增
                sbgl.setId(IdUtils.getSnowflakeId());
                sbglMapper.insertSbgl(sbgl);
            }
            List<SbCyry> cyryTList = sbgl.getCyryTList();
            for(int i = 0; i < cyryTList.size(); i++){
                SbCyry tempSbcyry = cyryTList.get(i);
                tempSbcyry.setSqid(sbgl.getId());
                tempSbcyry.setId(IdUtils.getSnowflakeId());
                sbglMapper.insertSbCyry(tempSbcyry);
            }
            return 1;
        }else{
            return 0;
        }
    }

    /**
     * 批量删除申报管理
     *
     * @param ids 需要删除的申报管理主键
     * @return 结果
     */
    @Override
    public int deleteSbglByIds(String[] ids)
    {
        sbglMapper.deleteSbglByIds(ids);
        for(String id:ids){
            Sbgl sbgl = new Sbgl();
            sbgl.setId(id);
            sbglMapper.deleteSbcyry(sbgl);
        }
        return 1;
    }

    /**
     * 删除申报管理信息
     *
     * @param id 申报管理主键
     * @return 结果
     */
    @Override
    public int deleteSbglById(String id)
    {
        return sbglMapper.deleteSbglById(id);
    }
}
