/**
 * Controller ViajeController - Endpoints para gestion de viajes
 * 
 * Este controller maneja todo el ciclo de vida de un viaje:
 * solicitud, aceptacion, inicio, finalizacion y cancelacion.
 */
package com.tropimotos.controller;

import com.tropimotos.dto.ApiResponse;
import com.tropimotos.dto.ViajeDTO;
import com.tropimotos.dto.ViajeRequest;
import com.tropimotos.service.ViajeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *@RestController: Devuelve JSON
 *@RequestMapping: Ruta base /api/viajes
 */
@RestController
@RequestMapping("/api/viajes")
@RequiredArgsConstructor
public class ViajeController {

    /** Servicio de viajes inyectado */
    private final ViajeService viajeService;

    /**
     * GET /api/viajes - Lista todos los viajes
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ViajeDTO>>> listarTodos() {
        List<ViajeDTO> viajes = viajeService.listarTodos();
        return ResponseEntity.ok(ApiResponse.success(viajes));
    }

    /**
     * GET /api/viajes/{id} - Busca un viaje por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ViajeDTO>> buscarPorId(@PathVariable Integer id) {
        return viajeService.buscarPorId(id)
                .map(viaje -> ResponseEntity.ok(ApiResponse.success(viaje)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/viajes/usuario/{idUsuario} - Lista viajes de un usuario
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<ApiResponse<List<ViajeDTO>>> buscarPorUsuarioId(@PathVariable Integer idUsuario) {
        List<ViajeDTO> viajes = viajeService.buscarPorUsuarioId(idUsuario);
        return ResponseEntity.ok(ApiResponse.success(viajes));
    }

    /**
     * GET /api/viajes/chofer/{idChofer} - Lista viajes de un chofer
     */
    @GetMapping("/chofer/{idChofer}")
    public ResponseEntity<ApiResponse<List<ViajeDTO>>> buscarPorChoferId(@PathVariable Integer idChofer) {
        List<ViajeDTO> viajes = viajeService.buscarPorChoferId(idChofer);
        return ResponseEntity.ok(ApiResponse.success(viajes));
    }

    /**
     * POST /api/viajes/solicitar/{idUsuario} - Solicita un nuevo viaje
     */
    @PostMapping("/solicitar/{idUsuario}")
    public ResponseEntity<ApiResponse<ViajeDTO>> solicitarViaje(
            @PathVariable Integer idUsuario,
            @Valid @RequestBody ViajeRequest request) {
        try {
            ViajeDTO viaje = viajeService.crear(idUsuario, request);
            return ResponseEntity.ok(ApiResponse.success("Viaje solicitado exitosamente", viaje));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * PUT /api/viajes/{idViaje}/aceptar - Un chofer acepta un viaje
     */
    @PutMapping("/{idViaje}/aceptar")
    public ResponseEntity<ApiResponse<ViajeDTO>> aceptarViaje(
            @PathVariable Integer idViaje,
            @RequestBody Map<String, Integer> body) {
        try {
            Integer idChofer = body.get("idChofer");
            Integer idVehiculo = body.get("idVehiculo");
            ViajeDTO viaje = viajeService.aceptarViaje(idViaje, idChofer, idVehiculo);
            return ResponseEntity.ok(ApiResponse.success("Viaje aceptado exitosamente", viaje));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * PUT /api/viajes/{idViaje}/iniciar - Inicia el viaje
     */
    @PutMapping("/{idViaje}/iniciar")
    public ResponseEntity<ApiResponse<ViajeDTO>> iniciarViaje(@PathVariable Integer idViaje) {
        try {
            ViajeDTO viaje = viajeService.iniciarViaje(idViaje);
            return ResponseEntity.ok(ApiResponse.success("Viaje iniciado exitosamente", viaje));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * PUT /api/viajes/{idViaje}/finalizar - Finaliza el viaje
     */
    @PutMapping("/{idViaje}/finalizar")
    public ResponseEntity<ApiResponse<ViajeDTO>> finalizarViaje(@PathVariable Integer idViaje) {
        try {
            ViajeDTO viaje = viajeService.finalizarViaje(idViaje);
            return ResponseEntity.ok(ApiResponse.success("Viaje finalizado exitosamente", viaje));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * PUT /api/viajes/{idViaje}/cancelar - Cancela el viaje
     */
    @PutMapping("/{idViaje}/cancelar")
    public ResponseEntity<ApiResponse<ViajeDTO>> cancelarViaje(
            @PathVariable Integer idViaje,
            @RequestBody Map<String, String> body) {
        try {
            String canceladoPor = body.get("canceladoPor");
            String observacion = body.get("observacion");
            ViajeDTO viaje = viajeService.cancelarViaje(idViaje, canceladoPor, observacion);
            return ResponseEntity.ok(ApiResponse.success("Viaje cancelado", viaje));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
