package RUT.vokzal.Service.Impl;

import RUT.vokzal.DTO.VokzalDTO;
import RUT.vokzal.Exception.DeleteAlreadyException;
import RUT.vokzal.Model.entity.Vokzal;
import RUT.vokzal.Repository.VokzalRepository;
import RUT.vokzal.Service.VokzalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VokzalServiceImpl implements VokzalService {

    private VokzalRepository vokzalRepository;
    private ModelMapper modelMapper;

    @Autowired
    public void setVokzalRepository(VokzalRepository vokzalRepository) {
        this.vokzalRepository = vokzalRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Integer createVokzal(String name, String city, Integer capacity) {
        Vokzal vokzal = new Vokzal(name, city, capacity);
        vokzalRepository.create(vokzal);
        return vokzal.getId();
    }

    @Override
    public VokzalDTO getVokzalById(Integer id) {
        Vokzal vokzal = vokzalRepository.findById(id);
        VokzalDTO vokzalDTO = modelMapper.map(vokzal, VokzalDTO.class);
        return new VokzalDTO(vokzalDTO.getId(), vokzalDTO.getName(), vokzalDTO.getCity(), vokzalDTO.getCapacity(), vokzalDTO.getDel());
    }

    @Override
    public void updateVokzal(Integer id, String name, String city, Integer capacity) {
        Vokzal vokzal = vokzalRepository.findById(id);
            vokzal.setName(name);
            vokzal.setCity(city);
            vokzal.setCapacity(capacity);
            vokzalRepository.update(vokzal);
    }

    @Override
    public Page<VokzalDTO> getVokzals(String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Vokzal> vokzalsPage = searchTerm.isEmpty()
                ? vokzalRepository.findAll(pageable)
                : vokzalRepository.findByNameContainingIgnoreCase(searchTerm, pageable);
        return vokzalsPage.map(vokzal -> modelMapper.map(vokzal, VokzalDTO.class));
    }

    @Override
    public List<VokzalDTO> getAllVokzals() {
        List<VokzalDTO> result = new ArrayList<>();
        List<Vokzal> vokzals = vokzalRepository.findAll();
        for (Vokzal vokzal : vokzals) {
            result.add(modelMapper.map(vokzal, VokzalDTO.class));
        }
        return result;
    }

    @Override
    public void deleteVokzal(Integer id) {
        Vokzal vokzal = vokzalRepository.findById(id);
        if (!vokzal.getDel()) {
            vokzal.setDel(true);
            vokzalRepository.update(vokzal);
        } else throw new DeleteAlreadyException(id);
    }
}