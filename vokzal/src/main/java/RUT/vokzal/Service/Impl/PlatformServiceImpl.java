package RUT.vokzal.Service.Impl;

import RUT.vokzal.DTO.PlatformDTO;
import RUT.vokzal.Exception.DeleteAlreadyException;
import RUT.vokzal.Model.entity.Platform;
import RUT.vokzal.Model.enums.StatusPlatform;
import RUT.vokzal.Model.entity.Vokzal;
import RUT.vokzal.Exception.VokzalNotFoundException;
import RUT.vokzal.Repository.PlatformRepository;
import RUT.vokzal.Repository.VokzalRepository;
import RUT.vokzal.Service.PlatformService;
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

import java.util.ArrayList;
import java.util.List;

@Service
@EnableCaching
public class PlatformServiceImpl implements PlatformService {

    private PlatformRepository platformRepository;
    private VokzalRepository vokzalRepository;
    private ModelMapper modelMapper;

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setPlatformRepository(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    @Autowired
    public void setVokzalRepository(VokzalRepository vokzalRepository) {
        this.vokzalRepository = vokzalRepository;
    }

    @Override
    @CacheEvict(cacheNames = "platforms", allEntries = true)
    public Integer createPlatform(Integer number, String type, String statusPlatform, Integer vokzalId) {
        Vokzal vokzal = vokzalRepository.findById(vokzalId);
        if (vokzal == null) {
            throw new VokzalNotFoundException(vokzalId);
        }
        StatusPlatform status = StatusPlatform.valueOf(statusPlatform.toUpperCase());
        Platform platform = new Platform(number, type, status, vokzal);
        platformRepository.create(platform);
        return platform.getId();
    }

    @Override
    public PlatformDTO getPlatformById(Integer id) {
        Platform platform = platformRepository.findById(id);
        PlatformDTO platformDTO = modelMapper.map(platform, PlatformDTO.class);
        return new PlatformDTO(platformDTO.getId(), platformDTO.getNumber(), platformDTO.getType(),
                platformDTO.getStatusPlatform(), platformDTO.getVokzalId());
    }

    @Override
    @Cacheable("platforms")
    public Page<PlatformDTO> getPlatforms(String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Platform> platformsPage = searchTerm.isEmpty()
                ? platformRepository.findAll(pageable)
                : platformRepository.findByTypeContainingIgnoreCase(searchTerm, pageable);
        return platformsPage.map(platform -> modelMapper.map(platform, PlatformDTO.class));
    }

    @Override
    @CacheEvict(cacheNames = "platforms", allEntries = true)
    public void updatePlatform(Integer id, Integer number, String type, String statusPlatform, Integer vokzalId) {
        Vokzal vokzal = vokzalRepository.findById(vokzalId);
        if (vokzal == null) {
            throw new VokzalNotFoundException(vokzalId);
        }

        Platform platformI = platformRepository.findById(id);
        PlatformDTO platform = modelMapper.map(platformI, PlatformDTO.class);
        platform.setNumber(number);
        platform.setType(type);
        platform.setStatusPlatform(statusPlatform);
        platform.setVokzalId(vokzalId);
        Platform platformC = modelMapper.map(platform, Platform.class);
        platformRepository.update(platformC);
    }

    @Override
    public List<PlatformDTO> getAllPlatforms() {
        List<PlatformDTO> result = new ArrayList<>();
        List<Platform> platforms = platformRepository.findAll();
        for (Platform platform : platforms) {
            result.add(modelMapper.map(platform, PlatformDTO.class));
        }
        return result;
    }

    @Override
    @CacheEvict(cacheNames = "platforms", allEntries = true)
    public void deletePlatform(Integer id) {
        Platform platform = platformRepository.findById(id);
        if (platform.getStatusPlatform() != StatusPlatform.NOT_USED) {
            platform.setStatusPlatform(StatusPlatform.NOT_USED);
            platformRepository.update(platform);
        } else throw new DeleteAlreadyException(id);
    }
}