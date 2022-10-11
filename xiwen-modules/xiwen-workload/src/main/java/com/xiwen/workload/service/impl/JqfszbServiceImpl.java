package com.xiwen.workload.service.impl;

import java.util.List;

import com.xiwen.common.core.utils.uuid.IdUtils;
import com.xiwen.common.security.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.xiwen.workload.mapper.JqfszbMapper;
import com.xiwen.workload.domain.Jqfszb;
import com.xiwen.workload.service.JqfszbService;

import javax.annotation.Resource;

/**
 * 加权分设置Service业务层处理
 *
 * @author xiwen
 * @date 2022-10-10
 */
@Service
public class JqfszbServiceImpl implements JqfszbService
{
    @Resource
    private JqfszbMapper jqfszbMapper;

    /**
     * 查询加权分设置
     *
     * @param id 加权分设置主键
     * @return 加权分设置
     */
    @Override
    public Jqfszb selectJqfszbById(String id)
    {
        return jqfszbMapper.selectJqfszbById(id);
    }

    /**
     * 查询加权分设置列表
     *
     * @param jqfszb 加权分设置
     * @return 加权分设置
     */
    @Override
    public List<Jqfszb> selectJqfszbList(Jqfszb jqfszb)
    {
        return jqfszbMapper.selectJqfszbList(jqfszb);
    }

    /**
     * 新增加权分设置
     *
     * @param jqfszb 加权分设置
     * @return 结果
     */
    @Override
    public int insertJqfszb(Jqfszb jqfszb)
    {
        jqfszb.setId(IdUtils.getSnowflakeId());
        jqfszb.setCreateuse(SecurityUtils.getUserId()+"");
        jqfszb.setUpdateuse(SecurityUtils.getUserId()+"");
        int isc = jqfszbMapper.queryByLxAndNl(jqfszb);
        if(isc > 0){
            return 0;
        }
        return jqfszbMapper.insertJqfszb(jqfszb);
    }

    /**
     * 修改加权分设置
     *
     * @param jqfszb 加权分设置
     * @return 结果
     */
    @Override
    public int updateJqfszb(Jqfszb jqfszb)
    {
        jqfszb.setUpdateuse(SecurityUtils.getUserId()+"");
        int isc = jqfszbMapper.queryByLxAndNl(jqfszb);
        if(isc > 0){
            return 0;
        }
        return jqfszbMapper.updateJqfszb(jqfszb);
    }

    /**
     * 批量删除加权分设置
     *
     * @param ids 需要删除的加权分设置主键
     * @return 结果
     */
    @Override
    public int deleteJqfszbByIds(String[] ids)
    {
        return jqfszbMapper.deleteJqfszbByIds(ids);
    }

    /**
     * 删除加权分设置信息
     *
     * @param id 加权分设置主键
     * @return 结果
     */
    @Override
    public int deleteJqfszbById(String id)
    {
        return jqfszbMapper.deleteJqfszbById(id);
    }
}
