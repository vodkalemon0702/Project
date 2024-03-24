package com.fsse2401.project_man.api;

import com.fsse2401.project_man.data.domainObject.transaction.response.TransactionResponseData;
import com.fsse2401.project_man.data.dto.transaction.response.TransactionResponseDto;
import com.fsse2401.project_man.data.dto.transaction.response.TransactionSuccessResponseDto;
import com.fsse2401.project_man.service.TransactionService;
import com.fsse2401.project_man.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionApi {
    private final TransactionService transactionService;
    @Autowired
    public TransactionApi(TransactionService transactionService){
        this.transactionService = transactionService;
    }
    @PostMapping("/prepare")
    public TransactionResponseDto prepareTransaction(JwtAuthenticationToken jwtToken){
        TransactionResponseData transactionResponseData = transactionService.prepareTransaction(JwtUtil.getFirebaseUserData(jwtToken));
        return new TransactionResponseDto(transactionResponseData);
    }
    @GetMapping("/{tid}")
    public TransactionResponseDto getTransactionByTid(JwtAuthenticationToken jwtToken, @PathVariable Integer tid){
        TransactionResponseData transactionResponseData = transactionService.getTransactionByTid(JwtUtil.getFirebaseUserData(jwtToken),tid);
        return new TransactionResponseDto(transactionResponseData);
    }
    @PatchMapping("/{tid}/pay")
    public TransactionSuccessResponseDto payTransaction(JwtAuthenticationToken jwtToken, @PathVariable Integer tid){
        transactionService.payTransaction(JwtUtil.getFirebaseUserData(jwtToken),tid);
        return new TransactionSuccessResponseDto();
    }
    @PatchMapping("/{tid}/finish")
    public TransactionResponseDto finishTransactionByTid(JwtAuthenticationToken jwtToken,@PathVariable Integer tid){
        TransactionResponseData transactionResponseData = transactionService.finishTransaction(JwtUtil.getFirebaseUserData(jwtToken),tid);
        return new TransactionResponseDto(transactionResponseData);
    }
}
