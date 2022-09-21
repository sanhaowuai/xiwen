package com.xiwen.workload.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiwen.common.core.utils.uuid.IdUtils;
import com.xiwen.workload.domain.Lcgl;
import com.xiwen.workload.domain.SbCyry;
import com.xiwen.workload.domain.Shjlb;
import com.xiwen.workload.service.SbglService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiwen.workload.mapper.SbglMapper;
import com.xiwen.workload.domain.Sbgl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 申报管理Service业务层处理
 *
 * @author xiwen
 * @date 2022-09-05
 */
@Service
@Transactional
public class SbglServiceImpl implements SbglService
{
    @Resource
    private SbglMapper sbglMapper;

    /**
     * 查询申报管理
     *
     * @param id 申报管理主键
     * @return 申报管理
     */
    @Override
    public Sbgl selectSbglById(String id)
    {
        return sbglMapper.selectSbglById(id);
    }

    /**
     * 查询申报管理列表
     *
     * @param sbgl 申报管理
     * @return 申报管理
     */
    @Override
    public List<Sbgl> selectSbglList(Sbgl sbgl)
    {
        return sbglMapper.selectSbglList(sbgl);
    }
    @Override
    public List<Sbgl> selectSbshglList(Sbgl sbgl)
    {
        return sbglMapper.selectSbshglList(sbgl);
    }
    @Override
    public List<SbCyry> querySbcyryList(Sbgl sbgl)
    {
        return sbglMapper.querySbcyryList(sbgl);
    }
    @Override
    public List<SbCyry> queryCyryList(SbCyry sbCyry)
    {
        return sbglMapper.queryCyryList(sbCyry);
    }

    @Override
    public SbCyry getUserByDlr(SbCyry sbCyry)
    {
        return sbglMapper.getUserByDlr(sbCyry);
    }


    /**
     * 新增申报管理
     *
     * @param sbgl 申报管理
     * @return 结果
     */
    @Override
    public int insertSbgl(Sbgl sbgl)
    {
        return sbglMapper.insertSbgl(sbgl);
    }

