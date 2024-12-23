package RUT.vokzal.Repository;

import java.time.LocalDate;
import java.util.List;
import RUT.vokzal.Model.entity.Vokzal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VokzalRepository {
    void create(Vokzal vokzal);
    Vokzal findById(Integer id);
    Vokzal update(Vokzal vokzal);
    List<Vokzal> findAll();
    Vokzal findByName(String name);
    List<Object[]> findTop5VokzalsByDepartures(LocalDate nowDate);
    Page<Vokzal> findByNameContainingIgnoreCase(String searchTerm, Pageable pageable);
    Page<Vokzal> findAll(Pageable pageable);
}