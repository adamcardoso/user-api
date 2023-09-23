package com.adam.backend.userapi.api;

import com.adam.backend.userapi.dtos.UserDTO;
import com.adam.backend.userapi.exceptions.UserNotFoundException;
import com.adam.backend.userapi.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public List<UserDTO> getUsers() {
        return userService.getAll();
    }
    @GetMapping("/user/{id}")
    UserDTO findById(@PathVariable Long id) {
        return userService.findById(id);
    }
    @PostMapping("/user")
    UserDTO newUser(@RequestBody UserDTO userDTO) {
        return userService.save(userDTO);
    }
    @GetMapping("/user/cpf/{cpf}")
    UserDTO findByCpf(@PathVariable String cpf) {
        return userService.findByCpf(cpf);
    }
    @DeleteMapping("/user/{id}")
    UserDTO delete(@PathVariable Long id) throws UserNotFoundException {
        return userService.delete(id);
    }

    @GetMapping("/user/search")
    public List<UserDTO> queryByName(
            @RequestParam(name="nome", required = true)
            String nome) {
        return userService.queryByName(nome);
    }
}
