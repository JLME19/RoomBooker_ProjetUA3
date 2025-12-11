package RoomBooker_ProjetUA3;

import java.util.Optional;

public class authentification {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String username;
    
    private String password;
    
    private String role; // ADMIN, USER, etc.
    
    // Getters/Setters
}

// 2. Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByUsername(String username);
}

// 3. Service d'authentification
@Service
public class AuthService {
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public void enregistrerUtilisateur(String username, String password) {
        if(utilisateurRepository.existsByUsername(username)) {
            throw new RuntimeException("Nom d'utilisateur déjà pris");
        }
        
        Utilisateur user = new Utilisateur();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        
        utilisateurRepository.save(user);
    }
    
    public boolean authentifier(String username, String password) {
        Optional<Utilisateur> user = utilisateurRepository.findByUsername(username);
        return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
    }
}

// 4. Contrôleur
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
        authService.enregistrerUtilisateur(request.getUsername(), request.getPassword());
        return ResponseEntity.ok("Enregistrement réussi");
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        if(authService.authentifier(request.getUsername(), request.getPassword())) {
            return ResponseEntity.ok("Authentification réussie");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Échec de l'authentification");
    }
}

// 5. Configuration de sécurité
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
            .and()
            .httpBasic();
        
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
    

