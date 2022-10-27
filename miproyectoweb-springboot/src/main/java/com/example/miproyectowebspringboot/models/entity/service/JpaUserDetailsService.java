package com.example.miproyectowebspringboot.models.entity.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.miproyectowebspringboot.models.entity.Rol;
import com.example.miproyectowebspringboot.models.entity.Usuario;
import com.example.miproyectowebspringboot.models.entity.dao.IUsuarioDao;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService{

    @Autowired
    private IUsuarioDao usuarioDao;

    private Logger LOG = LoggerFactory.getLogger(JpaUserDetailsService.class);

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByUsername(username);
        if (usuario == null) {
            LOG.error("ERROR-LOGIN: ".concat(username).concat(" no existe en el sistema"));
            throw new UsernameNotFoundException("ERROR-LOGIN: ".concat(username).concat(" no existe en el sistema"));
        }
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (Rol rol : usuario.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(rol.getAuthority()));
        }

        if (authorities.isEmpty()) {
            LOG.error("ERROR-LOGIN: El usuario ".concat(username).concat(" no tiene rol asignado"));
            throw new UsernameNotFoundException("ERROR-LOGIN: El usuario ".concat(username).concat(" no tiene rol asignado"));
        }

        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
    }
    
}
