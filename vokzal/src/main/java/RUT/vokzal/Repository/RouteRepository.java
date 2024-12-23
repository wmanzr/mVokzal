package RUT.vokzal.Repository;

import java.util.List;

import RUT.vokzal.Model.entity.Platform;
import RUT.vokzal.Model.entity.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RouteRepository {
    void create(Route employee);
    Route findById(Integer id);
    Route update(Route employee);
    List<Route> findAll();
    Page<Route> findByDepPlId(Platform depPlId, Pageable pageable);
    Page<Route> findAll(Pageable pageable);
}