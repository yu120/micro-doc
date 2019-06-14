package org.micro.doc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MicroClass implements Serializable {
    /**
     * 名称
     */
    private String name;
    /**
     * 标题
     */
    private String title;
    /**
     * 包名
     */
    private String packageName;
    /**
     * 作者
     */
    private String author;
    /**
     * 版本号
     */
    private String version;
    /**
     * API笔记
     */
    private String apiNote;
    /**
     * 日期
     */
    private String since;
    /**
     * 是否舍弃
     */
    private Boolean deprecated;
    /**
     * 是否是接口
     */
    private Boolean interfaced;
    /**
     * 方法
     */
    private List<MicroMethod> methods;
}
