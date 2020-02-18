package com.cg.po.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.po.model.Accounts;

@Repository
public interface AccountRepo extends JpaRepository<Accounts, String> {

	@Query(value="Select * from Accounts where mobile=?1",nativeQuery = true)
	Accounts findByMobileAndPassword(String mobile);

	@Query(value="Select * from Accounts where mobile=?1", nativeQuery = true)
	Accounts findByMobile(String mobile);

}
