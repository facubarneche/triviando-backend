package com.example.proyecto2025_BE.dao;

import java.util.Optional;

import com.example.proyecto2025_BE.utils.UserRankingProjection;
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
					"  SELECT u.id, SUM(a.score) as total_score, ROW_NUMBER() OVER (ORDER BY SUM(a.score) DESC, u.id ASC) as position " +
					"  FROM users u LEFT JOIN answer a ON u.id = a.user_id " +
					"  GROUP BY u.id " +
					") ranked WHERE ranked.id = :userId",
			nativeQuery = true)
	Integer findUserRankPosition(@Param("userId") Long userId);

	@Query(value = """
       SELECT u.id as id, u.username as username, COALESCE(SUM(a.score), 0) as score, ranked.position as position
       FROM (
          SELECT u_inner.id, COALESCE(SUM(a_inner.score), 0) as total_score,
                ROW_NUMBER() OVER (ORDER BY COALESCE(SUM(a_inner.score), 0) DESC, u_inner.id ASC) as position
          FROM users u_inner
          LEFT JOIN answer a_inner ON u_inner.id = a_inner.user_id
          GROUP BY u_inner.id
       ) ranked
       JOIN users u ON u.id = ranked.id
       LEFT JOIN answer a ON u.id = a.user_id
       GROUP BY u.id, u.username, ranked.position
       ORDER BY ranked.position ASC, u.id ASC
       """,
			countQuery = "SELECT COUNT(DISTINCT u.id) FROM users u LEFT JOIN answer a ON u.id = a.user_id",
			nativeQuery = true)
	Page<UserRankingProjection> findAllUsersWithRank(Pageable pageable);

	@Query(value = """
      SELECT u.id as id, u.username as username, COALESCE(SUM(a.score), 0) as score, ranked.position as position
      FROM (
         SELECT u_inner.id, COALESCE(SUM(a_inner.score), 0) as weekly_score,
               ROW_NUMBER() OVER (ORDER BY COALESCE(SUM(a_inner.score), 0) DESC, u_inner.id ASC) as position
         FROM users u_inner
         LEFT JOIN answer a_inner ON u_inner.id = a_inner.user_id
           AND EXTRACT(WEEK FROM a_inner.fecha_respuesta) = EXTRACT(WEEK FROM CURRENT_DATE)
           AND EXTRACT(YEAR FROM a_inner.fecha_respuesta) = EXTRACT(YEAR FROM CURRENT_DATE)
         WHERE EXTRACT(WEEK FROM u_inner.ultima_actividad) = EXTRACT(WEEK FROM CURRENT_DATE)
           AND EXTRACT(YEAR FROM u_inner.ultima_actividad) = EXTRACT(YEAR FROM CURRENT_DATE)
         GROUP BY u_inner.id
      ) ranked
      JOIN users u ON u.id = ranked.id
      LEFT JOIN answer a ON u.id = a.user_id 
         AND EXTRACT(WEEK FROM a.fecha_respuesta) = EXTRACT(WEEK FROM CURRENT_DATE)
         AND EXTRACT(YEAR FROM a.fecha_respuesta) = EXTRACT(YEAR FROM CURRENT_DATE)
      GROUP BY u.id, u.username, ranked.position
      ORDER BY ranked.position ASC, u.id ASC
      """,
	countQuery = """
	  SELECT COUNT(DISTINCT u.id) FROM users u
	  WHERE EXTRACT(WEEK FROM u.ultima_actividad) = EXTRACT(WEEK FROM CURRENT_DATE)
	    AND EXTRACT(YEAR FROM u.ultima_actividad) = EXTRACT(YEAR FROM CURRENT_DATE)
	""",
	nativeQuery = true)
	Page<UserRankingProjection> findWeeklyRanking(Pageable pageable);

}
