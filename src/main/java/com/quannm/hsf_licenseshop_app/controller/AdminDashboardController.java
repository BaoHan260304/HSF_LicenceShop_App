package com.quannm.hsf_licenseshop_app.controller;

import com.quannm.hsf_licenseshop_app.entity.User;
import com.quannm.hsf_licenseshop_app.utils.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;


public class AdminDashboardController {

    @FXML
    private Label welcomeLabel;

    public void setUserData(User user) {
        welcomeLabel.setText("Chào mừng Admin: " + user.getFullName());
    }

    @FXML
    private void handleManageUsers() {
        try {
            SceneManager.switchScene("admin-user-management-view.fxml", "Quản Lý Người Dùng");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}