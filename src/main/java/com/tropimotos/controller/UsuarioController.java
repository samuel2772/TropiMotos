/**
 * Controller UsuarioController - Endpoints para gestion de usuarios
 * 
 * Este controller maneja las operaciones CRUD sobre usuarios.
 */
package com.tropimotos.controller;

import com.tropimotos.dto.ApiResponse;
import com.tropimotos.dto.UsuarioDTO;
import com.tropimotos.entity.Usuario;
import com.tropimotos.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *@RestController: Devuelve JSON en lugar de vistas
 *@RequestMapping: Ruta base /api/usuarios
 */
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    /** Servicio de usuarios inyectado */
    private final UsuarioService usuarioService;

    /**
     * GET /api/usuarios - Lista todos los usuarios
     * 
     * @return Lista de usuarios
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UsuarioDTO>>> listarTodos() {
        List<UsuarioDTO> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(ApiResponse.success(usuarios));
    }

    /**
     * GET /api/usuarios/{id} - Busca un usuario por ID
     * 
     * @param id ID del usuario a buscar
     * @return Usuario si existe, 404 si no
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioDTO>> buscarPorId(@PathVariable Integer id) {
        return usuarioService.buscarPorId(id)
                .map(usuario -> ResponseEntity.ok(ApiResponse.success(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/usuarios - Crea un nuevo usuario
     * 
     * @param usuario Datos del usuario
     * @return Usuario creado
     */
    @PostMapping
    public ResponseEntity<ApiResponse<UsuarioDTO>> crear(@RequestBody Usuario usuario) {
        try {
            UsuarioDTO nuevoUsuario = usuarioService.crear(usuario);
            return ResponseEntity.ok(ApiResponse.success("Usuario creado exitosamente", nuevoUsuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * PUT /api/usuarios/{id} - Actualiza un usuario
     * 
     * @param id ID del usuario a actualizar
     * @param usuario Nuevos datos del usuario
     * @return Usuario actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioDTO>> actualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        try {
            UsuarioDTO usuarioActualizado = usuarioService.actualizar(id, usuario);
            return ResponseEntity.ok(ApiResponse.success("Usuario actualizado exitosamente", usuarioActualizado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * DELETE /api/usuarios/{id} - Elimina un usuario
     * 
     * @param id ID del usuario a eliminar
     * @return Mensaje de confirmacion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Integer id) {
        try {
            usuarioService.eliminar(id);
            return ResponseEntity.ok(ApiResponse.success("Usuario eliminado exitosamente", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
