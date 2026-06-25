package com.tropimotos.service.impl;

import com.tropimotos.dto.AuthResponse;
import com.tropimotos.dto.LoginRequest;
import com.tropimotos.dto.RegistroRequest;
import com.tropimotos.dto.UsuarioDTO;
import com.tropimotos.entity.Chofer;
import com.tropimotos.entity.Rol;
import com.tropimotos.entity.Usuario;
import com.tropimotos.entity.Vehiculo;
import com.tropimotos.repository.ChoferRepository;
import com.tropimotos.repository.RolRepository;
import com.tropimotos.repository.UsuarioRepository;
import com.tropimotos.repository.VehiculoRepository;
import com.tropimotos.security.JwtService;
import com.tropimotos.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final ChoferRepository choferRepository;
    private final VehiculoRepository vehiculoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales invalidas"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales invalidas");
        }

        String token = jwtService.generateToken(usuario);
        Integer idChofer = null;
        Integer idVehiculo = null;

        if (usuario.getRol() != null && "CHOFER".equalsIgnoreCase(usuario.getRol().getNombre())) {
            Optional<Chofer> choferOpt = choferRepository.findByUsuarioIdUsuario(usuario.getIdUsuario());

            if (choferOpt.isPresent()) {
                Chofer chofer = choferOpt.get();
                idChofer = chofer.getIdChofer();

                List<Vehiculo> vehiculos = vehiculoRepository.findByChoferIdChofer(idChofer);
                if (!vehiculos.isEmpty()) {
                    idVehiculo = vehiculos.get(0).getIdVehiculo();
                }
            }
        }

        return new AuthResponse(
                token,
                usuario.getIdUsuario(),
                idChofer,
                idVehiculo,
                usuario.getNombre() + " " + usuario.getApellido(),
                usuario.getEmail(),
                usuario.getRol().getNombre()
        );
    }

    @Override
    @Transactional
    public UsuarioDTO registro(RegistroRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya esta registrado");
        }

        Rol rolCliente = rolRepository.findByNombre("CLIENTE")
                .orElseThrow(() -> new RuntimeException("Rol CLIENTE no encontrado"));

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setTelefono(request.getTelefono());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(rolCliente);
        usuario.setEstadoCuenta("ACTIVO");

        usuario = usuarioRepository.save(usuario);
        return convertirADTO(usuario);
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setIdRol(usuario.getRol().getIdRol());
        dto.setNombreRol(usuario.getRol().getNombre());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setTelefono(usuario.getTelefono());
        dto.setEmail(usuario.getEmail());
        dto.setFotoPerfil(usuario.getFotoPerfil());
        dto.setEstadoCuenta(usuario.getEstadoCuenta());
        return dto;
    }
}
