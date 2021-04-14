package demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtils {
    public static void log(Object str) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS - ").format(new Date()) + str);
    }
}
