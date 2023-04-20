package com.jonasqf.myexpenses.commitment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonasqf.myexpenses.controllers.CommitmentController;
import com.jonasqf.myexpenses.entities.Account;
import com.jonasqf.myexpenses.entities.Commitment;
import com.jonasqf.myexpenses.services.CommitmentService;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CommitmentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CommitmentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommitmentService commitmentService;
    @Autowired
    private ObjectMapper objectMapper;
    private Commitment commitment;
    private Account account;
    private final String urlTemplate = "/api/v1/commitments";
    @BeforeEach
    public void init(){
        UUID accountId = UUID.fromString("7ce4a601-3aa3-4d52-a437-3a931fe67c70");
        account = new Account(BigDecimal.ZERO, UUID.randomUUID());
        account.setId(accountId);

        commitment = new Commitment("CREATED",
                "INCOME",
                "Monthly biweekly salary",
                BigDecimal.valueOf(4300.00),
                BigDecimal.valueOf(0.00),
                12,
                accountId,
                LocalDate.of(2023, 4, 15).atStartOfDay()
        );
        commitment.setId(UUID.randomUUID());
    }

    @Test
    void registerCommitmentSuccessfully() throws Exception {
        given(commitmentService.register(ArgumentMatchers.any()))
                .willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commitment)));
        response.andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",
                        CoreMatchers.is(commitment.getId().toString())));
    }

    @Test
    void getAllCommitments() throws Exception {
        List<Commitment> commitmentList = new ArrayList();
        commitmentList.add(new Commitment("CREATED",
                "INCOME",
                "Monthly biweekly salary",
                BigDecimal.valueOf(500.00),
                BigDecimal.valueOf(0.00),
                12,
                UUID.randomUUID(),
                LocalDate.of(2023, 4, 15).atStartOfDay()
        ));
        commitmentList.add(new Commitment("CREATED",
                "BILL",
                "Monthly biweekly salary",
                BigDecimal.valueOf(1000.00),
                BigDecimal.valueOf(0.00),
                12,
                UUID.randomUUID(),
                LocalDate.of(2023, 5, 15).atStartOfDay()
        ));

        when(commitmentService.findAll()).thenReturn(commitmentList);

        ResultActions response = mockMvc.perform(get(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        CoreMatchers.is(commitmentList.size())));
    }

    @Test
    void getCommitment_ById() throws Exception {
        UUID id = UUID.fromString("7ce4a601-3aa3-4d52-a437-3a931fe67c70");
        when(commitmentService.findById(id)).thenReturn(Optional.ofNullable(commitment));

        ResultActions response = mockMvc.perform(get(urlTemplate+"/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commitment)));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",
                        CoreMatchers.is(commitment.getId().toString())));
    }

    @Test
    void getById_throwsRuntimeException_whenInvalidIdProvided() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        // When
        when(commitmentService.findById(id)).thenReturn(Optional.empty());
        // Then
        mockMvc.perform(get(urlTemplate+"/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCommitment() throws Exception {
        // Stubbing
        when(commitmentService.findById(commitment.getId())).thenReturn(Optional.of(commitment));
        doNothing().when(commitmentService).delete(commitment);

        // when/then
        mockMvc.perform(MockMvcRequestBuilders.delete(urlTemplate+"/{id}", commitment.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void delete_throwNotFoundException_whenInvalidIdProvided() throws Exception {
        doNothing().when(commitmentService).delete(commitment);

        mockMvc.perform(MockMvcRequestBuilders.delete(urlTemplate+"/{id}", commitment.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateCommitment() throws Exception {
        commitment.setStatus("CANCELLED");

        doNothing().when(commitmentService).update(commitment);

        ResultActions response = mockMvc.perform(put(urlTemplate+"/{id}", commitment.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commitment)));
        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status"
                        , CoreMatchers.is(commitment.getStatus())));
    }
}