package cn.jh.exception;

public class ProductOperationException extends RuntimeException {
    private static final long serialVersionUID=2361446884822298909L;
    public ProductOperationException(String msg) {
        super(msg);
    }
}
