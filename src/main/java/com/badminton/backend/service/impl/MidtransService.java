package com.badminton.backend.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.badminton.backend.dto.midtrans.MidtransRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MidtransService {
    @Value("${MIDTRANS_SERVER_KEY}")
    private String serverKey;
    @Value("${MIDTRANS_URL}")
    private String midtransUlr;

    private final RestTemplate restTemplate;

    public String createTransaction(MidtransRequest request) {

        try {

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);

            headers.setBasicAuth(serverKey, "", StandardCharsets.UTF_8);

            HttpEntity<MidtransRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    midtransUlr,
                    HttpMethod.POST,
                    entity,
                    Map.class);
            return (String) response.getBody().get("token");
        } catch (HttpStatusCodeException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);

        }

    }
}
