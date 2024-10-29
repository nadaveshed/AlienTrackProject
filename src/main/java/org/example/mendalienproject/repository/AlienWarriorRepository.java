package org.example.mendalienproject.repository;

import org.example.mendalienproject.model.AlienWarrior;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlienWarriorRepository extends JpaRepository<AlienWarrior, Long> {
}
