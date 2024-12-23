package RUT.vokzal.Service;

import RUT.vokzal.DTO.RouteDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RouteService {
    Integer createRoute(String timeDep, String timeArr, int depPlId, int arrPlId);
    RouteDTO getRouteById(Integer id);
    void updateRoute(int id, String timeDep, String timeArr, int depPlId, int arrPlId);
    List<RouteDTO> getAllRoutes();
    Page<RouteDTO> getRoutes(Integer depPlId, int page, int size);
    void deleteRoute(Integer id);
}