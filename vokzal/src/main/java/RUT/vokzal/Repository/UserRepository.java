package RUT.vokzal.Repository;

import RUT.vokzal.Model.entity.Trip;
import RUT.vokzal.Model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void create(User user);
    User findById(Integer id);
    User update(User user);
    List<User> findAll();
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}