package com.quannm.hsf_licenseshop_app.service;

import com.quannm.hsf_licenseshop_app.entity.User;
import com.quannm.hsf_licenseshop_app.model.UserFX;

import java.util.Optional;
import java.util.List;

public interface IUserService {
    /**
     * Xác thực người dùng dựa trên username và password.
     *
     * @param username Tên đăng nhập.
     * @param password Mật khẩu (chưa mã hóa).
     * @return Trả về một Optional chứa User nếu đăng nhập thành công, ngược lại trả về Optional rỗng.
     */
    Optional<User> login(String username, String password);

    /**
     * Lấy danh sách tất cả người dùng trong hệ thống.
     *
     * @return Danh sách các đối tượng UserFX.
     */
    List<UserFX> findAll();

    /**
     * Cập nhật trạng thái của một người dùng.
     * @param userId ID của người dùng.
     * @param newStatus Trạng thái mới.
     * @return true nếu cập nhật thành công, false nếu thất bại.
     */
    boolean updateUserStatus(Long userId, User.Status newStatus);

    /**
     * Cập nhật vai trò của một người dùng.
     * @param userId ID của người dùng.
     * @param newRole Vai trò mới.
     * @return true nếu cập nhật thành công, false nếu thất bại.
     */
    boolean updateUserRole(Long userId, User.Role newRole);
}