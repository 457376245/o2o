package cn.jh.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request) {
        String verifyCodeExpected= (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        System.out.println(request);
        String verifyCodeActual=HttpServletRequestUtil.getString(request,"verifyCodeActual");
        if (verifyCodeActual==null||!verifyCodeActual.equals(verifyCodeActual)){
            return false;
        }
        return true;
    }

}
