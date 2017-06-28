package com.lilian.utils;

import org.slf4j.helpers.MessageFormatter;

public class StringSpliceUtils {
    public StringSpliceUtils() {
    }

    public static String splice(String pattern, Object... arr) {
        return MessageFormatter.arrayFormat(pattern, arr).getMessage();
    }
}
