package com.grupo5.trainee.minsait.MiniEcommerce.service;

import com.grupo5.trainee.minsait.MiniEcommerce.dto.UserCreateDTO;
import com.grupo5.trainee.minsait.MiniEcommerce.dto.UserDTO;
import com.grupo5.trainee.minsait.MiniEcommerce.enums.Role;
import com.grupo5.trainee.minsait.MiniEcommerce.model.User;
import com.grupo5.trainee.minsait.MiniEcommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO create(UserCreateDTO dto) {

        User u = new User();
        u.setLogin(dto.login());

        u.setRole(Role.ROLE_USER);

        String encodedPassword = passwordEncoder.encode(dto.senha());
        u.setSenha(encodedPassword);

        User saved = userRepository.save(u);

        return mapToDTO(saved);
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário com ID " + id + " não encontrado."
                ));

        return mapToDTO(user);
    }

    public void delete(Long id) {
        findById(id);

        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(User u) {
        return new UserDTO(
                u.getId(),
                u.getLogin()
        );
    }
}