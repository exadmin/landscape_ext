package com.github.exadmin.utils;

import org.apache.commons.cli.*;

import java.util.HashMap;
import java.util.Map;

import static com.github.exadmin.utils.CmdLineArgument.PATH_TO_SCAN;
import static com.github.exadmin.utils.CmdLineArgument.RESULT_FILE;

public class CommandLineHelper {
    private String[] args;
    private Map<CmdLineArgument, String> argValuesMap = new HashMap<>();

    public CommandLineHelper(String[] args) {
        this.args = args;
    }

    public boolean areArgumentsSufficient() {
        CommandLineParser clParser = new DefaultParser();
        Options options = new Options();
        {
            Option readDir = Option.builder()
                    .argName("path to *.yml")
                    .hasArg(true)
                    .longOpt(PATH_TO_SCAN.toString())
                    .required(true)
                    .desc("Path to *.yml files in landscape2 format to build common configuration")
                    .build();

            Option resultFile = Option.builder()
                    .argName("output yml file name")
                    .hasArg(true)
                    .longOpt(RESULT_FILE.toString())
                    .required(true)
                    .desc("Resulted configuration will be created in the specified file (in override mode if file exists)")
                    .build();

            options.addOption(readDir);
            options.addOption(resultFile);
        }

        boolean isErrorExist = false;

        try {
            CommandLine cmdLine = clParser.parse(options, args);
            argValuesMap.put(PATH_TO_SCAN, cmdLine.getOptionValue(PATH_TO_SCAN.toString()));
        } catch (Exception ex) {
            MyLogger.error(ex.toString());
            isErrorExist = true;
        }

        if (isErrorExist) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar FinalConfigurationBuilderApp.jar", options);

            return false;
        }

        return true;

    }

    public String getValue(CmdLineArgument argName) {
        return argValuesMap.get(argName);
    }
}
