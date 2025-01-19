package com.github.exadmin.utils;

import org.apache.commons.cli.*;

import java.util.HashMap;
import java.util.Map;

import static com.github.exadmin.utils.CmdLineArgument.*;

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
            Option baseFile = Option.builder()
                    .argName("primary landscape configuration file")
                    .hasArg(true)
                    .longOpt(PRIMARY_FILE.toString())
                    .required(true)
                    .desc("The file will be used a a base of future new configuration. All other found valid configurations will be merged into it. No modifications of this file will be done.")
                    .build();

            Option readDir = Option.builder()
                    .argName("path to *.yml")
                    .hasArg(true)
                    .longOpt(PATH_TO_SCAN.toString())
                    .required(true)
                    .desc("Root directory to search *.yml files in landscape2 format with secondary (expanding) configurations")
                    .build();

            Option resultFile = Option.builder()
                    .argName("result file name to create (override)")
                    .hasArg(true)
                    .longOpt(OUTPUT_FILE_NAME.toString())
                    .required(true)
                    .desc("Result configuration will be written in the specified file (override mode is used)")
                    .build();

            options.addOption(baseFile);
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
