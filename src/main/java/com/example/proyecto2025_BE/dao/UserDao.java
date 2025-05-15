package com.example.proyecto2025_BE.dao;

import java.util.Optional;

import com.example.proyecto2025_BE.utils.ranking.UserRankingProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.proyecto2025_BE.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Long>{

	public Optional<User> findByEmailAndPassword(String email, String password);

	public Optional<User> findByEmail(String email);

	@Query(value =
			"SELECT position FROM (" +
					"  SELECT id, ROW_NUMBER() OVER (ORDER BY score DESC, id ASC) as position " +
					"  FROM users" +
					") ranked WHERE ranked.id = :userId",
			nativeQuery = true)
	Integer findUserRankPosition(@Param("userId") Long userId);

	@Query(value = """
    SELECT u.id as id, u.username as username, u.score as score, ranked.position as position
    FROM (
        SELECT id, ROW_NUMBER() OVER (ORDER BY score DESC, id ASC) as position
        FROM users
    ) ranked
    JOIN users u ON u.id = ranked.id
    """,
			countQuery = "SELECT COUNT(*) FROM users",
			nativeQuery = true)
	Page<UserRankingProjection> findAllUsersWithRank(Pageable pageable);
}
