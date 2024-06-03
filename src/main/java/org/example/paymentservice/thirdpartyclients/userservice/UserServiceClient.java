package org.example.paymentservice.thirdpartyclients.userservice;

import org.example.paymentservice.dtos.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserServiceClient {

    public UserDto getUserById(long userId) {
        // Some logic to get user by id
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setName("Anurag Yadav");
        userDto.setEmail("omavihso@gmail.com");
        userDto.setPhoneno("+919717679964");
        return userDto;
    }

}
