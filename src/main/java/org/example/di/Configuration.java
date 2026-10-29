package org.example.di;

import org.example.datasource.mapper.GameEntityMapper;
import org.example.datasource.mapper.GameTypeEntityMapper;
import org.example.datasource.mapper.StateEntityMapper;
import org.example.datasource.mapper.UserEntityMapper;
import org.example.datasource.model.StateEntity;
import org.example.datasource.repository.*;
import org.example.domain.service.*;
import org.example.web.filter.AuthFilter;
import org.example.web.mapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public StateEntityMapper stateEntityMapper(){
        return new StateEntityMapper();
    }

    @Bean
    public GameTypeEntityMapper gameTypeEntityMapper(){
        return new GameTypeEntityMapper();
    }

    @Bean
    public GameEntityMapper gameEntityMapper(StateEntityMapper stateEntityMapper, GameTypeEntityMapper gameTypeEntityMapper){
        return new GameEntityMapper(stateEntityMapper, gameTypeEntityMapper);
    }

    @Bean
    public UserEntityMapper userEntityMapper(){
        return new UserEntityMapper();
    }

    @Bean
    public GridWebMapper gridWebMapper(){
        return new GridWebMapper();
    }

    @Bean
    public StateWebMapper stateWebMapper(){
        return new StateWebMapper();
    }

    @Bean
    public GameTypeWebMapper gameTypeWebMapper(){
        return new GameTypeWebMapper();
    }

    @Bean
    public GameWebMapper gameWebMapper(GridWebMapper gridWebMapper, StateWebMapper stateWebMapper, GameTypeWebMapper gameTypeWebMapper){
        return new GameWebMapper(gridWebMapper, stateWebMapper, gameTypeWebMapper);
    }

    @Bean
    public UserWebMapper userWebMapper(){
        return new UserWebMapper();
    }

    @Bean
    public SignUpRequestWebMapper signUpRequestWebMapper(){
        return new SignUpRequestWebMapper();
    }

    @Bean
    public Repository repository(JpaGameRepository jpaGameRepository, GameEntityMapper gameEntityMapper, StateEntityMapper stateEntityMapper){
        return new PostgresGameRepository(jpaGameRepository, gameEntityMapper, stateEntityMapper);
    }

    @Bean
    public UserRepository userRepository(JpaUserRepository jpaUserRepository, UserEntityMapper userEntityMapper){
        return new PostgresUserRepository(jpaUserRepository, userEntityMapper);
    }

    @Bean
    public Service Service(Repository repository){
        return new ServiceImpl(repository);
    }

    @Bean
    public UserService userService(UserRepository userRepository){
        return new UserServiceImpl(userRepository);
    }

    @Bean
    public AuthService AuthService(UserService userService){
        return new AuthServiceImpl(userService);
    }

    @Bean
    public AuthFilter AuthFilter(AuthService authService){
        return new AuthFilter(authService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthFilter authFilter) throws Exception{
        httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/register", "/auth/login").permitAll()
                        .requestMatchers("/", "/index.html", "/style.css", "/script.js").permitAll()
                        .requestMatchers("/static/**").permitAll()
                        .requestMatchers("/game/**", "/user/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(authFilter, BasicAuthenticationFilter.class);
        return httpSecurity.build();
    }

}
