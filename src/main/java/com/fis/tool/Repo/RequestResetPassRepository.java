package com.fis.tool.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fis.tool.Model.RequestResetPass;

@Repository
public interface RequestResetPassRepository extends JpaRepository<RequestResetPass, Long> {

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO request_reset_pass(\r\n"
			+ " code_reset_pass, created_date, username)\r\n"
			+ "	VALUES ( :codeResetPass,:currentDate, :username)", nativeQuery = true)
	int insertResetPassRequest(@Param("codeResetPass") String codeResetPass, @Param("currentDate") String currentDate,@Param("username") String username);
	
	@Query(value = "SELECT code_reset_pass\r\n"
			+ "FROM request_reset_pass\r\n"
			+ "WHERE (USERNAME,CREATED_DATE) \r\n"
			+ "in\r\n"
			+ "	(SELECT USERNAME,MAX(CREATED_DATE) AS date\r\n"
			+ "			FROM request_reset_pass\r\n"
			+ "			WHERE USERNAME = :username\r\n"
			+ "			GROUP BY USERNAME)", nativeQuery = true)
	String getCodeResetPass(@Param("username") String username);
	
	@Query(value = "SELECT id FROM request_reset_pass where username = :username", nativeQuery = true)
	List<Long> getAllIdByUsername(@Param("username") String username);
	
	
}
