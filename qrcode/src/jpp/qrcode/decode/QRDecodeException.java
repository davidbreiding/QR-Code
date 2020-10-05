package jpp.qrcode.decode;

public class QRDecodeException extends RuntimeException {
    public QRDecodeException() {
    }

    public QRDecodeException(String message) {
        super(message);
    }

    public QRDecodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public QRDecodeException(Throwable cause) {
        super(cause);
    }

    public QRDecodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
