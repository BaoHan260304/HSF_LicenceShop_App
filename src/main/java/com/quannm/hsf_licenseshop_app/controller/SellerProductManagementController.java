package com.quannm.hsf_licenseshop_app.controller;

import com.quannm.hsf_licenseshop_app.entity.Product;
import com.quannm.hsf_licenseshop_app.entity.Shop;
import com.quannm.hsf_licenseshop_app.entity.Warehouse;
import com.quannm.hsf_licenseshop_app.entity.User;
import com.quannm.hsf_licenseshop_app.model.ProductFX;
import com.quannm.hsf_licenseshop_app.service.ILicenseService;
import com.quannm.hsf_licenseshop_app.service.IProductService;
import com.quannm.hsf_licenseshop_app.service.impl.LicenseService;
import com.quannm.hsf_licenseshop_app.service.impl.ProductService;
import com.quannm.hsf_licenseshop_app.utils.SceneManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SellerProductManagementController implements Initializable {

    @FXML
    private TableView<ProductFX> productTableView;
    @FXML
    private TableColumn<ProductFX, Long> idColumn;
    @FXML
    private TableColumn<ProductFX, String> nameColumn;
    @FXML
    private TableColumn<ProductFX, String> typeColumn;
    @FXML
    private TableColumn<ProductFX, BigDecimal> priceColumn;
    @FXML
    private TableColumn<ProductFX, String> statusColumn;
    @FXML
    private Spinner<Integer> quantitySpinner; // Có thể xóa nếu không dùng nữa
    @FXML
    private Button generateButton; // Sẽ bị xóa khỏi FXML

    private final IProductService productService;
    private final ILicenseService licenseService;
    private User currentUser;

    public SellerProductManagementController() {
        this.productService = new ProductService();
        this.licenseService = new LicenseService();
    }

    public void setUserData(User user) {
        this.currentUser = user;
        loadProducts();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Chuyển đổi enum Status thành String để hiển thị
        statusColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatus().toString()));

        // Không cần listener này nữa vì nút generateButton sẽ bị xóa
    }

    private void loadProducts() {
        if (currentUser != null) {
            List<ProductFX> productList = productService.findBySeller(currentUser);
            productTableView.setItems(FXCollections.observableArrayList(productList));
        }
    }

    @FXML
    private void handleCreateNewProduct() {
        // Tạo một dialog để nhập thông tin sản phẩm mới
        Dialog<Product> dialog = new Dialog<>();
        dialog.setTitle("Tạo Sản Phẩm Mới");
        dialog.setHeaderText("Nhập thông tin chi tiết cho sản phẩm mới.");

        // Thêm các nút
        ButtonType createButtonType = new ButtonType("Tạo", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        // Tạo layout cho form
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nameField = new TextField();
        nameField.setPromptText("Tên sản phẩm");
        TextField priceField = new TextField();
        priceField.setPromptText("Giá sản phẩm");
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("KEY_LICENSE_BASIC", "KEY_LICENSE_PREMIUM");
        typeComboBox.getSelectionModel().selectFirst();
        Spinner<Integer> licenseQuantitySpinner = new Spinner<>(1, 100, 10);
        licenseQuantitySpinner.setEditable(true);

        grid.add(new Label("Tên:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Giá:"), 0, 1);
        grid.add(priceField, 1, 1);
        grid.add(new Label("Loại:"), 0, 2);
        grid.add(typeComboBox, 1, 2);
        grid.add(new Label("Số lượng license:"), 0, 3);
        grid.add(licenseQuantitySpinner, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Chuyển đổi kết quả dialog thành đối tượng Product
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                // Đặt số lượng license vào một thuộc tính tạm của Product để truyền đi
                // Đây là một mẹo nhỏ để truyền nhiều giá trị qua dialog
                Product newProduct = new Product(); 
                newProduct.setName(nameField.getText());
                newProduct.setPrice(new BigDecimal(priceField.getText()));
                newProduct.setType(typeComboBox.getValue());
                // Giả sử stall_id = 1 là gian hàng mặc định
                
                // Tự động tạo một uniqueKey đơn giản từ tên sản phẩm và thời gian
                String uniqueKey = nameField.getText().trim().toLowerCase().replaceAll("\\s+", "-") + "-" + System.currentTimeMillis() % 10000;
                newProduct.setUniqueKey(uniqueKey);
                
                newProduct.setStallId(1L);
                newProduct.setStatus(Product.Status.AVAILABLE);
                newProduct.setCreatedAt(LocalDateTime.now());
                newProduct.setIsDelete(false);
                newProduct.setQuantity(licenseQuantitySpinner.getValue()); // Dùng tạm trường quantity để chứa số license cần tạo
                return newProduct;
            }
            return null;
        });

        Optional<Product> result = dialog.showAndWait();

        result.ifPresent(product -> {
            try {
                int licenseQuantity = product.getQuantity();
                List<Warehouse> generatedLicenses = productService.createProductAndLicenses(product, licenseQuantity, currentUser);
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã tạo sản phẩm '" + product.getName() + "' và " + generatedLicenses.size() + " license key thành công!");
                loadProducts(); // Tải lại danh sách sản phẩm
            } catch (IllegalStateException e) {
                // Bắt lỗi "Không tìm thấy cửa hàng" từ service
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không tìm thấy cửa hàng của bạn. Vui lòng tạo cửa hàng trên web trước khi tạo sản phẩm.");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Đã có lỗi xảy ra. Vui lòng thử lại.");
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void handleBackButton() {
        try {
            SellerDashboardController controller = SceneManager.switchScene("seller-dashboard-view.fxml", "Seller Dashboard");
            controller.setUserData(currentUser);
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
}