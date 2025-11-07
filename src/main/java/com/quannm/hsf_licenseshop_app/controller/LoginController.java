package com.quannm.hsf_licenseshop_app.controller;

import com.quannm.hsf_licenseshop_app.entity.User;
import com.quannm.hsf_licenseshop_app.service.IUserService;
import com.quannm.hsf_licenseshop_app.service.impl.UserService;
import com.quannm.hsf_licenseshop_app.utils.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Optional;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessageLabel;

    private final IUserService userService;

    public LoginController() {
        // Khởi tạo UserService
        this.userService = new UserService();
    }

    @FXML
    private void handleLoginButton(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorMessageLabel.setText("Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
            return;
        }

        Optional<User> loggedInUser = userService.login(username, password);

        if (loggedInUser.isPresent()) {
            User user = loggedInUser.get();
            // Lưu người dùng hiện tại vào "session"
            SceneManager.setCurrentUser(user);

            // Đăng nhập thành công, chuyển màn hình dựa trên vai trò
            try {
                switch (user.getRole()) {
                    case ADMIN:
                        AdminDashboardController adminController = SceneManager.switchScene("admin-dashboard-view.fxml", "Admin Dashboard");
                        adminController.setUserData(user);
                        break;
                    case USER:
                        UserDashboardController userController = SceneManager.switchScene("user-dashboard-view.fxml", "User Dashboard");
                        userController.setUserData(user);
                        break;
                    case SELLER:
                        // TODO: Tạo màn hình cho Seller
                        errorMessageLabel.setText("Chức năng cho Seller đang được phát triển.");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                errorMessageLabel.setText("Lỗi: Không thể tải màn hình tiếp theo.");
            }
        } else {
            errorMessageLabel.setText("Tên đăng nhập hoặc mật khẩu không đúng.");
        }
    }
}