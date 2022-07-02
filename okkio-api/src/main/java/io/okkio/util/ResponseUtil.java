package io.okkio.util;

import io.okkio.common.Constants;
import io.okkio.exceptions.EntityNotFoundException;
import io.okkio.exceptions.ResponseErrorModel;
import io.okkio.exceptions.ResponseSuccessModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ResponseUtil {
    public static <T> HttpHeaders errorHeaders(String message) {
        try {
            message = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
        } catch (Exception ex) {
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.APP_ERROR_HEADER, message);
        return headers;
    }

    private static ResponseErrorModel responseError(String code, String message, Integer status) {
        ResponseErrorModel responseErrorModel = new ResponseErrorModel();
        responseErrorModel.setErrorCode(code);
        responseErrorModel.setErrorMessage(message);
        responseErrorModel.setStatus(status);
        return responseErrorModel;
    }

    private static ResponseSuccessModel responseSuccess(String code, String message, Integer status, Object data) {
        ResponseSuccessModel responseSuccessModel = new ResponseSuccessModel();
        responseSuccessModel.setSuccessCode(code);
        responseSuccessModel.setMessageCode(message);
        responseSuccessModel.setStatus(status);
        responseSuccessModel.setData(data);
        return responseSuccessModel;

    }
    public static <T> ResponseEntity<T> ok(String message, Object data) {
        return new ResponseEntity<>((T) responseSuccess(HttpStatus.OK.name(),message, HttpStatus.OK.value(), data), HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> badRequest(String message) {
        return new ResponseEntity<>((T) responseError(HttpStatus.BAD_REQUEST.name(),message, HttpStatus.BAD_REQUEST.value()), HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> notFound(String message) {
        return new ResponseEntity<>((T) responseError(HttpStatus.NOT_FOUND.name(),message, HttpStatus.NOT_FOUND.value()), HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> internalServerError(String message) {
        return new ResponseEntity<>((T) responseError(HttpStatus.INTERNAL_SERVER_ERROR.name(),message, HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static <T> ResponseEntity<T> unsupportedMediaType(String message) {
        return new ResponseEntity<>((T) responseError(HttpStatus.UNSUPPORTED_MEDIA_TYPE.name(),message, HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    public static <T> ResponseEntity<T> forbidden(String message, Object data) {
        return new ResponseEntity<>((T) responseSuccess(HttpStatus.FORBIDDEN.name(),message, HttpStatus.FORBIDDEN.value(), data), HttpStatus.FORBIDDEN);
    }

    public static <T> ResponseEntity<T> error(Exception ex) {
        if (ex instanceof EntityNotFoundException) {
            return new ResponseEntity<>((T) responseError(HttpStatus.NOT_FOUND.name(),ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>((T) responseError(HttpStatus.INTERNAL_SERVER_ERROR.name(),ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
