package com.egg.AppRECC.servicios;

import com.egg.AppRECC.entidades.Usuario;
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
public class UsuarioServicio implements UserDetailsService{
    
    @Autowired
    private UsuarioRepositorio repo;
    /***
     * 
     * @param nombre
     * @param email
     * @param password
     * @param password2
     * @return 1 contraseña no coincide
     *         0 proceso finalizado correctamente
     *        -1 excepcion no tratada
     */
    public int crearUsuario(String nombre, String email, String password, String password2){
        
        try {
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setPassword(password);
            usuario.setActivo(1);
            if(password.equals(password2)){
                repo.save(usuario);
            }else{
                return 1;
            }
            
        } catch (Exception e) {
            return -1;
        }
        
        
        
        return 0;     
    }
    
    public List<Usuario> listarUsuarios(){
        return repo.listarActivos();
    }
    
    @Transactional
    public void actualizar(String id, String nombre, String email, String password){
        
        Optional<Usuario> respuesta = repo.findById(id);
        
        if(respuesta.isPresent()){
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            usuario.setActivo(usuario.getActivo()+1);

            repo.save(usuario);
            
        }        
    }
    
    @Transactional
    public void eliminar(String id){
        
        Optional<Usuario> respuesta = repo.findById(id);
        
        if(respuesta.isPresent()){
            Usuario usuario = respuesta.get();
            
            usuario.setActivo(0);

            repo.save(usuario);
            
        }        
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repo.encontrarUsuarioPorEmail(email);
        
        if(usuario != null){
            
            List<GrantedAuthority> permisos = new ArrayList();
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+usuario.getRol().toString());
            
            permisos.add(p);
            
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            
            HttpSession sesion = attr.getRequest().getSession(true);
            
            sesion.setAttribute("usuariosession", usuario);
            
            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        }else{
            return null;
        }
        
    }
    
}
