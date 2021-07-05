package com.khoubyari.example.service;

import com.khoubyari.example.domain.Hotel;
import com.khoubyari.example.dao.jpa.HotelRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.binder.BaseUnits;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.actuate.metrics.CounterService;
//import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

//import java.lang.management.MemoryUsage;

/*
 * Sample service to demonstrate what the API would use to get things done
 */
@Service
public class HotelService {

   // private static final Logger LOGGER = LoggerFactory.getLogger(HotelService.class);

    @Autowired
    private HotelRepository hotelRepository;
/*
    @Autowired
    CounterService counterService;

    @Autowired
    GaugeService gaugeService;*/

    MeterRegistry meterRegistry;

    Counter counterHotelPayload;

    public HotelService() {
        meterRegistry = new SimpleMeterRegistry();
        counterHotelPayload = Counter
                .builder("Khoubyari.HotelService.getAll.largePayload")
                .description("count hotel page calls > 50")
                .tags("hotel","payload > 50")
                .register(meterRegistry);
    }

    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel getHotel(long id) {
        //return hotelRepository.findOne(id);
        return hotelRepository.findById(id).get();  // TODO Optional proper handling
    }

    public void updateHotel(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }

    public Page<Hotel> getAllHotels(final Integer pageNumber, final Integer size) {
        final Page pageOfHotels = hotelRepository.findAll(PageRequest.of(pageNumber, size));
        // example of adding to the /metrics
        if (size > 50) {
            //counterService.increment("Khoubyari.HotelService.getAll.largePayload");
            counterHotelPayload.increment();

/*
            Gauge.builder("jvm.memory.used", Runtime.getRuntime(), mem -> mem.totalMemory() - mem.freeMemory())
                    .tag("host", "earth_host")
                    .tag("region", "local_cloudy_region")
                    .description("The amount of used memory")
                    .baseUnit(BaseUnits.BYTES)
                    .register(meterRegistry); */

        }
        return pageOfHotels;
    }
}
