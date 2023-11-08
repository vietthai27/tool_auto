package com.fis.tool.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.fis.tool.Model.ToolUser;

public interface UserRepository extends JpaRepository<ToolUser, Long> {
	

	Optional<ToolUser> findByUsername(String username);

	@Query(value = "SELECT COUNT (EMAIL)\r\n" + "FROM tool_user\r\n"
			+ "WHERE EMAIL = :email ", nativeQuery = true)
	int checkEmail(@Param("email") String email);

	@Query(value = "SELECT COUNT(USERNAME)\r\n" + "FROM tool_user\r\n"
			+ "WHERE USERNAME = :username", nativeQuery = true)
	int checkUsername(@Param("username") String username);
	
	@Query(value = "SELECT EMAIL\r\n" + "FROM tool_user\r\n"
			+ "WHERE USERNAME = :username ", nativeQuery = true)
	String getEmail(@Param("username") String username);

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO tool_user (\r\n" + "	 email, password, username)\r\n"
			+ "	VALUES ( :email, :password, :username)", nativeQuery = true)
	int insertUser(@Param("username") String username, @Param("password") String password,
			@Param("email") String email);
	
	@Query(value = "SELECT ID\r\n" + "FROM tool_user\r\n"
			+ "WHERE USERNAME = :username", nativeQuery = true)
	int getUserId(@Param("username") String username);
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO tool_user_roles(\r\n"
			+ "	tool_users_id, roles_id)\r\n"
			+ "	VALUES (:userId, 1)", nativeQuery = true)
	int insertDefaultRole(@Param("userId") int userId);
	
}
