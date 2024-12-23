package RUT.vokzal.Repository.Impl;

import RUT.vokzal.Model.entity.Trip;
import RUT.vokzal.Model.entity.User;
import RUT.vokzal.Repository.BaseRepository;
import RUT.vokzal.Repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl extends BaseRepository<User, Integer> implements UserRepository {

    public UserRepositoryImpl() {
        super(User.class);
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void create(User user) {
        super.create(user);
    }

    @Override
    public User findById(Integer id) {
        return super.findById(id);
    }

    @Override
    @Transactional
    public User update(User user) {
        return super.update(user);
    }

    @Override
    public List<User> findAll() {
        return super.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            User users = entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(users);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            User users = entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                    .getSingleResult();
            return Optional.of(users);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
