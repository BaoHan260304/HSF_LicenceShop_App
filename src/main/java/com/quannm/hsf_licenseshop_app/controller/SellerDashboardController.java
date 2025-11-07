package com.quannm.hsf_licenseshop_app.controller;

import com.quannm.hsf_licenseshop_app.entity.Product;
import com.quannm.hsf_licenseshop_app.entity.Warehouse;
import com.quannm.hsf_licenseshop_app.entity.User;
import com.quannm.hsf_licenseshop_app.service.ILicenseService;
import com.quannm.hsf_licenseshop_app.service.IProductService;
import com.quannm.hsf_licenseshop_app.service.impl.LicenseService;
import com.quannm.hsf_licenseshop_app.service.impl.ProductService;
import com.quannm.hsf_licenseshop_app.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class SellerDashboardController {
    @FXML
    private Label welcomeLabel;
    @FXML
    private Button copyKeyButton;
    private User currentUser;

    private final IProductService productService = new ProductService();
    private final ILicenseService licenseService = new LicenseService();
    private String lastGeneratedKey;


    public void setUserData(User user) {
        this.currentUser = user;
        welcomeLabel.setText("Chào mừng Người bán: " + user.getFullName());
    }

    @FXML
    private void handleManageProducts() {
        try {
            SellerProductManagementController controller = SceneManager.switchScene("seller-product-management-view.fxml", "Quản Lý Sản Phẩm");
            controller.setUserData(currentUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreateTestKey() {
        // ID 1 là sản phẩm mặc định chúng ta đã tạo bằng script SQL
        Optional<Product> testProductOpt = productService.findById(1L);

        if (testProductOpt.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không tìm thấy sản phẩm mặc định (ID=1) để tạo key. Vui lòng chạy script SQL để tạo.");
            return;
        }

        List<Warehouse> generatedLicenses = licenseService.generateLicenses(testProductOpt.get(), 1, currentUser);

        if (generatedLicenses.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tạo key test. Vui lòng thử lại.");
        } else {
            lastGeneratedKey = generatedLicenses.get(0).getItemData();
            copyKeyButton.setDisable(false); // Bật nút copy
            showAlert(Alert.AlertType.INFORMATION, "Tạo Key Thành Công", "Key test của bạn là: " + lastGeneratedKey);
        }
    }

    @FXML
    private void handleCopyTestKey() {
        if (lastGeneratedKey != null && !lastGeneratedKey.isEmpty()) {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(lastGeneratedKey);
            clipboard.setContent(content);
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã sao chép key vào clipboard!");
        } else {
            showAlert(Alert.AlertType.WARNING, "Thông báo", "Chưa có key nào được tạo để sao chép.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}