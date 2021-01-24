package com.upgrad.hirewheels.controllers;

import com.upgrad.hirewheels.dto.VehicleDTO;
import com.upgrad.hirewheels.entities.Vehicle;
import com.upgrad.hirewheels.exceptions.*;
import com.upgrad.hirewheels.responsemodel.CustomResponse;
import com.upgrad.hirewheels.services.AdminService;
import com.upgrad.hirewheels.validator.AdminRequestValidator;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value="/hirewheels/v1")
public class AdminController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AdminService adminService;

    @Autowired
    AdminRequestValidator adminRequestValidator;

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    /**
     *
     * @param vehicleDTO
     * @throws APIException
     * @throws VehicleNumberNotUniqueException
     */

    @PostMapping("/vehicles")
    public ResponseEntity addVehicleRequest(@RequestBody VehicleDTO vehicleDTO) throws APIException, VehicleNumberNotUniqueException {
        logger.info("Inside addVehicleRequest() method");
        adminRequestValidator.validateAddVehicleRequest(vehicleDTO);
        logger.debug("Vehicle details validated");
        Vehicle vehicle = modelMapper.map(vehicleDTO, Vehicle.class);
        adminService.registerVehicle(vehicle);
        logger.debug("Accepted new vehicle details: "+ vehicle);
        CustomResponse response = new CustomResponse(new Date(), "Vehicle Added Successfully", 200);
        logger.debug("Returning successful vehicle addition response: "+response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     *
     * @param vehicleDTO
     * @param vehicleId
     * @throws APIException
     * @throws VehicleNotFoundException
     */

    @PutMapping("vehicles/{vehicleId}")
    public ResponseEntity changeVehicleAvailability(@RequestBody VehicleDTO vehicleDTO, @PathVariable int vehicleId) throws APIException, VehicleNotFoundException{

        logger.debug("Change Availability status: vehicle id: "+ vehicleId, vehicleDTO);
        int availability_status = vehicleDTO.getAvailabilityStatus();
        adminRequestValidator.validateChangeVehicleAvailability(availability_status);
        logger.debug("Validated Availability Status: "+ availability_status);
        Vehicle vehicle = adminService.changeAvailability(vehicleId, availability_status);
        logger.debug("Changed Availability Status for Vehicle: "+ vehicle);
        CustomResponse response = new CustomResponse(new Date(), "Activity performed successfully", 200);
        logger.debug("Returning successful changing of availability status response: "+response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
