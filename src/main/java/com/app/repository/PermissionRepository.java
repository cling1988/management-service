package com.app.repository;

import com.app.entity.Permission;
import com.app.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    List<Permission> findByRoles_Id(Long id);

    Optional<Permission> findByName(String name);
}
