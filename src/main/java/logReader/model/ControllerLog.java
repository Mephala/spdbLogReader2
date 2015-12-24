package logReader.model;

import java.util.Date;

/**
 * Created by GokhanOzgozen on 12/24/2015.
 */
public class ControllerLog {

    private Date logDate;
    private String method;
    private Long executiontime;
    private String className;
    private String parameter;
    private String retval;
    private String customLog;
    private String exception;

    @Override
    public String toString() {
        return "ControllerLog{" +
                "logDate=" + logDate +
                ", method='" + method + '\'' +
                ", executiontime=" + executiontime +
                ", className='" + className + '\'' +
                ", parameter='" + parameter + '\'' +
                ", retval='" + retval + '\'' +
                ", customLog='" + customLog + '\'' +
                ", exception='" + exception + '\'' +
                '}';
    }

    public ControllerLog(){

    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Long getExecutiontime() {
        return executiontime;
    }

    public void setExecutiontime(Long executiontime) {
        this.executiontime = executiontime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getRetval() {
        return retval;
    }

    public void setRetval(String retval) {
        this.retval = retval;
    }

    public String getCustomLog() {
        return customLog;
    }

    public void setCustomLog(String customLog) {
        this.customLog = customLog;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
