package RUT.vokzal.Repository;

import RUT.vokzal.Model.entity.Role;
import RUT.vokzal.Model.enums.UserRoles;
import java.util.List;
import java.util.Optional;

public interface UserRoleRepository {
    Optional<Role> findRoleByName(UserRoles userRoles);

    void create(Role role);

    List<Role> findAll();
}