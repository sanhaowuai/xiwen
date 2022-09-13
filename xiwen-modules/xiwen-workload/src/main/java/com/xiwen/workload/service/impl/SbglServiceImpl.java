package com.xiwen.workload.service.impl;

import java.util.List;

import com.xiwen.workload.domain.SbCyry;
import com.xiwen.workload.service.SbglService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiwen.workload.mapper.SbglMapper;
import com.xiwen.workload.domain.Sbgl;

import javax.annotation.Resource;

/**
 * 申报管理Service业务层处理
 *
 * @author xiwen
 * @date 2022-09-05
 */
@Service
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
    public List<SbCyry> queryCyryList(SbCyry sbCyry)
    {
        return sbglMapper.queryCyryList(sbCyry);
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
     * 修改申报管理
     *
     * @param sbgl 申报管理
     * @return 结果
     */
    @Override
    public int updateSbgl(Sbgl sbgl)
    {
        return sbglMapper.updateSbgl(sbgl);
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
        return sbglMapper.deleteSbglByIds(ids);
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
