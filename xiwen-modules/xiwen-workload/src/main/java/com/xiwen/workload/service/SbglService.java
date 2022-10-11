package com.xiwen.workload.service;

import java.util.List;
import java.util.Map;

import com.xiwen.workload.domain.Lcgl;
import com.xiwen.workload.domain.SbCyry;
import com.xiwen.workload.domain.Sbgl;
import com.xiwen.workload.domain.Shjlb;

/**
 * 申报管理Service接口
 *
 * @author xiwen
 * @date 2022-09-05
 */
public interface SbglService
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
    public List<Sbgl> listSbglCysb(Sbgl sbgl);
    public List<Sbgl> selectShSbglList(Sbgl sbgl);
    public List<Lcgl> getLcjdxx(String sqid);
    public List<Shjlb> getShjlList(String sqid);
    public Lcgl getShdqjd(String sqid);
    public List<Sbgl> selectSbshglList(Sbgl sbgl);
    public List<SbCyry> queryCyryList(SbCyry sbCyry);
    public List<SbCyry> querySbcyryList(Sbgl sbgl);
    public SbCyry getUserByDlr(SbCyry sbCyry);

    /**
     * 新增申报管理
     *
     * @param sbgl 申报管理
     * @return 结果
     */
    public int insertSbgl(Sbgl sbgl);

    /**
     * 新增、修改申报管理
     *
     * @param sbgl 申报管理
     * @return 结果
     */
    public Map<String,String> updateSbgl(Sbgl sbgl);
    public Map<String,String> updateSbsh(Sbgl sbgl);

    /**
     * 批量删除申报管理
     *
     * @param ids 需要删除的申报管理主键集合
     * @return 结果
     */
    public int deleteSbglByIds(String[] ids);

    /**
     * 删除申报管理信息
     *
     * @param id 申报管理主键
     * @return 结果
     */
    public int deleteSbglById(String id);
}
