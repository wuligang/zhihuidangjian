package org.jeecg.modules.party_building.util;

/**
 * @program: jeecg-boot-parent
 * @description: 密码格式验证
 * @author: wangqian
 * @create: 2020-08-05 16:55
 */
public class PwdVerificationUtil {
    public static boolean isLetterDigit(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        boolean isLength = false;//定义一个boolean值，用来表示字符串长度是否在某个范围
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        if (str.length() >= 8 && str.length() <= 16) {
            isLength = true;
        }
        boolean isRight = isDigit && isLetter && isLength;
        return isRight;
    }
}
