package com.quannm.hsf_licenseshop_app.model;

import com.quannm.hsf_licenseshop_app.entity.User;
import javafx.beans.property.*;

import java.time.LocalDateTime;

public class UserFX {

    private final LongProperty id;
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty email;
    private final StringProperty phone;
    private final StringProperty fullName;
    private final ObjectProperty<User.Role> role;
    private final ObjectProperty<User.Status> status;
    private final StringProperty provider;
    private final StringProperty providerId;
    private final ObjectProperty<byte[]> avatarData;
    private final ObjectProperty<LocalDateTime> createdAt;
    private final ObjectProperty<LocalDateTime> updatedAt;

    /**
     * Hàm khởi tạo rỗng.
     * Dùng để tạo một đối tượng UserFX mới, sẵn sàng để binding với form tạo mới người dùng.
     */
    public UserFX() {
        this.id = new SimpleLongProperty();
        this.username = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.phone = new SimpleStringProperty("");
        this.fullName = new SimpleStringProperty("");
        this.role = new SimpleObjectProperty<>(User.Role.USER);
        this.status = new SimpleObjectProperty<>(User.Status.ACTIVE);
        this.provider = new SimpleStringProperty("LOCAL");
        this.providerId = new SimpleStringProperty();
        this.avatarData = new SimpleObjectProperty<>();
        this.createdAt = new SimpleObjectProperty<>();
        this.updatedAt = new SimpleObjectProperty<>();
    }

    /**
     * Hàm khởi tạo từ một User entity.
     * Dùng để chuyển đổi dữ liệu từ database sang model để hiển thị trên giao diện.
     * @param user User entity từ database.
     */
    public UserFX(User user) {
        this.id = new SimpleLongProperty(user.getId());
        this.username = new SimpleStringProperty(user.getUsername());
        this.password = new SimpleStringProperty(user.getPassword());
        this.email = new SimpleStringProperty(user.getEmail());
        this.phone = new SimpleStringProperty(user.getPhone());
        this.fullName = new SimpleStringProperty(user.getFullName());
        this.role = new SimpleObjectProperty<>(user.getRole());
        this.status = new SimpleObjectProperty<>(user.getStatus());
        this.provider = new SimpleStringProperty(user.getProvider());
        this.providerId = new SimpleStringProperty(user.getProviderId());
        this.avatarData = new SimpleObjectProperty<>(user.getAvatarData());
        this.createdAt = new SimpleObjectProperty<>(user.getCreatedAt());
        this.updatedAt = new SimpleObjectProperty<>(user.getUpdatedAt());
    }

    // --- Getters and Property Methods ---

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public String getFullName() {
        return fullName.get();
    }

    public StringProperty fullNameProperty() {
        return fullName;
    }

    public User.Role getRole() {
        return role.get();
    }

    public ObjectProperty<User.Role> roleProperty() {
        return role;
    }

    public User.Status getStatus() {
        return status.get();
    }

    public ObjectProperty<User.Status> statusProperty() {
        return status;
    }

    public String getProvider() {
        return provider.get();
    }

    public StringProperty providerProperty() {
        return provider;
    }

    public String getProviderId() {
        return providerId.get();
    }

    public StringProperty providerIdProperty() {
        return providerId;
    }

    public byte[] getAvatarData() {
        return avatarData.get();
    }

    public ObjectProperty<byte[]> avatarDataProperty() {
        return avatarData;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt.get();
    }

    public ObjectProperty<LocalDateTime> createdAtProperty() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt.get();
    }

    public ObjectProperty<LocalDateTime> updatedAtProperty() {
        return updatedAt;
    }

    /**
     * Chuyển đổi UserFX thành User entity.
     * Lưu ý: Các trường như password, createdAt, updatedAt có thể cần được xử lý thêm ở tầng Service
     * tùy thuộc vào nghiệp vụ (ví dụ: mã hóa password mới, tự động cập nhật thời gian).
     *
     * @return User entity tương ứng.
     */
    public User toEntity() {
        User user = new User();
        user.setId(this.getId());
        user.setUsername(this.getUsername());
        user.setPassword(this.getPassword()); // Cần xử lý mã hóa ở Service nếu là mật khẩu mới
        user.setEmail(this.getEmail());
        user.setPhone(this.getPhone());
        user.setFullName(this.getFullName());
        user.setRole(this.getRole());
        user.setStatus(this.getStatus());
        user.setProvider(this.getProvider());
        user.setProviderId(this.getProviderId());
        user.setAvatarData(this.getAvatarData());
        user.setCreatedAt(this.getCreatedAt());
        user.setUpdatedAt(this.getUpdatedAt());
        return user;
    }
}