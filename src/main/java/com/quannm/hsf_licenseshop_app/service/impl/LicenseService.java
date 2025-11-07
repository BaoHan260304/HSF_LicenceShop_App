package com.quannm.hsf_licenseshop_app.service.impl;

import com.quannm.hsf_licenseshop_app.dao.WarehouseDAO;
import com.quannm.hsf_licenseshop_app.entity.Product;
import com.quannm.hsf_licenseshop_app.entity.User;
import com.quannm.hsf_licenseshop_app.entity.Warehouse;
import com.quannm.hsf_licenseshop_app.service.ILicenseService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LicenseService implements ILicenseService {

    private final WarehouseDAO warehouseDAO;

    public LicenseService() {
        this.warehouseDAO = new WarehouseDAO();
    }

    @Override
    public List<Warehouse> generateLicenses(Product product, int quantity, User creator) {
        if (product == null || quantity <= 0 || creator == null) {
            return new ArrayList<>(); // Trả về danh sách rỗng nếu đầu vào không hợp lệ
        }

        List<Warehouse> newLicenses = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            // Tạo một mã license duy nhất
            String licenseKey = "HSF-" + product.getUniqueKey() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            Warehouse license = Warehouse.builder()
                    .itemType(Warehouse.ItemType.valueOf(product.getType())) // Lấy loại từ sản phẩm
                    .itemData(licenseKey)
                    .product(product)
                    .shop(product.getShop())
                    .stall(product.getStall())
                    .user(creator) // Người tạo ra license này
                    .createdAt(LocalDateTime.now())
                    .locked(false) // Mặc định là chưa khóa
                    .isDelete(false)
                    .build();
            newLicenses.add(license);
        }

        // Lưu tất cả các license mới vào DB
        // Sử dụng phương thức saveAll đã được tối ưu hóa
        warehouseDAO.saveAll(newLicenses);

        return newLicenses; // Trả về danh sách các license đã tạo
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

        // Trả về kết quả thành công tương ứng với loại license
        if ("KEY_LICENSE_BASIC".equals(license.getProduct().getType())) {
            return ActivationResult.SUCCESS_BASIC;
        } else {
            return ActivationResult.SUCCESS_PREMIUM;
        }
    }
}