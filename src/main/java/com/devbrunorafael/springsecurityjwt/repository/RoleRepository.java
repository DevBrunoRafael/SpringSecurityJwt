package com.devbrunorafael.springsecurityjwt.repository;

import com.devbrunorafael.springsecurityjwt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT e FROM Role e WHERE e.roleName= (:roleName)")
    Role findRoleByRoleName(String roleName);
}
