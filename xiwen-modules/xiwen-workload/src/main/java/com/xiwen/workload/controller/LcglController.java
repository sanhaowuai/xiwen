package com.xiwen.workload.controller;

import com.xiwen.common.core.constant.UserConstants;
import com.xiwen.common.core.utils.StringUtils;
import com.xiwen.common.core.web.controller.BaseController;
import com.xiwen.common.core.web.domain.AjaxResult;
import com.xiwen.common.core.web.page.TableDataInfo;
import com.xiwen.common.security.annotation.RequiresPermissions;
import com.xiwen.common.security.utils.SecurityUtils;
import com.xiwen.workload.domain.Lcgl;
import com.xiwen.workload.service.LcglService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @BelongsProject: xiwen
 * @BelongsPackage: com.xiwen.workload.controller
 * @Author: cuiqichao
 * @CreateTime: 2022-08-23  22:34
 * @Description: 流程管理
 * @Version: 1.0
 */
@RestController
@RequestMapping("/lcgl")
public class LcglController extends BaseController {

    @Autowired
    private LcglService lcglService;

    @GetMapping("/list")
    public TableDataInfo list(Lcgl lcgl)
    {
        startPage();
        List<Lcgl> list = lcglService.selectLcglList(lcgl);
        return getDataTable(list);
    }
    @GetMapping("/listBySb")
    public AjaxResult listBySb(Lcgl lcgl)
    {
        List<Lcgl> list = lcglService.listBySb(lcgl);
        return AjaxResult.success(list);
    }

    @PostMapping("/add")
    public AjaxResult add(@RequestBody Lcgl lcgl){
        if(lcgl.getJdList() == null || lcgl.getJdList().size() < 2){
            return AjaxResult.error("流程节点不少于两个！");
        }
        lcgl.setCreateBy(SecurityUtils.getUsername());
        return AjaxResult.success(lcglService.insertLcgl(lcgl));
    }

    @PostMapping("/update")
    public AjaxResult update(@RequestBody Lcgl lcgl){
        lcgl.setCreateBy(SecurityUtils.getUsername());
        //检查流程是否已经被使用，如果被使用不能修改和删除
        if(lcglService.checkedLc(lcgl)){
            return AjaxResult.error("流程已经被使用，不能修改");
        }
        return AjaxResult.success(lcglService.updateLcgl(lcgl));
    }

    @DeleteMapping("/delete")
    public AjaxResult delete(String id){
        Lcgl lcgl = new Lcgl();
        if(StringUtils.isEmpty(id)){
            return AjaxResult.error("参数错误");
        }
        lcgl.setId(id);
        lcgl.setUpdateBy(SecurityUtils.getUsername());
        lcgl.setCreateBy(SecurityUtils.getUsername());
        //检查流程是否已经被使用，如果被使用不能修改和删除
        if(lcglService.checkedLc(lcgl)){
            return AjaxResult.error("流程已经被使用，不能删除");
        }
        lcglService.deleteLcgl(lcgl);
        return AjaxResult.success("操作成功！");
    }

    @PostMapping("/changeLcStatus")
    public AjaxResult changeLcStatus(@RequestBody Lcgl lcgl){
        lcgl.setCreateBy(SecurityUtils.getUsername());
        //检查流程是否已经被使用，如果被使用不能修改、删除、停用
        if(lcglService.checkedLc(lcgl)){
            return AjaxResult.error("流程已经被使用，不能修改");
        }
        return AjaxResult.success(lcglService.updateLcglStatus(lcgl));
    }

    @GetMapping("/getLcxxById")
    public AjaxResult getLcxxById(String id){
        if(org.apache.commons.lang.StringUtils.isBlank(id)){
            return AjaxResult.error("参数错误");
        }
        return AjaxResult.success(lcglService.getLcxxById(id));
    }


}
