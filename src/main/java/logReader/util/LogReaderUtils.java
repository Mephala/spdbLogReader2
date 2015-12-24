package logReader.util;

import logReader.model.ControllerLog;

import java.util.List;

/**
 * Created by GokhanOzgozen on 12/24/2015.
 */
public class LogReaderUtils {

    public static boolean isEmpty(String s){
        return s==null || s.length()==0;
    }


    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static <E> boolean isEmpty(List<E> list) {
        return list==null || list.size()==0;
    }

    public static <E> boolean isNotEmpty(List<E> list) {
        return !isEmpty(list);
    }
}
