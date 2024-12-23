package RUT.vokzal.Repository.Impl;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.NoResultException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import RUT.vokzal.Model.entity.Vokzal;
import RUT.vokzal.Repository.BaseRepository;
import RUT.vokzal.Repository.VokzalRepository;
import jakarta.transaction.Transactional;

@Repository
public class VokzalRepositoryImpl extends BaseRepository<Vokzal, Integer> implements VokzalRepository {

    public VokzalRepositoryImpl() {
        super(Vokzal.class);
    }

    @Override
    @Transactional
    public void create(Vokzal vokzal) {
        super.create(vokzal);
    }

    @Override
    public Vokzal findById(Integer id) {
        return super.findById(id);
    }

    @Override
    @Transactional
    public Vokzal update(Vokzal vokzal) {
        return super.update(vokzal);
    }

    @Override
    public List<Vokzal> findAll() {
        return super.findAll();
    }

    @Override
    public Vokzal findByName(String name) {
        try {
            return entityManager.createQuery(
                            "SELECT v FROM Vokzal v WHERE v.name = :name AND v.del <> true", Vokzal.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Object[]> findTop5VokzalsByDepartures(LocalDate nowDate) {
    return entityManager.createQuery(
            "SELECT v, COUNT(t) " +
            "FROM Vokzal v " +
            "JOIN Platform p ON p.vokzalId = v " +
            "JOIN Trip t ON t.route.depPlId = p " +
            "WHERE t.dateDep <= :nowDate " +
            "GROUP BY v " +
            "ORDER BY COUNT(t) DESC", Object[].class)
            .setParameter("nowDate", nowDate)
            .setMaxResults(5)
            .getResultList();
    }

    @Override
    public Page<Vokzal> findByNameContainingIgnoreCase(String searchTerm, Pageable pageable) {
        List<Vokzal> vokzals = entityManager.createQuery(
                        "FROM Vokzal v WHERE LOWER(v.name) LIKE LOWER(:searchTerm)", Vokzal.class)
                .setParameter("searchTerm", "%" + searchTerm + "%")
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = entityManager.createQuery(
                        "SELECT COUNT(v) FROM Vokzal v WHERE LOWER(v.name) LIKE LOWER(:searchTerm)", Long.class)
                .setParameter("searchTerm", "%" + searchTerm + "%")
                .getSingleResult();

        return new PageImpl<>(vokzals, pageable, total);
    }

    @Override
    public Page<Vokzal> findAll(Pageable pageable) {
        List<Vokzal> vokzals = entityManager.createQuery("FROM Vokzal v ORDER BY v.city ASC", Vokzal.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = entityManager.createQuery("SELECT COUNT(v) FROM Vokzal v", Long.class)
                .getSingleResult();

        return new PageImpl<>(vokzals, pageable, total);
    }

}