package RUT.vokzal.Service.Impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import RUT.vokzal.Model.entity.Vokzal;
import RUT.vokzal.DTO.VokzalDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import RUT.vokzal.Repository.VokzalRepository;
import RUT.vokzal.Service.TopVokzalService;

@Service
public class TopVokzalServiceImpl implements TopVokzalService {

    private ModelMapper modelMapper;
    private VokzalRepository vokzalRepository;

    @Autowired
    public void setVokzalRepository(VokzalRepository vokzalRepository) {
        this.vokzalRepository = vokzalRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public List<VokzalDTO> getTop5VokzalsByDepartures() {
        LocalDate nowDate = LocalDate.now();
        List<Object[]> topVokzals = vokzalRepository.findTop5VokzalsByDepartures(nowDate);
        List<VokzalDTO> result = new ArrayList<>();

        for (Object[] row : topVokzals) {
            Vokzal vokzal = (Vokzal) row[0];
            Long departuresCount = (Long) row[1];

            VokzalDTO vokzalDTO = modelMapper.map(vokzal, VokzalDTO.class);
            vokzalDTO.setDeparturesCount(departuresCount.intValue());
            result.add(vokzalDTO);
        }
        return result;
    }
}