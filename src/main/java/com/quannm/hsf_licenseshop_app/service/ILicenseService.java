package com.quannm.hsf_licenseshop_app.service;

import com.quannm.hsf_licenseshop_app.entity.Warehouse;
import com.quannm.hsf_licenseshop_app.entity.User;

import java.util.List;

public interface ILicenseService {
    List<Warehouse> generateLicenses(com.quannm.hsf_licenseshop_app.entity.Product product, int quantity, User creator);

    /**
     * Enum để biểu diễn kết quả của việc kích hoạt license.
     */
    enum ActivationResult {
        SUCCESS,
        SUCCESS_BASIC,
        SUCCESS_PREMIUM,
        NOT_FOUND,
        ALREADY_USED,
        INVALID_PRODUCT_TYPE
    }

    /**
     * Kích hoạt một license cho người dùng.
     */
    ActivationResult activateLicense(String licenseKey, User user);
}