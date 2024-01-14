/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Entidad.clsEusuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Jhonatan
 */
public class clsNusuario {
    private Connection conexion;

    public clsNusuario(Connection conexion) {
        this.conexion = conexion;
    }
    public List<clsEusuario> obtenerUsuarios(String nombre, String apellido) {
        List<clsEusuario> usuarios = new ArrayList<>();

        try {
            String query = "SELECT * FROM usuarios WHERE nombre LIKE ? AND apellido LIKE ?";
            try (PreparedStatement statement = conexion.prepareStatement(query)) {
                statement.setString(1, "%" + nombre + "%");
                statement.setString(2, "%" + apellido + "%");

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        clsEusuario usuario = new clsEusuario();
                        usuario.setNombre(resultSet.getString("nombre"));
                        usuario.setApellido(resultSet.getString("apellido"));
                        usuario.setUsuario(resultSet.getString("usuario"));
                        usuario.setContraseña(resultSet.getString("contrasena"));
                        usuario.setRol(resultSet.getString("rol"));
                        usuarios.add(usuario);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }
    public boolean eliminarUsuario(String nombreUsuario) {
        try {
            String query = "DELETE FROM usuarios WHERE usuario = ?";
            try (PreparedStatement statement = conexion.prepareStatement(query)) {
                statement.setString(1, nombreUsuario);
                int filasAfectadas = statement.executeUpdate();
                return filasAfectadas > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean registrarUsuario(clsEusuario usuario) {
        try {
            String query = "INSERT INTO usuarios (nombre,apellido,usuario, contrasena, rol) VALUES (?,?,?,?,?)";
            try (PreparedStatement statement = conexion.prepareStatement(query)) {
                statement.setString(1, usuario.getNombre());
                statement.setString(2, usuario.getApellido());
                statement.setString(3, usuario.getUsuario());
                statement.setString(4, usuario.getContraseña());
                statement.setString(5, usuario.getRol());

                int filasAfectadas = statement.executeUpdate();
                return filasAfectadas > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public clsEusuario validarInicioSesion(String nombreUsuario, String contraseña) {
        try {
            String query = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?";
try (PreparedStatement statement = conexion.prepareStatement(query)) {
                statement.setString(1, nombreUsuario);
                statement.setString(2, contraseña);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        clsEusuario usuario = new clsEusuario();
                        usuario.setUsuario(resultSet.getString("usuario"));
                        usuario.setContraseña(resultSet.getString("contrasena"));
                        usuario.setRol(resultSet.getString("rol"));
                        return usuario;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
