package edu.bbte.idde.frim1910.reactivefrim1910.service;

import edu.bbte.idde.frim1910.reactivefrim1910.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserService {

    private final ReactiveMongoTemplate template;

    public Mono<User> save(User user) {
        return template.save(user);
    }

    public Flux<User> findAll() {
        return template.findAll(User.class);
    }

    public Mono<User> findById(String id) {
        return template.findById(id, User.class);
    }

    public Mono<User> findByUsername(String username) {
        return template.findOne(
            Query.query(Criteria.where("username").is(username)),
            User.class
        );
    }

    public Mono<User> deleteById(String id) {
        return template.findAndRemove(
            Query.query(Criteria.where("id").is(id)),
            User.class
        );
    }
}
