package com.quannm.hsf_licenseshop_app.controller;

import com.quannm.hsf_licenseshop_app.entity.User;
import com.quannm.hsf_licenseshop_app.model.UserFX;
import com.quannm.hsf_licenseshop_app.service.IUserService;
import com.quannm.hsf_licenseshop_app.service.impl.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
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

        // Tải dữ liệu từ service và hiển thị lên TableView
        List<UserFX> userList = userService.findAll();
        ObservableList<UserFX> observableUserList = FXCollections.observableArrayList(userList);
        userTableView.setItems(observableUserList);
    }
}