package med.voll.api.controller;


import jakarta.validation.Valid;
import med.voll.api.domain.usuario.UsuarioRepository;
import med.voll.api.domain.usuario.dto.DadosAutentificacao;
import med.voll.api.domain.usuario.dto.DadosRegistroUsuario;
import med.voll.api.domain.usuario.enums.RegraUsuario;
import med.voll.api.domain.usuario.service.TokenService;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.dto.DadosTokenJWT;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autenticar")
public class AutentificacaoController implements med.voll.api.controller.doc.AutentificacaoDoc {

    private final AuthenticationManager manager;

    private final TokenService tokenService;

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    public AutentificacaoController(AuthenticationManager manager,
                                    TokenService tokenService,
                                    UsuarioRepository usuarioRepository,
                                    PasswordEncoder passwordEncoder) {
        this.manager = manager;
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    @Override
    public ResponseEntity<DadosTokenJWT> login(@RequestBody @Valid DadosAutentificacao body) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(body.login(), body.senha());

        var autentificacao = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken((Usuario) autentificacao.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

    @PostMapping("/registrar")
    public ResponseEntity<Void> registrar(@RequestBody @Valid DadosRegistroUsuario body) {
        if (this.usuarioRepository.findByLogin(body.login()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String senhaEncriptada = passwordEncoder.encode(body.senha());

        Usuario usuario = new Usuario(body.login(), senhaEncriptada, RegraUsuario.COMUM);

        this.usuarioRepository.save(usuario);

        return ResponseEntity.ok().build();
    }
}
