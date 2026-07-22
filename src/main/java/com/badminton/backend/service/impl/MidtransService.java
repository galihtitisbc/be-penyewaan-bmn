package com.badminton.backend.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.badminton.backend.dto.midtrans.MidtransRequest;
import com.midtrans.Midtrans;
import com.midtrans.httpclient.SnapApi;
import com.midtrans.httpclient.error.MidtransError;
import java.util.Map;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class MidtransService {
    @Value("${MIDTRANS_SERVER_KEY}")
    private String serverKey;
    private final ObjectMapper objectMapper;

    public String createTransaction(MidtransRequest request) throws MidtransError {
        Midtrans.serverKey = serverKey;
        Midtrans.isProduction = false;

        Map<String, Object> requestBody = objectMapper.convertValue(
                request, new TypeReference<Map<String, Object>>() {
                });
        return SnapApi.createTransactionToken(requestBody);
    }
}
