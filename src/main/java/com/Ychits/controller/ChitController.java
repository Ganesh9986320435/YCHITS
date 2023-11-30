package com.Ychits.controller;

import com.Ychits.dao.ApiResponse;
import com.Ychits.dao.ChitDao;
import com.Ychits.data.dto.AddChit;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/chit")
public class ChitController {
    private final ChitDao chitDao;

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody AddChit addChit){
        return chitDao.create(addChit);
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
    public ResponseEntity<ApiResponse> getAll(@RequestParam @DefaultValue("0") int pageNo, @RequestParam @DefaultValue("10") int pageSize){
        return chitDao.getAll(pageNo,pageSize);
    }
}
