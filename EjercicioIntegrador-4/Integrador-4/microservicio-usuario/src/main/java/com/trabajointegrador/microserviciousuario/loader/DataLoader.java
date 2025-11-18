package com.trabajointegrador.microserviciousuario.loader;

import com.trabajointegrador.microserviciousuario.entity.Cuenta;
import com.trabajointegrador.microserviciousuario.entity.Usuario;
import com.trabajointegrador.microserviciousuario.entity.UsuarioCuenta;
import com.trabajointegrador.microserviciousuario.entity.UsuarioCuentaid;
import com.trabajointegrador.microserviciousuario.repository.CuentaRepository;
import com.trabajointegrador.microserviciousuario.repository.UsuarioCuentaRepository;
import com.trabajointegrador.microserviciousuario.repository.UsuarioRepository;
import com.trabajointegrador.microserviciousuario.utils.Rol;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final CuentaRepository cuentaRepo;
    private final UsuarioRepository usuarioRepo;
    private final UsuarioCuentaRepository usuarioCuentaRepo;

    public DataLoader(CuentaRepository cuentaRepo,
                      UsuarioRepository usuarioRepo,
                      UsuarioCuentaRepository usuarioCuentaRepo) {
        this.cuentaRepo = cuentaRepo;
        this.usuarioRepo = usuarioRepo;
        this.usuarioCuentaRepo = usuarioCuentaRepo;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if (cuentaRepo.count() > 0) {
            return;
        }

        cargarCuentas();
        cargarUsuarios();
        cargarUsuarioCuenta();
    }

    private void cargarCuentas() throws Exception {
        var resource = new ClassPathResource("data/cuenta.csv");
        try (var reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line = reader.readLine(); // header
            while ((line = reader.readLine()) != null) {
                String[] c = line.split(",");
                Cuenta cuenta = new Cuenta();
                cuenta.setId(Long.valueOf(c[0]));
                cuenta.setNumeroCuenta(c[1]);
                cuenta.setFechaAlta(LocalDate.parse(c[2]));
                cuenta.setTipoCuenta(Enum.valueOf(Cuenta.TipoCuenta.class, c[3]));
                cuenta.setSaldoCreditos(new BigDecimal(c[4]));
                cuenta.setCuentaMercadoPagoId(c[5].isEmpty() ? null : c[5]);
                cuenta.setActiva(Boolean.parseBoolean(c[6]));
                cuenta.setKmRecorridosMes(new BigDecimal(c[7]));
                cuenta.setFechaRenovacionCupo(c[8].isEmpty() ? null : LocalDate.parse(c[8]));
                cuenta.setFechaBaja(c.length > 9 && !c[9].isEmpty() ? LocalDate.parse(c[9]) : null);

                cuentaRepo.save(cuenta);
            }
        }
    }

    private void cargarUsuarios() throws Exception {
        var resource = new ClassPathResource("data/usuario.csv");
        try (var reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line = reader.readLine(); // header
            while ((line = reader.readLine()) != null) {
                String[] u = line.split(",");
                Usuario usuario = new Usuario();
                usuario.setId(Long.valueOf(u[0]));
                usuario.setNombreUsuario(u[1]);
                usuario.setNombre(u[2]);
                usuario.setApellido(u[3]);
                usuario.setEmail(u[4]);
                usuario.setTelefono(u[5]);
                usuario.setFechaRegistro(LocalDateTime.parse(u[6]));
                usuario.setActivo(Boolean.parseBoolean(u[7]));
                usuario.setRol(Rol.valueOf(u[8]));
                usuario.setPassword(u[9]);

                usuarioRepo.save(usuario);
            }
        }
    }

    private void cargarUsuarioCuenta() throws Exception {
        var resource = new ClassPathResource("data/usuario_cuenta.csv");
        try (var reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line = reader.readLine(); // header
            while ((line = reader.readLine()) != null) {
                String[] uc = line.split(",");

                Long usuarioId = Long.valueOf(uc[0]);
                Long cuentaId = Long.valueOf(uc[1]);
                LocalDate fecha = LocalDate.parse(uc[2]);

                UsuarioCuentaid id = new UsuarioCuentaid(usuarioId, cuentaId);

                UsuarioCuenta vinculo = new UsuarioCuenta();
                vinculo.setId(id);
                vinculo.setUsuario(usuarioRepo.getReferenceById(usuarioId));
                vinculo.setCuenta(cuentaRepo.getReferenceById(cuentaId));
                vinculo.setFechaVinculacion(fecha);

                usuarioCuentaRepo.save(vinculo);
            }
        }
    }
}