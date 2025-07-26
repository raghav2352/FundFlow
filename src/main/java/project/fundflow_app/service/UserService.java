package project.fundflow_app.service;

import project.fundflow_app.dto.user.UserRequest;
import project.fundflow_app.dto.user.UserResponse;
import project.fundflow_app.entity.User;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse updateUser(Long userId, UserRequest request);
    void deleteUser(Long id);
    void makeUserAdmin(Long userId);
    User findByEmail(String email);
    UserResponse getCurrentUser();
}
