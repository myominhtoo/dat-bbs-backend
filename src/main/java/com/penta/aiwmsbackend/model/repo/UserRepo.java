package com.penta.aiwmsbackend.model.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.penta.aiwmsbackend.model.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM users WHERE email = ?1 ", nativeQuery = true)
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE email = ?1 AND valid_user = 1", nativeQuery = true)
    Optional<User> findByEmailWithValidId(String email);

    @Query(value = "SELECT * FROM users u1 LEFT JOIN boards_has_users u2 ON  u1.id=u2.user_id WHERE u2.joined_status= true ", nativeQuery = true)
    List<User> findMember();
}
