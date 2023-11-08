package com.fis.tool.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fis.tool.Model.FisUser;

@Repository
public interface FisUserRepository extends JpaRepository<FisUser, Long>{
	
	@Query(value = "SELECT * FROM fis_user WHERE auto_state = true", nativeQuery = true)
	List<FisUser> getAllAutoUser();

}
