package com.xiwen.workload.controller;

import com.xiwen.common.core.web.domain.AjaxResult;
import com.xiwen.system.api.domain.SysDept;
import com.xiwen.workload.domain.Tjgl;
import com.xiwen.workload.service.TjglService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: xiwen
 * @BelongsPackage: com.xiwen.workload.controller
 * @Author: cuiqichao
 * @CreateTime: 2022-10-03  19:53
 * @Description: 统计管理
 * @Version: 1.0
 */
@RestController
@RequestMapping("/tjgl")
public class TjglController {

    @Autowired
    private TjglService tjglService;

    //获取默认部门
    @GetMapping("/getMrbmxx")
    public AjaxResult getMrbmxx(){
        return AjaxResult.success(tjglService.getMrbmxx());
    }

    //部门分数
    @GetMapping("/getBmzfList")
    public AjaxResult getBmzfList(String bmid,String nd){
        return AjaxResult.success(tjglService.getBmzfList(bmid,nd));
    }
    //个人分数
    @GetMapping("/getGrzfList")
    public AjaxResult getGrzfList(String nd,String yhid){
        return AjaxResult.success(tjglService.getGrzfList(nd,yhid));
    }
    //人员信息和当前用户
    @GetMapping("/getRyxxList")
    public AjaxResult getRyxxList(){
        return AjaxResult.success(tjglService.getRyxxList());
    }

    //统计总表 表头
    @GetMapping("/getTableHeader")
    public AjaxResult getTableHeader(String nd){
        return AjaxResult.success(tjglService.getTableHeader(nd));
    }








}
