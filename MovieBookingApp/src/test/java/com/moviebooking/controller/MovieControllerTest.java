package com.moviebooking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviebooking.entity.Movie;
import com.moviebooking.entity.MovieId;
import com.moviebooking.exception.CommonException;
import com.moviebooking.exception.MovieAlreadyExistsException;
import com.moviebooking.exception.MovieNotFoundException;
import com.moviebooking.service.MovieServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest
public class MovieControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    @InjectMocks
    private MovieController movieController;
    @Mock
    private MovieServiceImpl movieService;
    private Movie movie;
    private MovieId movieId;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
        movieId = new MovieId("movieName", "theatreName");
        movie = new Movie(movieId, 1, 100.00, 100, 1, "Available");
    }

    @Test
    public void addMovie_successTest() throws Exception {
        ResponseEntity<String> response = ResponseEntity.ok("Movie created successfully!");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/moviebooking/addMovie")
                .header("Authorization", "token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie))).andReturn();
        assertEquals(201, result.getResponse().getStatus());
        assertEquals(response.getBody(), result.getResponse().getContentAsString());
    }
    @Test
    public void addMovie_MovieAlreadyExistsExceptionTest() throws Exception {
        ResponseEntity<String> response = ResponseEntity.ok("Movie already exists by Id");
        Mockito.when(movieService.addMovie(movie)).thenThrow(new MovieAlreadyExistsException("Movie already exists by Id"));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/moviebooking/addMovie")
                .header("Authorization", "token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie))).andReturn();
        assertEquals(406, result.getResponse().getStatus());
        assertEquals(response.getBody(), result.getResponse().getContentAsString());
    }
    @Test
    public void addMovie_CommonExceptionTest() throws Exception {
        ResponseEntity<String> response = ResponseEntity.ok("Number of tickets allotted cannot be less than or Equal to ZERO");
        Mockito.when(movieService.addMovie(movie)).thenThrow(new CommonException("Number of tickets allotted cannot be less than or Equal to ZERO"));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/moviebooking/addMovie")
                .header("Authorization", "token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie))).andReturn();
        assertEquals(406, result.getResponse().getStatus());
        assertEquals(response.getBody(), result.getResponse().getContentAsString());
    }
    @Test
    public void updateMovie_successTest() throws Exception {
        ResponseEntity<String> response = ResponseEntity.ok("Movie updated successfully!");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/moviebooking/updateMovie")
                .header("Authorization", "token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie))).andReturn();
        assertEquals(200, result.getResponse().getStatus());
        assertEquals(response.getBody(), result.getResponse().getContentAsString());
    }
    @Test
    public void updateMovie_ExceptionTest() throws Exception {
        ResponseEntity<String> response = ResponseEntity.ok("Movie not found to update");
        Mockito.when(movieService.updateMovie(movie)).thenThrow(new MovieNotFoundException("Movie not found to update"));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/moviebooking/updateMovie")
                .header("Authorization", "token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie))).andReturn();
        assertEquals(406, result.getResponse().getStatus());
        assertEquals(response.getBody(), result.getResponse().getContentAsString());
    }

    @Test
    public void updateTicketStatus_successTest() throws Exception {
        ResponseEntity<String> response = ResponseEntity.ok("Updated Ticket Status");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/moviebooking/update")
                .header("Authorization", "token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieId))).andReturn();
        assertEquals(200, result.getResponse().getStatus());
        assertEquals(response.getBody(), result.getResponse().getContentAsString());
    }
    @Test
    public void updateTicketStatus_ExceptionTest() throws Exception {
        ResponseEntity<String> response = ResponseEntity.ok("Movie not found");
        Mockito.when(movieService.updateTicketStatus(movieId)).thenThrow(new MovieNotFoundException("Movie not found"));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/moviebooking/update")
                .header("Authorization", "token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieId))).andReturn();
        assertEquals(406, result.getResponse().getStatus());
        assertEquals(response.getBody(), result.getResponse().getContentAsString());
    }

    @Test
    public void getAllMovies_Found() throws Exception {
        Mockito.when(movieService.getAllMovies()).thenReturn(List.of(movie));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/moviebooking/all")
                        .header("Authorization", "Bearer " + "token"))
                .andExpect(status().isFound())
                .andDo(print());
    }
    @Test
    public void getAllMovies_NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/moviebooking/all")
                        .header("Authorization", "Bearer " + "token"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Movies not FOUND..."))
                .andDo(print());
    }
    @Test
    public void SearchByMovieName_Found() throws Exception {
        Mockito.when(movieService.searchMoviesByMovieName(movieId.getMovieName())).thenReturn(List.of(movie));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/moviebooking/search/movie/movieName")
                        .header("Authorization", "Bearer " + "token"))
                .andExpect(status().isFound())
                .andDo(print());
    }
    @Test
    public void SearchByMovieName_NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/moviebooking/search/movie/movieName")
                        .header("Authorization", "Bearer " + "token"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Movies not FOUND...please check movie name"))
                .andDo(print());
    }
    @Test
    public void SearchByTheatreName_Found() throws Exception {
        Mockito.when(movieService.searchMoviesByTheatreName(movieId.getTheatreName())).thenReturn(List.of(movie));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/moviebooking/search/theatre/theatreName")
                        .header("Authorization", "Bearer " + "token"))
                .andExpect(status().isFound())
                .andDo(print());
    }
    @Test
    public void SearchByTheatreName_NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/moviebooking/search/theatre/theatreName")
                        .header("Authorization", "Bearer " + "token"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Movies not FOUND...please check theatre name"))
                .andDo(print());
    }
    @Test
    public void SearchByMovieTheatreName_Found() throws Exception {
        Mockito.when(movieService.searchByMovieId(movieId.getMovieName(), movieId.getTheatreName())).thenReturn(movie);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/moviebooking/search/movieName/theatreName")
                        .header("Authorization", "Bearer " + "token"))
                .andExpect(status().isFound())
                .andDo(print());
    }
    @Test
    public void SearchByMovieTheatreName_NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/moviebooking/search/movieName/theatreName")
                        .header("Authorization", "Bearer " + "token"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Movies not FOUND...please check movie or theatre name"))
                .andDo(print());
    }
    @Test
    public void deleteTest_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/moviebooking/delete/movieName/TheatreName")
                .header("Authorization","Bearer "+"token"))
                .andExpect(status().isOk())
                .andExpect(content().string("Movie Deleted!"))
                .andDo(print());
    }
//    @Test
//    public void deleteTest_Exception() throws Exception {
//        doThrow(new MovieNotFoundException("Movie does not exist to delete")).when(movieService).deleteMovieById(movieId);
////        when(movieService.deleteMovieById(movieId)).thenThrow(new MovieNotFoundException("Movie does not exist to delete"));
//        mockMvc.perform(MockMvcRequestBuilders
//                        .delete("/moviebooking/delete/movieName/TheatreName")
//                        .header("Authorization","Bearer "+"token"))
//                .andExpect(status().isNotAcceptable())
//                .andExpect(content().string("Movie does not exist to delete"))
//                .andDo(print());
//    }

}
