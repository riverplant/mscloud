package com.riverplant.security.component;

import com.riverplant.security.entity.TPerm;
import com.riverplant.security.entity.TUser;
import com.riverplant.security.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component// 定义如何去数据库查询用户信息
@RequiredArgsConstructor
public class AppReactiveUserDetailsService implements ReactiveUserDetailsService {

    private final TUserRepository userRepository;
    private final TUserRoleRepository userRoleRepository;
    private final TRolesRepository roleRepository;
    private final TRolePermRepository rolePermissionRepository;
    private final TPermRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 使用detabaseClient
     * SELECT  u.*, r.id rid, r.name, r.value ,pm.id pid, pm.value pvalue, pm.description
     * FROM t_user u
     * LEFT JOIN t_user_role ur on ur.user_id = u.id
     * LEFT JOIN t_roles r on ur.role_id = r.id
     * LEFT JOIN t_role_perm rp on rp.role_id = r.id
     * LEFT JOIN t_perm pm  on rp.perm_id = pm.id
     * WHERE u.username='zhangsan';
     *
     */
    //自定义如何按照用户名去数据库查询用户信息
    @Override
    public Mono<UserDetails> findByUsername(String username) {

        // 用数据库查询所有用户、角色、权限的逻辑
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")))
                .flatMap(user ->
                        userRoleRepository.findAllByUserId(user.getId())
                                .flatMap(userRole -> roleRepository.findById(userRole.getRoleId()))
                                .flatMap(role -> rolePermissionRepository.findAllByRoleId(role.getId()))
                                .flatMap(rolePerm -> permissionRepository.findById(rolePerm.getPermId()))
                                .map(TPerm::getValue)
                                .collectList()
                                .map(permissions -> buildUserDetails(user, permissions))

                );
    }


    private UserDetails buildUserDetails(TUser user, List<String> permissions) {

        List<SimpleGrantedAuthority> authorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
        return User.builder().username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }

}
