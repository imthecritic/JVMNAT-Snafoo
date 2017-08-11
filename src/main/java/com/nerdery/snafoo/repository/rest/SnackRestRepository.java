package com.nerdery.snafoo.repository.rest;

import com.nerdery.snafoo.model.domain.rest.SuggestionModel;
import com.nerdery.snafoo.model.domain.rest.SnackRESTModel;
import com.nerdery.snafoo.repository.SnackRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import javax.inject.Inject;

import static org.hibernate.jpa.internal.EntityManagerImpl.LOG;


/**
 * Created by jasminefarley on 7/14/17.
 */

@Repository
public class SnackRestRepository implements SnackRepository {

    private String restUrl = "https://api-snacks.nerderylabs.com/v1/snacks/?ApiKey=825a0505-2620-4f23-92bc-e2d3cb7cfc14";
    private RestTemplate restTemplate = new RestTemplate();

    @Inject
    public SnackRestRepository(@Value("${snacks.nerdery.restUrl}") String restUrl) {
        restTemplate = new RestTemplate();
        this.restUrl = restUrl;
    }

    public SnackRESTModel[] getSnacks() {
        return restTemplate.getForObject(restUrl, SnackRESTModel[].class);
    }

    public String addSnackSuggestion(SuggestionModel suggestionModel) {
        String toReturn = "";
        MultiValueMap<String, Object> headers = new LinkedMultiValueMap<String, Object>();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        try {
            HttpEntity request = new HttpEntity(suggestionModel.toString(), headers);
            ResponseEntity<String> response = restTemplate.postForEntity(restUrl, request, String.class);
            toReturn = "Thanks! Your suggestion has been submitted!";
        } catch (HttpClientErrorException ce) {
            if (ce.getStatusCode().equals(HttpStatus.CONFLICT)) {
                toReturn = "Oh, it looks like someone already suggested that! Go vote for it!";
            }

            if (ce.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                toReturn = "Uhhh, it looks like something went wrong. Probabaly the programmer's fault.";
            }

        }
        return toReturn;


    }
}
