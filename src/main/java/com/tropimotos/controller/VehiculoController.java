/**
 * Controller VehiculoController - Endpoints para gestion de vehiculos
 * 
 * Este controller maneja las operaciones CRUD sobre vehiculos.
 */
package com.tropimotos.controller;

import com.tropimotos.dto.ApiResponse;
import com.tropimotos.dto.VehiculoDTO;
import com.tropimotos.dto.VehiculoRequest;
import com.tropimotos.service.VehiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *@RestController: Devuelve JSON
 *@RequestMapping: Ruta base /api/vehiculos
 */
@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

    /** Servicio de vehiculos inyectado */
    private final VehiculoService vehiculoService;

    /**
     * GET /api/vehiculos - Lista todos los vehiculos
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<VehiculoDTO>>> listarTodos() {
        List<VehiculoDTO> vehiculos = vehiculoService.listarTodos();
        return ResponseEntity.ok(ApiResponse.success(vehiculos));
    }

    /**
     * GET /api/vehiculos/{id} - Busca un vehiculo por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VehiculoDTO>> buscarPorId(@PathVariable Integer id) {
        return vehiculoService.buscarPorId(id)
                .map(vehiculo -> ResponseEntity.ok(ApiResponse.success(vehiculo)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/vehiculos/chofer/{idChofer} - Lista vehiculos de un chofer
     */
    @GetMapping("/chofer/{idChofer}")
    public ResponseEntity<ApiResponse<List<VehiculoDTO>>> buscarPorChoferId(@PathVariable Integer idChofer) {
        List<VehiculoDTO> vehiculos = vehiculoService.buscarPorChoferId(idChofer);
        return ResponseEntity.ok(ApiResponse.success(vehiculos));
    }

    /**
     * POST /api/vehiculos - Registra un nuevo vehiculo
     */
    @PostMapping
    public ResponseEntity<ApiResponse<VehiculoDTO>> crear(@Valid @RequestBody VehiculoRequest request) {
        try {
            VehiculoDTO vehiculo = vehiculoService.crear(request);
            return ResponseEntity.ok(ApiResponse.success("Vehículo registrado exitosamente", vehiculo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * PUT /api/vehiculos/{id} - Actualiza un vehiculo
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VehiculoDTO>> actualizar(@PathVariable Integer id, @Valid @RequestBody VehiculoRequest request) {
        try {
            VehiculoDTO vehiculo = vehiculoService.actualizar(id, request);
            return ResponseEntity.ok(ApiResponse.success("Vehículo actualizado exitosamente", vehiculo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * DELETE /api/vehiculos/{id} - Elimina un vehiculo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        try {
            vehiculoService.eliminar(id);
            return ResponseEntity.ok(ApiResponse.success("Vehículo eliminado exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
