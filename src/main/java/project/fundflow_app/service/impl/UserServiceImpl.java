package project.fundflow_app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import project.fundflow_app.dto.user.UserRequest;
import project.fundflow_app.dto.user.UserResponse;
import project.fundflow_app.entity.Role;
import project.fundflow_app.entity.User;
import project.fundflow_app.mapper.UserMapper;
import project.fundflow_app.repository.BankAccountRepository;
import project.fundflow_app.repository.RoleRepository;
import project.fundflow_app.repository.UserRepository;
import project.fundflow_app.security.AuthUtil;
import project.fundflow_app.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private  BankAccountRepository bankAccountRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private  AuthUtil authUtil;


    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
            .stream()
            .map(userMapper::toDto)
            .collect(Collectors.toList());

    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() ->  new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }

    

    @Override
    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)){
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }


    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return user;
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        if(request.getPassword() != null){
            user.setPassword(request.getPassword());
        }
        return userMapper.toDto(user);
    }

    @Override
    public void makeUserAdmin(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Role admiRole = roleRepository.findByName("ROLE_ADMIN")
            .orElseThrow(() ->  new RuntimeException("Admin role not found"));
        
        user.getRoles().add(admiRole);
        userRepository.save(user);
    }

    @Override
    public UserResponse getCurrentUser() {
        String email = authUtil.getCurrentUser().getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }
}
