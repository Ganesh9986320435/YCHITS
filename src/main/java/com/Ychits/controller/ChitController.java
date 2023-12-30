package com.Ychits.controller;

import com.Ychits.dao.ApiResponse;
import com.Ychits.dao.ChitDao;
import com.Ychits.data.dto.AddAuction;
import com.Ychits.data.dto.AddChit;
import com.Ychits.data.dto.AddChitUser;
import com.Ychits.data.dto.AddUser;
import com.Ychits.data.entity.Auction;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/chit")
public class ChitController {
    private final ChitDao chitDao;
    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody AddChit addChit,@RequestParam String userId){
        return chitDao.create(addChit,userId);
    }

    @PutMapping
    public ResponseEntity<ApiResponse> update(@RequestBody AddChit addChit, @RequestParam String id){
        return chitDao.update(addChit,id);
    }
    @DeleteMapping
    public ResponseEntity<ApiResponse> delete(@RequestParam @NotBlank(message = "id should not be blank")  String id){
        return chitDao.delete(id);
    }
    @GetMapping
    public ResponseEntity<ApiResponse> get(@RequestParam @NotBlank(message = "id should not be blank")  String id){
        return chitDao.get(id);
    }
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll(@RequestParam @DefaultValue("0") int page, @RequestParam @DefaultValue("10") int size){
        return chitDao.getAll(page,size);
    }

    @PutMapping("/addUserToChit")
    public ResponseEntity<ApiResponse> addUserToChit(@RequestBody AddChitUser addUser, @RequestParam String chitId){
        return chitDao.addUserToChit(addUser,chitId);
    }

    @PutMapping("/addExistingUserToChit")
    public ResponseEntity<ApiResponse> addExistingUserToChit(@RequestParam String userId,@RequestParam String chitId){
        return chitDao.addExistingUserToChit(userId,chitId);
    }

    @GetMapping("/getChitUsers")
    public ResponseEntity<ApiResponse> getChitUsers(@RequestParam String chitId){
        return chitDao.getChitUsers(chitId);
    }

    @GetMapping("/getChitsAssociatedTo")
    public ResponseEntity<ApiResponse> getChitsAssociatedTo(@RequestParam String userId){
        return chitDao.getChitsAssociatedTo(userId);
    }

    @GetMapping("/getChitsMaintainedBy")
    public ResponseEntity<ApiResponse> getChitsMaintainedBy(@RequestParam String userId){
        return chitDao.getChitsMaintainedBy(userId);
    }

    @PutMapping("/addAuction")
    public ResponseEntity<ApiResponse> addAuction(@RequestBody AddAuction auction, @RequestParam String chitId){
        return chitDao.addAuction(auction,chitId);
    }

    @GetMapping("/getAuctions")
    public ResponseEntity<ApiResponse> getAuctions(@RequestParam String chitId){
        return chitDao.getAuctions(chitId);
    }






    //get chit users//
    //get chit auctions//
    //add user to chit//
    //add auction//
    //get chits associated to(for chit payer)//
    //gets chits created by for chit maintainer//
    //JWT token//

}
