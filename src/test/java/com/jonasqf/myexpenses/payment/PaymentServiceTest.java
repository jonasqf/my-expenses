package com.jonasqf.myexpenses.payment;

import com.jonasqf.myexpenses.commitment.Commitment;
import com.jonasqf.myexpenses.commitment.CommitmentRepository;
import com.jonasqf.myexpenses.commitment.CommitmentStatus;
import com.jonasqf.myexpenses.commitment.CommitmentType;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @InjectMocks
    private PaymentService underTest;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private CommitmentRepository commitmentRepository;
    @Mock
    private Commitment commitment;
    private Payment payment = new PaymentMockFactory().createMockPayment();

    @BeforeEach
    void setUp() {
        underTest = new PaymentService(paymentRepository, commitmentRepository);
    }

    @Test
    void itShouldRegisterAnewPayment() {
        given(paymentRepository.save(payment)).willAnswer(invocation -> invocation.getArgument(0));
        //when
        Payment savedTransaction = underTest.register(payment);
        //then
        assertThat(savedTransaction).isNotNull();
        verify(paymentRepository).save(payment);
    }

    @Test
    void thePaymentBalanceShouldBeZero() {
        given(paymentRepository.save(payment)).willAnswer(invocation -> invocation.getArgument(0));
        //when
        Payment savedTransaction = underTest.register(payment);
        //then
        BigDecimal expected = new BigDecimal("0.0");

        assertThat(savedTransaction).isNotNull();
        assertThat(expected).isEqualTo(payment.getBalance());
    }

    @Test
    void itShouldListAllPayment() {
        //given
        List<Payment> paymentsList = new ArrayList();
        paymentsList.add(new PaymentMockFactory().createMockPayment());
        paymentsList.add(new PaymentMockFactory().createMockPayment());
        paymentsList.add(new PaymentMockFactory().createMockPayment());
        //when
        given(paymentRepository.findAll()).willReturn(paymentsList);
        //then
        Collection<Payment> expected = underTest.findAll();
        assertEquals(expected, paymentsList);
    }

    @Test
    void itShouldListPayment_ById() {
        //given
        final UUID id = UUID.fromString("f0d45730-b812-4b21-a7c1-22a574ebbdb4");
        given(underTest.findById(id)).willReturn(Optional.of(payment));
        //when
        final Optional<Payment> expected = underTest.findById(id);
        //then
        assertThat(expected).isNotNull();
    }

    @Test
    void itShouldDeleteAnPayment() {
        //when
        underTest.delete(payment);
        //then
        verify(paymentRepository).delete(payment);
    }

    @Test
    void itShouldUpdateAnPayment() {
        underTest.update(payment);
        //then
        verify(paymentRepository).save(payment);
    }

    @Test
    void itShould_UpdateAnPayment_ENDCommitment_whenStatusIs_DONE() {
        commitment = new Commitment(CommitmentStatus.CREATED,
                CommitmentType.INCOME,
                "Monthly biweekly salary",
                BigDecimal.valueOf(4300.00),
                BigDecimal.valueOf(0.00),
                1,
                UUID.randomUUID(),
                LocalDate.of(2023, 4, 15)
        );
        commitment.setId(UUID.randomUUID());
        //given
        when(commitmentRepository.findById((UUID) any())).thenReturn(Optional.ofNullable(commitment));
        commitmentRepository.findById(commitment.getId());
        when(paymentRepository.save(any())).thenReturn(payment);
        //when
        payment.setStatus(PaymentStatus.DONE);
        underTest.update(payment);
        //then
        MatcherAssert.assertThat("DONE", equalTo(payment.getStatus().toString()));
        MatcherAssert.assertThat("DONE", equalTo(commitment.getStatus().toString()));
    }
    @Test
    void itShould_NOT_UpdateAnPayment_ENDCommitment_whenStatusIs_DONE() {
        commitment = new Commitment(CommitmentStatus.CREATED,
                CommitmentType.INCOME,
                "Monthly biweekly salary",
                BigDecimal.valueOf(4300.00),
                BigDecimal.valueOf(0.00),
                2,
                UUID.randomUUID(),
                LocalDate.of(2023, 4, 15)
        );
        commitment.setId(UUID.randomUUID());
        //given
        when(commitmentRepository.findById((UUID) any())).thenReturn(Optional.ofNullable(commitment));
        commitmentRepository.findById(commitment.getId());
        when(paymentRepository.save(any())).thenReturn(payment);
        //when
        payment.setStatus(PaymentStatus.DONE);
        underTest.update(payment);
        //then
        MatcherAssert.assertThat("DONE", equalTo(payment.getStatus().toString()));
        assertNotEquals("DONE", commitment.getStatus().toString());
    }
}