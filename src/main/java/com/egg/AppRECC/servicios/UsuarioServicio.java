package com.egg.AppRECC.servicios;

import com.egg.AppRECC.entidades.Usuario;
import com.egg.AppRECC.enumeraciones.Rol;
import com.egg.AppRECC.excepciones.MiException;
import com.egg.AppRECC.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService {
  @Autowired
  private UsuarioRepositorio repo;

  public void crearUsuario(
          
    String nombre,
    String email,
    String password,
    String password2
  )
    throws MiException {
    if(validar(nombre, email, password, password2)){

    Usuario usuario = new Usuario();
    usuario.setNombre(nombre);
    usuario.setEmail(email);
    usuario.setPassword(new BCryptPasswordEncoder().encode(password));
    usuario.setActivo(1);
    usuario.setRol(Rol.GUEST);
    
    repo.save(usuario);
  }}

  public boolean validar(
    String nombre,
    String email,
    String password,
    String password2
  )
    throws MiException {
    Usuario usuario = repo.encontrarUsuarioPorEmail(email);

    if (nombre.isEmpty() || nombre == null || nombre.equals("")) {
      throw new MiException("El nombre no puede ser nulo o estar vacio");
    } 
    if (email.isEmpty() || email == null || email.equals("")) {
      throw new MiException("El email no puede ser nulo o estar vacio");
    }  
    if (password.isEmpty() || password == null || password.length() <= 5) {
      throw new MiException(
        "La contraseña no puede estar vacia, y debe tener mas de 5 digitos"
      );
    } 
    if (!password.equals(password2)) {
      throw new MiException("Las contraseñas ingresadas deben ser iguales");
    } 
    if(usuario != null && usuario.getEmail().equals(email)){
            throw new MiException("El usuario que intenta registrar ya se encuentra registrado, intenta otro");
        } return true;
  } 

  public List<Usuario> listarUsuarios() {
    return repo.listarActivos();
  }
  
  public List<Usuario> listar() {
    return repo.findAll();
  }

  @Transactional
  public void actualizar(
    String id,
    String nombre,
    String email,
    String password
  ) {
    Optional<Usuario> respuesta = repo.findById(id);

    if (respuesta.isPresent()) {
      Usuario usuario = respuesta.get();
      usuario.setNombre(nombre);
      usuario.setEmail(email);
      usuario.setPassword(new BCryptPasswordEncoder().encode(password));
      usuario.setActivo(usuario.getActivo() + 1);

      repo.save(usuario);
    }
  }
  /***
   * 
   * @param id identificador único de usuario
   * @param rol rol al cual se debe pasar el usuario
   * 
   * Descripción: recibe un id de usuario y si encuentra dicho usuario le 
   * setea el rol que viene por parametro.
   * El rol debe ser uno de los definidos en el enum
   */
  @Transactional
  public void cambiarRol(String id, Rol rol) {
    Optional<Usuario> respuesta = repo.findById(id);

    if (respuesta.isPresent()) {
      Usuario usuario = respuesta.get();
      usuario.setRol(rol);

      repo.save(usuario);
    }
  }

  @Transactional
  public void eliminar(String id) {
    Optional<Usuario> respuesta = repo.findById(id);

    if (respuesta.isPresent()) {
      Usuario usuario = respuesta.get();

      usuario.setActivo(0);

      repo.save(usuario);
    }
  }

  @Override
  public UserDetails loadUserByUsername(String email)
    throws UsernameNotFoundException {
    Usuario usuario = repo.encontrarUsuarioPorEmail(email);

    if (usuario != null) {
      List<GrantedAuthority> permisos = new ArrayList();

      GrantedAuthority p = new SimpleGrantedAuthority(
        "ROLE_" + usuario.getRol().toString()
      );

      permisos.add(p);

      ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

      HttpSession sesion = attr.getRequest().getSession(true);

      sesion.setAttribute("usuariosession", usuario);

      return new User(usuario.getEmail(), usuario.getPassword(), permisos);
    } else {
      return null;
    }
  }
}
