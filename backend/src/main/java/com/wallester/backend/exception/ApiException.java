package com.wallester.backend.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApiException extends RuntimeException {
    private static final long serialVersionUID = -1L;
    private int status;

    public ApiException(String message, Throwable cause, int status) {
        super(message, cause);
        this.status = status;
    }

    public ApiException(String message, int status) {
        super(message);
        this.status = status;
    }

    /**
     * Returns ApiException with internal server error symptom (http code 500)
     *
     * @param   message         custom error message
     * @return  ApiException
     */
    public static ApiException newInternalError(String message) {
        return new ApiException(message, 500);
    }

    /**
     * Returns ApiException with bad request symptom (http code 400)
     *
     * @return  ApiException
     */
    public static ApiException badRequestError() {
        return new ApiException("Bad request, check your data", 400);
    }

    /**
     * Returns ApiException with bad request symptom (http code 400) and custom message
     *
     * @param   message         custom error message
     * @return  ApiException
     */
    public static ApiException badRequestError(String message) {
        return new ApiException(message, 400);
    }

    /**
     * Returns ApiException with bad request symptom (http code 400) and custom message
     *
     * @param   message         custom error message
     * @param   cause           cause exception
     * @return  ApiException
     */
    public static ApiException badRequestError(String message, Throwable cause) {
        return new ApiException(message, cause, 400);
    }
}
