package com.marcin.ania.ToDoAPP.repository;

import com.marcin.ania.ToDoAPP.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo,Long> {
    Optional<UserInfo> findByUsername(String username);
}
