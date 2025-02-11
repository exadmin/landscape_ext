package com.github.exadmin.hide.t001;

import com.github.exadmin.model.landscapefile.TheCategory;
import com.github.exadmin.model.landscapefile.TheItem;
import com.github.exadmin.model.landscapefile.TheLandscape;
import com.github.exadmin.model.landscapefile.TheSubCategory;
import com.github.exadmin.utils.TheModelsUtils;
import com.github.exadmin.yaml_based.ConfigA_Factory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class HideSomeItemsTest {

    private void doTestLogic(String extraResourceName, List<String> expItemNamesAfterMerge) {
        try {
            TheLandscape baseModel = ConfigA_Factory.getConfigA();
            TheLandscape extraModel = TheModelsUtils.loadModelFromResource(extraResourceName);

            // merge configs
            baseModel.mergeValuesFrom(extraModel);
            TheModelsUtils.removeDroppedItems(baseModel);

            // check results
            TheCategory theCat = baseModel.getCategories().get(0);
            TheSubCategory theSubCat = theCat.getSubcategories().get(0);

            List<TheItem> items = theSubCat.getItemList();
            List<String> expNames = new ArrayList<>(expItemNamesAfterMerge);

            assertEquals(expNames.size(), items.size());
            for (TheItem nextItem : items) {
                expNames.remove(nextItem.getName());
            }

            assertTrue("Unexpected item names: " + expNames, expNames.isEmpty());


        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void hideItem001() {
        doTestLogic("com/github/exadmin/hide/t001/drop-item-001.yml",
                Arrays.asList("Item - 002", "Item - 003"));
    }

    @Test
    public void hideItem002() {
        doTestLogic("com/github/exadmin/hide/t001/drop-item-002.yml",
                Arrays.asList("Item - 001", "Item - 003"));
    }

    @Test
    public void hideItem003() {
        doTestLogic("com/github/exadmin/hide/t001/drop-item-003.yml",
                Arrays.asList("Item - 002", "Item - 001"));
    }

    @Test
    public void hideNonExistedItem() {
        doTestLogic("com/github/exadmin/hide/t001/drop-item-004.yml",
                Arrays.asList("Item - 001", "Item - 002", "Item - 003"));
    }

    @Test (expected = AssertionError.class)
    public void rainyTest() {
        doTestLogic("com/github/exadmin/hide/t001/drop-item-001.yml",
                Arrays.asList("Item - 001", "Item - 002", "Item - 003"));
    }
}
