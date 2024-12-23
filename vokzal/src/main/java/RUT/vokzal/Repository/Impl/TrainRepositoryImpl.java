package RUT.vokzal.Repository.Impl;

import RUT.vokzal.Model.entity.Train;
import RUT.vokzal.Repository.BaseRepository;
import RUT.vokzal.Repository.TrainRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class TrainRepositoryImpl extends BaseRepository<Train, Integer> implements TrainRepository {

    public TrainRepositoryImpl() {
        super(Train.class);
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void create(Train train) {
        super.create(train);
    }

    @Override
    public Train findById(Integer id) {
        return super.findById(id);
    }

    @Override
    @Transactional
    public Train update(Train train) {
        return super.update(train);
    }

    @Override
    public List<Train> findAll() {
        return super.findAll();
    }

    @Override
    public Page<Train> findByNumberContainingIgnoreCase(String searchTerm, Pageable pageable) {
        List<Train> trains = entityManager.createQuery(
                        "FROM Train t WHERE CAST(t.number AS string) LIKE LOWER(:searchTerm)", Train.class)
                .setParameter("searchTerm", "%" + searchTerm + "%")
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = entityManager.createQuery(
                        "SELECT COUNT(t) FROM Train t WHERE CAST(t.number AS string) LIKE :searchTerm", Long.class)
                .setParameter("searchTerm", "%" + searchTerm + "%")
                .getSingleResult();

        return new PageImpl<>(trains, pageable, total);
    }

    @Override
    public Page<Train> findAll(Pageable pageable) {
        List<Train> trains = entityManager.createQuery("FROM Train t ORDER BY t.number ASC", Train.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = entityManager.createQuery("SELECT COUNT(t) FROM Train t", Long.class)
                .getSingleResult();

        return new PageImpl<>(trains, pageable, total);
    }
}