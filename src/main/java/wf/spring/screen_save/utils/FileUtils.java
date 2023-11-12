package wf.spring.screen_save.utils;

import jakarta.annotation.Nullable;



public class FileUtils {

    public static String getExtension(@Nullable String fullName) {
        if (fullName == null || fullName.isEmpty()) return "";
        int dotIndex = fullName.lastIndexOf(".");
        return dotIndex == -1 ? "" : fullName.substring(dotIndex + 1);
    }


}
