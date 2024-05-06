package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.User;
import sk.tuke.gamestudio.service.AuthenticationException;
import sk.tuke.gamestudio.service.AuthenticationService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.1.56:3000", "http://192.168.33.12:3000/"})
public class AuthenticationServiceRest {
    @Autowired
    AuthenticationService authenticationService;
    @GetMapping("/register")
    public boolean register(@RequestParam String username, @RequestParam String password){
        User user = new User();
        if(Objects.equals(password, "") || Objects.equals(username, "")) {
            throw new AuthenticationException("No password or username given");
        }
        user.setPassword(password);
        user.setUsername(username);
        return authenticationService.register(user);
    }
    @GetMapping("/login")
    public ResponseEntity<User> isRegistered(@RequestParam String username, @RequestParam String password){
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        if(authenticationService.isRegistered(user)) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
