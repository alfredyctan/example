package org.afc.example.springboot.multidata.repository.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.afc.example.springboot.multidata.model.UserGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional("userGroupTxMgr")
@Repository
public class UserGroupJpaRepository {

	private static final Logger logger = LoggerFactory.getLogger(UserGroupJpaRepository.class);

	@PersistenceContext(unitName = "userGroupEmf")
	private EntityManager entityManager;

	public UserGroup findById(Integer id) {
		return entityManager.find(UserGroup.class, id);
	}

}
