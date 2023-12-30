package com.Ychits.dao;

import com.Ychits.data.dto.AddChit;
import com.Ychits.data.dto.AddUser;
import com.Ychits.data.entity.Chit;
import com.Ychits.data.entity.User;
import com.Ychits.data.repository.ChitRepository;
import com.Ychits.data.repository.UserRepository;
import com.Ychits.util.ResponseUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;

@Data
@Component
@RequiredArgsConstructor
public class UserDao {
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<ApiResponse> create(AddUser addUser){
        if(userRepository.existsByEmailOrMobile(addUser.getEmail(),addUser.getMobile())) {
            throw new NoSuchElementException("user already exists with given email or mobile");
        }
        var user=modelMapper.map(addUser, User.class);
        user.setId(UUID.randomUUID().toString());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return ResponseUtil.getCreatedResponse(userRepository.save(user));
    }

    public ResponseEntity<ApiResponse> update(AddUser addUser,String id) {
        var user=userRepository.findById(id).orElseThrow(()->new NoSuchElementException("no data found with given id"));
        modelMapper.map(addUser,user);
        return ResponseUtil.getCreatedResponse(userRepository.save(user));
    }

    public ResponseEntity<ApiResponse> delete(String id){
        var user=userRepository.findById(id).orElseThrow(()->new NoSuchElementException("no data found with given id"));
        userRepository.delete(user);
        return ResponseUtil.getOkResponse(user);
    }

    public ResponseEntity<ApiResponse> get(String id){
        return ResponseUtil.getOkResponse(userRepository.findById(id).orElseThrow(()->new NoSuchElementException("no data found with given id")));
    }

    public ResponseEntity<ApiResponse> getAll(int pageNo,int pageSize){
        return ResponseUtil.getOkResponse(userRepository.findAll(PageRequest.of(pageNo,pageSize)));
    }

    public ResponseEntity<ApiResponse> getByEmail(String email) {
        return ResponseUtil.getOkResponse(userRepository.findByEmail(email));
    }

    public ResponseEntity<ApiResponse> getByEmailOrMobile(String value) {
        return ResponseUtil.getOkResponse(userRepository.findByEmailOrMobile(value));
    }
}
