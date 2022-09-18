package com.xiwen.workload.controller;

import java.util.List;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.xiwen.workload.service.NdbService;
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
import com.xiwen.workload.domain.Ndb;
import com.xiwen.common.core.web.controller.BaseController;
import com.xiwen.common.core.web.domain.AjaxResult;
import com.xiwen.common.core.utils.poi.ExcelUtil;
import com.xiwen.common.core.web.page.TableDataInfo;

/**
 * 年度管理Controller
 *
 * @author xiwen
 * @date 2022-09-17
 */
@RestController
@RequestMapping("/ndgl")
public class NdglController extends BaseController
{
    @Resource
    private NdbService ndbService;

    /**
     * 查询年度管理列表
     */
    @RequiresPermissions("workload:ndgl:list")
    @GetMapping("/list")
    public TableDataInfo list(Ndb ndb)
    {
        startPage();
        List<Ndb> list = ndbService.selectNdbList(ndb);
        return getDataTable(list);
    }

    @GetMapping("/getNdList")
    public AjaxResult getNdList(Ndb ndb)
    {
        List<Ndb> list = ndbService.getNdList(ndb);
        return AjaxResult.success(list);
    }

    /**
     * 导出年度管理列表
     */
    @RequiresPermissions("workload:ndgl:export")
    @Log(title = "年度管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Ndb ndb)
    {
        List<Ndb> list = ndbService.selectNdbList(ndb);
        ExcelUtil<Ndb> util = new ExcelUtil<Ndb>(Ndb.class);
        util.exportExcel(response, list, "年度管理数据");
    }

    /**
     * 获取年度管理详细信息
     */
    @RequiresPermissions("workload:ndgl:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(ndbService.selectNdbById(id));
    }

    /**
     * 新增年度管理
     */
    @RequiresPermissions("workload:ndgl:add")
    @Log(title = "年度管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Ndb ndb)
    {
        return toAjax(ndbService.insertNdb(ndb));
    }

    /**
     * 修改年度管理
     */
    @RequiresPermissions("workload:ndgl:edit")
    @Log(title = "年度管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Ndb ndb)
    {
        return toAjax(ndbService.updateNdb(ndb));
    }

    /**
     * 删除年度管理
     */
    @RequiresPermissions("workload:ndgl:remove")
    @Log(title = "年度管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(ndbService.deleteNdbByIds(ids));
    }
}
