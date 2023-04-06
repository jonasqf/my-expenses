package com.jonasqf.myexpenses.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonasqf.myexpenses.controllers.TransactionController;
import com.jonasqf.myexpenses.entities.Transaction;
import com.jonasqf.myexpenses.services.TransactionService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TransactionService transactionService;
    @Autowired
    private ObjectMapper objectMapper;
    private Transaction transaction;

    final UUID id = UUID.fromString("f0d45730-b812-4b21-a7c1-22a574ebbdb4");
    @BeforeEach
    void setUp() {
        transaction = new Transaction("Test Description",
                "BILL", 1, new BigDecimal("200.0"),
                new BigDecimal("200.0"));
        transaction.setId(id);
    }

    @Test
    void itShouldSuccessfullyRegisterTransaction() throws Exception {
        given(transactionService.register(ArgumentMatchers.any()))
                .willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/v1/transactions/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaction)));

        response.andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description"
                        , CoreMatchers.is(transaction.getDescription())));

    }

    @Test
    void itShouldFindAll() throws Exception {
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(new Transaction("Test Description 1",
                "BILL", 1, new BigDecimal("200.0"),
                new BigDecimal("200.0")));
        transactionList.add(new Transaction("Test Description 2",
                "BILL", 1, new BigDecimal("200.0"),
                new BigDecimal("200.0")));
        transactionList.add(new Transaction("Test Description 3",
                "BILL", 1, new BigDecimal("200.0"),
                new BigDecimal("200.0")));

        when(transactionService.findAll()).thenReturn(transactionList);

        ResultActions response = mockMvc.perform(get("/api/v1/transactions/")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()"
                        , CoreMatchers.is(transactionList.size())));

    }

    @Test
    void itShouldFindById() throws Exception {
        UUID id = UUID.fromString("7ce4a601-3aa3-4d52-a437-3a931fe67c70");

        when(transactionService.findById(id)).thenReturn(Optional.ofNullable(transaction));

        ResultActions response = mockMvc.perform(get("/api/v1/transactions/7ce4a601-3aa3-4d52-a437-3a931fe67c70")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaction)));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id"
                        , CoreMatchers.is(transaction.getId().toString())));
    }

    @Test
    void itShouldDeleteAnOwnerSuccessfully() throws Exception {
        when(transactionService.findById(transaction.getId())).thenReturn(Optional.of(transaction));
        doNothing().when(transactionService).delete(transaction);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/transactions/" + transaction.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void itShouldUpdateAnOwnerSuccessfully() throws Exception {
        transaction.setDescription("Test");

        doNothing().when(transactionService).update(transaction);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/transactions/" + transaction.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaction)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description"
                        , CoreMatchers.is(transaction.getDescription())));
    }

}