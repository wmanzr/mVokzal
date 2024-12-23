package RUT.vokzal.Service;

import RUT.vokzal.DTO.PlatformDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PlatformService {
    Integer createPlatform(Integer number, String type, String statusPlatform, Integer vokzalId);
    PlatformDTO getPlatformById(Integer id);
    void updatePlatform(Integer id, Integer number, String type, String statusPlatform, Integer vokzalId);
    List<PlatformDTO> getAllPlatforms();
    Page<PlatformDTO> getPlatforms(String searchTerm, int page, int size);
    void deletePlatform(Integer id);
}