package RUT.vokzal.Repository;

import java.util.List;
import RUT.vokzal.Model.entity.Train;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TrainRepository {
    void create(Train employee);
    Train findById(Integer id);
    Train update(Train employee);
    List<Train> findAll();
    Page<Train> findByNumberContainingIgnoreCase(String searchTerm, Pageable pageable);
    Page<Train> findAll(Pageable pageable);
}