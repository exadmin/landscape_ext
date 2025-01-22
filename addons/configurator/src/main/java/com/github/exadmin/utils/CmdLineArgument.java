package com.github.exadmin.utils;

public enum CmdLineArgument {
    PATH_TO_SCAN("path-to-scan"),
    OUTPUT_FILE_NAME("output-file-name"),
    PRIMARY_FILE("primary-file");

    private final String cmdArgumentName;

    CmdLineArgument(String cmdArgumentName) {
        this.cmdArgumentName = cmdArgumentName;
    }

    @Override
    public String toString() {
        return cmdArgumentName;
    }


}
