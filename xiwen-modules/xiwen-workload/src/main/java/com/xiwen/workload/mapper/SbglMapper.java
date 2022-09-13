package com.xiwen.workload.mapper;

import java.util.List;

import com.xiwen.workload.domain.SbCyry;
import com.xiwen.workload.domain.Sbgl;

/**
 * 申报管理Mapper接口
 *
 * @author xiwen
 * @date 2022-09-05
 */
public interface SbglMapper
{
    /**
     * 查询申报管理
     *
     * @param id 申报管理主键
     * @return 申报管理
     */
    public Sbgl selectSbglById(String id);

    /**
     * 查询申报管理列表
     *
     * @param sbgl 申报管理
     * @return 申报管理集合
     */
    public List<Sbgl> selectSbglList(Sbgl sbgl);
    public List<SbCyry> queryCyryList(SbCyry sbCyry);

    /**
     * 新增申报管理
     *
     * @param sbgl 申报管理
     * @return 结果
     */
    public int insertSbgl(Sbgl sbgl);

    /**
     * 修改申报管理
     *
     * @param sbgl 申报管理
     * @return 结果
     */
    public int updateSbgl(Sbgl sbgl);

    /**
     * 删除申报管理
     *
     * @param id 申报管理主键
     * @return 结果
     */
    public int deleteSbglById(String id);

    /**
     * 批量删除申报管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSbglByIds(String[] ids);
}