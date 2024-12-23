package RUT.vokzal.Service;

import RUT.vokzal.DTO.VokzalDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VokzalService {
    Integer createVokzal(String name, String city, Integer capacity);
    VokzalDTO getVokzalById(Integer id);
    void updateVokzal(Integer id, String name, String city, Integer capacity);
    List<VokzalDTO> getAllVokzals();
    Page<VokzalDTO> getVokzals(String searchTerm, int page, int size);
    void deleteVokzal(Integer id);
}