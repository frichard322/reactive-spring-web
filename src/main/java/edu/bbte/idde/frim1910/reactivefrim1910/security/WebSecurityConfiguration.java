package edu.bbte.idde.frim1910.reactivefrim1910.security;

import edu.bbte.idde.frim1910.reactivefrim1910.model.Role;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;

import java.net.URI;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfiguration {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
            .csrf().disable()
            .authorizeExchange()
            .matchers(PathRequest.toStaticResources().atCommonLocations())
            .permitAll()
            .matchers(EndpointRequest.to("health"))
            .permitAll()
            .matchers(EndpointRequest.to("info"))
            .permitAll()
            .matchers(EndpointRequest.toAnyEndpoint())
            .hasRole(Role.ADMIN.name())
            .pathMatchers("/api/cars/**")
            .hasAnyRole(Role.ADMIN.name(), Role.USER.name())
            .pathMatchers("/api/users/**")
            .hasRole(Role.ADMIN.name())
            .anyExchange()
            .authenticated()
            .and()
            .httpBasic()
            .and()
            .formLogin()
            .and()
            .logout()
            .logoutSuccessHandler(logoutSuccessHandler())
            .and()
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ServerLogoutSuccessHandler logoutSuccessHandler() {
        RedirectServerLogoutSuccessHandler logoutSuccessHandler = new RedirectServerLogoutSuccessHandler();
        logoutSuccessHandler.setLogoutSuccessUrl(URI.create("/"));
        return logoutSuccessHandler;
    }
}
