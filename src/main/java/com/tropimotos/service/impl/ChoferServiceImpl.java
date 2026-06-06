/**
 * Implementacion del servicio de gestion de chofers
 * 
 * Esta clase implementa la interfaz ChoferService y proporciona
 * la logica de negocio para las operaciones sobre chofers.
 */
package com.tropimotos.service.impl;

import com.tropimotos.dto.ChoferDTO;
import com.tropimotos.dto.ChoferRequest;
import com.tropimotos.entity.Chofer;
import com.tropimotos.entity.Usuario;
import com.tropimotos.repository.ChoferRepository;
import com.tropimotos.repository.UsuarioRepository;
import com.tropimotos.service.ChoferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *@Service: Marca la clase como un componente de servicio de Spring
 *@RequiredArgsConstructor: Lombok - genera constructor con campos final
 */
@Service
@RequiredArgsConstructor
public class ChoferServiceImpl implements ChoferService {

    /** Repositorio para acceder a datos de chofers */
    private final ChoferRepository choferRepository;
    
    /** Repositorio para acceder a datos de usuarios */
    private final UsuarioRepository usuarioRepository;

    /**
     * Lista todos los chofers del sistema
     * 
     * @return Lista de ChoferDTO con todos los chofers
     */
    @Override
    public List<ChoferDTO> listarTodos() {
        return choferRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca un chofer por su ID
     * 
     * @param id ID del chofer a buscar
     * @return Optional con el chofer si existe, vacio si no
     */
    @Override
    public Optional<ChoferDTO> buscarPorId(Integer id) {
        return choferRepository.findById(id)
                .map(this::convertirADTO);
    }

    /**
     * Busca un chofer asociado a un usuario especifico
     * Un usuario solo puede tener un registro de chofer
     * 
     * @param idUsuario ID del usuario asociado
     * @return Optional con el chofer si existe
     */
    @Override
    public Optional<Chofer> buscarPorUsuarioId(Integer idUsuario) {
        return choferRepository.findByUsuarioIdUsuario(idUsuario);
    }

    /**
     * Registra un nuevo chofer en el sistema
     * Asocia un usuario existente como chofer
     * 
     * @param request Datos del chofer (ID de usuario, licencia, CI)
     * @return ChoferDTO con los datos del chofer creado
     * @throws RuntimeException si el usuario no existe
     */
    @Override
    @Transactional
    public ChoferDTO crear(ChoferRequest request) {
        // Busca el usuario que sera convertido en chofer
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crea un nuevo chofer y establece sus propiedades
        Chofer chofer = new Chofer();
        chofer.setUsuario(usuario);
        chofer.setNumeroLicencia(request.getNumeroLicencia());
        chofer.setCi(request.getCi());
        // Por defecto, un nuevo chofer no esta disponible ni verificado
        chofer.setDisponible(false);
        chofer.setVerificado(false);
        chofer.setEstadoChofer("ACTIVO");

        // Guarda el chofer en la base de datos
        chofer = choferRepository.save(chofer);
        return convertirADTO(chofer);
    }

    /**
     * Actualiza los datos de un chofer existente
     * 
     * @param id ID del chofer a actualizar
     * @param request Nuevos datos del chofer
     * @return ChoferDTO con los datos actualizados
     * @throws RuntimeException si el chofer no existe
     */
    @Override
    @Transactional
    public ChoferDTO actualizar(Integer id, ChoferRequest request) {
        Chofer chofer = choferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chofer no encontrado"));

        // Actualiza los campos permitidos
        chofer.setNumeroLicencia(request.getNumeroLicencia());
        chofer.setCi(request.getCi());

        chofer = choferRepository.save(chofer);
        return convertirADTO(chofer);
    }

    /**
     * Actualiza la disponibilidad de un chofer
     * Se usa cuando un chofer se conecta o desconecta
     * 
     * @param id ID del chofer
     * @param disponible Nuevo estado de disponibilidad
     * @return ChoferDTO con los datos actualizados
     * @throws RuntimeException si el chofer no existe
     */
    @Override
    @Transactional
    public ChoferDTO actualizarDisponibilidad(Integer id, Boolean disponible) {
        Chofer chofer = choferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chofer no encontrado"));

        chofer.setDisponible(disponible);
        chofer = choferRepository.save(chofer);
        return convertirADTO(chofer);
    }

    /**
     * Elimina un chofer del sistema
     * 
     * @param id ID del chofer a eliminar
     */
    @Override
    @Transactional
    public void eliminar(Integer id) {
        choferRepository.deleteById(id);
    }

    /**
     * Lista chofers verificados y disponibles
     * Solo retorna aquellos que pueden接受了 viajes
     * 
     * @return Lista de chofers disponibles y verificados
     */
    @Override
    public List<ChoferDTO> listarDisponibles() {
        return choferRepository.findByVerificadoTrueAndDisponibleTrue().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una entidad Chofer a un ChoferDTO
     * Incluye datos del usuario asociado
     * 
     * @param chofer Entidad Chofer a convertir
     * @return ChoferDTO con los datos del chofer
     */
    private ChoferDTO convertirADTO(Chofer chofer) {
        ChoferDTO dto = new ChoferDTO();
        dto.setIdChofer(chofer.getIdChofer());
        dto.setIdUsuario(chofer.getUsuario().getIdUsuario());
        dto.setNombreUsuario(chofer.getUsuario().getNombre());
        dto.setApellidoUsuario(chofer.getUsuario().getApellido());
        dto.setTelefonoUsuario(chofer.getUsuario().getTelefono());
        dto.setEmailUsuario(chofer.getUsuario().getEmail());
        dto.setFotoPerfil(chofer.getUsuario().getFotoPerfil());
        dto.setNumeroLicencia(chofer.getNumeroLicencia());
        dto.setCi(chofer.getCi());
        dto.setDisponible(chofer.getDisponible());
        dto.setVerificado(chofer.getVerificado());
        dto.setEstadoChofer(chofer.getEstadoChofer());
        dto.setFechaRegistro(chofer.getFechaRegistro());
        return dto;
    }
}
