package com.quannm.hsf_licenseshop_app;

import com.quannm.hsf_licenseshop_app.utils.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            // Giao Stage chính cho SceneManager quản lý
            SceneManager.setPrimaryStage(stage);
            // Cài đặt để cửa sổ không thể thay đổi kích thước
            stage.setResizable(true);

            // Hiển thị màn hình đăng nhập đầu tiên
            SceneManager.showLoginScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}