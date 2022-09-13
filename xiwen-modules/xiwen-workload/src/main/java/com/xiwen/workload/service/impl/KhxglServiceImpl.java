package com.xiwen.workload.service.impl;

import java.util.List;
import java.util.Map;

import com.xiwen.workload.service.KhxglService;
import org.springframework.stereotype.Service;
import com.xiwen.workload.mapper.KhxglMapper;
import com.xiwen.workload.domain.Khxgl;

import javax.annotation.Resource;

/**
 * 考核项管理Service业务层处理
 *
 * @author xiwen
 * @date 2022-09-04
 */
@Service
public class KhxglServiceImpl implements KhxglService
{
    @Resource
    private KhxglMapper khxglMapper;

    /**
     * 查询考核项管理
     *
     * @param id 考核项管理主键
     * @return 考核项管理
     */
    @Override
    public Khxgl selectKhxglById(String id)
    {
        return khxglMapper.selectKhxglById(id);
    }

    /**
     * 查询考核项管理列表
     *
     * @param khxgl 考核项管理
     * @return 考核项管理
     */
    @Override
    public List<Khxgl> selectKhxglList(Khxgl khxgl)
    {
        return khxglMapper.selectKhxglList(khxgl);
    }
    @Override
    public List<Khxgl> getKhxlist(Khxgl khxgl)
    {
        return khxglMapper.getKhxlist(khxgl);
    }

    /**
     * 新增考核项管理
     *
     * @param khxgl 考核项管理
     * @return 结果
     */
    @Override
    public int insertKhxgl(Khxgl khxgl)
    {
        return khxglMapper.insertKhxgl(khxgl);
    }

    /**
     * 修改考核项管理
     *
     * @param khxgl 考核项管理
     * @return 结果
     */
    @Override
    public int updateKhxgl(Khxgl khxgl)
    {
        return khxglMapper.updateKhxgl(khxgl);
    }

    /**
     * 批量删除考核项管理
     *
     * @param paramMap 需要删除的考核项管理主键
     * @return 结果
     */
    @Override
    public int deleteKhxglByIds(Map paramMap)
    {
        return khxglMapper.deleteKhxglByIds(paramMap);
    }

    /**
     * 删除考核项管理信息
     *
     * @param paramMap 考核项管理主键
     * @return 结果
     */
    @Override
    public int deleteKhxglById(Map paramMap)
    {
        return khxglMapper.deleteKhxglById(paramMap);
    }
}
