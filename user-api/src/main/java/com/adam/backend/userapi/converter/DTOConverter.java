package com.adam.backend.userapi.converter;

import com.adam.backend.userapi.dtos.UserDTO;
import com.adam.backend.userapi.models.User;

public class DTOConverter {

    private DTOConverter() {
        // Private constructor to prevent instantiation
        throw new UnsupportedOperationException("Utility class - do not instantiate");
    }

    public static UserDTO convert(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setNome(user.getNome());
        userDTO.setEndereco(user.getEndereco());
        userDTO.setCpf(user.getCpf());
        userDTO.setKey(user.getKey());
        userDTO.setEmail(user.getEmail());
        userDTO.setTelefone(user.getTelefone());
        userDTO.setDataCadastro(user.getDataCadastro());
        return userDTO;
    }
}
