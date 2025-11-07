package com.quannm.hsf_licenseshop_app.service;

import com.quannm.hsf_licenseshop_app.entity.User;

import java.util.Optional;

public interface IUserService {
    /**
     * Xác thực người dùng dựa trên username và password.
     *
     * @param username Tên đăng nhập.
     * @param password Mật khẩu (chưa mã hóa).
     * @return Trả về một Optional chứa User nếu đăng nhập thành công, ngược lại trả về Optional rỗng.
     */
    Optional<User> login(String username, String password);
}