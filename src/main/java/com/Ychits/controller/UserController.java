package com.Ychits.controller;

import com.Ychits.dao.ApiResponse;
import com.Ychits.dao.UserDao;
import com.Ychits.data.dto.AddUser;
import com.Ychits.data.entity.User;
import com.Ychits.data.repository.UserRepository;
import com.Ychits.util.jwt.ErrorResponse;
import com.Ychits.util.jwt.JwtUtil;
import com.Ychits.util.jwt.LoginRequest;
import com.Ychits.util.jwt.LoginResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/user")
public class UserController {
    private final UserDao userDao;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> create(@RequestBody AddUser addUser){
        return userDao.create(addUser);
    }

    @PutMapping
    public ResponseEntity<ApiResponse> update(@RequestBody AddUser addUser,@RequestParam String id){
        return userDao.update(addUser,id);
    }
    @DeleteMapping
    public ResponseEntity<ApiResponse> delete(@RequestParam @NotBlank(message = "id should not be blank")  String id){
        return userDao.delete(id);
    }
    @GetMapping
    public ResponseEntity<ApiResponse> get(@RequestParam @NotBlank(message = "id should not be blank")  String id){
        return userDao.get(id);
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll(@RequestParam @DefaultValue("0") int pageNo, @RequestParam @DefaultValue("10") int pageSize){
        return userDao.getAll(pageNo,pageSize);
    }
    @GetMapping("/byEmail")
    public ResponseEntity<ApiResponse> getByEmail(@RequestParam @NotBlank(message = "email should not be blank")  String email){
        return userDao.getByEmail(email);
    }

    @GetMapping("/existByEmailOrMobile")
    public ResponseEntity<ApiResponse> getByEmailOrMobile(@RequestParam @NotBlank(message = "email should not be blank")  String value){
        return userDao.getByEmailOrMobile(value);
    }
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginReq)  {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
            String email = authentication.getName();
            User user = new User(email,"");
            String token = jwtUtil.createToken(user);
            User loggedUser=userRepository.findByEmail(user.getEmail());
            LoginResponse loginRes = new LoginResponse(loggedUser.getId(), loggedUser.getUserType(),token);
            return ResponseEntity.ok(loginRes);

        }catch (BadCredentialsException e){
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST,"Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }


}
