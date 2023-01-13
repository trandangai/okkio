package io.okkio.common;

public class Constants {
    public static final String APP_ERROR_HEADER = "X-App-Error";
    public static final String MESSAGE_BAD_REQUEST = "Bad request";
    public static final String MESSAGE_NOT_FOUND = "Not found";
    public static final String MESSAGE_USER_IS_EXISTED = "2022001";
    public static final String MESSAGE_PRODUCT_DETAIL_IS_EXISTED = "2022002";
    public static final String MESSAGE_CATEGORY_IS_EXISTED = "Category is existed! ";
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

    // Redis
    public static final String REDIS_REFRESH_TOKEN = "REFRESH_TOKEN_";
    public static final String REDIS_JWT_TOKEN = "REFRESH_JWT_TOKEN_";
    public static final String REDIS_LEFT_MENU = "LEFT_MENU_";
    public static final String REDIS_FILTER_MARKET = "FILTER_MARKET_";
    public static final String REDIS_ROLE = "ROLE_";

    public static final String REDIS_CATEGORY_ID_DAPPS = "REDIS_CATEGORY_ID_DAPPS_";
    public static final String REDIS_ALL_BLOCKCHAIN = "REDIS_ALL_BLOCKCHAIN_";
    public static final String REDIS_TOP_COINS_BY_CATEGORY_ID = "REDIS_TOP_COINS_BY_CATEGORY_ID_";
    public static final String REDIS_COIN_DETAIL_ID = "REDIS_COIN_DETAIL_ID_";
    public static final String REDIS_COIN_DETAIL_ID_WITH_LOW_CACHE = "REDIS_COIN_DETAIL_ID_WITH_LOW_CACHE_";
    public static final String REDIS_COIN_DETAIL_ID_DTO = "REDIS_COIN_DETAIL_ID_DTO_";
    public static final String REDIS_COIN_CATEGORIES_ALL = "REDIS_COIN_CATEGORIES_ALL_";
    public static final String REDIS_TVL_BY_COIN = "REDIS_TVL_BY_COIN_";
    public static final String REDIS_SOCIAL_ID = "REDIS_SOCIAL_ID_";
    public static final String REDIS_TICKER_ID = "REDIS_TICKER_ID_";
    public static final String REDIS_CROWD_SALE_SYMBOL_BY_ID = "REDIS_CROWD_SALE_SYMBOL_BY_ID_";
    public static final String REDIS_CROWD_SALE_SYMBOL = "REDIS_CROWD_SALE_SYMBOL_";
    public static final String REDIS_CATEGORY_DETAIL_WITH_ID = "REDIS_CATEGORY_DETAIL_WITH_ID_";
    public static final String REDIS_CATEGORY_FATHER_ID = "REDIS_CATEGORY_FATHER_ID_";
    public static final String REDIS_ALL_BLOCKCHAINS_ORDER = "REDIS_ALL_BLOCKCHAINS_ORDER_";
    public static final String REDIS_ALL_BLOCKCHAINS_ORDERS = "REDIS_ALL_BLOCKCHAINS_ORDERS_";

    // All coins by category id
    public static final String REDIS_ALL_COINS_BY_CATEGORY_ID = "REDIS_ALL_COINS_BY_CATEGORY_ID_";
    public static final String REDIS_COIN_BY_COIN_ID_IS_CHART = "REDIS_COIN_BY_COIN_ID_IS_CHART_";

    // CoinFullData by FatherId and CategoryId
    public static final String REDIS_COIN_FULL_DATA_CATEGORY_BY_FATHER_ID = "REDIS_COIN_FULL_DATA_CATEGORY_BY_FATHER_ID_";

    public static final String LINE_CHART_IN_COIN_GECKO_IN_7D_CATEGORIES = "https://www.coingecko.com/en/categories/%d/sparkline";
    public static final String LINE_CHART_IN_COIN_GECKO_IN_7D_COINS = "https://www.coingecko.com/coins/%s/sparkline";

    // List Ecosystem BlockChain To Support - 11
    public static final String BINANCE_SMART_BLOCKCHAIN_NAME = "Binance Smart Chain Ecosystem";
    public static final String CATEGORY_BINANCE_SMART_BLOCKCHAIN_ID = "binance-smart-chain";
    public static final String COSMOS_BLOCKCHAIN_NAME = "Cosmos Ecosystem";
    public static final String CATEGORY_COSMOS_BLOCKCHAIN_ID = "cosmos-ecosystem";
    public static final String SOLANA_BLOCKCHAIN_NAME = "Solana Ecosystem";
    public static final String CATEGORY_SOLANA_BLOCKCHAIN_ID = "solana-ecosystem";
    public static final String ARBITRUM_BLOCKCHAIN_NAME = "Arbitrum Ecosystem";
    public static final String CATEGORY_ARBITRUM_BLOCKCHAIN_ID = "arbitrum-ecosystem";
    public static final String POLKADOT_BLOCKCHAIN_NAME = "Polkadot Ecosystem";
    public static final String CATEGORY_POLKADOT_BLOCKCHAIN_ID = "dot-ecosystem";
    public static final String HECO_BLOCKCHAIN_NAME = "HECO Chain Ecosystem";
    public static final String CATEGORY_HECO_BLOCKCHAIN_ID = "heco-chain-ecosystem";
    public static final String AVALANCHE_BLOCKCHAIN_NAME = "Avalanche Ecosystem";
    public static final String CATEGORY_AVALANCHE_BLOCKCHAIN_ID = "avalanche-ecosystem";
    public static final String HARMONY_BLOCKCHAIN_NAME = "Harmony Ecosystem";
    public static final String CATEGORY_HARMONY_BLOCKCHAIN_ID = "harmony-ecosystem";
    public static final String CELO_BLOCKCHAIN_NAME = "Celo Ecosystem";
    public static final String CATEGORY_CELO_BLOCKCHAIN_ID = "celo-ecosystem";
    public static final String FANTOM_BLOCKCHAIN_NAME = "Fantom Ecosystem";
    public static final String CATEGORY_FANTOM_BLOCKCHAIN_ID = "celo-ecosystem";
    public static final String NEAR_BLOCKCHAIN_NAME = "Near Protocol Ecosystem";
    public static final String CATEGORY_NEAR_BLOCKCHAIN_ID = "near-protocol-ecosystem";

    public static final String MESSAGE_GET_USER_SUCCESS = "Get User Success!";
    public static final String MESSAGE_GET_DATA_SUCCESS = "Get Data Success!";
    public static final String MESSAGE_INSERT_USER_SUCCESS = "Insert user Success!";
    public static final String MESSAGE_INSERT_DATA_SUCCESS = "Insert data Success!";
    public static final String MESSAGE_INSERT_DATA_FAILED = "Insert data Failed!";
    public static final String MESSAGE_DELETE_DATA_SUCCESS = "Delete Data Success!";
    public static final String MESSAGE_UPDATED_CATEGORY_SUCCESS = "Updated category to success!";
    public static final String MESSAGE_UPDATED_DATA_SUCCESS = "Updated data to success!";

    public static final String MESSAGE_UPDATED_DATA_FAILED = "Updated data Failed!";

    public static final String MESSAGE_ERROR_WITH_IDS = "Database don't have the ids: ";

    public static final String TYPE_COIN = "Coin";
    public static final String TYPE_TOKEN = "Token";
    public static final String BINANCE_IDENTIFIER_EXCHANGE_NAME = "binance";

    public final static class DATE_TIME_FORMAT {
        public static final String DD_MM_YYYY = "dd-MM-YYYY";
    }
}
