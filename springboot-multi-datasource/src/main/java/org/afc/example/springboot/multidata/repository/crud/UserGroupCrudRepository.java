package org.afc.example.springboot.multidata.repository.crud;

import java.util.Optional;

import org.afc.example.springboot.multidata.model.UserGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional("userGroupTx")
@Repository
public interface UserGroupCrudRepository extends CrudRepository<UserGroup, Integer> {

	@Query("select ug from UserGroup ug where ug.id = ?1")
	Optional<UserGroup> findById(Integer id);
	
	@Query("select ug from UserGroup ug")
	Iterable<UserGroup> findAll();
	
}
