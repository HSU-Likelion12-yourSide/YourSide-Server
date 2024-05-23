package com.likelion.yourside.notation.repository;

import com.likelion.yourside.domain.Notation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotationRepository extends JpaRepository<Notation,Long> {
}
