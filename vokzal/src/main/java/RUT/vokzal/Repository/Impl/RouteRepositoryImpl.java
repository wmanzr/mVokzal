package RUT.vokzal.Repository.Impl;

import java.util.List;
import RUT.vokzal.Model.entity.Platform;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import RUT.vokzal.Model.entity.Route;
import RUT.vokzal.Repository.BaseRepository;
import RUT.vokzal.Repository.RouteRepository;
import jakarta.transaction.Transactional;

@Repository
public class RouteRepositoryImpl extends BaseRepository<Route, Integer> implements RouteRepository {

    public RouteRepositoryImpl() {
        super(Route.class);
    }

    @Override
    @Transactional
    public void create(Route route) {
        super.create(route);
    }

    @Override
    public Route findById(Integer id) {
        return super.findById(id);
    }

    @Override
    @Transactional
    public Route update(Route route) {
        return super.update(route);
    }

    @Override
    public List<Route> findAll() {
        return super.findAll();
    }

    @Override
    public Page<Route> findByDepPlId(Platform depPlId, Pageable pageable) {
        List<Route> routes = entityManager.createQuery(
                        "FROM Route r WHERE r.depPlId = :depPlId", Route.class)
                .setParameter("depPlId", depPlId)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = entityManager.createQuery(
                        "SELECT COUNT(r) FROM Route r WHERE r.depPlId = :depPlId", Long.class)
                .setParameter("depPlId", depPlId)
                .getSingleResult();

        return new PageImpl<>(routes, pageable, total);
    }

    @Override
    public Page<Route> findAll(Pageable pageable) {
        List<Route> routes = entityManager.createQuery("FROM Route r ORDER BY r.depPlId ASC", Route.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = entityManager.createQuery("SELECT COUNT(r) FROM Route r", Long.class)
                .getSingleResult();

        return new PageImpl<>(routes, pageable, total);
    }
}