package com.example.PowerUser;

import com.example.PowerUser.Model.PowerUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.net.URI;

public class PowerUserMain {
    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080//power/users/v1/user/9";

//        restTemplate.delete(url, new PowerUser());

//        PowerUser user = new PowerUser(12, "Jamila same", "jamila@mame.com", "Kaduna, Nigeria",31 );

//        restTemplate.put(url, user, new PowerUser());
//
//        PowerUser returnedUser = restTemplate.postForObject("http://localhost:8080//power/users/v1/user", user, PowerUser.class, new PowerUser());

//        ResponseEntity<PowerUser> returnedUser = restTemplate.postForEntity("http://localhost:8080//power/users/v1/user", user, PowerUser.class);
//        PowerUser extractedUser = returnedUser.getBody();

//        assert extractedUser != null;
//        System.out.printf("Scheme: %s, Query: %s, Path: %s", extractedUser.getFullName(), extractedUser.getEmail(), extractedUser.getAddress());
//
//        ResponseEntity<PowerUser> responseEntity = restTemplate.getForEntity(url, PowerUser.class, new PowerUser());
//        PowerUser powerUser = responseEntity.getBody();
//
//        assert powerUser != null;
//        System.out.printf("Power username: %s, Age: %d, Email: %s\n", powerUser.getFullName(), powerUser.getAge(), powerUser.getEmail());
//        System.out.printf("The response code: %s, The response header: %s", responseEntity.getStatusCode(), responseEntity.getHeaders());
//
//        PowerUser powerUser1 = restTemplate.getForObject(url, PowerUser.class);
//        assert powerUser1 != null;
//        System.out.printf("Power user name: %s, Age: %s", powerUser1.getFullName(), powerUser1.getAge());

    }

}