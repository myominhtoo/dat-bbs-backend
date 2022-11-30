package com.penta.aiwmsbackend.model.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.penta.aiwmsbackend.model.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    @Query(name = "SELECT * FROM users WHERE email = ? ", nativeQuery = true)
    Optional<User> findByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    @Query(value = "SELECT * FROM users WHERE email = ?1 AND valid_user = 1", nativeQuery = true)
    Optional<User> findByEmailWithValidId(String email);

    @Query(value = "SELECT * FROM users u1 LEFT JOIN boards_has_users u2 ON  u1.id=u2.user_id WHERE u2.joined_status= true ", nativeQuery = true)
    List<User> findMember();

    // @Query(value = "select * from boards_has_users t1 left join  boards t2 on t1.board_id=t2.id left join users t3 on t1.user_id=t3.id ", nativeQuery = true)
    // List<User> findReportMember(Integer id);

    // @Query(value = "select * from boards b join users u on b.user_id=u.id where b.id= ?1 ", nativeQuery = true)
    // List<User> findOwner(Integer id);

    @Query(value = "SELECT DISTINCT(u.id) ,u.* FROM users u LEFT JOIN boards_has_users bs ON u.id = bs.user_id LEFT JOIN boards b ON b.id = bs.board_id WHERE ( b.user_id = ?1 AND u.id != ?1 AND bs.joined_status = true ) ", nativeQuery = true)
    List<User> findOwnedBoardsCollaboratorsByUserId(Integer userId);

    @Query(value = "SELECT DISTINCT(u.id) , u.*  FROM boards_has_users bs LEFT JOIN boards b ON b.id = bs.board_id LEFT JOIN users u ON u.id = bs.user_id  WHERE bs.board_id IN (SELECT board_id from boards_has_users WHERE user_id = ?1 AND joined_status = true ) AND bs.user_id != ?1 ", nativeQuery = true)
    List<User> findJoinedBoardsCollaboratorsByUserId(Integer userId);

    @Query(value = "SELECT DISTINCT(b.user_id) , u.* FROM boards b LEFT JOIN users u ON b.user_id = u.id WHERE b.id IN((SELECT bs.board_id FROM boards_has_users bs WHERE bs.user_id = ?1 AND joined_status = true))", nativeQuery = true)
    List<User> findJoinedBoardsOwnersByUserId(Integer userId);
}
