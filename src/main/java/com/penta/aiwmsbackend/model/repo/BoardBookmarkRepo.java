package com.penta.aiwmsbackend.model.repo;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.penta.aiwmsbackend.model.entity.Board;
import com.penta.aiwmsbackend.model.entity.BoardBookmark;

@Repository
public interface BoardBookmarkRepo extends JpaRepository<BoardBookmark, Integer> {

    @Query(name = "SELECT * FROM board_bookmarks t WHERE t.user_id = ?1 ", nativeQuery = true)
    List<BoardBookmark> findByUserId(Integer userId);

    @Query(name = "SELECT * FROM board_bookmarks t WHERE t.user_id = ?1 AND t.board_id = ?2 ", nativeQuery = true)
    Optional<BoardBookmark> findByUserIdAndBoardId(Integer userId, Integer boardId);

    @Query(value = "SELECT * FROM board_bookmarks bm LEFT JOIN boards b on bm.board_id=b.id WHERE bm.user_id=?1" , nativeQuery = true)
    List<BoardBookmark> findBookmarkByUserId(Integer id);
}
