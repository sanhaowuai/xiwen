package com.xiwen.workload.common.importTitle;

import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 功能描述:
 * 导入excel 对应关系类
 * @author cqc
 */
public class ImportExcelKhfsbTitle {
    public enum ExpertImportExcel {

        /**
         * 表头信息
         */
        RYBH("人员编号", "rybh", 0, true),
        RYXM("人员姓名", "ryxm", 0, true),
        ND("年度", "nd", 0, true),
        FS("分数", "fs", 0, true),
        BZ("备注", "bz", 0, false),
        ;
        /**
         * 中文标题，对应EXCEL的标题，不一致会报错
         */
        private final String titleCn;
        /**
         * 查出的值对应Map的key
         */
        private final String valueKey;
        /**
         * 对应的字段长度
         * 为0时代表不限制
         */
        private final Integer titleLength;
        /**
         * 是否必填
         */
        private final Boolean isRequired;

        ExpertImportExcel(String titleCn, String valueKey, Integer titleLength, Boolean isRequired) {
            this.titleCn = titleCn;
            this.valueKey = valueKey;
            this.titleLength = titleLength;
            this.isRequired = isRequired;
        }
        public String getTitleCn() {
            return titleCn;
        }
        public String getValueKey() {
            return valueKey;
        }
        public Integer getTitleLength() {
            return titleLength;
        }
        public Boolean getIsRequired() {
            return isRequired;
        }

        /**
         * 获取excel对应的数据
         */
        public static Map<String, String> getKeyValue() {
            return Arrays.stream(ExpertImportExcel.values()).collect(
                    Collectors.toMap(ExpertImportExcel::getTitleCn, ExpertImportExcel::getValueKey));
        }
        /**
         * 根据值获取对应的信息
         */
        public static Map<String, ExpertImportExcel> getExcelTitle() {
            return Arrays.stream(ExpertImportExcel.values()).collect(
                    Collectors.toMap(ExpertImportExcel::getValueKey, title -> title));
        }
        public static List<String> checkData(ExpertImportExcel taskImportExcel, String value) {
            return checkDataCommon(value, taskImportExcel.getIsRequired(), taskImportExcel.getTitleCn(), taskImportExcel.getTitleLength());
        }
    }

    /**
     * 校验导入信息长度和必填选项
     */
    private static List<String> checkDataCommon(String value, Boolean isRequired, String titleCn, Integer titleLength) {
        List<String> result = new ArrayList<>();
        if (Boolean.TRUE.equals(isRequired) && StringUtils.isBlank(value)) {
            result.add(String.format("%s信息未填", titleCn));
        }
        if (titleLength != 0 && value != null && value.length() > titleLength) {
            result.add(String.format("%s过长", titleCn));
        }
        return result;
    }
}