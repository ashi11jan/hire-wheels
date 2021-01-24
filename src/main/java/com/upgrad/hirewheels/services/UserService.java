package com.upgrad.hirewheels.services;

import com.upgrad.hirewheels.entities.User;
import com.upgrad.hirewheels.exceptions.APIException;
import com.upgrad.hirewheels.exceptions.BadCredentialsException;
import com.upgrad.hirewheels.exceptions.UserAlreadyExistsException;
import com.upgrad.hirewheels.exceptions.UserNotFoundException;

public interface UserService {
    User getUser(User user) throws APIException, UserNotFoundException, BadCredentialsException;
    User createUser(User user) throws APIException, UserAlreadyExistsException;
}
