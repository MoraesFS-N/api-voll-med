package med.voll.api.infra.security;


import jakarta.validation.Valid;
import med.voll.api.domain.entity.Usuario;
import med.voll.api.infra.model.AuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/login")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid AuthDTO data) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
        Authentication auth =  manager.authenticate(authToken);
        String tokenJWT = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new TokenDataJWT(tokenJWT));
    }

}
