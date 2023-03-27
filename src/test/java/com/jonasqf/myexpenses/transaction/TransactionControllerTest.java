package com.jonasqf.myexpenses.transaction;

import com.jonasqf.myexpenses.controllers.TransactionController;
import com.jonasqf.myexpenses.entities.Transaction;
import com.jonasqf.myexpenses.repositories.TransactionRepository;
import com.jonasqf.myexpenses.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @InjectMocks
    private TransactionController underTest;
    @Mock
    private TransactionService transactionService;
    @Mock
    private TransactionRepository transactionRepository;
    private Transaction transaction;
    @Autowired
    private MockMvc mvc;
    final UUID id = UUID.fromString("f0d45730-b812-4b21-a7c1-22a574ebbdb4");
    @BeforeEach
    void setUp() {
        underTest = new TransactionController(transactionService);
        transactionService = new TransactionService(transactionRepository);
        transaction = new Transaction("Test Description",
                "BILL", 1, new BigDecimal("200.0"),
                new BigDecimal("200.0"));
        transaction.setId(id);
    }
    @Disabled
    @Test
    void register() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    }

    @Disabled
    @Test
    void findAll() throws Exception {

    }

    @Disabled
    @Test
    void findById() {
        given(underTest.findById(id)).willAnswer((Answer<?>) transaction);
        final ResponseEntity<Transaction> expected = underTest.findById(id);
        assertThat(expected.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Disabled
    @Test
    void notFoundWhenFindById() {
        underTest.register(transaction);
        ResponseEntity<Transaction> savedTransaction = underTest.findById(transaction.getId());
        assertThat(savedTransaction).isNotNull();
        assertThat(savedTransaction.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
    }

    @Disabled
    @Test
    void delete() {
    }

    @Disabled
    @Test
    void update() {
    }
}