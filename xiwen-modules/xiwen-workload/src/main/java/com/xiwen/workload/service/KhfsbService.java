package com.xiwen.workload.service;

import java.util.List;
import com.xiwen.workload.domain.Khfsb;

/**
 * 人员考核分数Service接口
 *
 * @author xiwen
 * @date 2022-09-16
 */
public interface KhfsbService
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
     * 批量删除人员考核分数
     *
     * @param ids 需要删除的人员考核分数主键集合
     * @return 结果
     */
    public int deleteKhfsbByIds(String[] ids);

    /**
     * 删除人员考核分数信息
     *
     * @param id 人员考核分数主键
     * @return 结果
     */
    public int deleteKhfsbById(String id);
}
