package com.app.repository;

import com.app.entity.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface LoginUserRepository extends JpaRepository<LoginUser,Long> {

    List<LoginUser> findByUsernameAndActive(String username, boolean active);
}
