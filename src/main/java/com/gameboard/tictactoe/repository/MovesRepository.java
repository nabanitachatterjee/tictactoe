package com.gameboard.tictactoe.repository;

import com.gameboard.tictactoe.model.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovesRepository extends JpaRepository<Move, Integer> {

//    @Modifying
//    @Query("update Move u set u.lastMove = ?1 where u.gameId = ?2 and u.player = ?3")
//    void updateLastMove(String lastMove,int gameId, int player);
//
//    @Modifying
//    @Query("update Move u set u.winner = ?1 where u.gameId = ?2 and u.player = ?3")
//    void updateWinner(String winner, int gameId, int player);
}
