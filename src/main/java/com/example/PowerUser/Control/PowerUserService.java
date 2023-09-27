package com.example.PowerUser.Control;

import com.example.PowerUser.Exception.UserException;
import com.example.PowerUser.Model.PowerUser;
import com.example.PowerUser.Model.Role;
import com.example.PowerUser.Repository.PowerUserRepository;
import com.example.PowerUser.Service.MailMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class PowerUserService {
    @Autowired
    private PowerUserRepository userRepository;

    @Autowired
    private MailMessageService mailMessageService;


    @Cacheable("allPowerUsers")
    public List<PowerUser> getAllPowerUsers(){
        return userRepository.findAll();
    }

    @CacheEvict(value = "allPowerUsers", allEntries = true)
    public ResponseEntity<PowerUser> addUser(@RequestBody PowerUser powerUser){
        powerUser.setRole(Role.USER);

        userRepository.save(powerUser);
        mailMessageService.sendMail(powerUser.getUsername(), "Successfully registered", "Welcome\n User with %s username is good to have you here");
        return (ResponseEntity.ok(powerUser));
    }
    @Cacheable(value = "getUserById", key = "#id")
    public ResponseEntity<PowerUser> getPowerUser(Integer id){
        PowerUser user = userRepository.findById(id).orElseThrow(()-> new UserException("User not found"));
        return ResponseEntity.ok(user);
    }
    @Cacheable(value = "getUserByEmail", key = "#email")
    public ResponseEntity<PowerUser> getPowerUserByEmail( String email){
        PowerUser user = userRepository.findByUsername(email).orElseThrow(()-> new UserException("User not found"));
        return ResponseEntity.ok(user);
    }

    @Cacheable(value = "getUserByFullName", key = "fullName")
    public ResponseEntity<PowerUser> getPowerUserFullName(String fullName){
        PowerUser user = userRepository.findByFullName(fullName).orElseThrow(()-> new UserException("User not found"));
        return ResponseEntity.ok(user);
    }
    @CacheEvict(value = {"getUserById", "getUserByEmail", "getUserByFullName"}, key = "#id")
    public ResponseEntity<String> updatePowerUser(Integer id, PowerUser powerUser){
        PowerUser user = userRepository.findById(id).orElseThrow(() -> new UserException("User not found"));
        user.setFullName(powerUser.getFullName());
        user.setUsername(powerUser.getUsername());
        user.setAddress(powerUser.getAddress());
        user.setAge(powerUser.getAge());

        userRepository.save(user);

        return ResponseEntity.ok("User Successfully updated");
    }
    @CacheEvict(value = "getByUserId", key = "#id")
    public ResponseEntity<String> deletePowerUser(Integer id){
        userRepository.deleteById(id);
        return ResponseEntity.ok("User successfully deleted");
    }

}
