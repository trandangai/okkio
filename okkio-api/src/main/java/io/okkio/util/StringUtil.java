package io.okkio.util;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class StringUtil extends StringUtils {

    /**
     * Checks if the field isn't null and length of the field is greater than zero not including whitespace.
     *
     * @param str String
     * @return result of checking
     */
    public static boolean isEmpty(String str) {
        boolean res = true;

        if (str != null && str.length() > 0)
            res = false;

        return res;
    }

    /**
     * Checks if the field isn't null and length of the field is greater than zero not including whitespace.
     *
     * @param object the object
     * @return result of checking
     */
    public static String defaultString(Object object) {

        String res = "";
        if (ObjectUtil.isNotEmpty(object))
            res = object.toString();

        return res;
    }

    /**
     * Checks if the field isn't null and length of the field is greater than zero not including whitespace.
     *
     * @param str String
     * @return result of checking
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * Gets ext.
     *
     * @param filename the filename
     * @return ext
     */
    public static String getExt(String filename) {
        int delimiter = filename.lastIndexOf(".");
        if (delimiter == -1) {
            return null;
        }
        return filename.substring(delimiter, filename.length());
    }

    /**
     * saveFile file operation.
     * In the internal,  call uploadService to saveFile file
     *
     * @param file the file
     * @return name file after saveFile
     */
    public static String getNameFile(MultipartFile file) {
        if (file == null) {
            return null;
        }
        String ext = StringUtil.getExt(file.getOriginalFilename());
        if (ext == null) {
            ext = ".jpg";
        }
        String fileName = new Date().getTime() + ext;
        return fileName;
    }
}
