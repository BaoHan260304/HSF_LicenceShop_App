package com.quannm.hsf_licenseshop_app.service.impl;

import com.quannm.hsf_licenseshop_app.dao.UserDAO;
import com.quannm.hsf_licenseshop_app.entity.User;
import com.quannm.hsf_licenseshop_app.model.UserFX;
import com.quannm.hsf_licenseshop_app.service.IUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

public class UserService implements IUserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public UserService() {
        this.userDAO = new UserDAO();
        // Sử dụng BCryptPasswordEncoder từ thư viện Spring Security đã thêm vào pom.xml
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Xác thực người dùng dựa trên username và password.
     *
     * @param username Tên đăng nhập.
     * @param password Mật khẩu (chưa mã hóa).
     * @return Trả về một Optional chứa User nếu đăng nhập thành công, ngược lại trả về Optional rỗng.
     */
    @Override
    public Optional<User> login(String username, String password) {
        Optional<User> userOptional = userDAO.findByUsername(username);

        // Kiểm tra xem user có tồn tại và mật khẩu có khớp không
        return userOptional.filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }

    @Override
    public List<UserFX> findAll() {
        return userDAO.findAll().stream()
                .map(UserFX::new) // Chuyển đổi mỗi User entity thành UserFX
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateUserStatus(Long userId, User.Status newStatus) {
        Optional<User> userOptional = userDAO.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus(newStatus);
            userDAO.update(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUserRole(Long userId, User.Role newRole) {
        Optional<User> userOptional = userDAO.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Thêm logic kiểm tra nếu cần, ví dụ: không cho phép hạ vai trò của admin cuối cùng
            user.setRole(newRole);
            userDAO.update(user);
            return true;
        }
        return false;
    }
}