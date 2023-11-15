package com.walmart.LoginModule.controllers;

//import com.walmart.LoginModule.payload.response.UserInfoResponse;
//import com.walmart.LoginModule.security.services.UserDetailsImpl;
//import org.springframework.http.HttpHeaders;
import com.walmart.LoginModule.models.User;
import com.walmart.LoginModule.payload.request.Deposit;
import com.walmart.LoginModule.payload.response.MessageResponse;
import com.walmart.LoginModule.repository.UserRepository;
import com.walmart.LoginModule.security.services.UserDetailsImpl;
import com.walmart.LoginModule.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @PostMapping("/deposit")
  public ResponseEntity<MessageResponse> deposit(@RequestBody Deposit deposit) {
    Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
    String c_user = authentication1.getName();
    Optional<User> optuser = userDetailsService.getUserByUsername(c_user);
    if (optuser.isPresent()) {
      User user = optuser.get();
      userDetailsService.deposit(user, deposit.getAmount());
      return ResponseEntity.ok().body(new MessageResponse("Deposit Successful !!"));
    } else {
      return ResponseEntity.ok().body(new MessageResponse("Error: Login Required"));
    }
  }

  @PostMapping("/withdraw")
  public ResponseEntity<MessageResponse> withdraw(@RequestBody Deposit deposit) {
    Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
    String c_user = authentication1.getName();
    Optional<User> optuser = userDetailsService.getUserByUsername(c_user);
    if (optuser.isPresent()) {
      User user = optuser.get();
      userDetailsService.withdraw(user, deposit.getAmount());
      return ResponseEntity.ok().body(new MessageResponse("Withdraw successful !!"));
    } else {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Login Required"));
    }
  }
}
