package com.xiwen.workload.mapper;

import com.xiwen.system.api.domain.SysDept;
import com.xiwen.workload.domain.Khfsxqb;
import com.xiwen.workload.domain.Tjgl;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: xiwen
 * @BelongsPackage: com.xiwen.workload.mapper
 * @Author: cuiqichao
 * @CreateTime: 2022-10-03  19:57
 * @Description: TODO
 * @Version: 1.0
 */
public interface TjglMapper {

    public Tjgl getMrbmxx();
    public List<Tjgl> getBmzfList(Map<String,String> paramMap);
    public List<Map<String,String>> getRyxxList();
    public List<Tjgl> getKhlxList();
    public List<Tjgl> getKhxList();
    public Khfsxqb getKhfsbByIdAndNd(Map<String,String> paramMap);
    public Map<String,Object> getGrfsByNy(Map<String,String> paramMap);
    public String getZfsByBm(Tjgl tjgl);


}
