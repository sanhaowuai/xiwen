package com.xiwen.workload.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.xiwen.common.security.utils.SecurityUtils;
import com.xiwen.workload.domain.SbCyry;
import com.xiwen.workload.service.SbglService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.xiwen.common.log.annotation.Log;
import com.xiwen.common.log.enums.BusinessType;
import com.xiwen.common.security.annotation.RequiresPermissions;
import com.xiwen.workload.domain.Sbgl;
import com.xiwen.common.core.web.controller.BaseController;
import com.xiwen.common.core.web.domain.AjaxResult;
import com.xiwen.common.core.utils.poi.ExcelUtil;
import com.xiwen.common.core.web.page.TableDataInfo;

/**
 * 申报管理Controller
 *
 * @author xiwen
 * @date 2022-09-05
 */
@RestController
@RequestMapping("/sbgl")
public class SbglController extends BaseController
{
    @Resource
    private SbglService sbglService;

    /**
     * 查询申报管理列表
     */
    @RequiresPermissions("workload:sbgl:list")
    @GetMapping("/list")
    public TableDataInfo list(Sbgl sbgl)
    {
        startPage();
        List<Sbgl> list = sbglService.selectSbglList(sbgl);
        return getDataTable(list);
    }

    @GetMapping("/queryCyryList")
    public TableDataInfo queryCyryList(String ryxm)
    {
        startPage();
        SbCyry sbCyry = new SbCyry();
        sbCyry.setRyxm(ryxm);
        List<SbCyry> list = sbglService.queryCyryList(sbCyry);
        return getDataTable(list);
    }
    //获取登录人信息
    @GetMapping("/getUserByDlr")
    public AjaxResult getUserByDlr(String yhid)
    {
        SbCyry sbCyry = new SbCyry();
        if(StringUtils.isNotBlank(yhid)){
            sbCyry.setYhid(yhid);
        }else{
            sbCyry.setYhid(SecurityUtils.getUserId()+"");
        }
        return AjaxResult.success(sbglService.getUserByDlr(sbCyry));
    }

    /**
     * 导出申报管理列表
     */
    @RequiresPermissions("workload:sbgl:export")
    @Log(title = "申报管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Sbgl sbgl)
    {
        List<Sbgl> list = sbglService.selectSbglList(sbgl);
        ExcelUtil<Sbgl> util = new ExcelUtil<Sbgl>(Sbgl.class);
        util.exportExcel(response, list, "申报管理数据");
    }

    /**
     * 获取申报管理详细信息
     */
    @RequiresPermissions("workload:sbgl:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        Sbgl sbgl = sbglService.selectSbglById(id);
        sbgl.setCyryTList(sbglService.querySbcyryList(sbgl));
        return AjaxResult.success(sbgl);
    }
    /**
     * 获取申报表单中参与人员list
     */
    @GetMapping(value = "querySbcyryList")
    public AjaxResult querySbcyryList(String id)
    {
        Sbgl sbgl = new Sbgl();
        sbgl.setId(id);
        List<SbCyry> sbCyryList= sbglService.querySbcyryList(sbgl);
        return AjaxResult.success(sbCyryList);
    }

    /**
     * 新增申报管理
     */
    @RequiresPermissions("workload:sbgl:add")
    @Log(title = "申报管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Sbgl sbgl)
    {
        return toAjax(sbglService.insertSbgl(sbgl));
    }

    /**
     * 新增、修改申报管理
     */
    @RequiresPermissions("workload:sbgl:edit")
    @Log(title = "申报管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Sbgl sbgl)
    {
        sbgl.setCreateuse(SecurityUtils.getUserId()+"");
        sbgl.setUpdateuse(SecurityUtils.getUserId()+"");
        sbgl.setSqr(SecurityUtils.getUserId()+"");
        int resInt = sbglService.updateSbgl(sbgl);
        return toAjax(resInt);
    }

    /**
     * 删除申报管理
     */
    @RequiresPermissions("workload:sbgl:remove")
    @Log(title = "申报管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(sbglService.deleteSbglByIds(ids));
    }
}
