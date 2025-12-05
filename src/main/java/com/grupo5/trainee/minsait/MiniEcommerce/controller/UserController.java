package com.grupo5.trainee.minsait.MiniEcommerce.controller;

import com.grupo5.trainee.minsait.MiniEcommerce.dto.UserCreateDTO;
import com.grupo5.trainee.minsait.MiniEcommerce.dto.UserDTO;
import com.grupo5.trainee.minsait.MiniEcommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/usuarios")
@Tag(name = "Usuários", description = "Cria, buscar e exclui usuários!")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Criar", description = "Cria um usuário")
    @ApiResponse(responseCode = "201", description = "Usuário criado!")
    public ResponseEntity<UserDTO> create(@RequestBody UserCreateDTO dto) {
        UserDTO newUser = userService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping
    @Operation(summary = "Listar todos", description = "Retorna todos os usuários.")
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar", description = "Retorna um usuário de acordo com o id.")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado!")
    @ApiResponse(responseCode = "404", description = "Usuário com ID {id} não encontrado.")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        UserDTO user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir", description = "Exclui um usuário de acodo com o id.")
    @ApiResponse(responseCode = "204", description = "Usuário excluído!")
    @ApiResponse(responseCode = "404", description = "Usuário com ID {id} não encontrado.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}