package org.example.mendalienproject.repository;

import org.example.mendalienproject.model.AlienCommander;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlienCommanderRepository extends JpaRepository<AlienCommander, Long> {
}
