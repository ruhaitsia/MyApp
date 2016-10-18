package com.frank.newoa.model;


import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * Created by fzhang090 on 10/12/2016.
 * Configuration 查询使用对象
 */

public class ConfigurationBO implements Serializable {
    private static final long serialVersionUID = 2891156303167295325L;
   private String moduleName = "";

    private Integer currentPage = 0;

    private Integer itemPerPage = 0;


    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getCurrentPage() {
        int start = (this.currentPage - 1) * this.itemPerPage;
        if (start < 0) {
            start = 0;
        }

        return start;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getItemPerPage() {
        return itemPerPage;
    }

    public void setItemPerPage(Integer itemPerPage) {
        this.itemPerPage = itemPerPage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigurationBO that = (ConfigurationBO) o;
        return Objects.equal(moduleName, that.moduleName) &&
                Objects.equal(currentPage, that.currentPage) &&
                Objects.equal(itemPerPage, that.itemPerPage);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(moduleName, currentPage, itemPerPage);
    }
}
