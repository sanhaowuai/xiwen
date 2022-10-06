package com.xiwen.workload.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiwen.common.core.utils.uuid.IdUtils;
import com.xiwen.common.security.utils.SecurityUtils;
import com.xiwen.system.api.domain.SysUser;
import com.xiwen.workload.domain.Khfsb;
import com.xiwen.workload.mapper.KhfsbMapper;
import com.xiwen.workload.mapper.SbglMapper;
import org.apache.commons.lang3.StringUtils;
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

    @Resource
    private KhfsbMapper khfsbMapper;
    @Resource
    private SbglMapper sbglMapper;

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
        khfsxqbMapper.deleteKhfsxqbByIds(ids);
        for(String s:ids){
            Khfsxqb khfsxqb =  khfsxqbMapper.selectKhfsxqbById(s);
            Khfsb khfsb = khfsbMapper.selectKhfsbByRykhId(khfsxqb.getRykhfsbid());
            khfsb.setUpdateuse(SecurityUtils.getUserId()+"");
            BigDecimal yfs = khfsb.getZfs();
            BigDecimal xfs = khfsxqb.getFs();
            if("2".equals(khfsxqb.getFslx())){
                khfsb.setZfs(yfs.add(xfs));
            }else if("1".equals(khfsxqb.getFslx()) || "3".equals(khfsxqb.getFslx())){
                khfsb.setZfs(yfs.subtract(xfs));
            }
            khfsbMapper.updateKhfsbByXq(khfsb);
        }
        return 1;
    }

    public Map<String,Object> uploadKhfsb(List<Map<String,String>> excelList){
        Map<String,Object> resMap = new HashMap<>();
        //错误信息集合
        List<Map<String,String>> errorList = new ArrayList<>();
        String code = "200";
        int cgCount = 0;
        int cwCount = 0;
        for (Map<String,String> map: excelList) {
            Khfsxqb khfsxqb = new Khfsxqb();
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("yhid",map.get("rybh"));
            paramMap.put("yhxm",map.get("ryxm"));
            paramMap.put("nd",map.get("nd"));
            String fsStr = map.get("fs");
            BigDecimal fs = null;
            if(fsStr != null && fsStr.length() >0){
                try {
                    fs = new BigDecimal(map.get("fs"));
                }catch (Exception e){
                    e.printStackTrace();
                    paramMap.put("msg",map.get("ryxm") + ":" + "分值格式错误");
                    errorList.add(paramMap);
                    cwCount++;
                    code = "202";
                    continue;
                }

            }else{
                paramMap.put("msg",map.get("ryxm") + ":" + "分值格式错误");
                errorList.add(paramMap);
                cwCount++;
                code = "202";
                continue;
            }
            String userId = khfsxqbMapper.getUserByYhidAndYhxm(paramMap);
            if(userId == null || StringUtils.isBlank(userId+"")){
                paramMap.put("msg",map.get("ryxm") + ":" + "用户不存在或者编号和姓名不匹配");
                errorList.add(paramMap);
                cwCount++;
                code = "202";
                continue;
            }
            paramMap.put("yhid",userId + "");
            khfsxqb = khfsbMapper.getKsfsbByYhidAndNd(paramMap);
            Khfsb rykhfsb = new Khfsb();
            rykhfsb.setCreateuse(SecurityUtils.getUserId() + "");
            if(khfsxqb != null && StringUtils.isNotBlank(khfsxqb.getId())){//已经存在当前年度数据
                khfsxqb.setUpdateuse(SecurityUtils.getUserId() + "");
                rykhfsb.setZfs(khfsxqb.getZfs());
                rykhfsb.setId(khfsxqb.getId());
            }else{//不存在  插入
                //插入人员考核分
                rykhfsb.setId(IdUtils.getSnowflakeId());
                rykhfsb.setYhid(paramMap.get("yhid"));
                rykhfsb.setZfs(new BigDecimal("0"));
                //插入人员考核表
                sbglMapper.insertRyKhfsb(rykhfsb);
            }
            Khfsxqb khxfsxqb = new Khfsxqb();
            khxfsxqb.setId(IdUtils.getSnowflakeId());
            khxfsxqb.setCreateuse(SecurityUtils.getUserId() + "");
            khxfsxqb.setRykhfsbid(rykhfsb.getId());
            if(fs.compareTo(BigDecimal.ZERO) >= 0){
                khxfsxqb.setFslx("3");
                khxfsxqb.setFs(fs);
            }else{
                khxfsxqb.setFslx("3");
                khxfsxqb.setFs(new BigDecimal("0"));
            }
            khxfsxqb.setYwid("");
            khxfsxqb.setBz(map.get("bz"));
            //插入考核详情表
            sbglMapper.insertRyKhfsxqb(khxfsxqb);
            //修改人员考核表分数
            rykhfsb.setZfs(rykhfsb.getZfs().add(fs));
            sbglMapper.updateRyKhfsb(rykhfsb);
            cgCount++;
            //业务代码
        }
        resMap.put("myCode",Integer.parseInt(code));
        resMap.put("errorList",errorList);
        return resMap;
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
