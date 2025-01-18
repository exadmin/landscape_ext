package com.github.exadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.exadmin.model.landscapefile.TheLandscape;
import com.github.exadmin.utils.CmdLineArgument;
import com.github.exadmin.utils.CommandLineHelper;
import com.github.exadmin.utils.FileUtils;
import com.github.exadmin.utils.MyLogger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FinalConfigurationBuilderApp {
    public static void main(String[] args) throws Exception {
        // Generic algorithm:
        // Step1: read all *.yml files and try them as landscape2-formatted file
        // Step2: build common merged model
        // Step2: save common merged model into result yaml file

        // Check all required parameters are set via command-line interface
        CommandLineHelper cmdHelper = new CommandLineHelper(args);
        if (!cmdHelper.areArgumentsSufficient()) {
            System.exit(-1);
        }

        // Instantiate JSON/YAML reader
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Step1: read original landscape.yml file and fetch Categories & Subcategories
        String rootDir = cmdHelper.getValue(CmdLineArgument.PATH_TO_SCAN);
        List<String> collectedYamls = FileUtils.findAllFilesRecursively(rootDir, ".yml");

        if (collectedYamls.isEmpty()) {
            MyLogger.error("No *.yml files were found at " + rootDir);
            System.exit(-1);
        }


        List<TheLandscape> allModels = new ArrayList<>(collectedYamls.size());
        for (String nextFile : collectedYamls) {
            try {
                TheLandscape nextModel = mapper.readValue(new File(nextFile), TheLandscape.class);
                allModels.add(nextModel);
            } catch (Exception ex) {
                MyLogger.warn("File can't be read in landscape2 format. Skipping. Exception is " + ex);
            }
        }

        if (allModels.isEmpty()) {
            MyLogger.error("No valid yml files in landscape2 format is found. Terminating.");
            System.exit(-1);
        }

        // Here we have list of models. We need to merge them into single one and store

        // mapper.writeValue(new File("./testdata/data.yml"), outFileModel);

    }
}
