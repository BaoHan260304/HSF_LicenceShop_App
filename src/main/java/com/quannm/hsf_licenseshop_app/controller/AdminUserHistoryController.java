package com.quannm.hsf_licenseshop_app.controller;

import com.quannm.hsf_licenseshop_app.entity.Order;
import com.quannm.hsf_licenseshop_app.entity.OrderItem;
import com.quannm.hsf_licenseshop_app.model.UserFX;
import com.quannm.hsf_licenseshop_app.service.IOrderService;
import com.quannm.hsf_licenseshop_app.service.impl.OrderService;
import com.quannm.hsf_licenseshop_app.utils.SceneManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AdminUserHistoryController {

    @FXML
    private Label titleLabel;
    @FXML
    private TableView<OrderItem> orderTableView;
    @FXML
    private TableColumn<OrderItem, Long> orderIdColumn;
    @FXML
    private TableColumn<OrderItem, String> productNameColumn;
    @FXML
    private TableColumn<OrderItem, String> licenseKeyColumn;
    @FXML
    private TableColumn<OrderItem, LocalDateTime> orderDateColumn;
    @FXML
    private TableColumn<OrderItem, BigDecimal> totalAmountColumn;
    @FXML
    private TableColumn<OrderItem, Order.Status> statusColumn;

    private final IOrderService orderService;

    public AdminUserHistoryController() {
        this.orderService = new OrderService();
    }

    public void loadUserHistory(UserFX user) {
        titleLabel.setText("Lịch sử mua hàng của: " + user.getUsername());

        // Thiết lập các cột
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        productNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        licenseKeyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWarehouse().getItemData()));
        orderDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getOrder().getCreatedAt()));
        totalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        statusColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getOrder().getStatus()));

        // Lấy dữ liệu
        List<Order> orders = orderService.findByBuyerId(user.getId());

        // Vì mỗi đơn hàng có thể có nhiều item, ta sẽ "làm phẳng" danh sách
        List<OrderItem> allOrderItems = orders.stream()
                .flatMap(order -> order.getOrderItems().stream())
                .collect(Collectors.toList());

        orderTableView.setItems(FXCollections.observableArrayList(allOrderItems));
    }

    @FXML
    private void handleBackButton() {
        try {
            SceneManager.switchScene("admin-user-management-view.fxml", "Quản Lý Người Dùng");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}