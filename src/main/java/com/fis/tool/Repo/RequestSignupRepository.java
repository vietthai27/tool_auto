package com.fis.tool.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fis.tool.Model.RequestSignup;


@Repository
public interface RequestSignupRepository extends JpaRepository<RequestSignup, Long> {

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO request_signup(\r\n"
			+ "	created_date, code_signup, username)\r\n"
			+ "	VALUES ( :currentDate, :codeSignup, :username)", nativeQuery = true)
	int insertSignUpRequest(@Param("codeSignup") String codeSignup, @Param("currentDate") String currentDate,@Param("username") String username);
	
	@Query(value = "SELECT code_signup\r\n"
			+ "FROM request_signup\r\n"
			+ "WHERE (USERNAME,CREATED_DATE) \r\n"
			+ "in\r\n"
			+ "	(SELECT USERNAME,MAX(CREATED_DATE) AS date\r\n"
			+ "			FROM request_signup\r\n"
			+ "			WHERE USERNAME = :username\r\n"
			+ "			GROUP BY USERNAME)", nativeQuery = true)
	String getCodeSignup(@Param("username") String username);
	
	@Query(value = "SELECT id\r\n"
			+ "	FROM request_signup where username = :username ", nativeQuery = true)
	List<Long> getAllIdByUsername(@Param("username") String username);
}
