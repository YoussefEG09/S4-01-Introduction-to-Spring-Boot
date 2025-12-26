package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.exceptions.UserNotFoundException;
import cat.itacademy.s04.t01.userapi.models.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final List<User> users = new ArrayList<>();

    @GetMapping
    public List<User> getUsers() {
        return users;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        User newUser = new User(
                UUID.randomUUID(),
                user.getName(),
                user.getEmail()
        );

        users.add(newUser);
        return newUser;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with id " + id)
                );
    }

    @GetMapping
    public List<User> getUsers(
            @RequestParam(required = false) String name) {

        if (name == null) {
            return users;
        }

        return users.stream()
                .filter(user ->
                        user.getName().toLowerCase().contains(name.toLowerCase())
                )
                .toList();
    }
}
