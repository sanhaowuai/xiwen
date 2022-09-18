package com.xiwen.workload.service.impl;

import java.util.List;

import com.xiwen.workload.service.NdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiwen.workload.mapper.NdbMapper;
import com.xiwen.workload.domain.Ndb;

import javax.annotation.Resource;

/**
 * 年度管理Service业务层处理
 *
 * @author xiwen
 * @date 2022-09-17
 */
@Service
public class NdbServiceImpl implements NdbService
{
    @Resource
    private NdbMapper ndbMapper;

    /**
     * 查询年度管理
     *
     * @param id 年度管理主键
     * @return 年度管理
     */
    @Override
    public Ndb selectNdbById(String id)
    {
        return ndbMapper.selectNdbById(id);
    }

    /**
     * 查询年度管理列表
     *
     * @param ndb 年度管理
     * @return 年度管理
     */
    @Override
    public List<Ndb> selectNdbList(Ndb ndb)
    {
        return ndbMapper.selectNdbList(ndb);
    }
    @Override
    public List<Ndb> getNdList(Ndb ndb)
    {
        return ndbMapper.getNdList(ndb);
    }

    /**
     * 新增年度管理
     *
     * @param ndb 年度管理
     * @return 结果
     */
    @Override
    public int insertNdb(Ndb ndb)
    {
        return ndbMapper.insertNdb(ndb);
    }

    /**
     * 修改年度管理
     *
     * @param ndb 年度管理
     * @return 结果
     */
    @Override
    public int updateNdb(Ndb ndb)
    {
        return ndbMapper.updateNdb(ndb);
    }

    /**
     * 批量删除年度管理
     *
     * @param ids 需要删除的年度管理主键
     * @return 结果
     */
    @Override
    public int deleteNdbByIds(String[] ids)
    {
        return ndbMapper.deleteNdbByIds(ids);
    }

    /**
     * 删除年度管理信息
     *
     * @param id 年度管理主键
     * @return 结果
     */
    @Override
    public int deleteNdbById(String id)
    {
        return ndbMapper.deleteNdbById(id);
    }
}
