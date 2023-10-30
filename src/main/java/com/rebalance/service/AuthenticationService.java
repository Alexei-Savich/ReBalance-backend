package com.rebalance.service;

import com.rebalance.entity.Group;
import com.rebalance.entity.User;
import com.rebalance.exception.RebalanceErrorType;
import com.rebalance.exception.RebalanceException;
import com.rebalance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final GroupService groupService;
    private final UserService userService;

    public User createUser(User userRequest, String currency) {
        validateUserNotExists(userRequest.getEmail());
        User user = userRepository.save(userRequest);

        Group personalGroup = new Group();
        personalGroup.setName("personal_" + user.getEmail());
        personalGroup.setCurrency(currency);
        personalGroup.setCreator(user);
        personalGroup.setPersonal(true);
        Group group = groupService.createGroupAndAddUser(personalGroup);

        user.setCreatedGroups(Set.of(group));

        return user;
    }

    public Optional<User> authorizeUser(String email, String password) {
        User user = userService.getUserByEmail(email);
        if (user.getPassword().equals(password)) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    private void validateUserNotExists(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            throw new RebalanceException(RebalanceErrorType.RB_001);
        }
    }
}