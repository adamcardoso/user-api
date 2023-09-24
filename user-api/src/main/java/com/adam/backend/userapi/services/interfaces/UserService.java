package com.adam.backend.userapi.services.interfaces;

import com.adam.backend.userapi.dtos.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Page<UserDTO> getAll(Pageable pageable);

    Optional<UserDTO> findById(Long userId);

    UserDTO save(UserDTO userDTO);

    void delete(Long userId);

    UserDTO findByCpf(String cpf);

    List<UserDTO> queryByName(String name);
}
