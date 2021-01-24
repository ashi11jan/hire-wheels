package com.upgrad.hirewheels.services;

import com.upgrad.hirewheels.dao.UserDao;
import com.upgrad.hirewheels.entities.User;
import com.upgrad.hirewheels.exceptions.BadCredentialsException;
import com.upgrad.hirewheels.exceptions.UserAlreadyExistsException;
import com.upgrad.hirewheels.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao userDao;

    /**
     * Checks if the userDTO mobile number/email is already exists or not.
     * If not exists, saves the user details else throws an error.
     * @param user
     * @return
     * @throws UserAlreadyExistsException
     */

    @Override
    public User createUser(User user) throws UserAlreadyExistsException {
        User returnedUser = userDao.findByEmailIgnoreCase(user.getEmail());
        if ( returnedUser != null) {
            throw new UserAlreadyExistsException("Email Already Exists");
        }
        User returnedUser1 = userDao.findByMobileNoIgnoreCase(user.getMobileNo());
        if (returnedUser1 != null) {
            throw new UserAlreadyExistsException("Mobile Number Already Exists");
        }
        User savedUser = userDao.save(user);
        return savedUser;
    }

    /**
     * Checks if the user is registered or not.
     * If registered it returns the user details else throws an error.
     * @param user
     * @return
     * @throws BadCredentialsException
     * @throws UserNotFoundException
     */

    @Override
    public User getUser(User user) throws BadCredentialsException, UserNotFoundException {
        User checkUser = userDao.findByEmailIgnoreCase(user.getEmail());
        if (checkUser == null){
            throw new UserNotFoundException("User not Registered");
        }
        User retrievedUser = userDao.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (retrievedUser == null){
            throw new BadCredentialsException("Unauthorized User");
        }
        return retrievedUser;
    }
}
