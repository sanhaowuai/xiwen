package com.xiwen.workload.mapper;

import java.util.List;
import com.xiwen.workload.domain.Ndb;

/**
 * 年度管理Mapper接口
 *
 * @author xiwen
 * @date 2022-09-17
 */
public interface NdbMapper
{
    /**
     * 查询年度管理
     *
     * @param id 年度管理主键
     * @return 年度管理
     */
    public Ndb selectNdbById(String id);

    /**
     * 查询年度管理列表
     *
     * @param ndb 年度管理
     * @return 年度管理集合
     */
    public List<Ndb> selectNdbList(Ndb ndb);
    public List<Ndb> getNdList(Ndb ndb);

    /**
     * 新增年度管理
     *
     * @param ndb 年度管理
     * @return 结果
     */
    public int insertNdb(Ndb ndb);

    /**
     * 修改年度管理
     *
     * @param ndb 年度管理
     * @return 结果
     */
    public int updateNdb(Ndb ndb);

    /**
     * 删除年度管理
     *
     * @param id 年度管理主键
     * @return 结果
     */
    public int deleteNdbById(String id);

    /**
     * 批量删除年度管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNdbByIds(String[] ids);
}
