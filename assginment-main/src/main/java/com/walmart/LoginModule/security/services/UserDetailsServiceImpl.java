package com.walmart.LoginModule.security.services;

import com.walmart.LoginModule.Exception.UserException;
import com.walmart.LoginModule.models.User;
import com.walmart.LoginModule.payload.response.MessageResponse;
import com.walmart.LoginModule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { //username
    User user = userRepository.findByEmail(email) //username
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + email));

    return (UserDetails) UserDetailsImpl.build(user);
  }

  public Optional<User> getUserByUsername(String username){
    return userRepository.findByUsername(username);
  }
  public void deposit(User user, BigDecimal amount ){
    BigDecimal currentAmount = user.getAmount();
    user.setAmount(currentAmount.add(amount));
    userRepository.save(user);
  }

  public void withdraw(User user, BigDecimal amount){
    BigDecimal currentAmount = user.getAmount();
    if(currentAmount.compareTo(amount) >= 0){
      user.setAmount(currentAmount.subtract(amount));
      userRepository.save(user);
    }else {
      throw new UserException("Error: Insufficient Fund");
    }
  }
}
