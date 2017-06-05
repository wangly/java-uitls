// Copyright (C) 2017 Meituan
// All rights reserved

import org.junit.Test;

/**
 * @author wangliyue
 * @version 1.0
 * @created 17/3/5 下午9:12
 **/
public class CodeByte {

    @Test
    public void test() {
        String result = letterChanges("hello*3");
        assert result.equals("Ifmmp*3");
    }

    public String letterChanges(String str) {
        String abc = "abcdefghijklmnopqrstuvwxyz";
        String vowel = "aeiou";
        StringBuilder result = new StringBuilder();
        int index;
        for (int i=0; i<str.length(); i++) {
            if ((index = abc.indexOf(String.valueOf(str.charAt(i)))) != -1) {
                index = index == abc.length() -1 ? 0 : index + 1;
                char newChar = abc.charAt(index);
                int vowelIndex = vowel.indexOf(newChar);
                if(vowelIndex != -1) {
                    newChar -= 32;
                }
                result.append(newChar);
            } else {
                result.append(str.charAt(i));
            }
        }
        return result.toString();
    }
}