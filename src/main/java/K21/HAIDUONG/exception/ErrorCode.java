package K21.HAIDUONG.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND(1001, "User not found", HttpStatus.NOT_FOUND),
    USERNAME_ALREADY_EXISTS(1002, "Username already exists", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS(1003, "Email already exists", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_FOUND(1004,"Email not found", HttpStatus.NOT_FOUND),
    UNAUTHORIZED(1005,"You have not permission" , HttpStatus.FORBIDDEN),
    UNAUTHENTICATED(1006,"You have not authenticated" , HttpStatus.UNAUTHORIZED),;


    private int code;
    private String message;
    private HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
