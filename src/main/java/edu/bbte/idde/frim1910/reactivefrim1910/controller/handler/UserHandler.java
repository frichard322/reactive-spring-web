package edu.bbte.idde.frim1910.reactivefrim1910.controller.handler;

import edu.bbte.idde.frim1910.reactivefrim1910.dto.incoming.UserCreationDto;
import edu.bbte.idde.frim1910.reactivefrim1910.dto.incoming.UserUpdateDto;
import edu.bbte.idde.frim1910.reactivefrim1910.dto.outgoing.UserDto;
import edu.bbte.idde.frim1910.reactivefrim1910.mapper.UserMapper;
import edu.bbte.idde.frim1910.reactivefrim1910.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    public Mono<ServerResponse> findAllUsers(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .body(userService.findAll().map(userMapper::modelToDto), UserDto.class);
    }

    public Mono<ServerResponse> findUserById(ServerRequest request) {
        return userService.findById(request.pathVariable("userId"))
            .map(userMapper::modelToDto)
            .flatMap(
                foundUser -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(foundUser)))
            .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        return userService.deleteById(request.pathVariable("userId"))
            .flatMap(deletedUser -> ServerResponse.noContent().build())
            .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request.bodyToMono(UserCreationDto.class)
            .map(userMapper::creationDtoToModel)
            .doOnSuccess(
                newUser -> newUser.setPassword(encoder.encode(newUser.getPassword()))
            )
            .subscribeOn(Schedulers.parallel())
            .flatMap(
                newUser -> userService.save(newUser)
                    .map(userMapper::modelToDto)
                    .flatMap(
                        createdUser -> ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(createdUser))
                    )
            );
    }

    public Mono<ServerResponse> updateUser(ServerRequest request) {
        return request.bodyToMono(UserUpdateDto.class)
            .flatMap(
                userUpdateDto -> userService.findById(request.pathVariable("userId"))
                    .flatMap(
                        foundUser -> userService.save(userMapper.updateModelByDto(userUpdateDto, foundUser))
                            .map(userMapper::modelToDto)
                            .flatMap(
                                updatedUser -> ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(BodyInserters.fromValue(updatedUser))
                            )
                    )
                    .switchIfEmpty(ServerResponse.notFound().build())
            );
    }
}
