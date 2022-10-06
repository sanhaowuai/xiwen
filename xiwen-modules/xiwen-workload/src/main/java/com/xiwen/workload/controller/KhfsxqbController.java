package com.xiwen.workload.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.xiwen.system.api.domain.SysUser;
import com.xiwen.workload.common.ImportExcelUtil;
import com.xiwen.workload.common.importTitle.ImportExcelKhfsbTitle;
import com.xiwen.workload.service.KhfsxqbService;
import org.springframework.web.bind.annotation.*;
import com.xiwen.common.log.annotation.Log;
import com.xiwen.common.log.enums.BusinessType;
import com.xiwen.common.security.annotation.RequiresPermissions;
import com.xiwen.workload.domain.Khfsxqb;
import com.xiwen.common.core.web.controller.BaseController;
import com.xiwen.common.core.web.domain.AjaxResult;
import com.xiwen.common.core.utils.poi.ExcelUtil;
import com.xiwen.common.core.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * @description:导入
     * @author: cuiqichao
     * @param: file
    **/
    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(@RequestParam("file") MultipartFile file){
        List<Map<String, String>> excelList = new ArrayList<>();
        Map<String,Object> resMap = new HashMap<>();
        try {
            excelList = ImportExcelUtil.parseExcel(
                    file.getInputStream(),
                    file.getOriginalFilename(),
                    ImportExcelKhfsbTitle.ExpertImportExcel.getKeyValue());
            resMap = khfsxqbService.uploadKhfsb(excelList);
        } catch (IOException e) {
            System.out.println("解析excel时失败" + e.getMessage());
        }
        return AjaxResult.success(resMap);
    }

    /**
     * @description:下载文件
     * @author: cuiqichao
     * @param: lx
     * @param: response
     * @return: javax.servlet.http.HttpServletResponse
    **/
    @GetMapping("/downloadFileSer")
    public byte[] downloadFileSer(String lx, HttpServletResponse response) {
        byte[] buffer = null;
        try {
            // path是指欲下载的文件的路径。
            String path = "";
            String projectUrl = this.getClass().getResource("/").getPath();
            if("1".equals(lx)){
                path = projectUrl + "static/rycsdrfsa.xlsx";
            }
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
//            // 清空response
//            response.reset();
//            // 设置response的Header
//            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
//            response.addHeader("Content-Length", "" + file.length());
//            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
//            toClient.write(buffer);
//            toClient.flush();
//            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return buffer;
    }





















}
