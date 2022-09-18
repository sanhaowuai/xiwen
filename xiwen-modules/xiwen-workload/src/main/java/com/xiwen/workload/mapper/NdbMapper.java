package com.xiwen.workload.mapper;

import java.util.List;
import com.xiwen.workload.domain.Ndb;
import com.xiwen.workload.domain.WfConfig;

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

    public WfConfig getWfConfig(String key);

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
    public void updateNdbByNd(Ndb ndb);
    public int getNdCountByNd(Ndb ndb);
    public int getNdCountByNdNotId(Ndb ndb);

    /**
     * 修改年度管理
     *
     * @param ndb 年度管理
     * @return 结果
     */
    public int updateNdb(Ndb ndb);
    public int updateNdbSfdqnd(Ndb ndb);
    public int updateNdbSfdqndNotNd(Ndb ndb);

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
