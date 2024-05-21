package com.MinerApp.service;

import com.MinerApp.domain.Dwarf;
import com.MinerApp.dto.CreateDwarfCommand;
import com.MinerApp.dto.DwarfInfo;
import com.MinerApp.exceptions.DwarfExistsWithSameNameException;
import com.MinerApp.repository.DwarfRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class DwarfService {

    private DwarfRepository dwarfRepository;

    @Autowired
    public DwarfService(DwarfRepository dwarfRepository) {
        this.dwarfRepository = dwarfRepository;
    }

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
}
