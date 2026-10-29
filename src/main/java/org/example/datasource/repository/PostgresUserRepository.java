package org.example.datasource.repository;

import org.example.datasource.mapper.UserEntityMapper;
import org.example.datasource.model.UserEntity;
import org.example.domain.model.User;

import java.util.UUID;

@org.springframework.stereotype.Repository
public class PostgresUserRepository implements UserRepository{

    private final JpaUserRepository jpaUserRepository;
    private final UserEntityMapper userEntityMapper;

    public PostgresUserRepository(JpaUserRepository jpaUserRepository, UserEntityMapper userEntityMapper){
        this.jpaUserRepository = jpaUserRepository;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public void saveUser(User user) {
        UserEntity userEntity = userEntityMapper.toEntity(user);
        jpaUserRepository.save(userEntity);
    }

    @Override
    public User findByLogin(String login) {
        UserEntity userEntity = jpaUserRepository.findByLogin(login);
        if(userEntity == null){
            return null;
        }
        return userEntityMapper.toDomain(userEntity);
    }

    @Override
    public User findByUUID(UUID uuid) {
        UserEntity userEntity = jpaUserRepository.findById(uuid).orElse(null);
        if(userEntity == null){
            return null;
        }
        return userEntityMapper.toDomain(userEntity);
    }
}
