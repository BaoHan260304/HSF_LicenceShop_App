package com.quannm.hsf_licenseshop_app.service;

import com.quannm.hsf_licenseshop_app.entity.User;

public interface ILicenseService {

    /**
     * Enum để biểu diễn kết quả của việc kích hoạt license.
     */
    enum ActivationResult {
        SUCCESS,
        NOT_FOUND,
        ALREADY_USED,
        INVALID_PRODUCT_TYPE
    }

    /**
     * Kích hoạt một license cho người dùng.
     */
    ActivationResult activateLicense(String licenseKey, User user);
}