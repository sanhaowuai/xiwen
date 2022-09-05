package com.xiwen.workload.controller;

import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.xiwen.common.core.utils.uuid.IdUtils;
import com.xiwen.common.security.utils.SecurityUtils;
import com.xiwen.workload.service.KhxglService;
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
import com.xiwen.workload.domain.Khxgl;
import com.xiwen.common.core.web.controller.BaseController;
import com.xiwen.common.core.web.domain.AjaxResult;
import com.xiwen.common.core.utils.poi.ExcelUtil;

/**
 * 考核项管理Controller
 *
 * @author xiwen
 * @date 2022-09-04
 */
@RestController
@RequestMapping("/khxgl")
public class KhxglController extends BaseController
{
    @Resource
    private KhxglService khxglService;

    /**
     * 查询考核项管理列表
     */
    @RequiresPermissions("workload:khxgl:list")
    @GetMapping("/list")
    public AjaxResult list(Khxgl khxgl)
    {
        List<Khxgl> list = khxglService.selectKhxglList(khxgl);
        return AjaxResult.success(list);
    }

    /**
     * 导出考核项管理列表
     */
    @RequiresPermissions("workload:khxgl:export")
    @Log(title = "考核项管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Khxgl khxgl)
    {
        List<Khxgl> list = khxglService.selectKhxglList(khxgl);
        ExcelUtil<Khxgl> util = new ExcelUtil<Khxgl>(Khxgl.class);
        util.exportExcel(response, list, "考核项管理数据");
    }

    /**
     * 获取考核项管理详细信息
     */
    @RequiresPermissions("workload:khxgl:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(khxglService.selectKhxglById(id));
    }

    /**
     * 新增考核项管理
     */
    @RequiresPermissions("workload:khxgl:add")
    @Log(title = "考核项管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Khxgl khxgl)
    {
        khxgl.setCreateuse(SecurityUtils.getUsername());
        khxgl.setUpdateuse(SecurityUtils.getUsername());
        khxgl.setId(IdUtils.getSnowflakeId());
        return toAjax(khxglService.insertKhxgl(khxgl));
    }

    /**
     * 修改考核项管理
     */
    @RequiresPermissions("workload:khxgl:edit")
    @Log(title = "考核项管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Khxgl khxgl)
    {
        khxgl.setUpdateuse(SecurityUtils.getUsername());
        return toAjax(khxglService.updateKhxgl(khxgl));
    }

    /**
     * 删除考核项管理
     */
    @RequiresPermissions("workload:khxgl:remove")
    @Log(title = "考核项管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("updateuse",SecurityUtils.getUsername());
        paramMap.put("ids",ids);
        return toAjax(khxglService.deleteKhxglByIds(paramMap));
    }
}
