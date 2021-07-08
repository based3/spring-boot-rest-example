package com.khoubyari.api;

/**
 * Uses JsonPath: https://code.google.com/archive/p/json-path/, Hamcrest and MockMVC
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khoubyari.example.Application;
import com.khoubyari.example.api.rest.HotelController;
import com.khoubyari.example.domain.Hotel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(
//   { "/application.xml" /*, "/test-data-access-config.xml" */})
//@SpringBootTest(classes = Application.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
//@WebMvcTest
@ActiveProfiles("test")
public class HotelControllerTest {

    public final static Logger LOG = LoggerFactory.getLogger(HotelControllerTest.class);

    private static final String API_PATH = "/api/v1/hotels/";
    private static final String RESOURCE_LOCATION_PATTERN = "http://localhost" + API_PATH + "[0-9]+";

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    HotelController controller;

   // @Autowired
   // static WebApplicationContext context;

    /*@BeforeAll
    public static void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }*/

    @Test//(timeout = 1)
    public void shouldHaveEmptyDB() throws Exception {
        mockMvc.perform(get(API_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)));
        // {content=[], pageable={sort={sorted=false, unsorted=true, empty=true}, offset=0, pageNumber=0, pageSize=100, unpaged=false, paged=true}, last=true, totalElements=0, totalPages=0, size=100, number=0, sort={sorted=false, unsorted=true, empty=true}, numberOfElements=0, first=true, empty=true}
    }

    @Test
    public void shouldCreateRetrieveDelete() throws Exception {
        Hotel r1 = mockHotel("shouldCreateRetrieveDelete");
        byte[] r1Json = toJson(r1);

        //CREATE
        MvcResult result = mockMvc.perform(post(API_PATH)
                .content(r1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
                .andReturn();
        long id = getResourceIdFromUrl(Objects.requireNonNull(result.getResponse().getRedirectedUrl()));

        //RETRIEVE
        mockMvc.perform(get(API_PATH + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) id)))
                .andExpect(jsonPath("$.name", is(r1.getName())))
                .andExpect(jsonPath("$.city", is(r1.getCity())))
                .andExpect(jsonPath("$.description", is(r1.getDescription())))
                .andExpect(jsonPath("$.rating", is(r1.getRating())));

        //DELETE
        mockMvc.perform(delete(API_PATH + id))
                .andExpect(status().isNoContent());

        //RETRIEVE should fail
        mockMvc.perform(get(API_PATH + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // 404
   // isNoContent() 204
/*
JSONAssert.assertEquals(
  "{foo: 'bar', baz: 'qux'}",
  JSONObject.fromObject("{foo: 'bar', baz: 'xyzzy'}"));
 */
    }

    @Test
    public void shouldCreateAndUpdateAndDelete() throws Exception {
        Hotel r1 = mockHotel("shouldCreateAndUpdate");
        byte[] r1Json = toJson(r1);
        //CREATE
        MvcResult result = mockMvc.perform(post(API_PATH)
                .content(r1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
                .andReturn();
        long id = getResourceIdFromUrl(Objects.requireNonNull(result.getResponse().getRedirectedUrl()));

        Hotel r2 = mockHotel("shouldCreateAndUpdate2");
        r2.setId(id);
        byte[] r2Json = toJson(r2);

        //UPDATE
        result = mockMvc.perform(put(API_PATH + id)
                .content(r2Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        //RETRIEVE updated
        mockMvc.perform(get(API_PATH + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) id)))
                .andExpect(jsonPath("$.name", is(r2.getName())))
                .andExpect(jsonPath("$.city", is(r2.getCity())))
                .andExpect(jsonPath("$.description", is(r2.getDescription())))
                .andExpect(jsonPath("$.rating", is(r2.getRating())));

        //DELETE
        mockMvc.perform(delete(API_PATH + id))
                .andExpect(status().isNoContent());
    }

    /*
    ******************************
     */

    private long getResourceIdFromUrl(String locationUrl) {
        String[] parts = locationUrl.split("/");
        return Long.parseLong(parts[parts.length - 1]);
    }


    private Hotel mockHotel(String prefix) {
        Hotel r = new Hotel();
        r.setCity(prefix + "_city");
        r.setDescription(prefix + "_description");
        r.setName(prefix + "_name");
        r.setRating(new Random().nextInt(6));
        return r;
    }

    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }

    // match redirect header URL (aka Location header)
    private static ResultMatcher redirectedUrlPattern(final String expectedUrlPattern) {
        return result -> {
            Pattern pattern = Pattern.compile("\\A" + expectedUrlPattern + "\\z");
            String urlPattern = Objects.requireNonNull(result.getResponse().getRedirectedUrl());
            LOG.info("### urlPattern: " + urlPattern);
            Assertions.assertTrue(pattern.matcher(urlPattern).find());
        };
    }

}
