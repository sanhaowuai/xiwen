package com.xiwen.workload.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiwen.common.core.utils.uuid.IdUtils;
import com.xiwen.common.security.utils.SecurityUtils;
import com.xiwen.workload.domain.*;
import com.xiwen.workload.service.SbglService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.xiwen.workload.mapper.SbglMapper;
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
    @Value("${workload.jqf.sfsyjqf}")
    private Boolean sfsyjqf;//是否使用加权分

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
    public List<Sbgl> selectShSbglList(Sbgl sbgl)
    {
        sbgl.setYhid(SecurityUtils.getUserId()+"");
        if("dsh".equals(sbgl.getLblx())){
            return sbglMapper.selectSbshglList(sbgl);
        }else{
            return sbglMapper.selectSbyshglList(sbgl);
        }

    }
    @Override
    public List<Sbgl> selectSbglList(Sbgl sbgl)
    {
        return sbglMapper.selectSbglList(sbgl);
    }
    @Override
    public List<Sbgl> listSbglCysb(Sbgl sbgl)
    {
        return sbglMapper.listSbglCysb(sbgl);
    }
    @Override
    public Lcgl getShdqjd(String sqid)
    {
        return sbglMapper.getShdqjd(sqid);
    }
    @Override
    public List<Lcgl> getLcjdxx(String sqid)
    {
        return sbglMapper.getLcjdxx(sqid);
    }
    @Override
    public List<Shjlb> getShjlList(String sqid)
    {
        return sbglMapper.getShjlList(sqid);
    }
    @Override
    public List<Sbgl> selectSbshglList(Sbgl sbgl)
    {
        List<Sbgl> list = new ArrayList<>();
        if("dsh".equals(sbgl.getLblx())){
            list = sbglMapper.selectSbshglList(sbgl);
        }else{
            list = sbglMapper.selectSbyshglList(sbgl);
        }
        return list;
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
            Sbgl tempSbgl = sbglMapper.getSbglById(sbgl.getId());
            if("1".equals(sftj)){
                //2.找到第一个节点
                List<Lcgl> ywlcjdbList = sbglMapper.getLcjdbList(tempSbgl.getLcid());
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
                    shjlb.setShr("");
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
        Lcgl lcgl = sbglMapper.getLcxxBySqid(sbgl.getId());
        Sbgl shSbgl = sbglMapper.selectSbglById(sbgl.getId());
        Sbgl sqb = new Sbgl();
        sqb.setVerss(shSbgl.getVerss());
        sqb.setId(shSbgl.getId());
        Shjlb shjlb = sbglMapper.getZhshjd(shSbgl.getId());
        String sfzzjd = "0";
        //一、判断审核状态
        if("99".equals(shjlb.getXyjdid())){//99已完结  不需要审核
            resMap.put("code", "xw30020");
            resMap.put("msg", "流程已完结，不能重复审核！");
            return resMap;
        }else if("-1".equals(shjlb.getXyjdid())){//-1申请人重新修改提交 不需要审核
            resMap.put("code", "xw30021");
            resMap.put("msg", "流程已驳回到申请人，不能重复审核！");
            return resMap;
        }else{
            //2 查询流程节点信息
            List<Lcgl> lcjdbList = sbglMapper.getShLcjdbList(shSbgl.getId());
            //3.查询当前节点所在位置
            int jdIndex = 0;//当前节点位置
            for (int i=0;i<lcjdbList.size();i++){
                if(lcjdbList.get(i).getId().equals(shjlb.getXyjdid())){
                    jdIndex = i;
                }
            }
            //4.通过
            Shjlb addShjlb = new Shjlb();
            addShjlb.setId(IdUtils.getSnowflakeId());
            addShjlb.setSqid(shSbgl.getId());
            addShjlb.setLcid(shSbgl.getLcid());
            addShjlb.setShyj(sbgl.getShyj());
            addShjlb.setShnr(sbgl.getShnr());
            addShjlb.setShr(SecurityUtils.getUserId()+"");
            if("2".equals(sbgl.getShzt())){//通过
                //2.1查询下一节点是否是完结节点  字典表添加审核中数据
                Lcgl lcjdb = lcjdbList.get(lcjdbList.size() - 1);//获取最后一个节点信息
                String tempZt = "5";
                String shr = "";
                if(lcjdb.getId().equals(shjlb.getXyjdid())){//是最后一个节点
                    tempZt = "99";
                    addShjlb.setXyjdid("99");
                    addShjlb.setDqjdid(lcjdb.getId());
                    sfzzjd = "1";
                }else{
                    addShjlb.setXyjdid(lcjdbList.get(jdIndex+1).getId());
                    addShjlb.setDqjdid(shjlb.getXyjdid());
                    shr = sbglMapper.getLcShr(lcjdb.getShjs());//设置节点审核人
                }
                //2.2修改申请表
                sqb.setShzt(tempZt);
                sqb.setShr(shr);
                int rowCount = sbglMapper.updateShSbgl(sqb);
                //2.3 如果上一步更新成功，则插入审核记录表
                if(rowCount > 0){
                    addShjlb.setShr(SecurityUtils.getUserId()+"");
                    addShjlb.setShzt(sbgl.getShzt());
                    sbglMapper.insertShjlb(addShjlb);//插入审核记录表

                }
            }else if("3".equals(sbgl.getShzt())){// 不通过
                String shr = "";
                addShjlb.setXyjdid("99");
                addShjlb.setDqjdid(lcjdbList.get(jdIndex).getId());
                //2.2修改申请表
                sqb.setShzt(sbgl.getShzt());
                sqb.setShr(shr);

                int rowCount = sbglMapper.updateShSbgl(sqb);
                //2.3 如果上一步更新成功，则插入审核记录表
                if(rowCount > 0){
                    addShjlb.setShr(SecurityUtils.getUserId()+"");
                    addShjlb.setShzt(sbgl.getShzt());
                    sbglMapper.insertShjlb(addShjlb);//插入审核记录表
                }
            }else if("4".equals(sbgl.getShzt())){// 驳回
                //1.查询是否可驳回 （查询当前申报的流程信息）
                Lcgl lcglxx = sbglMapper.getLcxxBySqid(shSbgl.getId());
                if("1".equals(lcglxx.getSfkbh())){ //可驳回
                    String tempZt = sbgl.getShzt();
                    String shr = "";
                    String bhlx = lcgl.getBhlx();//1驳回到申请人2驳回到上一节点
                    if("1".equals(bhlx)){
                        shr = sbglMapper.getLcShr(lcjdbList.get(1).getShjs());//查询第二个审核节点的审核人
                        addShjlb.setXyjdid("-1");//驳回至申请人
                        addShjlb.setDqjdid(lcjdbList.get(0).getId());//申请节点
                    }else if("2".equals(bhlx)){
                        if(jdIndex == 1){//第二个节点审核驳回直接驳回到申请人
                            shr = sbglMapper.getLcShr(lcjdbList.get(1).getShjs());//查询第二个审核节点的审核人
                            addShjlb.setXyjdid("-1");//驳回至申请人
                            addShjlb.setDqjdid(lcjdbList.get(0).getId());//申请节点
                        }else{
                            String syjdid = lcjdbList.get(jdIndex-1).getId();//驳回到上一个节点的id
                            shr = sbglMapper.getLcShr(lcjdbList.get(jdIndex-1).getShjs());//根据syjdid查询上一个节点的审核人
                            addShjlb.setXyjdid(lcjdbList.get(jdIndex).getId());
                            addShjlb.setDqjdid(syjdid);
                        }
                    }
                    //2.2修改申请表
                    sqb.setShzt(tempZt);
                    sqb.setShr(shr);
                    int rowCount = sbglMapper.updateShSbgl(sqb);
                    //2.3 如果上一步更新成功，则插入审核记录表
                    if(rowCount > 0){
                        addShjlb.setShr(SecurityUtils.getUserId()+"");
                        addShjlb.setShzt(sbgl.getShzt());
                        sbglMapper.insertShjlb(addShjlb);//插入审核记录表
                    }
                }else{
                    resMap.put("code", "xw3002");
                    resMap.put("msg", "此流程不可驳回！");
                    return resMap;
                }
            }
        }
        List<SbCyry> cyryTList = sbgl.getCyryTList();
        //修改参与人员的审核得分
        for (int i = 0; i < cyryTList.size(); i++) {
            SbCyry tempSbcyry = cyryTList.get(i);
            tempSbcyry.setSqid(sbgl.getId());
            sbglMapper.updateSbCyry(tempSbcyry);
        }
        if("1".equals(sfzzjd)){//最终审核通过 修改人员分数信息
            updateCyryfs(sbgl);
        }
        resMap.put("code", "200");
        resMap.put("msg", "审核成功！");
        return resMap;
    }

    //审核通过执行
    public String updateCyryfs(Sbgl sbgl){
        //查询参与人分数 性别 年龄  0男1女 fzlx 1加分 2减分
        List<SbCyry> cyrybList = sbglMapper.getCyrybList(sbgl.getId());
        for(SbCyry cyryb:cyrybList){
            BigDecimal fs = new BigDecimal(cyryb.getShdf());
            String tempJqbs = "";
            if(sfsyjqf && "1".equals(cyryb.getFzlx())){//判断是否需要加权倍数 并且只有加分的时候需要
                if(cyryb.getJqbs() != null && !"".equals(cyryb.getJqbs().toString())){ //并且加权倍数不等于空
                    tempJqbs = cyryb.getJqbs();
                    fs = fs.multiply(new BigDecimal(cyryb.getJqbs()));
                }
            }
            //查询是否有人员分数记录
            Khfsb rykhfsb = sbglMapper.getKhfsbByYhid(cyryb.getYhid() + "");
            if(rykhfsb != null && !"".equals(rykhfsb.getId())){ //已存在
                rykhfsb.setUpdateuse(SecurityUtils.getUserId() + "");
            }else{
                //插入人员考核分
                rykhfsb = new Khfsb();
                rykhfsb.setId(IdUtils.getSnowflakeId());
                rykhfsb.setCreateuse(SecurityUtils.getUserId() + "");
                rykhfsb.setYhid(cyryb.getYhid());
                rykhfsb.setZfs(new BigDecimal("0"));
                //插入人员考核表
                sbglMapper.insertRyKhfsb(rykhfsb);
            }
            Khfsxqb khxfsxqb = new Khfsxqb();
            khxfsxqb.setId(IdUtils.getSnowflakeId());
            khxfsxqb.setCreateuse(SecurityUtils.getUserId() + "");
            khxfsxqb.setRykhfsbid(rykhfsb.getId());
            khxfsxqb.setFslx(cyryb.getFzlx());
            khxfsxqb.setFs(fs);
            khxfsxqb.setYwid(sbgl.getId());
            if(StringUtils.isNotBlank(tempJqbs)){
                khxfsxqb.setBz("加权倍数：" + tempJqbs);
            }else{
                khxfsxqb.setBz("");
            }
            //插入考核详情表
            sbglMapper.insertRyKhfsxqb(khxfsxqb);
            //修改人员考核表分数
            if("1".equals(cyryb.getFzlx())){//加分
                rykhfsb.setZfs(rykhfsb.getZfs().add(fs));
            }else if("2".equals(cyryb.getFzlx())){//减分
                rykhfsb.setZfs(rykhfsb.getZfs().subtract(fs));
            }
            cyryb.setShdf(String.valueOf(fs));
            sbglMapper.updateSbCyryShdf(cyryb);
            sbglMapper.updateRyKhfsb(rykhfsb);
        }
        return "";
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
