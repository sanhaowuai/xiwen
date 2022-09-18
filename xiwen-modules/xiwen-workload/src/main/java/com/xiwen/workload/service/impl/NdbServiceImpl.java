package com.xiwen.workload.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.xiwen.common.core.utils.uuid.IdUtils;
import com.xiwen.workload.domain.WfConfig;
import com.xiwen.workload.service.NdbService;
import org.apache.commons.lang3.StringUtils;
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
     * @description:查询系统参数表
     * @author: cuiqichao
     * @date: 2022-09-18 22:40:38
     * @param: key 参数类型
     * @return: com.xiwen.workload.domain.Ndb
     **/
    @Override
    public WfConfig getWfConfig(String key){
        WfConfig wfConfig = ndbMapper.getWfConfig(key);
        if("WF_SB_TSXZ".equals(key)){
            try {
                if(StringUtils.isNotBlank(wfConfig.getValue()) || !"0".equals(wfConfig.getValue())){
                    Calendar dqsj=Calendar.getInstance(); //当前时间
                    Calendar bjsj= getBeforeLastMonthdate(Integer.parseInt(wfConfig.getValue())); //标记时间
                    if(dqsj.before(bjsj)){ //当前时间在标记时间之前
                        wfConfig.setSfky("1");
                    }else{
                        wfConfig.setSfky("0");
                        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Calendar calendar = getBeforeLastMonthdate(0);
                        String format = sf.format(calendar.getTime());
                        wfConfig.setDqsj(format);
                    }
                }else{ //为空不限制
                    wfConfig.setSfky("1");
                }
            } catch (Exception e) {
                wfConfig.setSfky("0");
                SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar calendar = getBeforeLastMonthdate(0);
                String format = sf.format(calendar.getTime());
                wfConfig.setDqsj(format);
            }
        }
        return wfConfig;
    }

    public static void main(String[] args) throws Exception {
        NdbServiceImpl ndbService = new NdbServiceImpl();
        System.out.println(ndbService.getBeforeLastMonthdate(0));
    }

    /**
     * 获取上个月的最后一天23点59分59秒的时间
     */
    public  Calendar getBeforeLastMonthdate(int xzts){
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar=Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + xzts);
        //将小时至23
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        calendar.set(Calendar.MINUTE, 59);
        //将秒至59
        calendar.set(Calendar.SECOND,59);
        String format = sf.format(calendar.getTime());
        System.out.println(format);
        return calendar;
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
        ndb.setId(IdUtils.getSnowflakeId());
        Integer count = ndbMapper.getNdCountByNd(ndb);
        if(count > 0){ //已经有要添加的年度了
            ndbMapper.updateNdbByNd(ndb);
        }else{
            ndbMapper.insertNdb(ndb);
        }
        if("1".equals(ndb.getSfdqnd())){
            ndbMapper.updateNdbSfdqnd(ndb);
        }
        return 1;
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
        Integer count = ndbMapper.getNdCountByNdNotId(ndb);
        if(count > 0) { //已经有要添加的年度了
            ndbMapper.updateNdbByNd(ndb);
            if("1".equals(ndb.getSfdqnd())){
                ndbMapper.updateNdbSfdqndNotNd(ndb);
            }
        }else{
            ndbMapper.updateNdb(ndb);
            if("1".equals(ndb.getSfdqnd())){
                ndbMapper.updateNdbSfdqnd(ndb);
            }
        }
        return 1;
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
