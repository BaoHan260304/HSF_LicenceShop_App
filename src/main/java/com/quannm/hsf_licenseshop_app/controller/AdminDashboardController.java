package com.quannm.hsf_licenseshop_app.controller;

import com.quannm.hsf_licenseshop_app.entity.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminDashboardController {

    @FXML
    private Label welcomeLabel;

    public void setUserData(User user) {
        welcomeLabel.setText("Chào mừng Admin: " + user.getFullName());
    }
}