package com.quannm.hsf_licenseshop_app.controller;

import com.quannm.hsf_licenseshop_app.entity.User;
import com.quannm.hsf_licenseshop_app.service.ILicenseService;
import com.quannm.hsf_licenseshop_app.service.impl.LicenseService;
import com.quannm.hsf_licenseshop_app.utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UserDashboardController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private TextField licenseField;

    private final ILicenseService licenseService;

    public UserDashboardController() {
        this.licenseService = new LicenseService();
    }

    public void setUserData(User user) {
        welcomeLabel.setText("Chào mừng bạn: " + user.getFullName());
    }

    @FXML
    public void handleActivateLicense(ActionEvent actionEvent) {
        String licenseKey = licenseField.getText().trim();
        if (licenseKey.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Lỗi", "Vui lòng nhập mã license.");
            return;
        }

        User currentUser = SceneManager.getCurrentUser();
        if (currentUser == null) {
            showAlert(Alert.AlertType.ERROR, "Lỗi hệ thống", "Không tìm thấy thông tin người dùng. Vui lòng đăng nhập lại.");
            return;
        }

        ILicenseService.ActivationResult result = licenseService.activateLicense(licenseKey, currentUser);

        switch (result) {
            case SUCCESS:
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Chúc mừng! Bạn đã kích hoạt license thành công.");
                licenseField.clear(); // Xóa trường nhập liệu sau khi thành công
                break;
            case NOT_FOUND:
                showAlert(Alert.AlertType.ERROR, "Thất bại", "Mã license không tồn tại trong hệ thống.");
                break;
            case ALREADY_USED:
                showAlert(Alert.AlertType.WARNING, "Thất bại", "Mã license này đã được sử dụng bởi một người khác.");
                break;
            case INVALID_PRODUCT_TYPE:
                showAlert(Alert.AlertType.WARNING, "Thất bại", "Loại sản phẩm của license này không hợp lệ để kích hoạt.");
                break;
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