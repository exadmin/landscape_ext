package com.github.exadmin.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static List<String> findAllFilesRecursively(String rootDirName, String ... allowedExt) {
        List<String> result = new ArrayList<>();

        findAllFilesRecursively(result, new File(rootDirName), allowedExt);

        return result;
    }

    private static void findAllFilesRecursively(List<String> collectedFiles, File dirToScan, String ... allowedExt) {
        File[] items = dirToScan.listFiles();
        if (items == null) return;

        for (File item : items) {
            if (item.isDirectory() && item.exists()) {
                findAllFilesRecursively(collectedFiles, item);
            } else {
                String fileName = item.toString();
                for (String ext : allowedExt) {
                    if (fileName.endsWith(ext)) {
                        collectedFiles.add(fileName);
                    }
                }
            }
        }
    }
}
