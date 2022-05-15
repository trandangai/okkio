package io.okkio.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Response error model.
 */
@Getter
@Setter
public final class ResponseErrorModel {

    private String errorCode;
    private String errorMessage;
    private Integer status;
    private Object data;

    /**
     * ResponseErrorModel
     */
    public ResponseErrorModel(){}

    /**
     * ResponseErrorModel
     *
     * @param errorCode    the error code
     * @param errorMessage the error message
     */
    public ResponseErrorModel(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ResponseErrorModel(String errorCode, String errorMessage, Integer status) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.status = status;
    }
}