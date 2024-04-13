package com.dev.eduacademy.service;

import com.dev.eduacademy.entity.Role;
import com.dev.eduacademy.repository.RoleRepository;
import com.dev.eduacademy.util.RoleType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    @PostConstruct
    public void initRoles() {
        Arrays.stream(RoleType.values())
                .forEach(roleType -> {
                    if (!roleRepository.findByRoleType(roleType).isPresent()) {
                        Role role = new Role();
                        role.setRoleType(roleType);
                        roleRepository.save(role);
                    }
                });
    }
}