package wisielec.wisielec.com.domain;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;


public class Category implements Serializable {
    private String categoryName;
    private List<Subcategory> subcategories;

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setSubcategories(List<Subcategory> subcategories) {
        this.subcategories = subcategories;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<Subcategory> getSubcategories() {
        return subcategories;
    }

    @NonNull
    @Override
    public String toString() {
        return "Category{" + "categoryName='" + categoryName + '\'' + ", subcategories=" + subcategories + '}';
    }
}


