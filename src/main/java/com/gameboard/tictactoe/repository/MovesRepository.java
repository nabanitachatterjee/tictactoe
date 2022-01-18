package com.gameboard.tictactoe.repository;

import com.gameboard.tictactoe.model.Move;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovesRepository extends CrudRepository<Move, Integer> {
}
