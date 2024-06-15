package Utils;

/**
 * 自定义异常
 */
public class MyException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MyException() {
        super();
    }
    public MyException(String message) {
        super();
        this.message = message;
    }
}
