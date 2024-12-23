package RUT.vokzal.Repository.Impl;

import RUT.vokzal.Model.entity.Platform;
import RUT.vokzal.Repository.BaseRepository;
import RUT.vokzal.Repository.PlatformRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class PlatformRepositoryImpl extends BaseRepository<Platform, Integer> implements PlatformRepository {

    public PlatformRepositoryImpl() {
        super(Platform.class);
    }

    @Override
    @Transactional
    public void create(Platform platform) {
        super.create(platform);
    }

    @Override
    public Platform findById(Integer id) {
        return super.findById(id);
    }

    @Override
    @Transactional
    public Platform update(Platform platform) {
        return super.update(platform);
    }

    @Override
    public List<Platform> findAll() {
        return super.findAll();
    }

    @Override
    public Page<Platform> findByTypeContainingIgnoreCase(String searchTerm, Pageable pageable) {
        List<Platform> platforms = entityManager.createQuery(
                        "FROM Platform p WHERE LOWER(p.type) LIKE LOWER(:searchTerm)", Platform.class)
                .setParameter("searchTerm", "%" + searchTerm + "%")
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = entityManager.createQuery(
                        "SELECT COUNT(p) FROM Platform p WHERE LOWER(p.type) LIKE LOWER(:searchTerm)", Long.class)
                .setParameter("searchTerm", "%" + searchTerm + "%")
                .getSingleResult();

        return new PageImpl<>(platforms, pageable, total);
    }

    @Override
    public Page<Platform> findAll(Pageable pageable) {
        List<Platform> platforms = entityManager.createQuery("FROM Platform p ORDER BY p.type ASC", Platform.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = entityManager.createQuery("SELECT COUNT(p) FROM Platform p", Long.class)
                .getSingleResult();

        return new PageImpl<>(platforms, pageable, total);
    }
}