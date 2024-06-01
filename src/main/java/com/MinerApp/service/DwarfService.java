package com.MinerApp.service;

import com.MinerApp.domain.Dwarf;
import com.MinerApp.dto.CreateDwarfCommand;
import com.MinerApp.dto.DwarfInfo;
import com.MinerApp.exceptions.DwarfExistsWithSameNameException;
import com.MinerApp.exceptions.DwarfNotExistsWithGivenId;
import com.MinerApp.repository.DwarfRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class DwarfService {

    private DwarfRepository dwarfRepository;

    private ModelMapper modelMapper;

    @Autowired
    public DwarfService(DwarfRepository dwarfRepository, ModelMapper modelMapper) {
        this.dwarfRepository = dwarfRepository;
        this.modelMapper = modelMapper;
    }

    //this object is created without using ModelMapper configuration
    public DwarfInfo dwarfSpawner(CreateDwarfCommand command) {
        dwarfNameValidator(command.getName());
        Dwarf dwarf = new Dwarf();
        dwarf.setName(command.getName());
        dwarf.setProductivity(command.getProductivity());
        dwarfRepository.save(dwarf);

        DwarfInfo dwarfInfo = new DwarfInfo();
        dwarfInfo.setId(dwarf.getId());
        dwarfInfo.setName(dwarf.getName());
        dwarfInfo.setProductivity(dwarf.getProductivity());

        return dwarfInfo;
    }

    private void dwarfNameValidator(String dwarfName) {
        Optional<Dwarf> optionalDwarf = dwarfRepository.findDwarfByName(dwarfName);
        if(optionalDwarf.isPresent()){
            throw new DwarfExistsWithSameNameException(dwarfName);
        }
    }

    public Dwarf findById(Long dwarfId) {
        Optional<Dwarf> optionalDwarf = dwarfRepository.findById(dwarfId);
        if(optionalDwarf.isEmpty()){
            throw new DwarfNotExistsWithGivenId(dwarfId);
        }
        return optionalDwarf.get();
    }
}
