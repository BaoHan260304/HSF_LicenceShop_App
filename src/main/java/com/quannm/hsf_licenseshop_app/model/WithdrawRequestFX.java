package com.quannm.hsf_licenseshop_app.model;

import com.quannm.hsf_licenseshop_app.entity.WithdrawRequest;
import javafx.beans.property.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WithdrawRequestFX {
    private final LongProperty id;
    private final LongProperty shopId; // Thêm shopId
    private final ObjectProperty<BigDecimal> amount;
    private final ObjectProperty<WithdrawRequest.Status> status;
    private final StringProperty bankAccountNumber;
    private final StringProperty bankAccountName;
    private final StringProperty bankName;
    private final StringProperty note;
    private final ObjectProperty<LocalDateTime> createdAt;
    private final StringProperty shopName;

    public WithdrawRequestFX(WithdrawRequest request) {
        this.id = new SimpleLongProperty(request.getId());
        this.amount = new SimpleObjectProperty<>(request.getAmount());
        this.shopId = new SimpleLongProperty(request.getShopId()); // Khởi tạo shopId
        this.status = new SimpleObjectProperty<>(request.getStatus());
        this.bankAccountNumber = new SimpleStringProperty(request.getBankAccountNumber());
        this.bankAccountName = new SimpleStringProperty(request.getBankAccountName());
        this.bankName = new SimpleStringProperty(request.getBankName());
        this.note = new SimpleStringProperty(request.getNote());
        this.createdAt = new SimpleObjectProperty<>(request.getCreatedAt());

        // Lấy tên Shop (LAZY loading, cần xử lý trong Service)
        // Tạm thời hiển thị ID nếu chưa được load
        this.shopName = new SimpleStringProperty(request.getShop() != null ? request.getShop().getShopName() : "Shop ID: " + request.getShopId());
    }

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public String getShopName() {
        return shopName.get();
    }

    public StringProperty shopNameProperty() {
        return shopName;
    }

    public long getShopId() {
        return shopId.get();
    }

    public LongProperty shopIdProperty() {
        return shopId;
    }

    public BigDecimal getAmount() {
        return amount.get();
    }

    public ObjectProperty<BigDecimal> amountProperty() {
        return amount;
    }

    public WithdrawRequest.Status getStatus() {
        return status.get();
    }

    public ObjectProperty<WithdrawRequest.Status> statusProperty() {
        return status;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber.get();
    }

    public StringProperty bankAccountNumberProperty() {
        return bankAccountNumber;
    }

    public String getBankAccountName() {
        return bankAccountName.get();
    }

    public StringProperty bankAccountNameProperty() {
        return bankAccountName;
    }

    public String getBankName() {
        return bankName.get();
    }

    public StringProperty bankNameProperty() {
        return bankName;
    }

    public String getNote() {
        return note.get();
    }

    public StringProperty noteProperty() {
        return note;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt.get();
    }

    public ObjectProperty<LocalDateTime> createdAtProperty() {
        return createdAt;
    }

    /**
     * Chuyển đổi WithdrawRequestFX thành WithdrawRequest entity.
     *
     * @return WithdrawRequest entity tương ứng.
     */
    public WithdrawRequest toEntity() {
        WithdrawRequest request = new WithdrawRequest();
        request.setId(this.getId());
        request.setShopId(this.getShopId());
        request.setAmount(this.getAmount());
        request.setStatus(this.getStatus());
        request.setBankAccountNumber(this.getBankAccountNumber());
        request.setBankAccountName(this.getBankAccountName());
        request.setBankName(this.getBankName());
        request.setNote(this.getNote());
        request.setCreatedAt(this.getCreatedAt());
        // shop entity sẽ được set bởi service layer nếu cần
        return request;
    }
}
