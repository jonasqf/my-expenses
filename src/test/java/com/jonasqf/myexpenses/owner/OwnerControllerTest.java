package com.jonasqf.myexpenses.owner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonasqf.myexpenses.controllers.OwnerController;
import com.jonasqf.myexpenses.entities.Owner;
import com.jonasqf.myexpenses.services.OwnerService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OwnerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OwnerService ownerService;
    @Autowired
    private ObjectMapper objectMapper;
    private Owner owner;
    @BeforeEach
    void setUp() {
        owner = new Owner("Jonas", "Flach");
        owner.setId(UUID.randomUUID());
    }

    @Test
    void itShouldSuccessfullyRegisterAOwner() throws Exception {
        given(ownerService.register(ArgumentMatchers.any()))
                .willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/v1/owners/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(owner)));

        response.andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(owner.getFirstName())));

    }

    @Test
    void itShouldFindAll() throws Exception {
        List<Owner> ownerList = new ArrayList<>();
        ownerList.add(new Owner("Josi", "Smith"));
        ownerList.add(new Owner("Lucas", "Doe"));
        ownerList.add(new Owner("Jack", "Black"));

        when(ownerService.findAll()).thenReturn(ownerList);

        ResultActions response = mockMvc.perform(get("/api/v1/owners/")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()"
                        , CoreMatchers.is(ownerList.size())));

    }

    @Test
    void itShouldFindById() throws Exception {
        UUID id = UUID.fromString("7ce4a601-3aa3-4d52-a437-3a931fe67c70");

        when(ownerService.findById(id)).thenReturn(Optional.ofNullable(owner));

        ResultActions response = mockMvc.perform(get("/api/v1/owners/7ce4a601-3aa3-4d52-a437-3a931fe67c70")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(owner)));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id"
                        , CoreMatchers.is(owner.getId().toString())));
    }

    @Test
    void itShouldDeleteAnOwnerSuccessfully() throws Exception {
        when(ownerService.findById(owner.getId())).thenReturn(Optional.of(owner));
        doNothing().when(ownerService).delete(owner);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/owners/" + owner.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void itShouldUpdateAnOwnerSuccessfully() throws Exception {
        owner.setFirstName("Test");

        doNothing().when(ownerService).update(owner);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/owners/" + owner.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(owner)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName"
                        , CoreMatchers.is(owner.getFirstName())));
    }
}