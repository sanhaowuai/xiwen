package com.xiwen.workload.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.xiwen.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.xiwen.workload.domain.Khfsxqb;
import com.xiwen.workload.mapper.KhfsbMapper;
import com.xiwen.workload.domain.Khfsb;
import com.xiwen.workload.service.KhfsbService;

import javax.annotation.Resource;

/**
 * 人员考核分数Service业务层处理
 *
 * @author xiwen
 * @date 2022-09-16
 */
@Service
public class KhfsbServiceImpl implements KhfsbService
{
    @Resource
    private KhfsbMapper khfsbMapper;

    /**
     * 查询人员考核分数
     *
     * @param id 人员考核分数主键
     * @return 人员考核分数
     */
    @Override
    public Khfsb selectKhfsbById(String id)
    {
        return khfsbMapper.selectKhfsbById(id);
    }

    /**
     * 查询人员考核分数列表
     *
     * @param khfsb 人员考核分数
     * @return 人员考核分数
     */
    @Override
    public List<Khfsb> selectKhfsbList(Khfsb khfsb)
    {
        return khfsbMapper.selectKhfsbList(khfsb);
    }

    /**
     * 新增人员考核分数
     *
     * @param khfsb 人员考核分数
     * @return 结果
     */
    @Transactional
    @Override
    public int insertKhfsb(Khfsb khfsb)
    {
        int rows = khfsbMapper.insertKhfsb(khfsb);
        insertKhfsxqb(khfsb);
        return rows;
    }

    /**
     * 修改人员考核分数
     *
     * @param khfsb 人员考核分数
     * @return 结果
     */
    @Transactional
    @Override
    public int updateKhfsb(Khfsb khfsb)
    {
        khfsbMapper.deleteKhfsxqbByRykhfsbid(khfsb.getId());
        insertKhfsxqb(khfsb);
        return khfsbMapper.updateKhfsb(khfsb);
    }

    /**
     * 批量删除人员考核分数
     *
     * @param ids 需要删除的人员考核分数主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteKhfsbByIds(String[] ids)
    {
        khfsbMapper.deleteKhfsxqbByRykhfsbids(ids);
        return khfsbMapper.deleteKhfsbByIds(ids);
    }

    /**
     * 删除人员考核分数信息
     *
     * @param id 人员考核分数主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteKhfsbById(String id)
    {
        khfsbMapper.deleteKhfsxqbByRykhfsbid(id);
        return khfsbMapper.deleteKhfsbById(id);
    }

    /**
     * 新增人员考核分数详情信息
     *
     * @param khfsb 人员考核分数对象
     */
    public void insertKhfsxqb(Khfsb khfsb)
    {
        List<Khfsxqb> khfsxqbList = khfsb.getKhfsxqbList();
        String id = khfsb.getId();
        if (StringUtils.isNotNull(khfsxqbList))
        {
            List<Khfsxqb> list = new ArrayList<Khfsxqb>();
            for (Khfsxqb khfsxqb : khfsxqbList)
            {
                khfsxqb.setRykhfsbid(id);
                list.add(khfsxqb);
            }
            if (list.size() > 0)
            {
                khfsbMapper.batchKhfsxqb(list);
            }
        }
    }
}
