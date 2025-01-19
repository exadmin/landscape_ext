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
        String primaryFileName = cmdHelper.getValue(CmdLineArgument.PRIMARY_FILE);
        File primaryFile = new File(primaryFileName);

        TheLandscape primaryModel = null;
        try {
             primaryModel = mapper.readValue(primaryFile, TheLandscape.class);
        } catch (Exception ex) {
            MyLogger.error("Primary model of landscape2 format can't be found/read. Terminating.");
            ex.printStackTrace();
            System.exit(-1);
        }

        String rootDir = cmdHelper.getValue(CmdLineArgument.PATH_TO_SCAN);
        List<String> collectedYamls = FileUtils.findAllFilesRecursively(rootDir, ".yml");

        if (collectedYamls.isEmpty()) {
            MyLogger.error("No additional *.yml files were found at " + rootDir);
        }

        // We should skip primary file which may be collected in PATH_TO_SCAN dir


        List<TheLandscape> allModels = new ArrayList<>(collectedYamls.size());
        for (String nextFileName : collectedYamls) {
            try {
                File secondaryFile = new File(nextFileName);
                if (primaryFile.equals(secondaryFile)) {
                    MyLogger.debug("Skip primary file from list of secondaries");
                    continue;
                }

                MyLogger.debug("Reading additional configuration at " + nextFileName);
                TheLandscape nextModel = mapper.readValue(secondaryFile, TheLandscape.class);

                MyLogger.debug("Merging additional configuration...");
                primaryModel.mergeValuesFrom(nextModel);
                MyLogger.debug("Merged");
            } catch (Exception ex) {
                MyLogger.warn("File can't be read in landscape2 format. Skipping. Exception is " + ex);
                ex.printStackTrace();
            }
        }

        String outputFileName = cmdHelper.getValue(CmdLineArgument.OUTPUT_FILE_NAME);
        MyLogger.debug("Saving result configuration into " + outputFileName);

        mapper.writeValue(new File(outputFileName), primaryModel);
    }
}
