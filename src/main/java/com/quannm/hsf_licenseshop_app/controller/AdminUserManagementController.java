package com.quannm.hsf_licenseshop_app.controller;

import com.quannm.hsf_licenseshop_app.entity.User;
import com.quannm.hsf_licenseshop_app.model.UserFX;
import com.quannm.hsf_licenseshop_app.service.IUserService;
import com.quannm.hsf_licenseshop_app.service.impl.UserService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.quannm.hsf_licenseshop_app.utils.SceneManager;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.List;
import java.util.ResourceBundle;

public class AdminUserManagementController implements Initializable {

    @FXML
    private TableView<UserFX> userTableView;
    @FXML
    private TableColumn<UserFX, Long> idColumn;
    @FXML
    private TableColumn<UserFX, String> usernameColumn;
    @FXML
    private TableColumn<UserFX, String> fullNameColumn;
    @FXML
    private TableColumn<UserFX, String> emailColumn;
    @FXML
    private TableColumn<UserFX, String> phoneColumn;
    @FXML
    private TableColumn<UserFX, User.Role> roleColumn;
    @FXML
    private TableColumn<UserFX, User.Status> statusColumn;
    @FXML
    private Button deactivateButton;
    @FXML
    private Button setSellerButton;
    @FXML
    private Button viewHistoryButton;
    private final IUserService userService;

    public AdminUserManagementController() {
        this.userService = new UserService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Thiết lập cách mỗi cột lấy dữ liệu từ đối tượng UserFX
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Thêm listener để bật/tắt các nút chức năng khi một dòng được chọn
        userTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean isUserSelected = newSelection != null;
            deactivateButton.setDisable(!isUserSelected);
            setSellerButton.setDisable(!isUserSelected);
            viewHistoryButton.setDisable(!isUserSelected);
        });

        loadUsers();
    }

    private void loadUsers() {
        // Tải dữ liệu từ service và hiển thị lên TableView
        List<UserFX> userList = userService.findAll();
        userTableView.setItems(FXCollections.observableArrayList(userList));
    }
    
    @FXML
    private void handleReload() {
        loadUsers();
        showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Đã tải lại danh sách người dùng.");
    }

    @FXML
    private void handleBackButton() {
        try {
            // Lấy lại thông tin admin hiện tại để hiển thị lại màn hình dashboard
            User currentUser = SceneManager.getCurrentUser();
            AdminDashboardController controller = SceneManager.switchScene("admin-dashboard-view.fxml", "Admin Dashboard");
            if (currentUser != null) {
                controller.setUserData(currentUser);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeactivateUser() {
        UserFX selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert(Alert.AlertType.WARNING, "Chưa chọn người dùng", "Vui lòng chọn một người dùng để vô hiệu hóa.");
            return;
        }

        User.Status newStatus = selectedUser.getStatus() == User.Status.ACTIVE ? User.Status.LOCKED : User.Status.ACTIVE;
        String actionText = newStatus == User.Status.LOCKED ? "vô hiệu hóa" : "kích hoạt";

        Optional<ButtonType> result = showConfirmation("Xác nhận", "Bạn có chắc chắn muốn " + actionText + " tài khoản '" + selectedUser.getUsername() + "' không?");

        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = userService.updateUserStatus(selectedUser.getId(), newStatus);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã " + actionText + " tài khoản thành công.");
                loadUsers(); // Tải lại bảng để cập nhật
            } else {
                showAlert(Alert.AlertType.ERROR, "Thất bại", "Có lỗi xảy ra, không thể cập nhật trạng thái người dùng.");
            }
        }
    }

    @FXML
    private void handleSetAsSeller() {
        UserFX selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert(Alert.AlertType.WARNING, "Chưa chọn người dùng", "Vui lòng chọn một người dùng để đặt làm Seller.");
            return;
        }

        if (selectedUser.getRole() == User.Role.SELLER) {
            showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Người dùng này đã là Seller.");
            return;
        }

        Optional<ButtonType> result = showConfirmation("Xác nhận", "Bạn có chắc chắn muốn nâng cấp tài khoản '" + selectedUser.getUsername() + "' thành SELLER không?");

        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = userService.updateUserRole(selectedUser.getId(), User.Role.SELLER);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã nâng cấp tài khoản thành Seller thành công.");
                loadUsers(); // Tải lại bảng để cập nhật
            } else {
                showAlert(Alert.AlertType.ERROR, "Thất bại", "Có lỗi xảy ra, không thể cập nhật vai trò người dùng.");
            }
        }
    }

    @FXML
    private void handleViewHistory() {
        UserFX selectedUser = userTableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert(Alert.AlertType.WARNING, "Chưa chọn người dùng", "Vui lòng chọn một người dùng để xem lịch sử.");
            return;
        }

        try {
            AdminUserHistoryController controller = SceneManager.switchScene("admin-user-history-view.fxml", "Lịch sử mua hàng");
            controller.loadUserHistory(selectedUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private Optional<ButtonType> showConfirmation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert.showAndWait();
    }
}