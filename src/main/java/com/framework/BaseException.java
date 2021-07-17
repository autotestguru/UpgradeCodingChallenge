package com.framework;

/**
 * This class captures exceptions from the framework and used to throw uer-defined exceptions for proper logging.
 */
public class BaseException extends RuntimeException {

    private String exceptionMessage;
    private String exceptionStackTrace;
    private int exceptionCode;
    private Exception exception;
    private Throwable throwable;

    /**
     * This method is used to end execution by throwing exception without any message or stacktrace
     */
    public BaseException() {
        super();
    }
    /**
     * This method is used to end execution by throwing exception with an exception message
     */
    public BaseException(String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;

    }
    /**
     * This method is used to end execution by throwing exception with an exception message and stacktrace
     */
    public BaseException(String exceptionMessage, Exception e) {
        super(exceptionMessage, e);
        this.exceptionMessage = exceptionMessage;
        this.exception = e;
    }
    /**
     * This method is used to end execution by throwing exception with an exception message and another throwable exception
     */
    public BaseException(String exceptionMessage, Throwable e) {
        super(exceptionMessage, e);
        this.exceptionMessage = exceptionMessage;
        this.throwable = e;
    }
    /**
     * This method is used to end execution by throwing exception with an exception message and stacktrace as string
     */
    public BaseException(String exceptionMessage, String exceptionStackTrace) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
        this.exceptionStackTrace = exceptionStackTrace;
    }
    /**
     * This method is used to end execution by throwing exception with an exception message and stacktrace
     */
    public BaseException(String exceptionMessage, String exceptionStackTrace, Throwable exception) {
        super(exceptionMessage, exception);
        this.exceptionMessage = exceptionMessage;
        this.exceptionStackTrace = exceptionStackTrace;
    }
    /**
     * This method is used to end execution by throwing exception with an exception message and stacktrace as string and an exception code
     */
    public BaseException(String exceptionMessage, String exceptionStackTrace, int exceptionCode) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
        this.exceptionStackTrace = exceptionStackTrace;
        this.exceptionCode = exceptionCode;
    }

    /**
     * This method can be used to return the exception message
     * @return String - Exception message
     */
    public String getErrorMessage() {
        return exceptionMessage;
    }

    /**
     * This method can be used to return the exception stacktrace  as a string
     * @return String - Exception stacktrace
     */
    public String getErrorStackTrace() {
        return exceptionStackTrace;
    }

    /**
     * This method is used to set Exception stacktrace
     * @param exceptionStackTrace - Exception stacktrace as string
     */
    public void setErrorStackTrace(String exceptionStackTrace) {
        this.exceptionStackTrace = exceptionStackTrace;
    }

    /**
     * This method is used to get error code from the exception
     * @return error code
     */
    public int getErrorCode() {
        return exceptionCode;
    }

    /**
     * This method is used to set the error code for the exception
     * @param exceptionCode - setting exception code
     */
    public void setErrorCode(int exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    /**
     * This method is used to get the exception thrown by the test
     * @return An instance of exception
     */
    public Exception getException() {
        return exception;
    }

    /**
     * This method is used to set the exception
     * @param exception Object of type Exception for setting the exception
     */
    public void setException(Exception exception) {
        this.exception = exception;
    }

    /**
     * This method return the throwable instance of the exception
     * @return Throwable
     */
    public Throwable getThrowable() {
        return throwable;
    }

    /**
     * This method sets the throwable instance
     * @param throwable exception class that was caught
     */
    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    /**
     * This method is used to get the message set through the exception
     * @return String - exception message
     */
    @Override
    public String getMessage() {
        return this.exceptionMessage;
    }
}