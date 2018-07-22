package utils;

import com.rdc.android_test_app_b.Link;

import java.text.SimpleDateFormat;
//класс для генерирования имени файла
public class LinkUtil {
    public static String getDateLink(Link link){
        SimpleDateFormat sdf=new SimpleDateFormat("dd M yyyy hh:mm:ss");
        return sdf.format(link.getDate());
    }
}
