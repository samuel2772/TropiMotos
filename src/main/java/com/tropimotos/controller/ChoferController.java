/**
 * Controller ChoferController - Endpoints para gestion de chofers
 * 
 * Este controller maneja las operaciones sobre chofers.
 */
package com.tropimotos.controller;

import com.tropimotos.dto.ApiResponse;
import com.tropimotos.dto.ChoferDTO;
import com.tropimotos.dto.ChoferRequest;
import com.tropimotos.service.ChoferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *@RestController: Devuelve JSON
 *@RequestMapping: Ruta base /api/choferes
 */
@RestController
@RequestMapping("/api/choferes")
@RequiredArgsConstructor
public class ChoferController {

    /** Servicio de chofers inyectado */
    private final ChoferService choferService;

    /**
     * GET /api/choferes - Lista todos los chofers
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ChoferDTO>>> listarTodos() {
        List<ChoferDTO> chofers = choferService.listarTodos();
        return ResponseEntity.ok(ApiResponse.success(chofers));
    }

    /**
     * GET /api/choferes/{id} - Busca un chofer por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ChoferDTO>> buscarPorId(@PathVariable Integer id) {
        return choferService.buscarPorId(id)
                .map(chofer -> ResponseEntity.ok(ApiResponse.success(chofer)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/choferes/disponibles - Lista chofers disponibles
     */
    @GetMapping("/disponibles")
    public ResponseEntity<ApiResponse<List<ChoferDTO>>> listarDisponibles() {
        List<ChoferDTO> chofers = choferService.listarDisponibles();
        return ResponseEntity.ok(ApiResponse.success(chofers));
    }

    /**
     * POST /api/choferes - Registra un nuevo chofer
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ChoferDTO>> crear(@Valid @RequestBody ChoferRequest request) {
        try {
            ChoferDTO chofer = choferService.crear(request);
            return ResponseEntity.ok(ApiResponse.success("Chofer registrado exitosamente", chofer));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * PUT /api/choferes/{id} - Actualiza un chofer
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ChoferDTO>> actualizar(@PathVariable Integer id, @Valid @RequestBody ChoferRequest request) {
        try {
            ChoferDTO chofer = choferService.actualizar(id, request);
            return ResponseEntity.ok(ApiResponse.success("Chofer actualizado exitosamente", chofer));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * PATCH /api/choferes/{id}/disponibilidad - Actualiza disponibilidad
     * Usa PATCH porque solo actualiza un campo
     */
    @PatchMapping("/{id}/disponibilidad")
    public ResponseEntity<ApiResponse<ChoferDTO>> actualizarDisponibilidad(
            @PathVariable Integer id,
            @RequestBody Map<String, Boolean> body) {
        try {
            Boolean disponible = body.get("disponible");
            ChoferDTO chofer = choferService.actualizarDisponibilidad(id, disponible);
            return ResponseEntity.ok(ApiResponse.success("Disponibilidad actualizada", chofer));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * DELETE /api/choferes/{id} - Elimina un chofer
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        try {
            choferService.eliminar(id);
            return ResponseEntity.ok(ApiResponse.success("Chofer eliminado exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
