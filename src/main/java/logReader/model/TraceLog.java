package logReader.model;

import java.util.Date;

/**
 * Created by GokhanOzgozen on 12/29/2015.
 */
public class TraceLog {

    private String className;
    private String method;
    private Long executionTime;
    private Long threadId;
    private String retval;
    private String parameter;
    private Date logTime;
    private Long preciseTime;
    private String exception;

    public TraceLog() {
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "TraceLog{" +
                "className='" + className + '\'' +
                ", method='" + method + '\'' +
                ", executionTime=" + executionTime +
                ", threadId=" + threadId +
                ", retval='" + retval + '\'' +
                ", parameter='" + parameter + '\'' +
                ", logTime=" + logTime +
                '}';
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Long executionTime) {
        this.executionTime = executionTime;
    }

    public Long getThreadId() {
        return threadId;
    }

    public void setThreadId(Long threadId) {
        this.threadId = threadId;
    }

    public String getRetval() {
        return retval;
    }

    public void setRetval(String retval) {
        this.retval = retval;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public Long getPreciseTime() {
        return preciseTime;
    }

    public void setPreciseTime(Long preciseTime) {
        this.preciseTime = preciseTime;
    }

}
