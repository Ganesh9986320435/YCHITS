package com.Ychits.dao;

import com.Ychits.data.dto.AddChit;
import com.Ychits.data.entity.Chit;
import com.Ychits.data.repository.ChitRepository;
import com.Ychits.util.ResponseUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

import java.util.UUID;

@Data
@Component
@RequiredArgsConstructor
public class ChitDao {

    private final ChitRepository chitRepository;

    private final ModelMapper modelMapper;

    public ResponseEntity<ApiResponse> create(AddChit addChit){
        var chit=modelMapper.map(addChit, Chit.class);
        chit.setId(UUID.randomUUID().toString());
        return ResponseUtil.getCreatedResponse(chitRepository.save(chit));
    }

    public ResponseEntity<ApiResponse> delete(String id){
        var chit=chitRepository.findById(id).get();
        chitRepository.delete(chit);
        return ResponseUtil.getCreatedResponse(chit);
    }

    public ResponseEntity<ApiResponse> get(String id){
        return ResponseUtil.getCreatedResponse(chitRepository.findById(id).get());
    }

    public ResponseEntity<ApiResponse> getAll(int pageNo,int pageSize){
        return ResponseUtil.getCreatedResponse(chitRepository.findAll(PageRequest.of(pageNo,pageSize)));
    }
}
