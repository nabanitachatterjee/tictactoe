package com.gameboard.tictactoe.repository;

import com.gameboard.tictactoe.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameBoardRepository extends JpaRepository<Game, Integer> {

}
