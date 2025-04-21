package com.example.pgbuddy.services;

import com.example.pgbuddy.Dtos.*;
import com.example.pgbuddy.models.*;
import com.example.pgbuddy.repositories.BookingRepository;
import com.example.pgbuddy.repositories.InternetDataAddOnRepository;
import com.example.pgbuddy.repositories.InternetDeviceAddOnRepository;
import com.example.pgbuddy.repositories.InternetUsageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternetUsageService {
    private final InternetUsageRepository internetUsageRepository;
    private final InternetDataAddOnRepository internetDataAddOnRepository;
    private final InternetDeviceAddOnRepository internetDeviceAddOnRepository;
    private final BookingRepository bookingRepository;

    public InternetUsageService(InternetUsageRepository internetUsageRepository,
                                 InternetDataAddOnRepository internetDataAddOnRepository,
                                 InternetDeviceAddOnRepository internetDeviceAddOnRepository,
                                BookingRepository bookingRepository) {
        this.internetUsageRepository = internetUsageRepository;
        this.internetDataAddOnRepository = internetDataAddOnRepository;
        this.internetDeviceAddOnRepository = internetDeviceAddOnRepository;
        this.bookingRepository = bookingRepository;
    }

    // GET method to fetch internet usage data (for the specific user)
    public InternetUsageDto getInternetUsage(Long userId) {
        // Get the InternetUsage object from the repository for the userId
        InternetUsage internetUsage = internetUsageRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Internet usage not found for user ID: " + userId));

        // Convert the InternetUsage object to InternetUsageDto
        InternetUsageDto internetUsageDto = new InternetUsageDto();
        internetUsageDto.setTotalDataGb(internetUsage.getTotalDataGb());
        internetUsageDto.setDataLeftGb(internetUsage.getDataLeftGb());
        internetUsageDto.setSpeedMbps(internetUsage.getSpeedMbps());
        internetUsageDto.setMaxDevices(internetUsage.getMaxDevices());
        internetUsageDto.setResetDate(internetUsage.getResetDate().toString());
        internetUsageDto.setUserId(internetUsage.getUser().getId());

        return internetUsageDto;
    }

    // GET method to fetch data add-on options
    public List<InternetDataAddOnDto> getDataAddOnOptions() {
        // return a list of all data add-ons (from the repository)
        // return  List<InternetDataAddOnDto>

        // get all data add-ons from the repository
        List<InternetDataAddOn> dataAddOns = internetDataAddOnRepository.findAll();

        // Convert the list of InternetDataAddOn objects to a list of InternetDataAddOnDto objects
        // map InternetDataAddOn to InternetDataAddOnDto
        List<InternetDataAddOnDto> dataAddOnDtos = dataAddOns.stream().map(dataAddOn -> {
            InternetDataAddOnDto dto = new InternetDataAddOnDto();
            dto.setPackId(Math.toIntExact(dataAddOn.getId()));
            dto.setData(dataAddOn.getData());
            dto.setPrice(dataAddOn.getPrice());
            dto.setValidity(dataAddOn.getValidity());
            dto.setRecommended(dataAddOn.isRecommended());
            return dto;
        }).toList();

        return dataAddOnDtos;
    }

    // GET method to fetch device add-on options
    public List<InternetDeviceAddOnDto> getDeviceAddOnOptions() {
        // get all device add-ons from the repository
        List<InternetDeviceAddOn> deviceAddOns = internetDeviceAddOnRepository.findAll();

        // Convert the list of InternetDeviceAddOn objects to a list of InternetDeviceAddOnDto objects
        // map InternetDeviceAddOn to InternetDeviceAddOnDto
        List<InternetDeviceAddOnDto> deviceAddOnDtos = deviceAddOns.stream().map(deviceAddOn -> {
            InternetDeviceAddOnDto dto = new InternetDeviceAddOnDto();
            dto.setPackId(Math.toIntExact(deviceAddOn.getId()));
            dto.setDevices(deviceAddOn.getDevices());
            dto.setPrice(deviceAddOn.getPrice());
            dto.setValidity(deviceAddOn.getValidity());
            dto.setRecommended(deviceAddOn.isRecommended());
            return dto;
        }).toList();

        return deviceAddOnDtos;
    }

    // POST method to update internet usage data based on the selected data add-on
    public void updateInternetUsageForData(Long userId, Long id) {
        // Get the appropriate InternetDataAddOn object from the repository using it id
        InternetDataAddOn dataAddOn = internetDataAddOnRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("Internet data add-on not found for ID: " + id));

        // Get the InternetUsage object from the repository for the userId
        InternetUsage internetUsage = internetUsageRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Internet usage not found for user ID: " + userId));

        // Update the InternetUsage object with the new data add-on values
        internetUsage.setTotalDataGb(internetUsage.getTotalDataGb() + dataAddOn.getData());
        internetUsage.setDataLeftGb(internetUsage.getDataLeftGb() + dataAddOn.getData());

        // Update bookings object with the increased duesRemaining
        Booking booking = bookingRepository.findByUserId(userId);
        if (booking == null) { throw new RuntimeException("Booking not found for user ID: " + userId); }
        booking.setDuesRemaining(booking.getDuesRemaining() + dataAddOn.getPrice());
        bookingRepository.save(booking);

        // Save the updated InternetUsage object back to the repository
        internetUsageRepository.save(internetUsage);
        return;
    }

    // POST method to update internet usage data based on the selected device add-on
    public void updateInternetUsageForDevice(Long userId, Long id) {
        // Get the appropriate InternetDeviceAddOn object from the repository using it id
        InternetDeviceAddOn deviceAddOn = internetDeviceAddOnRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("Internet device add-on not found for ID: " + id));

        // Get the InternetUsage object from the repository for the userId
        InternetUsage internetUsage = internetUsageRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Internet usage not found for user ID: " + userId));

        // Update the InternetUsage object with the new device add-on values
        internetUsage.setMaxDevices((int) (internetUsage.getMaxDevices() + deviceAddOn.getDevices()));

        // Update bookings object with the increased duesRemaining
        Booking booking = bookingRepository.findByUserId(userId);
        if (booking == null) { throw new RuntimeException("Booking not found for user ID: " + userId); }
        booking.setDuesRemaining(booking.getDuesRemaining() + deviceAddOn.getPrice());
        bookingRepository.save(booking);

        // Save the updated InternetUsage object back to the repository
        internetUsageRepository.save(internetUsage);
        return;
    }
}
