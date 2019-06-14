package org.micro.doc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MicroMethod implements Serializable {

    /**
     * 名称
     */
    private String name;
    /**
     * 标题
     */
    private String title;
    /**
     * 作者
     */
    private String author;
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
     * 返回模型
     */
    private MicroReturn returns;
    /**
     * 参数清单
     */
    private List<MicroParameter> parameters;
    /**
     * 堆栈清单
     */
    private List<MicroThrows> throwses;

}
