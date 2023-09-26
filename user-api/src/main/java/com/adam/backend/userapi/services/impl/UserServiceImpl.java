package com.adam.backend.userapi.services.impl;

import com.adam.backend.userapi.converter.DTOConverter;
import com.adam.backend.userapi.dtos.UserDTO;
import com.adam.backend.userapi.exceptions.DatabaseException;
import com.adam.backend.userapi.exceptions.ResourceNotFoundException;
import com.adam.backend.userapi.exceptions.UserNotFoundException;
import com.adam.backend.userapi.models.User;
import com.adam.backend.userapi.repositories.UserRepository;
import com.adam.backend.userapi.services.interfaces.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> getAll(Pageable pageable) {
        try{
            Page<User> usuarios = userRepository.findAll(pageable);

            return usuarios.map(UserDTO::convert);
        }catch (Exception e){
            throw new UserNotFoundException("Usuário não encontrado!", e);
        }
    }

    @Override
    @Transactional
    public Optional<UserDTO> findById(Long userId) {
        try{
            Optional<User> usuario = userRepository.findById(userId);

            return usuario.map(UserDTO::convert);
        }catch (Exception e){
            throw new UserNotFoundException("Usuário não encontrado!", e);
        }
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        try{
            userDTO.setKey(UUID.randomUUID().toString());
            userDTO.setDataCadastro(LocalDateTime.now());

            User user = userRepository.save(User.convert(userDTO));
            return UserDTO.convert(user);
        } catch (Exception e) {
            throw new DatabaseException("Erro ao inserir pessoa no banco de dados", e);
        }
    }

    @Override
    public void delete(Long userId) {
        try{
            userRepository.deleteById(userId);
        }catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("Usuário com a " + userId + "não encontrado", e);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Violação de integridade no banco de dados", e);
        } catch (Exception e) {
            throw new DatabaseException("Erro ao excluir usuário do banco de dados", e);
        }
    }

    @Override
    public UserDTO findByCpf(String cpf, String key) {
        User user = userRepository.findByCpfAndKey(cpf, key);
        if (Objects.nonNull(user)) {
            return UserDTO.convert(user);
        }
        throw new UserNotFoundException("Usuário com a " + cpf + "ou " + key + "não encontrado!");
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> queryByName(String name) {
        try{
            List<User> usuarios = userRepository.queryByNomeLike(name);

            if (usuarios.isEmpty()) {
                throw new ResourceNotFoundException("Nome não encontrado: " + name);
            }

            return usuarios.stream()
                    .map(DTOConverter::convert)
                    .toList();
        } catch (Exception e) {
            throw new DatabaseException("Erro ao buscar usuários no banco de dados", e);
        }
    }
}