    /**
     * 新增、修改申报管理
     *
     * @param sbgl 申报管理
     * @return 结果
     */
    @Override
    public Map<String,String> updateSbgl(Sbgl sbgl)
    {
        Map<String,String> resMap = new HashMap<>();
        if(sbgl.getCyryTList() != null && sbgl.getCyryTList().size()>0){
            if(StringUtils.isNotBlank(sbgl.getId())){//id不为空  修改
                sbglMapper.updateSbgl(sbgl);
                sbglMapper.deleteSbcyry(sbgl);
            }else{ //新增
                sbgl.setId(IdUtils.getSnowflakeId());
                sbglMapper.insertSbgl(sbgl);
            }
            List<SbCyry> cyryTList = sbgl.getCyryTList();
            for(int i = 0; i < cyryTList.size(); i++){
                SbCyry tempSbcyry = cyryTList.get(i);
                tempSbcyry.setSqid(sbgl.getId());
                tempSbcyry.setId(IdUtils.getSnowflakeId());
                sbglMapper.insertSbCyry(tempSbcyry);
            }
            String sftj = sbgl.getShzt();
            if("1".equals(sftj)){
                //2.找到第一个节点
                List<Lcgl> ywlcjdbList = sbglMapper.getLcjdbList(sbgl.getLcid());
                if(ywlcjdbList != null && ywlcjdbList.size() > 1){ //必须有两个或者两个以上节点，第一个节点时申报节点
                    Lcgl lcgl = ywlcjdbList.get(1);//找到第一个审核节点
                    //3.查询节点角色审核人
                    String shr = sbglMapper.getLcShr(lcgl.getShjs());
                    //4.插入审核记录表 shzt=1 shyj=提交 shnr=提交 xyjdid=ywlcjdb.getId()
                    Shjlb shjlb = new Shjlb();
                    shjlb.setId(IdUtils.getSnowflakeId());
                    shjlb.setLcid(lcgl.getLcid());
                    shjlb.setSqid(sbgl.getId());
                    shjlb.setShzt("1");
                    shjlb.setShyj("提交");
                    shjlb.setShnr("提交");
                    shjlb.setXyjdid(lcgl.getId());
                    sbglMapper.insertShjlb(shjlb);
                    //5.修改申请表的审核人字段
                    sbgl.setShr(shr);
                    sbglMapper.updateSbglShlc(sbgl);
                    resMap.put("msg","操作成功！");
                }else{
                    sbgl.setShzt("0");
                    sbglMapper.updateSbglShzt(sbgl);
                    resMap.put("msg","流程错误，提交失败，请联系管理员！");
                }
            }else{
                resMap.put("msg","操作成功！");
            }
        }else{
            resMap.put("msg","参与人员不能为空,操作失败！");
        }
        return resMap;
    }
    /**
     * @description:审核
     * @author: cuiqichao
     * @param: sbgl
     * @return: java.util.Map<java.lang.String,java.lang.String>
    **/
    @Override
    public Map<String,String> updateSbsh(Sbgl sbgl)
    {
        Map<String,String> resMap = new HashMap<>();
        if(sbgl.getCyryTList() != null && sbgl.getCyryTList().size()>0) {
            List<SbCyry> cyryTList = sbgl.getCyryTList();
            //修改参与人员的审核得分
            for (int i = 0; i < cyryTList.size(); i++) {
                SbCyry tempSbcyry = cyryTList.get(i);
                tempSbcyry.setSqid(sbgl.getId());
                sbglMapper.updateSbCyry(tempSbcyry);
            }

            String sftj = sbgl.getShzt(); //2通过 3不通过 4 驳回 99 完成
            if("2".equals(sftj)){ //通过

            }else if("3".equals(sftj)){ //不通过

            }else if("4".equals(sftj)){ //驳回1
                //2.找到第一个节点
                List<Lcgl> ywlcjdbList = sbglMapper.getLcjdbList(sbgl.getLcid());
                if(ywlcjdbList != null && ywlcjdbList.size() > 1){ //必须有两个或者两个以上节点，第一个节点时申报节点
                    Lcgl lcgl = ywlcjdbList.get(1);//找到第一个审核节点
                    //3.查询节点角色审核人
                    String shr = sbglMapper.getLcShr(lcgl.getShjs());
                    //4.插入审核记录表 shzt=1 shyj=提交 shnr=提交 xyjdid=ywlcjdb.getId()
                    Shjlb shjlb = new Shjlb();
                    shjlb.setId(IdUtils.getSnowflakeId());
                    shjlb.setLcid(lcgl.getLcid());
                    shjlb.setSqid(sbgl.getId());
                    shjlb.setShzt("1");
                    shjlb.setShyj("提交");
                    shjlb.setShnr("提交");
                    shjlb.setXyjdid(lcgl.getId());
                    sbglMapper.insertShjlb(shjlb);
                    //5.修改申请表的审核人字段
                    sbgl.setShr(shr);
                    sbglMapper.updateSbglShlc(sbgl);
                    resMap.put("msg","操作成功！");
                }else{
                    sbgl.setShzt("0");
                    sbglMapper.updateSbglShzt(sbgl);
                    resMap.put("msg","流程错误，提交失败，请联系管理员！");
                }
            }else{
                resMap.put("msg","操作成功！");
            }
        }else{
            resMap.put("msg","审核操作失败，参与人员数据异常！");
        }
        return resMap;
    }

    /**
     * 批量删除申报管理
     *
     * @param ids 需要删除的申报管理主键
     * @return 结果
     */
    @Override
    public int deleteSbglByIds(String[] ids)
    {
        sbglMapper.deleteSbglByIds(ids);
        for(String id:ids){
            Sbgl sbgl = new Sbgl();
            sbgl.setId(id);
            sbglMapper.deleteSbcyry(sbgl);
        }
        return 1;
    }

    /**
     * 删除申报管理信息
     *
     * @param id 申报管理主键
     * @return 结果
     */
    @Override
    public int deleteSbglById(String id)
    {
        return sbglMapper.deleteSbglById(id);
    }
}
