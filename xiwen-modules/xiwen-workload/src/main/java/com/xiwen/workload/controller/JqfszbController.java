package com.xiwen.workload.controller;

import java.util.List;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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
import com.xiwen.workload.domain.Jqfszb;
import com.xiwen.workload.service.JqfszbService;
import com.xiwen.common.core.web.controller.BaseController;
import com.xiwen.common.core.web.domain.AjaxResult;
import com.xiwen.common.core.utils.poi.ExcelUtil;
import com.xiwen.common.core.web.page.TableDataInfo;

/**
 * 加权分设置Controller
 *
 * @author xiwen
 * @date 2022-10-10
 */
@RestController
@RequestMapping("/jqfsz")
public class JqfszbController extends BaseController
{
    @Resource
    private JqfszbService jqfszbService;

    /**
     * 查询加权分设置列表
     */
    @RequiresPermissions("workload:jqfsz:list")
    @GetMapping("/list")
    public TableDataInfo list(Jqfszb jqfszb)
    {
        startPage();
        List<Jqfszb> list = jqfszbService.selectJqfszbList(jqfszb);
        return getDataTable(list);
    }

    /**
     * 导出加权分设置列表
     */
    @RequiresPermissions("workload:jqfsz:export")
    @Log(title = "加权分设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Jqfszb jqfszb)
    {
        List<Jqfszb> list = jqfszbService.selectJqfszbList(jqfszb);
        ExcelUtil<Jqfszb> util = new ExcelUtil<Jqfszb>(Jqfszb.class);
        util.exportExcel(response, list, "加权分设置数据");
    }

    /**
     * 获取加权分设置详细信息
     */
    @RequiresPermissions("workload:jqfsz:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(jqfszbService.selectJqfszbById(id));
    }

    /**
     * 新增加权分设置
     */
    @RequiresPermissions("workload:jqfsz:add")
    @Log(title = "加权分设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Jqfszb jqfszb)
    {
        return toAjax(jqfszbService.insertJqfszb(jqfszb));
    }

    /**
     * 修改加权分设置
     */
    @RequiresPermissions("workload:jqfsz:edit")
    @Log(title = "加权分设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Jqfszb jqfszb)
    {
        return toAjax(jqfszbService.updateJqfszb(jqfszb));
    }

    /**
     * 删除加权分设置
     */
    @RequiresPermissions("workload:jqfsz:remove")
    @Log(title = "加权分设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(jqfszbService.deleteJqfszbByIds(ids));
    }
}
