package com.jonasqf.myexpenses.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonasqf.myexpenses.owner.Owner;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountService accountService;
    @Autowired
    private ObjectMapper objectMapper;
    private Account account;
    private Owner owner;

    @BeforeEach
    public void init(){
        owner = new Owner("Jonas", "Flach");
        owner.setId(UUID.randomUUID());
        account = new Account(BigDecimal.ZERO, owner.getId());
        account.setId(UUID.fromString("7ce4a601-3aa3-4d52-a437-3a931fe67c70"));
    }

    @Test
    void register_an_account_successfully() throws Exception {
        given(accountService.register(ArgumentMatchers.any()))
                .willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(account)));
        response.andExpect(status().isCreated())
                //.andExpect(MockMvcResultMatchers.jsonPath("$.balance", CoreMatchers.is(account.getBalance().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ownerId", CoreMatchers.is(account.getOwnerId().toString())));
    }

    @Test
    void get_all_accounts() throws Exception {
        List<Account> accountsList = new ArrayList();
        accountsList.add(new Account(BigDecimal.ZERO, owner.getId()));
        accountsList.add(new Account(BigDecimal.ZERO, owner.getId()));

        when(accountService.findAll()).thenReturn(accountsList);

        ResultActions response = mockMvc.perform(get("/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        CoreMatchers.is(accountsList.size())));
    }

    @Test
    void get_account_by_id() throws Exception {
        UUID id = UUID.fromString("7ce4a601-3aa3-4d52-a437-3a931fe67c70");
        when(accountService.findById(id)).thenReturn(Optional.ofNullable(account));

        ResultActions response = mockMvc.perform(get("/api/v1/accounts/7ce4a601-3aa3-4d52-a437-3a931fe67c70")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(account)));

        response.andExpect(status().isOk())
                //.andExpect(MockMvcResultMatchers.jsonPath("$.balance", CoreMatchers.is(account.getBalance().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(account.getId().toString())));
    }
    @Test
    void findById_throwsRuntimeException_whenInvalidIdProvided() throws Exception {
        // Given
        UUID id = UUID.randomUUID();
        // When
        when(accountService.findById(id)).thenReturn(Optional.empty());
        // Then
        mockMvc.perform(get("/api/v1/accounts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_an_account_successfully() throws Exception {
        // Stubbing
        when(accountService.findById(account.getId())).thenReturn(Optional.of(account));
        doNothing().when(accountService).delete(account);

        // when/then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/accounts/" + account.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void delete_throw_exception_when_not_found_account() throws Exception {
        doNothing().when(accountService).delete(account);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/accounts/" + account.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    void update_an_account_successfully() throws Exception {
        BigDecimal newBalance = BigDecimal.TEN;
        account.setBalance(newBalance);

        doNothing().when(accountService).update(account);

        ResultActions response = mockMvc.perform(put("/api/v1/accounts/"+ account.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(account)));
        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id"
                        , CoreMatchers.is(account.getId().toString())));
    }
}
