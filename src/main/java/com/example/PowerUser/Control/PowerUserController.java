package com.example.PowerUser.Control;
import com.example.PowerUser.Model.LoginRequest;
import com.example.PowerUser.Model.PowerUser;
import com.example.PowerUser.Repository.PowerUserRepository;
import com.example.PowerUser.Service.JWTService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/power")
public class PowerUserController {



    @Autowired
    private PowerUserRepository userRepository;

    @Autowired
    private PowerUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @PostMapping( "/add_user" )
    public ResponseEntity<PowerUser> addUser(@RequestBody @Valid PowerUser powerUser){
        return userService.addUser(powerUser);
    }

    @PostMapping("login")
    public String powerUserLogin(@RequestBody LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getUsername()));

        PowerUser user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        String token = jwtService.generateToken(user);

        return token;
    }

    @GetMapping("/user")
    @PreAuthorize("ADMIN")
    public List<PowerUser> getAllPowerUsers(){
        List<PowerUser> powerUsers = userService.getAllPowerUsers();
        return powerUsers;
    }
    /*
        @GetMapping("/error")
        public ResponseEntity<String> error(HttpStatus httpStatus){

            if(HttpStatus.NOT_FOUND.is3xxRedirection()){
                return ResponseEntity.badRequest().body(new GlobalExceptions()
                                .handleResourceNotFound(new ResourceNotFoundException()))
                        .getBody();
            }
            if(HttpStatus.INTERNAL_SERVER_ERROR.is4xxClientError()){
                return ResponseEntity.badRequest().body(new GlobalExceptions()
                                .handleExceptions(new Exception()))
                        .getBody();
            }
            return ResponseEntity.internalServerError().body(new GlobalExceptions()
                            .handleResourceNotFound(new ResourceNotFoundException()))
                    .getBody();

        }

        @GetMapping("/errors")
        public ResponseEntity<String> notFoundHandler(){
            return ResponseEntity.badRequest().body(new GlobalExceptions()
                            .handleResourceNotFound(new ResourceNotFoundException()))
                    .getBody();
        }
    */
    @GetMapping("/user/{id}")
    @ResponseBody
    @PreAuthorize("{'USER', 'ADMIN'}")
    public ResponseEntity<PowerUser> getPowerUser(@PathVariable Integer id){
        return userService.getPowerUser(id);
    }

    @GetMapping("/user-mail/{email}")
    @ResponseBody
    @PreAuthorize("{'USER', 'ADMIN'}")
    public ResponseEntity<PowerUser> getPowerUserByEmail(@PathVariable String email){
        return userService.getPowerUserByEmail(email);
    }

    @GetMapping("/user-name/{fullName}")
    @ResponseBody
    @PreAuthorize("{'USER', 'ADMIN'}")
    public ResponseEntity<PowerUser> getPowerUserByFullName(@PathVariable String fullName){
        return userService.getPowerUserFullName(fullName);
    }

    @PutMapping("/user/{id}")
    @PreAuthorize("{'USER', 'ADMIN'}")
    public ResponseEntity<String> updatePowerUser( @PathVariable Integer id, @RequestBody PowerUser powerUser ){
        userService.updatePowerUser(id, powerUser);

        return ResponseEntity.ok("User successfully updated");

    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("{'USER', 'ADMIN'}")
    public ResponseEntity<String> deletePowerUser(@PathVariable Integer id){
        userService.deletePowerUser(id);
        return ResponseEntity.ok("User successfully deleted");
    }
}
