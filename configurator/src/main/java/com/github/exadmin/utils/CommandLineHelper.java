package com.github.exadmin.utils;

import org.apache.commons.cli.*;

import static com.github.exadmin.utils.CmdLineArgument.*;

public class CommandLineHelper {
    private String[] args;
    private CommandLine cmdLine;

    public CommandLineHelper(String[] args) {
        this.args = args;
    }

    public boolean areArgumentsSufficient() {
        CommandLineParser clParser = new DefaultParser();
        Options options = new Options();
        {
            Option primFile = Option.builder()
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

            options.addOption(primFile);
            options.addOption(readDir);
            options.addOption(resultFile);
        }

        boolean isErrorExist = false;

        try {
            this.cmdLine = clParser.parse(options, args);
        } catch (Exception ex) {
            ex.printStackTrace();
            isErrorExist = true;
        }

        if (isErrorExist) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar configurator.jar", options);

            return false;
        }

        return true;

    }

    public String getValue(CmdLineArgument argName) {
        return cmdLine.getOptionValue(argName.toString());
    }
}
