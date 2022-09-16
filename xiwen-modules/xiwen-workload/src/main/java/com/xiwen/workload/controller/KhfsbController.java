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
import com.xiwen.workload.domain.Khfsb;
import com.xiwen.workload.service.KhfsbService;
import com.xiwen.common.core.web.controller.BaseController;
import com.xiwen.common.core.web.domain.AjaxResult;
import com.xiwen.common.core.utils.poi.ExcelUtil;
import com.xiwen.common.core.web.page.TableDataInfo;

/**
 * 人员考核分数Controller
 *
 * @author xiwen
 * @date 2022-09-16
 */
@RestController
@RequestMapping("/khfsb")
public class KhfsbController extends BaseController
{
    @Resource
    private KhfsbService khfsbService;

    /**
     * 查询人员考核分数列表
     */
    @RequiresPermissions("workload:khfsb:list")
    @GetMapping("/list")
    public TableDataInfo list(Khfsb khfsb)
    {
        startPage();
        List<Khfsb> list = khfsbService.selectKhfsbList(khfsb);
        return getDataTable(list);
    }

    /**
     * 导出人员考核分数列表
     */
    @RequiresPermissions("workload:khfsb:export")
    @Log(title = "人员考核分数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Khfsb khfsb)
    {
        List<Khfsb> list = khfsbService.selectKhfsbList(khfsb);
        ExcelUtil<Khfsb> util = new ExcelUtil<Khfsb>(Khfsb.class);
        util.exportExcel(response, list, "人员考核分数数据");
    }

    /**
     * 获取人员考核分数详细信息
     */
    @RequiresPermissions("workload:khfsb:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(khfsbService.selectKhfsbById(id));
    }

    /**
     * 新增人员考核分数
     */
    @RequiresPermissions("workload:khfsb:add")
    @Log(title = "人员考核分数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Khfsb khfsb)
    {
        return toAjax(khfsbService.insertKhfsb(khfsb));
    }

    /**
     * 修改人员考核分数
     */
    @RequiresPermissions("workload:khfsb:edit")
    @Log(title = "人员考核分数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Khfsb khfsb)
    {
        return toAjax(khfsbService.updateKhfsb(khfsb));
    }

    /**
     * 删除人员考核分数
     */
    @RequiresPermissions("workload:khfsb:remove")
    @Log(title = "人员考核分数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(khfsbService.deleteKhfsbByIds(ids));
    }
}
