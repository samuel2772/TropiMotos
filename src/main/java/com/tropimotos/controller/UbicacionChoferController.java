/**
 * Controller UbicacionChoferController - Endpoints para ubicaciones GPS
 * 
 * Este controller maneja el tracking GPS de los choferes.
 */
package com.tropimotos.controller;

import com.tropimotos.dto.ApiResponse;
import com.tropimotos.dto.UbicacionChoferDTO;
import com.tropimotos.dto.UbicacionChoferRequest;
import com.tropimotos.service.UbicacionChoferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *@RestController: Devuelve JSON
 *@RequestMapping: Ruta base /api/ubicaciones
 */
@RestController
@RequestMapping("/api/ubicaciones")
@RequiredArgsConstructor
public class UbicacionChoferController {

    /** Servicio de ubicaciones inyectado */
    private final UbicacionChoferService ubicacionService;

    /**
     * GET /api/ubicaciones - Lista todas las ubicaciones
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UbicacionChoferDTO>>> listarTodos() {
        List<UbicacionChoferDTO> ubicaciones = ubicacionService.listarTodos();
        return ResponseEntity.ok(ApiResponse.success(ubicaciones));
    }

    /**
     * GET /api/ubicaciones/{id} - Busca una ubicacion por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UbicacionChoferDTO>> buscarPorId(@PathVariable Integer id) {
        return ubicacionService.buscarPorId(id)
                .map(ubicacion -> ResponseEntity.ok(ApiResponse.success(ubicacion)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/ubicaciones/chofer/{idChofer} - Lista ubicaciones de un chofer
     */
    @GetMapping("/chofer/{idChofer}")
    public ResponseEntity<ApiResponse<List<UbicacionChoferDTO>>> buscarPorChoferId(@PathVariable Integer idChofer) {
        List<UbicacionChoferDTO> ubicaciones = ubicacionService.buscarPorChoferId(idChofer);
        return ResponseEntity.ok(ApiResponse.success(ubicaciones));
    }

    /**
     * GET /api/ubicaciones/chofer/{idChofer}/ultima - Obtiene la ubicacion actual
     */
    @GetMapping("/chofer/{idChofer}/ultima")
    public ResponseEntity<ApiResponse<UbicacionChoferDTO>> obtenerUltimaUbicacion(@PathVariable Integer idChofer) {
        return ubicacionService.obtenerUltimaUbicacion(idChofer)
                .map(ubicacion -> ResponseEntity.ok(ApiResponse.success(ubicacion)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/ubicaciones - Registra una nueva ubicacion
     */
    @PostMapping
    public ResponseEntity<ApiResponse<UbicacionChoferDTO>> crear(@Valid @RequestBody UbicacionChoferRequest request) {
        try {
            UbicacionChoferDTO ubicacion = ubicacionService.crear(request);
            return ResponseEntity.ok(ApiResponse.success("Ubicación registrada exitosamente", ubicacion));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * PUT /api/ubicaciones/chofer/{idChofer} - Actualiza la ubicacion de un chofer
     */
    @PutMapping("/chofer/{idChofer}")
    public ResponseEntity<ApiResponse<UbicacionChoferDTO>> actualizarUbicacion(
            @PathVariable Integer idChofer,
            @Valid @RequestBody UbicacionChoferRequest request) {
        try {
            UbicacionChoferDTO ubicacion = ubicacionService.actualizarUbicacion(idChofer, request);
            return ResponseEntity.ok(ApiResponse.success("Ubicación actualizada exitosamente", ubicacion));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
