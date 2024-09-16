package com.dungeon.dungeonserve.repository;

import com.dungeon.dungeonserve.models.Alignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlignmentRepository extends JpaRepository<Alignment, Long> {
}
