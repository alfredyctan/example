package org.afc.example.springboot.multidata.repository.crud;

import java.util.List;
import java.util.Optional;

import org.afc.example.springboot.multidata.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional("usersTx")
@Repository
public interface UsersCrudRepository extends CrudRepository<Users, Integer> {

	@Query("select u from Users u where u.id = ?1")
	Optional<Users> findById(Integer id);
	
	@Query("select u from Users u")
	List<Users> findAll();

}
