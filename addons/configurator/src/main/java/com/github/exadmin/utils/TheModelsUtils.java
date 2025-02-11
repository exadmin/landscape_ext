package com.github.exadmin.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.exadmin.model.landscapefile.TheCategory;
import com.github.exadmin.model.landscapefile.TheItem;
import com.github.exadmin.model.landscapefile.TheLandscape;
import com.github.exadmin.model.landscapefile.TheSubCategory;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TheModelsUtils {
    private final static ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static TheLandscape loadModelFrom (File yamlFile) throws Exception {
        return mapper.readValue(yamlFile, TheLandscape.class);
    }

    public static TheLandscape loadModelFromResource (String resourceName) throws Exception {
        ClassLoader classloader = mapper.getClass().getClassLoader();
        InputStream is = classloader.getResourceAsStream(resourceName);

        return mapper.readValue(is, TheLandscape.class);
    }

    public static void removeDroppedItems(TheLandscape primaryModel) {
        for (TheCategory cat : primaryModel.getCategories()) {
            for (TheSubCategory subCat : cat.getSubcategories()) {

                List<TheItem> dropList = null;
                for (TheItem item : subCat.getItemList()) {
                    if ("DROP".equalsIgnoreCase(item.getProject())) {
                        if (dropList == null) dropList = new ArrayList<>();
                        dropList.add(item);
                    }
                }
                if (dropList != null) {
                    subCat.getItemList().removeAll(dropList);
                }
            }
        }
    }
}
