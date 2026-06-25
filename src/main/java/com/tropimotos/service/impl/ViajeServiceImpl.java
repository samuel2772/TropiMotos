/**
 * Implementacion del servicio de gestion de viajes
 * 
 * Esta clase implementa la interfaz ViajeService y proporciona
 * la logica de negocio para todo el ciclo de vida de un viaje.
 */
package com.tropimotos.service.impl;

import com.tropimotos.dto.ViajeDTO;
import com.tropimotos.dto.ViajeRequest;
import com.tropimotos.entity.*;
import com.tropimotos.repository.*;
import com.tropimotos.service.ViajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *@Service: Marca la clase como un componente de servicio de Spring
 *@RequiredArgsConstructor: Lombok - genera constructor con campos final
 */
@Service
@RequiredArgsConstructor
public class ViajeServiceImpl implements ViajeService {

    /** Repositorio para acceder a datos de viajes */
    private final ViajeRepository viajeRepository;
    
    /** Repositorio para acceder a datos de usuarios */
    private final UsuarioRepository usuarioRepository;
    
    /** Repositorio para acceder a datos de chofers */
    private final ChoferRepository choferRepository;
    
    /** Repositorio para acceder a datos de vehiculos */
    private final VehiculoRepository vehiculoRepository;
    
    /** Repositorio para acceder a datos de estados de viaje */
    private final EstadoViajeRepository estadoViajeRepository;

