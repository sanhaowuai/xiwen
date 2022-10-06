package com.xiwen.workload.service.impl;

import com.xiwen.common.security.utils.SecurityUtils;
import com.xiwen.system.api.domain.SysDept;
import com.xiwen.workload.domain.Khfsb;
import com.xiwen.workload.domain.Khfsxqb;
import com.xiwen.workload.domain.Tjgl;
import com.xiwen.workload.mapper.TjglMapper;
import com.xiwen.workload.service.TjglService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @BelongsProject: xiwen
 * @BelongsPackage: com.xiwen.workload.service.impl
 * @Author: cuiqichao
 * @CreateTime: 2022-10-03  19:56
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class TjglServiceImpl implements TjglService {

    @Resource
    private TjglMapper tjglMapper;

    public Tjgl getMrbmxx(){
        return tjglMapper.getMrbmxx();
    }
    public Map<String,Object> getRyxxList(){
        Map<String,Object> resMap = new HashMap<>();
        List<Map<String,String>> ryxxList = tjglMapper.getRyxxList();
        resMap.put("ryxxList",ryxxList);
        resMap.put("yhid",SecurityUtils.getUserId()+"");
        return resMap;
    }
    public Map<String,Object> getGrzfList(String nd,String yhid){
        Map<String,Object> resMap = new HashMap<>();
        Map<String,String> paramMap = new HashMap<>();
        String yhidh = SecurityUtils.getUserId()+"";
        if(StringUtils.isNotBlank(yhid)){
            yhidh = yhid;
        }
        paramMap.put("nd",nd);
        paramMap.put("yhid", yhidh);
        Khfsxqb khfsxqb = tjglMapper.getKhfsbByIdAndNd(paramMap);
        if(khfsxqb == null || StringUtils.isBlank(khfsxqb.getId())){

        }else{
            paramMap.put("fsbid",khfsxqb.getId());
        }

        List<Map<String,Object>> xList = new ArrayList<>();
        List<String> yzDataName = new ArrayList<>();
        List<BigDecimal> yzData = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            String ndt = nd + "-";
            if(i < 10){
                ndt = ndt + "0" + i;
            }else{
                ndt = ndt + i;
            }
            paramMap.put("dsj",ndt);
            Map<String,Object> tempMap = new HashMap<>();
            BigDecimal big = new BigDecimal("0");
            if(khfsxqb != null && StringUtils.isNotBlank(khfsxqb.getId()) && isLessThanThisMonth(ndt)){
                tempMap = tjglMapper.getGrfsByNy(paramMap);
                if(tempMap == null){
                    tempMap = new HashMap<>();
                    tempMap.put("id","");
                    tempMap.put("yhid",yhidh);
                    tempMap.put("nd",nd);
                    tempMap.put("fs","0");
                }
                big = new BigDecimal(String.valueOf(tempMap.get("fs")));
            }else{
                tempMap.put("id","");
                tempMap.put("yhid",yhidh);
                tempMap.put("nd",nd);
                tempMap.put("fs","0");
            }
            xList.add(tempMap);
            yzDataName.add(ndt);
            yzData.add(big);
        }
        resMap.put("grzf",khfsxqb);
        resMap.put("yzDataName",yzDataName);
        resMap.put("yzData",yzData);
        return resMap;
    }


    public Map<String,Object> getTableHeader(String nd){
        Map<String,Object> resMap = new HashMap<>();
        List<Tjgl> khlxList = tjglMapper.getKhlxList();
        List<Tjgl> khxList = tjglMapper.getKhxList();
        List<Tjgl> resList = new ArrayList<>();

        for(Tjgl tjgllx: khlxList){
            //1. 构建一级节点
            for (Tjgl tjgl : khxList) {
                if (tjgl.getPid().equals("0") && tjgl.getSjflx().equals(tjgllx.getValue())) {
                    if(tjgllx.getcList() == null){
                        tjgllx.setcList(new ArrayList<Tjgl>());
                    }
                    tjgllx.getcList().add(tjgl);
                }
            }
            tjgllx.setId(tjgllx.getValue());
            tjgllx.setPid("-1");
            tjgllx.setKhxmc(tjgllx.getLabel());
            resList.add(tjgllx);
        }

        // 2、递归获取子节点
        for (Tjgl tjgl : resList) {
            if(tjgl.getcList() == null){
                tjgl.setcList(new ArrayList<>());
            }
            for(Tjgl tjgle: tjgl.getcList()){
                tjgle = recursiveTree(tjgle, khxList);
            }

        }
        resMap.put("hList",resList);
        return resMap;
    }

    private Tjgl recursiveTree(Tjgl parent, List<Tjgl> list) {
        for (Tjgl tjgl : list) {
            if (Objects.equals(parent.getId(), tjgl.getPid())) {
                tjgl = recursiveTree(tjgl, list);
                if(parent.getcList() == null){
                    parent.setcList(new ArrayList<Tjgl>());
                }
                parent.getcList().add(tjgl);
            }
        }
        return parent;
    }

    public Map<String,Object> getBmzfList(String bmid,String nd){
        Map<String,Object> resMap = new HashMap<>();
        Map<String,String> paraMap = new HashMap<>();
        paraMap.put("bmid",bmid);
        paraMap.put("nd",nd);
        List<Integer> yzList = new ArrayList<>();
        List<Tjgl> list = tjglMapper.getBmzfList(paraMap);
        List<String> yzDataName = new ArrayList<>();
        List<BigDecimal> yzData = new ArrayList<>();
        BigDecimal tempBig = new BigDecimal("0");
        for(Tjgl tjgl: list){
            tjgl.setNd(nd);
            String zfsStr = tjglMapper.getZfsByBm(tjgl);
            if(zfsStr == null || "".equals(zfsStr)){
                zfsStr = "0";
            }
            BigDecimal zfs = new BigDecimal(zfsStr);
            if(tempBig.compareTo(zfs) < 0){
                tempBig = zfs;
            }
            tjgl.setZfs(zfs);
            yzDataName.add(tjgl.getBmmc());
            yzData.add(zfs);
        }
        if(tempBig.compareTo(new BigDecimal("100")) < 0){
            yzList = repYzList(yzList,100);
        }else if(tempBig.compareTo(new BigDecimal("500")) < 0){
            yzList = repYzList(yzList,500);
        }else if(tempBig.compareTo(new BigDecimal("1000")) < 0){
            yzList = repYzList(yzList,1000);
        }else if(tempBig.compareTo(new BigDecimal("1500")) < 0){
            yzList = repYzList(yzList,1500);
        }else if(tempBig.compareTo(new BigDecimal("2000")) < 0){
            yzList = repYzList(yzList,2000);
        }else if(tempBig.compareTo(new BigDecimal("2500")) < 0){
            yzList = repYzList(yzList,2500);
        }else if(tempBig.compareTo(new BigDecimal("3000")) < 0){
            yzList = repYzList(yzList,3000);
        }else if(tempBig.compareTo(new BigDecimal("4000")) < 0){
            yzList = repYzList(yzList,4000);
        }else if(tempBig.compareTo(new BigDecimal("5000")) < 0){
            yzList = repYzList(yzList,5000);
        }else if(tempBig.compareTo(new BigDecimal("10000")) < 0){
            yzList = repYzList(yzList,10000);
        }else if(tempBig.compareTo(new BigDecimal("50000")) < 0){
            yzList = repYzList(yzList,50000);
        }else if(tempBig.compareTo(new BigDecimal("100000")) < 0){
            yzList = repYzList(yzList,100000);
        }
        resMap.put("yzData",yzData);
        resMap.put("yzDataName",yzDataName);
        resMap.put("bmList",list);
        resMap.put("yzList",yzList);
        return resMap;
    }




    public List<Integer> repYzList( List<Integer> list,Integer zs){
        int tempI = zs / 10;
        for(int i = 1;i < 11; i++){
            list.add(i * tempI);
        }
        return list;
    }

    public static void main(String[] args) throws ParseException {

    }

    /**
     * @description:传入时间  判断当前月份是否大于等于传入的月份
     * @author: cuiqichao
     * @date: 2022-10-06 21:23:19
     * @param: bjm  传入月份   2022-09
     * @return: boolean 当前月份大于等于传入的月份返回true
    **/
    private boolean isLessThanThisMonth(String bjm) {
        try {
            Calendar cal = Calendar.getInstance();
            int y = cal.get(Calendar.YEAR) * 100;
            int m = cal.get(Calendar.MONTH) + 1;
            int ym = y + m;
            String endTime = bjm;//支持特殊格式转换
            String format = "yyyy-MM";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date edate = sdf.parse(endTime);
            Calendar cal1=Calendar.getInstance();
            cal1.setTime(edate);
            int y1 = cal1.get(Calendar.YEAR) * 100;
            int m1 = cal1.get(Calendar.MONTH) + 1;
            int ym1 = y1 + m1;
            if(ym >= ym1){ //大于指定月份
                return true;
            }else{
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
