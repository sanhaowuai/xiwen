package com.xiwen.workload.service;

import java.util.List;
import com.xiwen.workload.domain.Jqfszb;

/**
 * 加权分设置Service接口
 *
 * @author xiwen
 * @date 2022-10-10
 */
public interface JqfszbService
{
    /**
     * 查询加权分设置
     *
     * @param id 加权分设置主键
     * @return 加权分设置
     */
    public Jqfszb selectJqfszbById(String id);

    /**
     * 查询加权分设置列表
     *
     * @param jqfszb 加权分设置
     * @return 加权分设置集合
     */
    public List<Jqfszb> selectJqfszbList(Jqfszb jqfszb);

    /**
     * 新增加权分设置
     *
     * @param jqfszb 加权分设置
     * @return 结果
     */
    public int insertJqfszb(Jqfszb jqfszb);

    /**
     * 修改加权分设置
     *
     * @param jqfszb 加权分设置
     * @return 结果
     */
    public int updateJqfszb(Jqfszb jqfszb);

    /**
     * 批量删除加权分设置
     *
     * @param ids 需要删除的加权分设置主键集合
     * @return 结果
     */
    public int deleteJqfszbByIds(String[] ids);

    /**
     * 删除加权分设置信息
     *
     * @param id 加权分设置主键
     * @return 结果
     */
    public int deleteJqfszbById(String id);
}
