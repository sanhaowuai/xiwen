package com.xiwen.workload.mapper;

import java.util.List;
import com.xiwen.workload.domain.Khfsxqb;

/**
 * 人员考核分数详情Mapper接口
 *
 * @author xiwen
 * @date 2022-09-16
 */
public interface KhfsxqbMapper
{
    /**
     * 查询人员考核分数详情
     *
     * @param id 人员考核分数详情主键
     * @return 人员考核分数详情
     */
    public Khfsxqb selectKhfsxqbById(String id);

    /**
     * 查询人员考核分数详情列表
     *
     * @param khfsxqb 人员考核分数详情
     * @return 人员考核分数详情集合
     */
    public List<Khfsxqb> selectKhfsxqbList(Khfsxqb khfsxqb);

    /**
     * 新增人员考核分数详情
     *
     * @param khfsxqb 人员考核分数详情
     * @return 结果
     */
    public int insertKhfsxqb(Khfsxqb khfsxqb);

    /**
     * 修改人员考核分数详情
     *
     * @param khfsxqb 人员考核分数详情
     * @return 结果
     */
    public int updateKhfsxqb(Khfsxqb khfsxqb);

    /**
     * 删除人员考核分数详情
     *
     * @param id 人员考核分数详情主键
     * @return 结果
     */
    public int deleteKhfsxqbById(String id);

    /**
     * 批量删除人员考核分数详情
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteKhfsxqbByIds(String[] ids);
}
