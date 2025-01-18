package com.github.exadmin.utils;

public enum CmdLineArgument {
    PATH_TO_SCAN("path-to-scan"),
    RESULT_FILE("result-file-name");

    private final String cmdArgumentName;

    CmdLineArgument(String cmdArgumentName) {
        this.cmdArgumentName = cmdArgumentName;
    }

    @Override
    public String toString() {
        return cmdArgumentName;
    }


}
