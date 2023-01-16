package edu.bbte.idde.frim1910.reactivefrim1910.security;

import edu.bbte.idde.frim1910.reactivefrim1910.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.ReactiveUserDetailsPasswordService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
@Log4j2
public class AuthUserService implements ReactiveUserDetailsService, ReactiveUserDetailsPasswordService {

    private final UserService userService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        log.info("Finding user by '{}' username.", username);
        return userService.findByUsername(username).map(AuthUser::new);
    }

    @Override
    public Mono<UserDetails> updatePassword(UserDetails user, String newPassword) {
        log.info("Updating password for user with name '{}'.", user.getUsername());
        return userService
            .findByUsername(user.getUsername())
            .doOnSuccess(foundUser -> foundUser.setPassword(newPassword))
            .flatMap(userService::save)
            .map(AuthUser::new);
    }
}
