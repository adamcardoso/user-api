package com.adam.backend.userapi.api.interfaces;

import com.adam.backend.userapi.dtos.UserDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserController {

    ResponseEntity<Page<UserDTO>> getUsers(Pageable pageable);

    ResponseEntity<UserDTO> findById(@PathVariable("id") Long id);

    ResponseEntity<UserDTO> newUser(@Valid @RequestBody UserDTO userDTO);

    UserDTO findByCpf(@RequestParam(name="key") String key, @PathVariable String cpf);

    ResponseEntity<Void> delete(@PathVariable Long id);

    ResponseEntity<List<UserDTO>> queryByName(@RequestParam(name="nome") String nome);
}
