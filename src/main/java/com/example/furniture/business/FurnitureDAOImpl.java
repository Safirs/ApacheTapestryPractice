package com.example.furniture.business;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.example.furniture.entities.Furniture;
import com.example.furniture.pages.Index;

public class FurnitureDAOImpl implements FurnitureDAO {

	@Inject
	Session session;
	
	private static final Logger logger = LogManager.getLogger(Index.class);
	
	@Override
	public void save(Furniture furniture) {
		session.persist(furniture);
	}
    
	@Override
	public List<Furniture> getAllFurniture() {

		logger.info("Get Furniture List called from DAO");
		CriteriaBuilder builder = session.getCriteriaBuilder();
		logger.info("builder : {}", builder);

		CriteriaQuery<Furniture> criteria = builder.createQuery(Furniture.class);
		logger.info("criteria : {}", criteria);

		criteria.from(Furniture.class);
		logger.info("does critera : {}", criteria);

		List<Furniture> furnitureList = session.createQuery(criteria).getResultList();
		logger.info("furniture : {}", furnitureList);

		return furnitureList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Furniture> getFurnitureByType(String type) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Furniture> criteria = builder.createQuery(Furniture.class);
		Root<Furniture> root = criteria.from(Furniture.class);
		criteria.select(root).where(root.get("type").in(type));

		List<Furniture> results = session.createQuery(criteria).getResultList();
		return results;
	}

	@Override
	public List<Furniture> getFurnitureByName(String name) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Furniture> criteria = builder.createQuery(Furniture.class);
		Root<Furniture> root = criteria.from(Furniture.class);
		criteria.select(root).where(builder.like(root.get("name"), "%" + name + "%"));

		List<Furniture> results = session.createQuery(criteria).getResultList();
		return results;
	}

	@Override
	public long countFurniture() {
		  return (Long) session.createQuery("select count(*) from Furniture").getSingleResult();
	}

	@Override
	public Furniture getById(Long id) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Furniture> criteria = builder.createQuery(Furniture.class);
		Root<Furniture> root = criteria.from(Furniture.class);
		criteria.select(root).where(root.get("id").in(id));
		return session.createQuery(criteria).getSingleResult();
		
	}

	@Override
	public void editFurniture(Furniture furnitrue) {
		session.merge(furnitrue);
		session.flush();
	}

	@Override
	public void delete(Furniture furniture) {
		session.remove(furniture);
	}
}
