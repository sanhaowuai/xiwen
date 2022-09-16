package com.xiwen.workload.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiwen.workload.mapper.KhfsxqbMapper;
import com.xiwen.workload.domain.Khfsxqb;
import com.xiwen.workload.service.KhfsxqbService;

import javax.annotation.Resource;

/**
 * 人员考核分数详情Service业务层处理
 *
 * @author xiwen
 * @date 2022-09-16
 */
@Service
public class KhfsxqbServiceImpl implements KhfsxqbService
{
    @Resource
    private KhfsxqbMapper khfsxqbMapper;

    /**
     * 查询人员考核分数详情
     *
     * @param id 人员考核分数详情主键
     * @return 人员考核分数详情
     */
    @Override
    public Khfsxqb selectKhfsxqbById(String id)
    {
        return khfsxqbMapper.selectKhfsxqbById(id);
    }

    /**
     * 查询人员考核分数详情列表
     *
     * @param khfsxqb 人员考核分数详情
     * @return 人员考核分数详情
     */
    @Override
    public List<Khfsxqb> selectKhfsxqbList(Khfsxqb khfsxqb)
    {
        return khfsxqbMapper.selectKhfsxqbList(khfsxqb);
    }

    /**
     * 新增人员考核分数详情
     *
     * @param khfsxqb 人员考核分数详情
     * @return 结果
     */
    @Override
    public int insertKhfsxqb(Khfsxqb khfsxqb)
    {
        return khfsxqbMapper.insertKhfsxqb(khfsxqb);
    }

    /**
     * 修改人员考核分数详情
     *
     * @param khfsxqb 人员考核分数详情
     * @return 结果
     */
    @Override
    public int updateKhfsxqb(Khfsxqb khfsxqb)
    {
        return khfsxqbMapper.updateKhfsxqb(khfsxqb);
    }

    /**
     * 批量删除人员考核分数详情
     *
     * @param ids 需要删除的人员考核分数详情主键
     * @return 结果
     */
    @Override
    public int deleteKhfsxqbByIds(String[] ids)
    {
        return khfsxqbMapper.deleteKhfsxqbByIds(ids);
    }

    /**
     * 删除人员考核分数详情信息
     *
     * @param id 人员考核分数详情主键
     * @return 结果
     */
    @Override
    public int deleteKhfsxqbById(String id)
    {
        return khfsxqbMapper.deleteKhfsxqbById(id);
    }
}
