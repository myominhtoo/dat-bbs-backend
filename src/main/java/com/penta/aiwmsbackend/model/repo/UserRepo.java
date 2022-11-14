package com.penta.aiwmsbackend.model.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.penta.aiwmsbackend.model.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    @Query( name = "SELECT * FROM users WHERE email = ? ", nativeQuery = true)
    Optional<User> findByEmail(String email);

    User findByEmailAndPassword( String email , String password );

    @Query(value = "SELECT * FROM users WHERE email = ?1 AND valid_user = 1", nativeQuery = true)
    Optional<User> findByEmailWithValidId(String email);

    @Query(value = "SELECT * FROM users u1 LEFT JOIN boards_has_users u2 ON  u1.id=u2.user_id WHERE u2.joined_status= true ", nativeQuery = true)
    List<User> findMember();

    @Query(value = "select * from boards_has_users t1 left join  boards t2 on t1.board_id=t2.id left join users t3 on t1.user_id=t3.id where t1.board_id= ?1 ", nativeQuery = true)
    List<User> findReportMember(Integer id);
}
