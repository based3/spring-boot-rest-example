package com.khoubyari.example.dao.jpa;

import com.khoubyari.example.domain.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Spring Data Repository used to delegate CRUD operations against the data source:
 * https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#data.sql.jpa-and-spring-data
 */
public interface HotelRepository extends PagingAndSortingRepository<Hotel, Long> {
    Hotel findHotelByCity(String city);
    Page findAll(Pageable pageable);
}
