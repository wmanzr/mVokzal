package RUT.vokzal.Service.Impl;

import RUT.vokzal.DTO.RouteDTO;
import RUT.vokzal.Exception.DeleteAlreadyException;
import RUT.vokzal.Model.entity.Platform;
import RUT.vokzal.Model.entity.Route;
import RUT.vokzal.Exception.PlatformNotFoundException;
import RUT.vokzal.Repository.PlatformRepository;
import RUT.vokzal.Repository.RouteRepository;
import RUT.vokzal.Service.RouteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableCaching
public class RouteServiceImpl implements RouteService {

    private RouteRepository routeRepository;
    private PlatformRepository platformRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setPlatformRepository(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    @Autowired
    public void setRouteRepository(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    @CacheEvict(cacheNames = "routes", allEntries = true)
    public Integer createRoute(String timeDep, String timeArr, int depPlId, int arrPlId) {
        Platform depPl = platformRepository.findById(depPlId);
        Platform arrPl = platformRepository.findById(arrPlId);
        if (depPl == null) {
            throw new PlatformNotFoundException(depPlId);
        }
        if (arrPl == null) {
            throw new PlatformNotFoundException(arrPlId);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime departureTime = LocalTime.parse(timeDep, formatter);
        LocalTime arrivalTime = LocalTime.parse(timeArr, formatter);

        Route route = new Route(departureTime, arrivalTime, depPl, arrPl);
        routeRepository.create(route);
        return route.getId();
    }

    @Override
    public RouteDTO getRouteById(Integer id) {
        Route route = routeRepository.findById(id);
        return modelMapper.map(route, RouteDTO.class);
    }

    @Override
    @Cacheable("routes")
    public Page<RouteDTO> getRoutes(Integer depPlId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Route> routesPage;
        if (depPlId != null) {
            Platform PlId = platformRepository.findById(depPlId);
            routesPage = routeRepository.findByDepPlId(PlId, pageable);
        } else {
            routesPage = routeRepository.findAll(pageable);
        }

        return routesPage.map(route -> modelMapper.map(route, RouteDTO.class));
    }

    @Override
    @CacheEvict(cacheNames = "routes", allEntries = true)
    public void updateRoute(int id, String timeDep, String timeArr, int depPlId, int arrPlId) {
        Platform platformDep = platformRepository.findById(depPlId);
        if (platformDep == null) {
            throw new PlatformNotFoundException(depPlId);
        }

        Platform platformArr = platformRepository.findById(arrPlId);
        if (platformArr == null) {
            throw new PlatformNotFoundException(arrPlId);
        }

        Route routeI = routeRepository.findById(id);
        RouteDTO route = modelMapper.map(routeI, RouteDTO.class);
        route.setTimeDep(timeDep);
        route.setTimeArr(timeArr);
        route.setDepPlId(depPlId);
        route.setArrPlId(arrPlId);
        Route routeC = modelMapper.map(route, Route.class);
        routeRepository.update(routeC);
    }

    @Override
    public List<RouteDTO> getAllRoutes() {
        List<RouteDTO> result = new ArrayList<>();
        List<Route> routes = routeRepository.findAll();
        for (Route route : routes) {
            result.add(modelMapper.map(route, RouteDTO.class));
        }
        return result;
    }

    @Override
    @CacheEvict(cacheNames = "routes", allEntries = true)
    public void deleteRoute(Integer id) {
        Route route = routeRepository.findById(id);
        if (!route.getDel()) {
            route.setDel(true);
            routeRepository.update(route);
        } else throw new DeleteAlreadyException(id);
    }
}