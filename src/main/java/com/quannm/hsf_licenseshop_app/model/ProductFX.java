package com.quannm.hsf_licenseshop_app.model;

import com.quannm.hsf_licenseshop_app.entity.Product;
import javafx.beans.property.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductFX {

    private final LongProperty id;
    private final StringProperty name;
    private final StringProperty description;
    private final ObjectProperty<BigDecimal> price;
    private final LongProperty shopId;
    private final LongProperty stallId;
    private final IntegerProperty quantity;
    private final ObjectProperty<Product.Status> status;
    private final StringProperty type;
    private final StringProperty uniqueKey;
    private final StringProperty shopName;
    private final StringProperty stallName;
    private final ObjectProperty<LocalDateTime> createdAt;
    private final ObjectProperty<LocalDateTime> updatedAt;

    /**
     * Hàm khởi tạo rỗng.
     * Dùng để tạo một đối tượng ProductFX mới, sẵn sàng để binding với form tạo mới sản phẩm.
     */
    public ProductFX() {
        this.id = new SimpleLongProperty();
        this.name = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
        this.price = new SimpleObjectProperty<>(BigDecimal.ZERO);
        this.shopId = new SimpleLongProperty();
        this.stallId = new SimpleLongProperty();
        this.quantity = new SimpleIntegerProperty(0);
        this.status = new SimpleObjectProperty<>(Product.Status.UNAVAILABLE);
        this.type = new SimpleStringProperty("");
        this.uniqueKey = new SimpleStringProperty("");
        this.shopName = new SimpleStringProperty("");
        this.stallName = new SimpleStringProperty("");
        this.createdAt = new SimpleObjectProperty<>();
        this.updatedAt = new SimpleObjectProperty<>();
    }

    /**
     * Hàm khởi tạo từ một Product entity.
     * Dùng để chuyển đổi dữ liệu từ database sang model để hiển thị trên giao diện.
     * @param product Product entity từ database.
     */
    public ProductFX(Product product) {
        this.id = new SimpleLongProperty(product.getId());
        this.name = new SimpleStringProperty(product.getName());
        this.description = new SimpleStringProperty(product.getDescription());
        this.price = new SimpleObjectProperty<>(product.getPrice());
        this.shopId = new SimpleLongProperty(product.getShopId());
        this.stallId = new SimpleLongProperty(product.getStallId());
        this.quantity = new SimpleIntegerProperty(product.getQuantity());
        this.status = new SimpleObjectProperty<>(product.getStatus());
        this.type = new SimpleStringProperty(product.getType());
        this.uniqueKey = new SimpleStringProperty(product.getUniqueKey());
        this.createdAt = new SimpleObjectProperty<>(product.getCreatedAt());
        this.updatedAt = new SimpleObjectProperty<>(product.getUpdatedAt());

        // Lấy tên Shop (được set EAGER loading trong entity Product)
        this.shopName = new SimpleStringProperty(product.getShop() != null ? product.getShop().getShopName() : "N/A");

        // Lấy tên Stall (LAZY loading, cần xử lý trong Service trước khi chuyển đổi)
        // Tạm thời hiển thị tên nếu đã được load, nếu không thì hiển thị ID.
        this.stallName = new SimpleStringProperty(product.getStall() != null ? product.getStall().getStallName() : "Stall ID: " + product.getStallId());
    }

    // --- Getters and Property Methods ---

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public BigDecimal getPrice() {
        return price.get();
    }

    public ObjectProperty<BigDecimal> priceProperty() {
        return price;
    }

    public long getShopId() {
        return shopId.get();
    }

    public LongProperty shopIdProperty() {
        return shopId;
    }

    public long getStallId() {
        return stallId.get();
    }

    public LongProperty stallIdProperty() {
        return stallId;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public Product.Status getStatus() {
        return status.get();
    }

    public ObjectProperty<Product.Status> statusProperty() {
        return status;
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public String getUniqueKey() {
        return uniqueKey.get();
    }

    public StringProperty uniqueKeyProperty() {
        return uniqueKey;
    }

    public String getShopName() {
        return shopName.get();
    }

    public StringProperty shopNameProperty() {
        return shopName;
    }

    public String getStallName() {
        return stallName.get();
    }

    public StringProperty stallNameProperty() {
        return stallName;
    }

    /**
     * Chuyển đổi ProductFX thành Product entity.
     *
     * @return Product entity tương ứng.
     */
    public Product toEntity() {
        Product product = new Product();
        product.setId(this.getId());
        product.setName(this.getName());
        product.setDescription(this.getDescription());
        product.setPrice(this.getPrice());
        product.setShopId(this.getShopId());
        product.setStallId(this.getStallId());
        product.setQuantity(this.getQuantity());
        product.setStatus(this.getStatus());
        product.setType(this.getType());
        product.setUniqueKey(this.getUniqueKey());
        product.setCreatedAt(this.createdAt.get());
        product.setUpdatedAt(this.updatedAt.get());
        // shop và stall entity sẽ được set bởi service layer nếu cần
        return product;
    }
}