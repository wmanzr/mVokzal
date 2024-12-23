package RUT.vokzal.Repository;

import RUT.vokzal.Model.entity.Appeal;

import java.util.List;

public interface AppealRepository {
    void create(Appeal appeal);
    Appeal findById(int id);
    List<Appeal> findByUserId(int userId);
    Appeal update(Appeal appeal);
    List<Appeal> findAll();
}
