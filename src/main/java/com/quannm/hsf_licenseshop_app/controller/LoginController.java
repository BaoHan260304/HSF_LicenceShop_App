package com.quannm.hsf_licenseshop_app.controller;

import com.quannm.hsf_licenseshop_app.entity.User;
import com.quannm.hsf_licenseshop_app.service.IUserService;
import com.quannm.hsf_licenseshop_app.service.impl.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
            errorMessageLabel.setText("Đăng nhập thành công! Chào mừng " + user.getFullName() + " (" + user.getRole() + ")");
            // TODO: Chuyển sang màn hình chính của ứng dụng dựa trên vai trò của người dùng
            System.out.println("User logged in: " + user.getUsername() + " with role: " + user.getRole());
            // Ví dụ: Load một màn hình khác
            // Parent root = FXMLLoader.load(getClass().getResource("/com/quannm/hsf_licenseshop_app/fxml/main-app-view.fxml"));
            // Scene scene = new Scene(root);
            // Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            // stage.setScene(scene);
            // stage.show();
        } else {
            errorMessageLabel.setText("Tên đăng nhập hoặc mật khẩu không đúng.");
        }
    }
}