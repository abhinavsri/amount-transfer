package com.example.payment.transfer.service;

import com.example.payment.transfer.exception.UserNotFoundException;
import com.example.payment.transfer.model.User;
import com.example.payment.transfer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUser(Long id){
        User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User not found for this id"+id));
        return user;
    }

    public User save(User user) {
        userRepository.save(user);
        return user;
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public User updateUser(Long id, User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User existingUser = optionalUser.get();
            existingUser.setUsername(userDetails.getUsername());
            existingUser.setEmailAddress(userDetails.getEmailAddress());
            return userRepository.save(existingUser);

        }
        throw new UserNotFoundException("User not found for this id"+id);
    }
}
