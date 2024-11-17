package com.contact_manager.scm.servicesImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.contact_manager.scm.entities.User;
import com.contact_manager.scm.helper.AppConstants;
import com.contact_manager.scm.helper.Helper;
import com.contact_manager.scm.helper.ResourceNotFoundException;
import com.contact_manager.scm.repositories.UserRepo;
import com.contact_manager.scm.services.EmailService;
import com.contact_manager.scm.services.UserService;

@Service
public class UserSeviceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;  // For connect with db

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public EmailService emailService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public User saveUser(User user) {

        // Check if the email already exists
        Optional<User> existingUser = userRepo.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
             User foundUser = existingUser.get();
            if (!foundUser.isEnabled()) {
                throw new IllegalStateException("Email already exists but is not verified. Please verify your email.");
            } else {
                throw new IllegalArgumentException("User with this email already exists.");
            }
        }

        // user Id have to be generate
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);

        // password encode
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // set the user role
        user.setRoleList(List.of(AppConstants.ROLE_USER));

        String emailVerificationToken = UUID.randomUUID().toString();

        user.setEmailVerificationToken(emailVerificationToken);
        User savedUser = userRepo.save(user);

        
        String emailLink = Helper.getLinkForEmailVerification(emailVerificationToken);

        emailService.sendEmail(savedUser.getEmail(), "Please verify your email", emailLink);
        
        return savedUser;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User user2 = userRepo.findById(user.getUserId()).orElseThrow(() ->  new ResourceNotFoundException("User not found"));
        // update user2 from user
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setAbout(user.getAbout());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());

        // save user in DB
        User save = userRepo.save(user2);
        return Optional.ofNullable(save);
    }

    @Override
    public void deleteUser(String id) {
        User user2 = userRepo.findById(id).orElseThrow(() ->  new ResourceNotFoundException("User not found"));
        userRepo.delete(user2);
    }

    @Override
    public boolean isUserExist(String userId) {        
        User user2 = userRepo.findById(userId).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {         
        User user2 = userRepo.findByEmail(email).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }
}
