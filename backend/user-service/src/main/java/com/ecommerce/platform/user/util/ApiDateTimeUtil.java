package com.ecommerce.platform.user.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class ApiDateTimeUtil {
    public static LocalDateTime currentDateTime() {
        return LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
    }

}
