package com.github.exadmin.yaml_based;

import com.github.exadmin.model.landscapefile.TheCategory;
import com.github.exadmin.model.landscapefile.TheLandscape;
import com.github.exadmin.model.landscapefile.TheSubCategory;
import com.github.exadmin.utils.ListsUtils;
import com.github.exadmin.utils.TheModelsUtils;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ConfigA_Factory {
    public static TheLandscape getConfigA() throws Exception {
        TheLandscape model = TheModelsUtils.loadModelFromResource("com/github/exadmin/yaml_based/configA/config-A.yml");

        // assert model
        // check Category
        assertNotNull(model.getCategories());
        assertEquals(1, model.getCategories().size());

        TheCategory theCat = model.getCategories().get(0);
        assertEquals("Category 001", theCat.getName());

        // check sub-category
        assertNotNull(theCat.getSubcategories());
        assertEquals(1, theCat.getSubcategories().size());
        TheSubCategory theSubCat = theCat.getSubcategories().get(0);
        assertEquals("SubCategory 001", theSubCat.getName());

        // check items
        assertNotNull(theSubCat.getItemList());
        assertEquals(3, theSubCat.getItemList().size());

        List<String> expNames = ListsUtils.asList(
                "Item - 001",
                "Item - 002",
                "Item - 003");

        List<String> actNames = ListsUtils.asList(
                theSubCat.getItemList().get(0).getName(),
                theSubCat.getItemList().get(1).getName(),
                theSubCat.getItemList().get(2).getName());

        ListsUtils.assertListsSimilar(expNames, actNames);

        return model;
    }
}
