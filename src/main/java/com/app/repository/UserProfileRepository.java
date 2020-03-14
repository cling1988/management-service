package com.app.repository;

import com.app.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
