package edu.bbte.idde.frim1910.reactivefrim1910.controller.router;

import edu.bbte.idde.frim1910.reactivefrim1910.controller.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> route(UserHandler userHandler) {

        return RouterFunctions.route(
                RequestPredicates.GET("/api/users")
                    .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                userHandler::findAllUsers)
            .andRoute(
                RequestPredicates.GET("/api/users/{userId}")
                    .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                userHandler::findUserById)
            .andRoute(
                RequestPredicates.POST("/api/users")
                    .and(RequestPredicates.contentType(MediaType.APPLICATION_JSON)),
                userHandler::createUser)
            .andRoute(
                RequestPredicates.DELETE("/api/users/{userId}")
                    .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                userHandler::deleteUser)
            .andRoute(
                RequestPredicates.PATCH("/api/users/{userId}")
                    .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                userHandler::updateUser);
    }
}
