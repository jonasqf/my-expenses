package com.jonasqf.myexpenses.bill;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BillIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BillRepository billRepository;
    @Test
    void creationWorksThroughAllLayers() throws Exception {
        UUID billId = UUID.randomUUID();
        Bill sut = new Bill("Water bill", new BigDecimal("100.00"), LocalDate.now());
        sut.setId(billId);

        mockMvc.perform(post("/api/v1/bills")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(sut)))
                .andExpect(status().isCreated());

        Bill assertBill = billRepository.findAll().get(0);

        assertThat(assertBill.getAmount()).isEqualTo(new BigDecimal("100.00"));
    }
}