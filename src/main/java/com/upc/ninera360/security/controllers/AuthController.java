package com.upc.ninera360.security.controllers;

import com.upc.ninera360.dtos.ClientesDTO;
import com.upc.ninera360.dtos.CuidadoresDTO;
import com.upc.ninera360.dtos.UserProfileDTO;
import com.upc.ninera360.security.dtos.AuthRequestDTO;
import com.upc.ninera360.security.dtos.AuthResponseDTO;
import com.upc.ninera360.security.dtos.RegistroClienteRequestDTO;
import com.upc.ninera360.security.dtos.RegistroCuidadorRequestDTO;
import com.upc.ninera360.security.entities.Role;
import com.upc.ninera360.security.entities.User;
import com.upc.ninera360.security.repositories.RoleRepository;
import com.upc.ninera360.security.repositories.UserRepository;
import com.upc.ninera360.security.services.CustomUserDetailsService;
import com.upc.ninera360.security.util.JwtUtil;
import com.upc.ninera360.services.ClientesService;
import com.upc.ninera360.services.CuidadoresService;
import com.upc.ninera360.services.UserProfileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserProfileService userProfileService;
    private final ClientesService clientesService;
    private final CuidadoresService cuidadoresService;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            CustomUserDetailsService userDetailsService,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            UserProfileService userProfileService,
            ClientesService clientesService,
            CuidadoresService cuidadoresService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userProfileService = userProfileService;
        this.clientesService = clientesService;
        this.cuidadoresService = cuidadoresService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody AuthRequestDTO authRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        AuthResponseDTO response = new AuthResponseDTO();
        response.setJwt(token);
        response.setRoles(roles);

        UserProfileDTO perfil = userProfileService.findByCorreo(authRequest.getUsername());
        response.setIdUsuario(perfil.getIdUsuario());

        if (roles.contains("ROLE_CLIENTE")) {
            ClientesDTO cliente = clientesService.findByIdUsuario(perfil.getIdUsuario());
            response.setIdCliente(cliente.getIdCliente());
        }

        if (roles.contains("ROLE_CUIDADOR")) {
            CuidadoresDTO cuidador = cuidadoresService.findByIdUsuario(perfil.getIdUsuario());
            response.setIdCuidador(cuidador.getIdCuidador());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        return ResponseEntity.ok().headers(headers).body(response);
    }

    @PostMapping("/register-cliente")
    @Transactional
    public ResponseEntity<?> registerCliente(@RequestBody RegistroClienteRequestDTO dto) {

        if (userRepository.findByUsername(dto.getCorreo()).isPresent()) {
            return ResponseEntity.status(409).body("El correo ya está registrado");
        }

        User user = new User();
        user.setUsername(dto.getCorreo());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(new HashSet<>());

        Role role = roleRepository.findByName("ROLE_CLIENTE")
                .orElseThrow(() -> new RuntimeException("Rol CLIENTE no encontrado"));

        user.getRoles().add(role);
        userRepository.save(user);

        UserProfileDTO profile = new UserProfileDTO();
        profile.setNombre(dto.getNombre());
        profile.setApellidos(dto.getApellidos());
        profile.setDni(dto.getDni());
        profile.setDireccion(dto.getDireccion());
        profile.setTelefono(dto.getTelefono());
        profile.setCorreo(dto.getCorreo());
        profile.setActivo(true);

        UserProfileDTO usuarioCreado = userProfileService.insertar(profile);

        ClientesDTO cliente = new ClientesDTO();
        cliente.setIdUsuario(usuarioCreado.getIdUsuario());
        cliente.setDescripcion("Cliente familiar");
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefonoContacto(dto.getTelefono());
        cliente.setActivo(true);

        clientesService.insertarCliente(cliente);

        return ResponseEntity.ok("Cliente registrado correctamente");
    }

    @PostMapping("/register-cuidador")
    @Transactional
    public ResponseEntity<?> registerCuidador(@RequestBody RegistroCuidadorRequestDTO dto) {

        if (userRepository.findByUsername(dto.getCorreo()).isPresent()) {
            return ResponseEntity.status(409).body("El correo ya está registrado");
        }

        User user = new User();
        user.setUsername(dto.getCorreo());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(new HashSet<>());

        Role role = roleRepository.findByName("ROLE_CUIDADOR")
                .orElseThrow(() -> new RuntimeException("Rol CUIDADOR no encontrado"));

        user.getRoles().add(role);
        userRepository.save(user);

        UserProfileDTO profile = new UserProfileDTO();
        profile.setNombre(dto.getNombre());
        profile.setApellidos(dto.getApellidos());
        profile.setDni(dto.getDni());
        profile.setDireccion(dto.getDireccion());
        profile.setTelefono(dto.getTelefono());
        profile.setCorreo(dto.getCorreo());
        profile.setActivo(true);

        UserProfileDTO usuarioCreado = userProfileService.insertar(profile);

        CuidadoresDTO cuidador = new CuidadoresDTO();
        cuidador.setIdUsuario(usuarioCreado.getIdUsuario());
        cuidador.setDescripcion(dto.getDescripcion());
        cuidador.setTarifa(dto.getTarifa());
        cuidador.setExperiencia(dto.getExperiencia());
        cuidador.setAntecedentes(dto.getAntecedentes());
        cuidador.setDisponibilidad(dto.getDisponibilidad());
        cuidador.setCalificacion(0.0);
        cuidador.setActivo(true);

        cuidadoresService.insertarCuidador(cuidador);

        return ResponseEntity.ok("Cuidador registrado correctamente");
    }
}