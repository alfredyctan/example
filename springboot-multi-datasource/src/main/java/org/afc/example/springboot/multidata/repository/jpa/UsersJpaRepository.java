package org.afc.example.springboot.multidata.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.afc.example.springboot.multidata.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional("usersTxMgr")
@Repository
public class UsersJpaRepository {

	private static final Logger logger = LoggerFactory.getLogger(UsersJpaRepository.class);

	@PersistenceContext(unitName = "usersEmf")
	private EntityManager entityManager;

	public Users findById(Integer id) {
		return entityManager.find(Users.class, id);
	}

	public List<Users> findByUsername(String name) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Users> criteria = builder.createQuery(Users.class);
		Root<Users> table = criteria.from(Users.class);
		criteria.select(table);
		criteria.where(builder.like(table.get("username"), '%' + name + '%'));
		TypedQuery<Users> query = entityManager.createQuery(criteria);
		List<Users> results = query.getResultList();
		return (List) results;
	}
}