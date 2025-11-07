package com.quannm.hsf_licenseshop_app.service.impl;

import com.quannm.hsf_licenseshop_app.dao.WarehouseDAO;
import com.quannm.hsf_licenseshop_app.entity.User;
import com.quannm.hsf_licenseshop_app.entity.Warehouse;
import com.quannm.hsf_licenseshop_app.service.ILicenseService;

import java.time.LocalDateTime;
import java.util.Optional;

public class LicenseService implements ILicenseService {

    private final WarehouseDAO warehouseDAO;

    public LicenseService() {
        this.warehouseDAO = new WarehouseDAO();
    }

    @Override
    public ActivationResult activateLicense(String licenseKey, User currentUser) {
        Optional<Warehouse> warehouseOptional = warehouseDAO.findByItemData(licenseKey);

        // 1. Kiểm tra xem license có tồn tại không
        if (warehouseOptional.isEmpty()) {
            return ActivationResult.NOT_FOUND;
        }

        Warehouse license = warehouseOptional.get();

        // 2. Kiểm tra xem license đã được sử dụng (bị khóa) chưa
        if (license.getLocked()) {
            return ActivationResult.ALREADY_USED;
        }

        // 3. (Ví dụ) Kiểm tra xem loại sản phẩm có phải là loại có thể kích hoạt không
        if (!"KEY_LICENSE_BASIC".equals(license.getProduct().getType()) && !"KEY_LICENSE_PREMIUM".equals(license.getProduct().getType())) {
            return ActivationResult.INVALID_PRODUCT_TYPE;
        }

        // 4. Kích hoạt thành công: Cập nhật thông tin license
        license.setLocked(true);
        license.setUser(currentUser); // Gán license cho người dùng hiện tại
        license.setLockedAt(LocalDateTime.now());
        warehouseDAO.save(license);

        return ActivationResult.SUCCESS;
    }
}