package io.okkio.util;

import io.okkio.common.Constants;

public class ValidationUtil {
    public static boolean validatedStatus(String status) {
        return Constants.ACTIVATED_STATUS.equals(status)
                || Constants.DEACTIVATED_STATUS.equals(status);
    }
}
