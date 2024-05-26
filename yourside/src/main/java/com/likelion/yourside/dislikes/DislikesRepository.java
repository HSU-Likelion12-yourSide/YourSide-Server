package com.likelion.yourside.dislikes;

import com.likelion.yourside.domain.Dislikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DislikesRepository extends JpaRepository<Dislikes, Long> {
}
