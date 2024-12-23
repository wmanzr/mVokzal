package RUT.vokzal.Repository.Impl;

import RUT.vokzal.Model.entity.Appeal;
import RUT.vokzal.Repository.AppealRepository;
import RUT.vokzal.Repository.BaseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppealRepositoryImpl extends BaseRepository<Appeal, Integer> implements AppealRepository {
    public AppealRepositoryImpl() {
        super(Appeal.class);
    }

    @Override
    @Transactional
    public void create(Appeal appeal) {
        super.create(appeal);
    }

    @Override
    public Appeal findById(int id) {
        return super.findById(id);
    }

    @Override
    @Transactional
    public Appeal update(Appeal appeal) {
        return super.update(appeal);
    }

    @Override
    public List<Appeal> findAll() {
        return super.findAll();
    }

    @Override
    public List<Appeal> findByUserId(int userId) {
            return entityManager.createQuery(
                            "SELECT a FROM Appeal a WHERE a.userId.id = :userId", Appeal.class)
                    .setParameter("userId", userId)
                    .getResultList();
    }
}
