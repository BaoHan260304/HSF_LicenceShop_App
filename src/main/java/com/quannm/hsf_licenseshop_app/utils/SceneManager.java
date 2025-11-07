package com.quannm.hsf_licenseshop_app.utils;

import com.quannm.hsf_licenseshop_app.HelloApplication;
import com.quannm.hsf_licenseshop_app.entity.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static <T> T switchScene(String fxmlFile, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/" + fxmlFile));
        Parent root = fxmlLoader.load();

        // Nếu cửa sổ đã có Scene, chỉ cần thay đổi nội dung (root)
        if (primaryStage.getScene() != null) {
            primaryStage.getScene().setRoot(root);
        } else {
            // Nếu chưa có, tạo Scene mới
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        }

        primaryStage.setTitle(title);
        primaryStage.centerOnScreen(); // Đưa cửa sổ ra giữa màn hình

        // Trả về controller của màn hình mới để có thể truyền dữ liệu
        return fxmlLoader.getController();
    }

    public static void showLoginScreen() throws IOException {
        switchScene("login-view.fxml", "HSF License Shop - Đăng nhập");
    }

    public static User getCurrentUser() {
        // TODO: Implement a session manager to get the current user
        return null;
    }
}