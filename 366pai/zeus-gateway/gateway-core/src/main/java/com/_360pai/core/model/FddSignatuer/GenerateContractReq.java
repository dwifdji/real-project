package com._360pai.core.model.FddSignatuer;

/**
 * 描述：合同生成接口请求参数
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/13 13:06
 */
public class GenerateContractReq {

    private String doc_title;//文档标题

    private String template_id;//模板id

    private String contract_id;//合同编号

    private String font_size;//字体大小 参考 word 字体设置，例如：10,12,12.5,14；不传则为默认值 9

    private String font_type;//字体的类型 0-宋体；1-仿宋；2-黑体；3-楷体；4-微软雅黑

    private String parameter_map;//填充的参数

    private String dynamic_tables;//表格数据


    public String getDoc_title() {
        return doc_title;
    }

    public void setDoc_title(String doc_title) {
        this.doc_title = doc_title;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getContract_id() {
        return contract_id;
    }

    public void setContract_id(String contract_id) {
        this.contract_id = contract_id;
    }

    public String getFont_size() {
        return font_size;
    }

    public void setFont_size(String font_size) {
        this.font_size = font_size;
    }

    public String getFont_type() {
        return font_type;
    }

    public void setFont_type(String font_type) {
        this.font_type = font_type;
    }

    public String getParameter_map() {
        return parameter_map;
    }

    public void setParameter_map(String parameter_map) {
        this.parameter_map = parameter_map;
    }

    public String getDynamic_tables() {
        return dynamic_tables;
    }

    public void setDynamic_tables(String dynamic_tables) {
        this.dynamic_tables = dynamic_tables;
    }
}
