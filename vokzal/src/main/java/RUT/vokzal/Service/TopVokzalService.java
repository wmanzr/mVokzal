package RUT.vokzal.Service;

import java.util.List;
import RUT.vokzal.DTO.VokzalDTO;

public interface TopVokzalService {
    List<VokzalDTO> getTop5VokzalsByDepartures();
}