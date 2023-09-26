package com.adam.backend.userapi.api.impl;

import com.adam.backend.userapi.api.interfaces.UserController;
import com.adam.backend.userapi.dtos.UserDTO;
import com.adam.backend.userapi.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserControllerImpl implements UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);

    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping("/user")
    public ResponseEntity<Page<UserDTO>> getUsers(Pageable pageable) {
        Page<UserDTO> userDTOS = userService.getAll(pageable);

        logger.info("Listando todas os usuários!");

        return ResponseEntity.ok().body(userDTOS);
    }

    @Override
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Long id) {
        Optional<UserDTO> userDTO = userService.findById(id);

        if(userDTO.isPresent()){
            logger.info("Encontrado usuário com ID: {}", id);
            return ResponseEntity.ok(userDTO.get());
        }else {
            logger.error("Usuário com ID {} não encontrado", id);
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PostMapping("/user")
    public ResponseEntity<UserDTO> newUser(@Valid @RequestBody UserDTO userDTO) {
        userDTO = userService.save(userDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(userDTO.getId()).toUri();

        logger.info("Inserindo um novo usuário");
        return ResponseEntity.created(uri).body(userDTO);
    }

    @Override
    @GetMapping("/user/cpf/{cpf}")
    public UserDTO findByCpf(@RequestParam(name="key") String key, @PathVariable String cpf) {
        return userService.findByCpf(cpf, key);
    }

    @Override
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);

        logger.info("Deletando um usuário com ID: {}", id);

        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/user/search")
    public ResponseEntity<List<UserDTO>> queryByName(@RequestParam(name="nome") String nome) {
        List<UserDTO> userDTOS = userService.queryByName(nome);

        logger.info("Listando os usuários por nome");

        return ResponseEntity.ok().body(userDTOS);
    }
}
