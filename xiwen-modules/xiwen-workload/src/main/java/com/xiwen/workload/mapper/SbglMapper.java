package com.xiwen.workload.mapper;

import java.util.List;

import com.xiwen.workload.domain.*;

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
    public List<Sbgl> selectShSbglList(Sbgl sbgl);
    public List<Lcgl> getLcjdxx(String sqid);
    public List<Shjlb> getShjlList(String sqid);
    public Lcgl getShdqjd(String sqid);
    public List<Sbgl> selectSbshglList(Sbgl sbgl);
    public List<Sbgl> selectSbyshglList(Sbgl sbgl);
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
    public int insertSbCyry(SbCyry sbCyry);
    public int updateSbCyry(SbCyry sbCyry);
    public List<SbCyry> getCyrybList(String sqid);
    public Khfsb getKhfsbByYhid(String yhid);
    public Sbgl getSbglById(String sqid);
    public int insertRyKhfsb(Khfsb khfsb);
    public int insertRyKhfsxqb(Khfsxqb khfsxqb);
    public int updateRyKhfsb(Khfsb khfsb);

    /**
     * 修改申报管理
     *
     * @param sbgl 申报管理
     * @return 结果
     */
    public int updateSbgl(Sbgl sbgl);
    public void deleteSbcyry(Sbgl sbgl);

    /**
     * 删除申报管理
     *
     * @param id 申报管理主键
     * @return 结果
     */
    public int deleteSbglById(String id);
    public List<Lcgl> getLcjdbList(String lcid);
    public List<Lcgl> getShLcjdbList(String sqid);
    public Lcgl getSjshjd(String sqid);
    public Lcgl getLcxxBySqid(String sqid);
    public Shjlb getZhshjd(String sqid);
    public String getLcShr(String roleid);
    public int insertShjlb(Shjlb shjlb);
    public int updateSbglShlc(Sbgl sbgl);
    public int updateShSbgl(Sbgl sbgl);
    public int updateSbglShzt(Sbgl sbgl);

    /**
     * 批量删除申报管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSbglByIds(String[] ids);
}
