package com.jonasqf.myexpenses.payment;

import com.jonasqf.myexpenses.mocks.PaymentMockFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private PaymentService underTest;
    @Mock
    private PaymentRepository paymentRepository;
    private Payment payment = new PaymentMockFactory().createMockPayment();


    @BeforeEach
    void setUp() {
        underTest = new PaymentService(paymentRepository);
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

}