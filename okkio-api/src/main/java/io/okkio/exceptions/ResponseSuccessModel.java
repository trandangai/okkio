package io.okkio.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseSuccessModel {
    private String successCode;
    private String messageCode;
    private Integer status;
    private Object data;

    /**
     * Constructor
     */
    public ResponseSuccessModel(){}
}
