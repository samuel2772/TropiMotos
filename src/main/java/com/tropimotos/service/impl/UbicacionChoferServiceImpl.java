/**
 * Implementacion del servicio de ubicaciones de choferes
 * 
 * Esta clase implementa la interfaz UbicacionChoferService y proporciona
 * la logica de negocio para el tracking GPS de los chofers.
 */
package com.tropimotos.service.impl;

import com.tropimotos.dto.UbicacionChoferDTO;
import com.tropimotos.dto.UbicacionChoferRequest;
import com.tropimotos.entity.Chofer;
import com.tropimotos.entity.UbicacionChofer;
import com.tropimotos.repository.ChoferRepository;
import com.tropimotos.repository.UbicacionChoferRepository;
import com.tropimotos.service.UbicacionChoferService;
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
public class UbicacionChoferServiceImpl implements UbicacionChoferService {

    /** Repositorio para acceder a datos de ubicaciones */
    private final UbicacionChoferRepository ubicacionRepository;
    
    /** Repositorio para acceder a datos de chofers */
    private final ChoferRepository choferRepository;

    /**
     * Lista todas las ubicaciones registradas en el sistema
     * 
     * @return Lista de UbicacionChoferDTO con todas las ubicaciones
     */
    @Override
    public List<UbicacionChoferDTO> listarTodos() {
        return ubicacionRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca una ubicacion por su ID
     * 
     * @param id ID de la ubicacion a buscar
     * @return Optional con la ubicacion si existe
     */
    @Override
    public Optional<UbicacionChoferDTO> buscarPorId(Integer id) {
        return ubicacionRepository.findById(id)
                .map(this::convertirADTO);
    }

    /**
     * Lista todas las ubicaciones de un chofer especifico
     * 
     * @param idChofer ID del chofer
     * @return Lista de ubicaciones del chofer
     */
    @Override
    public List<UbicacionChoferDTO> buscarPorChoferId(Integer idChofer) {
        return ubicacionRepository.findByChoferIdChofer(idChofer).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene la ubicacion mas reciente de un chofer
     * Ordena por fecha descendente y toma la primera
     * 
     * @param idChofer ID del chofer
     * @return Optional con la ultima ubicacion del chofer
     */
    @Override
    public Optional<UbicacionChoferDTO> obtenerUltimaUbicacion(Integer idChofer) {
        return ubicacionRepository.findTopByChoferIdChoferOrderByFechaHoraDesc(idChofer)
                .map(this::convertirADTO);
    }

    /**
     * Registra una nueva ubicacion para un chofer
     * Cada actualizacion crea un nuevo registro en la BD
     * 
     * @param request Datos de la ubicacion
     * @return UbicacionChoferDTO con los datos de la ubicacion creada
     * @throws RuntimeException si el chofer no existe
     */
    @Override
    @Transactional
    public UbicacionChoferDTO crear(UbicacionChoferRequest request) {
        // Busca el chofer
        Chofer chofer = choferRepository.findById(request.getIdChofer())
                .orElseThrow(() -> new RuntimeException("Chofer no encontrado"));

        // Crea la nueva ubicacion
        UbicacionChofer ubicacion = new UbicacionChofer();
        ubicacion.setChofer(chofer);
        ubicacion.setLatitud(request.getLatitud());
        ubicacion.setLongitud(request.getLongitud());
        ubicacion.setPrecisionGps(request.getPrecisionGps());

        ubicacion = ubicacionRepository.save(ubicacion);
        return convertirADTO(ubicacion);
    }

    /**
     * Actualiza la ubicacion de un chofer
     * Crea un nuevo registro (no actualiza el existente)
     * 
     * @param idChofer ID del chofer
     * @param request Nuevos datos de ubicacion
     * @return UbicacionChoferDTO con los datos actualizados
     * @throws RuntimeException si el chofer no existe
     */
    @Override
    @Transactional
    public UbicacionChoferDTO actualizarUbicacion(Integer idChofer, UbicacionChoferRequest request) {
        // Busca la ultima ubicacion del chofer o crea una nueva
        UbicacionChofer ubicacion = ubicacionRepository
                .findTopByChoferIdChoferOrderByFechaHoraDesc(idChofer)
                .orElse(new UbicacionChofer());

        // Busca el chofer
        Chofer chofer = choferRepository.findById(idChofer)
                .orElseThrow(() -> new RuntimeException("Chofer no encontrado"));

        // Actualiza los datos
        ubicacion.setChofer(chofer);
        ubicacion.setLatitud(request.getLatitud());
        ubicacion.setLongitud(request.getLongitud());
        ubicacion.setPrecisionGps(request.getPrecisionGps());

        ubicacion = ubicacionRepository.save(ubicacion);
        return convertirADTO(ubicacion);
    }

    /**
     * Convierte una entidad UbicacionChofer a un UbicacionChoferDTO
     * 
     * @param ubicacion Entidad UbicacionChofer a convertir
     * @return UbicacionChoferDTO con los datos de ubicacion
     */
    private UbicacionChoferDTO convertirADTO(UbicacionChofer ubicacion) {
        UbicacionChoferDTO dto = new UbicacionChoferDTO();
        dto.setIdUbicacion(ubicacion.getIdUbicacion());
        dto.setIdChofer(ubicacion.getChofer().getIdChofer());
        dto.setLatitud(ubicacion.getLatitud());
        dto.setLongitud(ubicacion.getLongitud());
        dto.setPrecisionGps(ubicacion.getPrecisionGps());
        dto.setFechaHora(ubicacion.getFechaHora());
        return dto;
    }
}
