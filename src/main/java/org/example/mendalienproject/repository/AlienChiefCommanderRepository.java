package org.example.mendalienproject.repository;

import org.example.mendalienproject.model.AlienChiefCommander;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlienChiefCommanderRepository extends JpaRepository<AlienChiefCommander, Long> {
}
