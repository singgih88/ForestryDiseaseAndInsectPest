package com.jecyhw.core.util;

/**
 * Created by jecyhw on 16-12-5.
 */
final public class UrlUtils {
    static final public String buildUrl(String first, String ...more) {
        StringBuilder stringBuilder = new StringBuilder(first);
        for (String s : more) {
            if (more.length > 0) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append("/");
                }
                stringBuilder.append(s);
            }
        }
        return stringBuilder.toString();
    }
}
