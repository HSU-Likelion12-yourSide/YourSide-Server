package com.likelion.yourside.user.repository;

import com.likelion.yourside.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);
    Optional<User> findByUsername(String username);
    Optional<User> findByNameAndEmail(String name, String email);
    Optional<User> findByNameAndEmailAndUsername(String name, String email, String username);
}
