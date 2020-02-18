package com.cg.po.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.po.model.Accounts;
import com.cg.po.model.Menu;

@Repository
public interface MenuRepo extends JpaRepository<Menu, Accounts> {

	@Query(value="select * from menu where mobile=?1 ", nativeQuery=true)
	Optional<Menu> findByMobile(String mobile);

}