    /**
     * Lista todos los viajes del sistema
     * 
     * @return Lista de ViajeDTO con todos los viajes
     */
    @Override
    public List<ViajeDTO> listarTodos() {
        return viajeRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca un viaje por su ID
     * 
     * @param id ID del viaje a buscar
     * @return Optional con el viaje si existe
     */
    @Override
    public Optional<ViajeDTO> buscarPorId(Integer id) {
        return viajeRepository.findById(id)
                .map(this::convertirADTO);
    }

    /**
     * Lista todos los viajes de un usuario especifico
     * 
     * @param idUsuario ID del usuario
     * @return Lista de viajes del usuario
     */
    @Override
    public List<ViajeDTO> buscarPorUsuarioId(Integer idUsuario) {
        return viajeRepository.findByUsuarioIdUsuario(idUsuario).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Lista todos los viajes de un chofer especifico
     * 
     * @param idChofer ID del chofer
     * @return Lista de viajes del chofer
     */
    @Override
    public List<ViajeDTO> buscarPorChoferId(Integer idChofer) {
        return viajeRepository.findByChoferIdChofer(idChofer).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Solicita un nuevo viaje
     * El viaje se crea en estado SOLICITADO y espera a que un chofer lo acepte
     * 
     * @param idUsuario ID del usuario que solicita el viaje
     * @param request Datos del viaje (origen y destino)
     * @return ViajeDTO con los datos del viaje creado
     * @throws RuntimeException si el usuario no existe
     */
    @Override
    @Transactional
    public ViajeDTO crear(Integer idUsuario, ViajeRequest request) {
        // Busca el usuario que solicita el viaje
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Busca el estado SOLICITADO para asignarlo al nuevo viaje
        EstadoViaje estadoSolicitado = estadoViajeRepository.findByNombreEstado("SOLICITADO")
                .orElseThrow(() -> new RuntimeException("Estado SOLICITADO no encontrado"));

        // Crea el viaje y establece sus propiedades
        Viaje viaje = new Viaje();
        viaje.setUsuario(usuario);
        viaje.setEstadoViaje(estadoSolicitado);
        viaje.setOrigenTexto(request.getOrigenTexto());
        viaje.setOrigenLatitud(request.getOrigenLatitud());
        viaje.setOrigenLongitud(request.getOrigenLongitud());
        viaje.setDestinoTexto(request.getDestinoTexto());
        viaje.setDestinoLatitud(request.getDestinoLatitud());
        viaje.setDestinoLongitud(request.getDestinoLongitud());
        viaje.setDistanciaKm(request.getDistanciaKm());
        viaje.setTiempoEstimadoMin(request.getTiempoEstimadoMin());
        viaje.setTarifaCalculada(request.getTarifaCalculada());

        viaje = viajeRepository.save(viaje);
        return convertirADTO(viaje);
    }

    /**
     * Un chofer acepta un viaje solicitado
     * Asigna el chofer y vehiculo al viaje y cambia el estado a ACEPTADO
     * 
     * @param idViaje ID del viaje a aceptar
     * @param idChofer ID del chofer que acepta
     * @param idVehiculo ID del vehiculo a usar
     * @return ViajeDTO con los datos actualizados
     * @throws RuntimeException si viaje, chofer o vehiculo no existen
     */
    @Override
    @Transactional
    public ViajeDTO aceptarViaje(Integer idViaje, Integer idChofer, Integer idVehiculo) {
        // Busca el viaje, chofer y vehiculo
        Viaje viaje = viajeRepository.findById(idViaje)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        Chofer chofer = choferRepository.findById(idChofer)
                .orElseThrow(() -> new RuntimeException("Chofer no encontrado"));

        Vehiculo vehiculo = vehiculoRepository.findById(idVehiculo)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        // Busca el estado ACEPTADO
        EstadoViaje estadoAceptado = estadoViajeRepository.findByNombreEstado("ACEPTADO")
                .orElseThrow(() -> new RuntimeException("Estado ACEPTADO no encontrado"));

        // Asigna el chofer, vehiculo y cambia el estado
        viaje.setChofer(chofer);
        viaje.setVehiculo(vehiculo);
        viaje.setEstadoViaje(estadoAceptado);
        viaje.setFechaAceptacion(LocalDateTime.now());

        viaje = viajeRepository.save(viaje);
        return convertirADTO(viaje);
    }

    /**
     * Inicia el viaje con el pasajero
     * Cambia el estado a EN_CURSO y registra la hora de inicio
     * 
     * @param idViaje ID del viaje a iniciar
     * @return ViajeDTO con los datos actualizados
     * @throws RuntimeException si el viaje no existe
     */
    @Override
    @Transactional
    public ViajeDTO iniciarViaje(Integer idViaje) {
        Viaje viaje = viajeRepository.findById(idViaje)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        // Busca el estado EN_CURSO
        EstadoViaje estadoEnCurso = estadoViajeRepository.findByNombreEstado("EN_CURSO")
                .orElseThrow(() -> new RuntimeException("Estado EN_CURSO no encontrado"));

        viaje.setEstadoViaje(estadoEnCurso);
        viaje.setFechaInicio(LocalDateTime.now());

        viaje = viajeRepository.save(viaje);
        return convertirADTO(viaje);
    }

    /**
     * Finaliza un viaje completado
     * Cambia el estado a COMPLETADO y registra la hora de fin
     * 
     * @param idViaje ID del viaje a finalizar
     * @return ViajeDTO con los datos actualizados
     * @throws RuntimeException si el viaje no existe
     */
    @Override
    @Transactional
    public ViajeDTO finalizarViaje(Integer idViaje) {
        Viaje viaje = viajeRepository.findById(idViaje)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        // Busca el estado COMPLETADO
        EstadoViaje estadoCompletado = estadoViajeRepository.findByNombreEstado("COMPLETADO")
                .orElseThrow(() -> new RuntimeException("Estado COMPLETADO no encontrado"));

        viaje.setEstadoViaje(estadoCompletado);
        viaje.setFechaFin(LocalDateTime.now());

        viaje = viajeRepository.save(viaje);
        return convertirADTO(viaje);
    }

    /**
     * Cancela un viaje
     * Cambia el estado a CANCELADO y registra quien lo cancelo y el motivo
     * 
     * @param idViaje ID del viaje a cancelar
     * @param canceladoPor Quien cancela (USUARIO, CHOFER, SISTEMA)
     * @param observacion Motivo de cancelacion
     * @return ViajeDTO con los datos actualizados
     * @throws RuntimeException si el viaje no existe
     */
    @Override
    @Transactional
    public ViajeDTO cancelarViaje(Integer idViaje, String canceladoPor, String observacion) {
        Viaje viaje = viajeRepository.findById(idViaje)
                .orElseThrow(() -> new RuntimeException("Viaje no encontrado"));

        // Busca el estado CANCELADO
        EstadoViaje estadoCancelado = estadoViajeRepository.findByNombreEstado("CANCELADO")
                .orElseThrow(() -> new RuntimeException("Estado CANCELADO no encontrado"));

        // Establece el nuevo estado y los datos de cancelacion
        viaje.setEstadoViaje(estadoCancelado);
        viaje.setCanceladoPor(canceladoPor);
        viaje.setObservacion(observacion);
        viaje.setFechaFin(LocalDateTime.now());

        viaje = viajeRepository.save(viaje);
        return convertirADTO(viaje);
    }

    /**
     * Convierte una entidad Viaje a un ViajeDTO
     * Incluye datos relacionados (usuario, chofer, vehiculo, estado)
     * 
     * @param viaje Entidad Viaje a convertir
     * @return ViajeDTO con todos los datos del viaje
     */
    private ViajeDTO convertirADTO(Viaje viaje) {
        ViajeDTO dto = new ViajeDTO();
        dto.setIdViaje(viaje.getIdViaje());
        dto.setIdUsuario(viaje.getUsuario().getIdUsuario());
        dto.setNombreUsuario(viaje.getUsuario().getNombre() + " " + viaje.getUsuario().getApellido());

        // Solo incluye datos del chofer si esta asignado
        if (viaje.getChofer() != null) {
            dto.setIdChofer(viaje.getChofer().getIdChofer());
            dto.setNombreChofer(viaje.getChofer().getUsuario().getNombre() + " " +
                               viaje.getChofer().getUsuario().getApellido());
        }

        // Solo incluye datos del vehiculo si esta asignado
        if (viaje.getVehiculo() != null) {
            dto.setIdVehiculo(viaje.getVehiculo().getIdVehiculo());
            dto.setPlacaVehiculo(viaje.getVehiculo().getPlaca());
        }

        dto.setIdEstado(viaje.getEstadoViaje().getIdEstado());
        dto.setNombreEstado(viaje.getEstadoViaje().getNombreEstado());
        dto.setOrigenTexto(viaje.getOrigenTexto());
        dto.setOrigenLatitud(viaje.getOrigenLatitud());
        dto.setOrigenLongitud(viaje.getOrigenLongitud());
        dto.setDestinoTexto(viaje.getDestinoTexto());
        dto.setDestinoLatitud(viaje.getDestinoLatitud());
        dto.setDestinoLongitud(viaje.getDestinoLongitud());
        dto.setDistanciaKm(viaje.getDistanciaKm());
        dto.setTiempoEstimadoMin(viaje.getTiempoEstimadoMin());
        dto.setTarifaCalculada(viaje.getTarifaCalculada());
        dto.setFechaSolicitud(viaje.getFechaSolicitud());
        dto.setFechaAceptacion(viaje.getFechaAceptacion());
        dto.setFechaInicio(viaje.getFechaInicio());
        dto.setFechaFin(viaje.getFechaFin());
        dto.setCanceladoPor(viaje.getCanceladoPor());
        dto.setObservacion(viaje.getObservacion());

        return dto;
    }
}
