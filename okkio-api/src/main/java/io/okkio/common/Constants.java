package io.okkio.common;

public class Constants {
    public static final String APP_ERROR_HEADER = "X-App-Error";
    public static final String MESSAGE_BAD_REQUEST = "Bad request";
    public static final String MESSAGE_NOT_FOUND = "Not found";
    public static final String MESSAGE_USER_IS_EXISTED = "2022001";
    public static final String MESSAGE_PRODUCT_DETAIL_IS_EXISTED = "2022002";
    public static final String MESSAGE_PRODUCT_IS_NOT_EXISTS = "2022003";
    public static final String MESSAGE_CATEGORY_IS_EXISTED = "2022004";
    public static final String MESSAGE_CATEGORY_IS_NOT_EXISTED = "Category is not existed! ";
    public static final String MESSAGE_DATA_IS_NOT_EXISTED = "Data is not existed! ";
    public static final String MESSAGE_ORDER_IS_NOT_EXISTED = "Order is not existed! ";
    public static final String MESSAGE_USER_OR_ORDER_IS_NOT_EXISTED = "User or order is not existed! ";
    public static final String MESSAGE_EMAIL_IS_NOT_EXISTED = "Email is not existed! ";
    public static final String MESSAGE_USER_IS_NOT_EXISTED = "User is not existed or password is wrong";
    public static final String MESSAGE_USER_WRONG_PASSWORD = "Password was wrong or was not match current password";
    public static final String TOKEN_TYPE = "Bearer";
    public static final String ACTIVATED_STATUS = "ACTIVATED";
    public static final String DEACTIVATED_STATUS = "DEACTIVATED";
    public static final String WAITING_STATUS = "WAITING";
    public static final String RECEIPT_PAID_STATUS = "PAID";
    public static final String RECEIPT_ISSUED_STATUS = "ISSUED";
    public static final String MESSAGE_LOGIN_SUCCESS = "Login Success!";
    public static final String MESSAGE_REFRESH_TOKEN_SUCCESS = "Refresh Token Success!";
    public static final String MESSAGE_REGISTER_SUCCESS = "Register Success!";
    public static final String MESSAGE_PROFILE_SUCCESS = "Get Profile To Success!";
    public static final String MESSAGE_TOKEN_EXPIRED = "Token is expired!";
    public static final String MESSAGE_TOKEN_NOT_EXISTED = "Token is not existed!";
    public static final String MESSAGE_SEND_EMAIL_SUCCESS = "SENT EMAIL TO SUCCESS!";
    public static final String MESSAGE_SEND_EMAIL_FAILED = "SENT EMAIL TO FAILED!";
    public static final String PAYMENT_METHOD_COD = "cod";
    public static final String PAYMENT_METHOD_VISA = "visa";
    public static final String PAYMENT_METHOD_ATM = "atm";
    public static final String PAYMENT_METHOD_MOMO = "momo";
    public static final String MESSAGE_PAYMENT_METHOD_ERROR_CODE = "Invalid payment method! ";
    public static final String DELIVERY_METHOD_TLH = "tlh";
    public static final String DELIVERY_METHOD_NT = "nt";
    public static final String DELIVERY_METHOD_TQ = "tq";
    public static final String MESSAGE_DELIVERY_METHOD_ERROR_CODE = "Invalid delivery method! ";
    public static final String MESSAGE_ORDER_ISSUED = "Have Order Issued! ";

    // Redis
    public static final String REDIS_REFRESH_TOKEN = "REFRESH_TOKEN_";
    public static final String REDIS_JWT_TOKEN = "REFRESH_JWT_TOKEN_";
    public static final String REDIS_LEFT_MENU = "LEFT_MENU_";
    public static final String REDIS_FILTER_MARKET = "FILTER_MARKET_";
    public static final String REDIS_ROLE = "ROLE_";

    public static final String MESSAGE_GET_USER_SUCCESS = "Get User Success!";
    public static final String MESSAGE_GET_DATA_SUCCESS = "Get Data Success!";
    public static final String MESSAGE_INSERT_USER_SUCCESS = "Insert user Success!";
    public static final String MESSAGE_INSERT_DATA_SUCCESS = "Insert data Success!";
    public static final String MESSAGE_INSERT_DATA_FAILED = "Insert data Failed!";
    public static final String MESSAGE_DELETE_DATA_SUCCESS = "Delete Data Success!";
    public static final String MESSAGE_UPDATED_CATEGORY_SUCCESS = "Updated category to success!";
    public static final String MESSAGE_UPDATED_DATA_SUCCESS = "Updated data to success!";

    public static final String MESSAGE_UPDATED_DATA_FAILED = "Updated data Failed!";
}
