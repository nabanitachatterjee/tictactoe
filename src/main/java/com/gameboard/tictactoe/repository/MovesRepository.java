package com.gameboard.tictactoe.repository;

import com.gameboard.tictactoe.model.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovesRepository extends JpaRepository<Move, Integer> {

}
