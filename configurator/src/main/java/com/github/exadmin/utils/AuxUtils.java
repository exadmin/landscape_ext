package com.github.exadmin.utils;

import com.github.exadmin.model.landscapefile.TheCategory;
import com.github.exadmin.model.landscapefile.TheLandscape;
import com.github.exadmin.model.landscapefile.TheSubCategory;

import java.util.ArrayList;
import java.util.List;

public class AuxUtils {
    public static List<String> getSubcategories(TheLandscape lsModel) {
        final List<String> uniqueSet = new ArrayList<>();

        for (TheCategory category : lsModel.getCategories()) {
            String categoryName = category.getName();

            if (categoryName == null || categoryName.isEmpty()) {
                throw new IllegalStateException("Empty category is found in landscape-model. Value == '" + categoryName + "'");
            }

            categoryName = categoryName + ":"; // creating a kind of namespace for subcategory

            for (TheSubCategory subcategory : category.getSubcategories()) {
                String subCategoryName = subcategory.getName();

                if (subCategoryName == null || subCategoryName.isEmpty()) {
                    throw new IllegalStateException("Empty subcategory is found in category = '" + categoryName.substring(0, categoryName.length() - 1) + "'");
                }

                String subCategoryNameWithPrefix = categoryName + subCategoryName;
                if (uniqueSet.contains(subCategoryNameWithPrefix)) {
                    throw new IllegalStateException("Duplicate subcategory definition is found: " + subCategoryNameWithPrefix);
                }

                uniqueSet.add(subCategoryNameWithPrefix);
            }
        }

        return uniqueSet;
    }
}
