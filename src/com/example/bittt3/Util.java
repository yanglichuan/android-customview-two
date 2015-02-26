
package com.example.bittt3;

import java.util.Comparator;

import android.util.Log;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Util {
    /**
     * @param inputString
     * @return
     */
    public static String getPingYin(String inputString) {
        if (inputString == null) {
            return null;
        }
        char[] charArray = inputString.toCharArray();
        if (charArray == null) {
            return null;
        }

        HanyuPinyinOutputFormat hanyuPinyin = new HanyuPinyinOutputFormat();
        hanyuPinyin.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        hanyuPinyin.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        hanyuPinyin.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        StringBuilder sb = new StringBuilder();
        try {
            // 是否在汉字范围内
            for (char hanzi : charArray) {
                if (hanzi >= 0x4e00 && hanzi <= 0x9fa5) {
                    String[] sss = PinyinHelper.toHanyuPinyinStringArray(hanzi, hanyuPinyin);
                    sb.append(sss[0]);
                }
                else {
                    sb.append(new String(new char[] {
                            hanzi
                    }));
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        //
        return sb.toString();
    }

    public static Comparator<String> getComparator() {
        return new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return -Util.getPingYin(rhs).compareToIgnoreCase(Util.getPingYin(lhs));
            }
        };
    }
}
