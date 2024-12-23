package RUT.vokzal.Repository.Impl;

import RUT.vokzal.Model.entity.Role;
import RUT.vokzal.Model.entity.User;
import RUT.vokzal.Model.enums.UserRoles;
import RUT.vokzal.Repository.BaseRepository;
import RUT.vokzal.Repository.UserRoleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRoleRepositoryImpl extends BaseRepository<Role, Integer> implements UserRoleRepository {
    public UserRoleRepositoryImpl() {
        super(Role.class);
    }
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void create(Role role) {
        super.create(role);
    }

    @Override
    public List<Role> findAll() {
        return super.findAll();
    }

    @Override
    public Optional<Role> findRoleByName(UserRoles role) {
        try {
            Role roleResult = entityManager.createQuery(
                            "SELECT r FROM Role r WHERE r.name = :name", Role.class)
                    .setParameter("name", role)
                    .getSingleResult();
            return Optional.of(roleResult);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
