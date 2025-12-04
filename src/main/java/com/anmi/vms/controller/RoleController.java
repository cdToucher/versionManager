package com.anmi.vms.controller;

import com.anmi.vms.entity.User;
import com.anmi.vms.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "角色管理", description = "用户角色管理API")
public class RoleController {

    @Autowired
    private UserService userService;

    @Operation(summary = "获取所有用户角色", description = "获取系统中所有可用的用户角色")
    @GetMapping("/all-roles")
    public ResponseEntity<User.Role[]> getAllRoles() {
        User.Role[] roles = User.Role.values();
        return ResponseEntity.ok(roles);
    }

    @Operation(summary = "按角色获取用户", description = "根据角色获取所有用户")
    @GetMapping("/users-by-role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable User.Role role) {
        // 这里需要在UserService中添加相应方法，暂时返回所有用户
        List<User> users = userService.findAllUsers();
        // 过滤出特定角色的用户
        List<User> filteredUsers = users.stream()
                .filter(user -> user.getRole() == role)
                .toList();
        return ResponseEntity.ok(filteredUsers);
    }

    @Operation(summary = "更新用户角色", description = "更新指定用户的权限角色")
    @PutMapping("/update-role/{userId}")
    public ResponseEntity<User> updateUserRole(@PathVariable Long userId, @RequestParam User.Role role) {
        return userService.findUserById(userId)
                .map(user -> {
                    user.setRole(role);
                    User updatedUser = userService.updateUser(user);
                    return ResponseEntity.ok(updatedUser);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}