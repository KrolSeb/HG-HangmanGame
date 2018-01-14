package com.wisielec.wisielec.domain;

import java.util.List;

/**
 * Created by Emerex on 14/01/2018.
 */
public class Category {
    private String categoryName;
    private List<Subcategory> subcategoryList;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Subcategory> getSubcategoryList() {
        return subcategoryList;
    }

    public void setSubcategoryList(List<Subcategory> subcategoryList) {
        this.subcategoryList = subcategoryList;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryName='" + categoryName + '\'' +
                ", subcategoryList=" + subcategoryList +
                '}';
    }
}
