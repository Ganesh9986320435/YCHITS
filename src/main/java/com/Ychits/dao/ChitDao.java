package com.Ychits.dao;

import com.Ychits.data.dto.AddAuction;
import com.Ychits.data.dto.AddChit;
import com.Ychits.data.dto.AddChitUser;
import com.Ychits.data.dto.AddUser;
import com.Ychits.data.entity.Auction;
import com.Ychits.data.entity.Chit;
import com.Ychits.data.entity.User;
import com.Ychits.data.repository.ChitRepository;
import com.Ychits.data.repository.UserRepository;
import com.Ychits.util.ResponseUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Component
@RequiredArgsConstructor
public class ChitDao {

    private final ChitRepository chitRepository;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    public ResponseEntity<ApiResponse> create(AddChit addChit,String userId){
        var chit=modelMapper.map(addChit, Chit.class);
        chit.setId(UUID.randomUUID().toString());
        chit.createEntity(userId,userRepository.findById(userId).orElseThrow(()->new NoSuchElementException("no data found with given id")).getName());
        return ResponseUtil.getCreatedResponse(chitRepository.save(chit));
    }

    public ResponseEntity<ApiResponse> update(AddChit addChit, String chitId) {
        var chit=chitRepository.findById(chitId).orElseThrow(()->new NoSuchElementException("no data found with given id"));
        modelMapper.map(addChit,chit);
        chit.updateEntity(chit.getCreatedBy(),chit.getCreatedByUname());
        return ResponseUtil.getOkResponse(chitRepository.save(chit));
    }

    public ResponseEntity<ApiResponse> delete(String id){
        var chit=chitRepository.findById(id).orElseThrow(()->new NoSuchElementException("no data found with given id"));
        chitRepository.delete(chit);
        return ResponseUtil.getOkResponse(chit);
    }

    public ResponseEntity<ApiResponse> get(String id){
        return ResponseUtil.getOkResponse(chitRepository.findById(id).get());
    }

    public ResponseEntity<ApiResponse> getAll(int pageNo,int pageSize){
        return ResponseUtil.getOkResponse(chitRepository.findAll(PageRequest.of(pageNo,pageSize)));
    }

    public ResponseEntity<ApiResponse> addExistingUserToChit(String userId,String chitId) {
        var chit=chitRepository.findById(chitId).orElseThrow(()->new NoSuchElementException("no data found with given id"));
        if(chit.getUserId()!=null) {
            var users=chit.getUserId();
            users.add(userId);
            chit.setUserId(users);
        }else {
            List<String> users=new ArrayList<>();
            users.add(userId);
            chit.setUserId(users);
        }
        return ResponseUtil.getOkResponse(chitRepository.save(chit));
    }

    public ResponseEntity<ApiResponse> addUserToChit(AddChitUser addUser, String chitId) {
        var user=modelMapper.map(addUser, User.class);
        user.setId(UUID.randomUUID().toString());
        var chit=chitRepository.findById(chitId).orElseThrow(()->new NoSuchElementException("no data found with given id"));

        if(chit.getUserId()!=null) {
            var users=chit.getUserId();
            users.add(user.getId());
            chit.setUserId(users);
        }else {
            List<String> users=new ArrayList<>();
            users.add(user.getId());
            chit.setUserId(users);
        }
        userRepository.save(user);
        return ResponseUtil.getOkResponse(chitRepository.save(chit));
    }

    public ResponseEntity<ApiResponse> getChitUsers(String chitId) {
        List<User> users=new ArrayList<>();
        var chit=chitRepository.findById(chitId).orElseThrow(()->new NoSuchElementException("no data found with given id"));
        chit.getUserId().forEach(user->{
            users.add(userRepository.findById(user).orElseThrow(()->new NoSuchElementException("one of the user is missing")));
        });
        return ResponseUtil.getOkResponse(users);
    }

    public ResponseEntity<ApiResponse> getChitsAssociatedTo(String userId) {
        return ResponseUtil.getOkResponse(chitRepository.findAll().stream().filter(chit -> Objects.nonNull(chit.getUserId()) && chit.getUserId().contains(userId)).toList());
    }

    public ResponseEntity<ApiResponse> getChitsMaintainedBy(String userId) {
        return ResponseUtil.getOkResponse(chitRepository.findAll().stream().filter(chit -> chit.getCreatedBy().equals(userId)).toList());
    }

    public ResponseEntity<ApiResponse> addAuction(AddAuction auction, String chitId) {
        int size=0;
        var chit=chitRepository.findById(chitId).get();
        var mappedAuction=modelMapper.map(auction, Auction.class);
        if(chit.getAuction()!=null)
        size=chit.getAuction().keySet().size();
        mappedAuction.setMonthNumber(size+1);
        mappedAuction.setMonthName(LocalDateTime.now().getMonth().toString());
        mappedAuction.setReducedAmount((auction.getAmount() - Integer.parseInt(chit.getMaintainerCharge())) / chit.getUserId().size());
        mappedAuction.setAmountPayable(Integer.parseInt(chit.getAmount())-mappedAuction.getReducedAmount());
        if(chit.getAuction()==null){
            Map<String,Auction> settingAuction=new HashMap<>();
            settingAuction.put(LocalDateTime.now().getMonth().toString(),mappedAuction);
            chit.setAuction(settingAuction);
        }
        else {
            var settingAuction=chit.getAuction();
            settingAuction.put(LocalDateTime.now().getMonth().toString(),mappedAuction);
            chit.setAuction(settingAuction);
        }
        return ResponseUtil.getOkResponse(chitRepository.save(chit));
    }

    public ResponseEntity<ApiResponse> getAuctions(String chitId) {
        var chit=chitRepository.findById(chitId).get();
        if(chit.getAuction()==null)
            throw new NoSuchElementException("no auctions present for this chit");
        return ResponseUtil.getOkResponse(chit.getAuction().values());
    }


}
