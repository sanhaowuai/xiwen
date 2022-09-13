package com.xiwen.workload.service;

import java.util.List;
import java.util.Map;

import com.xiwen.workload.domain.Khxgl;

/**
 * 考核项管理Service接口
 *
 * @author xiwen
 * @date 2022-09-04
 */
public interface KhxglService
{
    /**
     * 查询考核项管理
     *
     * @param id 考核项管理主键
     * @return 考核项管理
     */
    public Khxgl selectKhxglById(String id);

    /**
     * 查询考核项管理列表
     *
     * @param khxgl 考核项管理
     * @return 考核项管理集合
     */
    public List<Khxgl> selectKhxglList(Khxgl khxgl);
    public List<Khxgl> getKhxlist(Khxgl khxgl);

    /**
     * 新增考核项管理
     *
     * @param khxgl 考核项管理
     * @return 结果
     */
    public int insertKhxgl(Khxgl khxgl);

    /**
     * 修改考核项管理
     *
     * @param khxgl 考核项管理
     * @return 结果
     */
    public int updateKhxgl(Khxgl khxgl);

    /**
     * 批量删除考核项管理
     *
     * @param paramMap 需要删除的考核项管理主键集合
     * @return 结果
     */
    public int deleteKhxglByIds(Map paramMap);

    /**
     * 删除考核项管理信息
     *
     * @param paramMap 考核项管理主键
     * @return 结果
     */
    public int deleteKhxglById(Map paramMap);
}
