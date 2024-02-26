package service;

import entity.User;
import lombok.RequiredArgsConstructor;
import repository.jpa.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository = new UserRepository();

    public Optional<User> getUser(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password);
    }

    public User createUser(String login, String password) throws Exception {
        return userRepository.save(login, password);
    }
}
