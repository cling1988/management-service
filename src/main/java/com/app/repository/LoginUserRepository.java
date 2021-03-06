package com.app.repository;

import com.app.entity.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface LoginUserRepository extends JpaRepository<LoginUser,Long> {

    Optional<LoginUser> findByUsernameAndActive(String username, boolean active);
}
