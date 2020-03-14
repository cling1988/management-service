package com.app.repository;

import com.app.entity.Outlet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface OutletRepository extends JpaRepository<Outlet, Long> {
}
