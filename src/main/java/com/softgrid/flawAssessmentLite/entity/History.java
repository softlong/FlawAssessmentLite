package com.softgrid.flawAssessmentLite.entity;

/**
 * Author: Cheryl
 * Description:
 * Date:Created in 10:55 2018/8/31
 */
public class History {
    private Integer id;

    private String createdDate;

    private String tfpipename;

    private String tflocation;

    private String result;

    private String dbTableName;

    public History(Integer id, String createdDate, String tfpipename, String tflocation, String result, String dbTableName) {
        this.id = id;
        this.createdDate = createdDate;
        this.tfpipename = tfpipename;
        this.tflocation = tflocation;
        this.result = result;
        this.dbTableName = dbTableName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getTfpipename() {
        return tfpipename;
    }

    public void setTfpipename(String tfpipename) {
        this.tfpipename = tfpipename;
    }

    public String getTflocation() {
        return tflocation;
    }

    public void setTflocation(String tflocation) {
        this.tflocation = tflocation;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDbTableName() {
        return dbTableName;
    }

    public void setDbTableName(String dbTableName) {
        this.dbTableName = dbTableName;
    }
}
