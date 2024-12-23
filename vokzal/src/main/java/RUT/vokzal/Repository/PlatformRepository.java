package RUT.vokzal.Repository;

import java.util.List;
import RUT.vokzal.Model.entity.Platform;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlatformRepository {
    void create(Platform platform);
    Platform findById(Integer id);
    Platform update(Platform platform);
    List<Platform> findAll();
    Page<Platform> findByTypeContainingIgnoreCase(String searchTerm, Pageable pageable);
    Page<Platform> findAll(Pageable pageable);
}