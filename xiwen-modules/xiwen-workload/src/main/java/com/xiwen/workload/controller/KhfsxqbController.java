package com.xiwen.workload.controller;

import java.util.List;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.xiwen.workload.service.KhfsxqbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xiwen.common.log.annotation.Log;
import com.xiwen.common.log.enums.BusinessType;
import com.xiwen.common.security.annotation.RequiresPermissions;
import com.xiwen.workload.domain.Khfsxqb;
import com.xiwen.workload.service.KhfsxqbService;
import com.xiwen.common.core.web.controller.BaseController;
import com.xiwen.common.core.web.domain.AjaxResult;
import com.xiwen.common.core.utils.poi.ExcelUtil;
import com.xiwen.common.core.web.page.TableDataInfo;

/**
 * 人员考核分数详情Controller
 *
 * @author xiwen
 * @date 2022-09-16
 */
@RestController
@RequestMapping("/Khfsxqb")
public class KhfsxqbController extends BaseController
{
    @Resource
    private KhfsxqbService khfsxqbService;

    /**
     * 查询人员考核分数详情列表
     */
    @RequiresPermissions("workload:Khfsxqb:list")
    @GetMapping("/list")
    public TableDataInfo list(Khfsxqb khfsxqb)
    {
        startPage();
        List<Khfsxqb> list = khfsxqbService.selectKhfsxqbList(khfsxqb);
        return getDataTable(list);
    }

    /**
     * 导出人员考核分数详情列表
     */
    @RequiresPermissions("workload:Khfsxqb:export")
    @Log(title = "人员考核分数详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Khfsxqb khfsxqb)
    {
        List<Khfsxqb> list = khfsxqbService.selectKhfsxqbList(khfsxqb);
        ExcelUtil<Khfsxqb> util = new ExcelUtil<Khfsxqb>(Khfsxqb.class);
        util.exportExcel(response, list, "人员考核分数详情数据");
    }

    /**
     * 获取人员考核分数详情详细信息
     */
    @RequiresPermissions("workload:Khfsxqb:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(khfsxqbService.selectKhfsxqbById(id));
    }

    /**
     * 新增人员考核分数详情
     */
    @RequiresPermissions("workload:Khfsxqb:add")
    @Log(title = "人员考核分数详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Khfsxqb khfsxqb)
    {
        return toAjax(khfsxqbService.insertKhfsxqb(khfsxqb));
    }

    /**
     * 修改人员考核分数详情
     */
    @RequiresPermissions("workload:Khfsxqb:edit")
    @Log(title = "人员考核分数详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Khfsxqb khfsxqb)
    {
        return toAjax(khfsxqbService.updateKhfsxqb(khfsxqb));
    }

    /**
     * 删除人员考核分数详情
     */
    @RequiresPermissions("workload:Khfsxqb:remove")
    @Log(title = "人员考核分数详情", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(khfsxqbService.deleteKhfsxqbByIds(ids));
    }
}
