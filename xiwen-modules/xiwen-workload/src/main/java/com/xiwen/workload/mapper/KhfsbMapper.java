package com.xiwen.workload.mapper;

import java.util.List;
import com.xiwen.workload.domain.Khfsb;
import com.xiwen.workload.domain.Khfsxqb;

/**
 * 人员考核分数Mapper接口
 *
 * @author xiwen
 * @date 2022-09-16
 */
public interface KhfsbMapper
{
    /**
     * 查询人员考核分数
     *
     * @param id 人员考核分数主键
     * @return 人员考核分数
     */
    public Khfsb selectKhfsbById(String id);

    /**
     * 查询人员考核分数列表
     *
     * @param khfsb 人员考核分数
     * @return 人员考核分数集合
     */
    public List<Khfsb> selectKhfsbList(Khfsb khfsb);

    /**
     * 新增人员考核分数
     *
     * @param khfsb 人员考核分数
     * @return 结果
     */
    public int insertKhfsb(Khfsb khfsb);

    /**
     * 修改人员考核分数
     *
     * @param khfsb 人员考核分数
     * @return 结果
     */
    public int updateKhfsb(Khfsb khfsb);

    /**
     * 删除人员考核分数
     *
     * @param id 人员考核分数主键
     * @return 结果
     */
    public int deleteKhfsbById(String id);

    /**
     * 批量删除人员考核分数
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteKhfsbByIds(String[] ids);

    /**
     * 批量删除人员考核分数详情
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteKhfsxqbByRykhfsbids(String[] ids);

    /**
     * 批量新增人员考核分数详情
     *
     * @param khfsxqbList 人员考核分数详情列表
     * @return 结果
     */
    public int batchKhfsxqb(List<Khfsxqb> khfsxqbList);


    /**
     * 通过人员考核分数主键删除人员考核分数详情信息
     *
     * @param id 人员考核分数ID
     * @return 结果
     */
    public int deleteKhfsxqbByRykhfsbid(String id);
}
