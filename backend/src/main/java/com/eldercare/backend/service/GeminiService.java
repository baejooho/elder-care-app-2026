package com.eldercare.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class GeminiService {

    @Value("${gemini.api-key}")
    private String apiKey;

    @Value("${gemini.api-url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String chat(String userMessage) {
        String url = apiUrl + "?key=" + apiKey;

        Map<String, Object> part = new HashMap<>();
        part.put("text", "당신은 독거 어르신과 대화하는 친절한 AI 친구입니다. 따뜻하고 공감적으로 대화하세요. 짧고 간결하게 1-2문장으로 답하세요.\n\n어르신: " + userMessage);

        Map<String, Object> content = new HashMap<>();
        content.put("parts", List.of(part));

        Map<String, Object> body = new HashMap<>();
        body.put("contents", List.of(content));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        List<Map> candidates = (List<Map>) response.getBody().get("candidates");
        Map firstCandidate = candidates.get(0);
        Map contentMap = (Map) firstCandidate.get("content");
        List<Map> parts = (List<Map>) contentMap.get("parts");
        return (String) parts.get(0).get("text");
    }
}
