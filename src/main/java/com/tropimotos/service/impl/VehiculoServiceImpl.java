/**
 * Implementacion del servicio de gestion de vehiculos
 * 
 * Esta clase implementa la interfaz VehiculoService y proporciona
 * la logica de negocio para las operaciones CRUD sobre vehiculos.
 */
package com.tropimotos.service.impl;

import com.tropimotos.dto.VehiculoDTO;
import com.tropimotos.dto.VehiculoRequest;
import com.tropimotos.entity.Chofer;
import com.tropimotos.entity.Vehiculo;
import com.tropimotos.repository.ChoferRepository;
import com.tropimotos.repository.VehiculoRepository;
import com.tropimotos.service.VehiculoService;
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
public class VehiculoServiceImpl implements VehiculoService {

    /** Repositorio para acceder a datos de vehiculos */
    private final VehiculoRepository vehiculoRepository;
    
    /** Repositorio para acceder a datos de chofers */
    private final ChoferRepository choferRepository;

    /**
     * Lista todos los vehiculos del sistema
     * 
     * @return Lista de VehiculoDTO con todos los vehiculos
     */
    @Override
    public List<VehiculoDTO> listarTodos() {
        return vehiculoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca un vehiculo por su ID
     * 
     * @param id ID del vehiculo a buscar
     * @return Optional con el vehiculo si existe
     */
    @Override
    public Optional<VehiculoDTO> buscarPorId(Integer id) {
        return vehiculoRepository.findById(id)
                .map(this::convertirADTO);
    }

    /**
     * Lista todos los vehiculos de un chofer especifico
     * 
     * @param idChofer ID del chofer cuyos vehiculos se buscan
     * @return Lista de vehiculos del chofer
     */
    @Override
    public List<VehiculoDTO> buscarPorChoferId(Integer idChofer) {
        return vehiculoRepository.findByChoferIdChofer(idChofer).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Registra un nuevo vehiculo en el sistema
     * Asocia el vehiculo a un chofer existente
     * 
     * @param request Datos del vehiculo
     * @return VehiculoDTO con los datos del vehiculo creado
     * @throws RuntimeException si el chofer no existe
     */
    @Override
    @Transactional
    public VehiculoDTO crear(VehiculoRequest request) {
        // Busca el chofer propietario del vehiculo
        Chofer chofer = choferRepository.findById(request.getIdChofer())
                .orElseThrow(() -> new RuntimeException("Chofer no encontrado"));

        // Crea un nuevo vehiculo y establece sus propiedades
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setChofer(chofer);
        vehiculo.setTipoVehiculo(request.getTipoVehiculo());
        vehiculo.setMarca(request.getMarca());
        vehiculo.setModelo(request.getModelo());
        vehiculo.setColor(request.getColor());
        vehiculo.setPlaca(request.getPlaca());
        vehiculo.setAnio(request.getAnio());
        vehiculo.setEstadoVehiculo("ACTIVO");

        vehiculo = vehiculoRepository.save(vehiculo);
        return convertirADTO(vehiculo);
    }

    /**
     * Actualiza los datos de un vehiculo existente
     * 
     * @param id ID del vehiculo a actualizar
     * @param request Nuevos datos del vehiculo
     * @return VehiculoDTO con los datos actualizados
     * @throws RuntimeException si el vehiculo no existe
     */
    @Override
    @Transactional
    public VehiculoDTO actualizar(Integer id, VehiculoRequest request) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        // Actualiza todos los campos
        vehiculo.setTipoVehiculo(request.getTipoVehiculo());
        vehiculo.setMarca(request.getMarca());
        vehiculo.setModelo(request.getModelo());
        vehiculo.setColor(request.getColor());
        vehiculo.setPlaca(request.getPlaca());
        vehiculo.setAnio(request.getAnio());

        vehiculo = vehiculoRepository.save(vehiculo);
        return convertirADTO(vehiculo);
    }

    /**
     * Elimina un vehiculo del sistema
     * 
     * @param id ID del vehiculo a eliminar
     */
    @Override
    @Transactional
    public void eliminar(Integer id) {
        vehiculoRepository.deleteById(id);
    }

    /**
     * Convierte una entidad Vehiculo a un VehiculoDTO
     * Incluye el nombre completo del chofer propietario
     * 
     * @param vehiculo Entidad Vehiculo a convertir
     * @return VehiculoDTO con los datos del vehiculo
     */
    private VehiculoDTO convertirADTO(Vehiculo vehiculo) {
        VehiculoDTO dto = new VehiculoDTO();
        dto.setIdVehiculo(vehiculo.getIdVehiculo());
        dto.setIdChofer(vehiculo.getChofer().getIdChofer());
        // Concatena nombre y apellido del chofer
        dto.setNombreChofer(vehiculo.getChofer().getUsuario().getNombre() + " " +
                           vehiculo.getChofer().getUsuario().getApellido());
        dto.setTipoVehiculo(vehiculo.getTipoVehiculo());
        dto.setMarca(vehiculo.getMarca());
        dto.setModelo(vehiculo.getModelo());
        dto.setColor(vehiculo.getColor());
        dto.setPlaca(vehiculo.getPlaca());
        dto.setAnio(vehiculo.getAnio());
        dto.setEstadoVehiculo(vehiculo.getEstadoVehiculo());
        dto.setFechaRegistro(vehiculo.getFechaRegistro());
        return dto;
    }
}
