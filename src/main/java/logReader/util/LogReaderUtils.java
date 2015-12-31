package logReader.util;

import logReader.model.ControllerLog;
import logReader.model.TraceLog;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
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

    public static void centralizeJFrame(JFrame frame) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }

    public static void sortTraceLogs(List<TraceLog> traceLogs) {
        Collections.sort(traceLogs, new Comparator<TraceLog>() {
            @Override
            public int compare(TraceLog o1, TraceLog o2) {
                return o1.getPreciseTime().compareTo(o2.getPreciseTime());
            }
        });
    }

    /**
     * Latest event on top! ( Sorts backward )
     *
     * @param controllerLogs
     */
    public static void sortControllerLogs(List<ControllerLog> controllerLogs) {
        Collections.sort(controllerLogs, new Comparator<ControllerLog>() {
            @Override
            public int compare(ControllerLog o1, ControllerLog o2) {
                return o2.getPreciseTime().compareTo(o1.getPreciseTime());
            }
        });
    }

}
