package carRental.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import carRental.DTO.AgencyDto;
import carRental.DTO.CreatingAgencyDto;
import carRental.models.Agency;
import carRental.services.AgencyService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/agencies")
public class AgencyController {
    @Autowired
    private AgencyService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AgencyDto>> get() {
        List<Agency> agencies = this.service.getAll();
        if (agencies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<AgencyDto> result = new ArrayList<>();

        for (Agency agency : agencies) {
        	AgencyDto dto = toDto(agency);
            result.add(dto);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AgencyDto> addAgency(@RequestBody CreatingAgencyDto agencyDto) {
    	Agency agency = toCreatingModel(agencyDto);
    	Agency savedAgency = this.service.save(agency);
        return new ResponseEntity<>(toDto(savedAgency), HttpStatus.CREATED);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<AgencyDto> getStudentById(@PathVariable("id") Long id) {
    	Agency agency = this.service.getById(id);
        if (agency == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(toDto(agency), HttpStatus.OK);
    }

    public AgencyDto toDto(Agency agency) {
    	AgencyDto dto = new AgencyDto();
        dto.name = agency.getName();
        dto.address = agency.getAddress();
        dto.id = agency.getId();
        return dto;
    }

    public Agency toCreatingModel(CreatingAgencyDto dto) {
    	Agency agency = new Agency();
    	agency.setName(dto.name);
    	agency.setAddress(dto.address);
        return agency;
    }
}
