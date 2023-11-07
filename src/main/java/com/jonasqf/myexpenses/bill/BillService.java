package com.jonasqf.myexpenses.bill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;

    public Bill createBill(Bill bill) throws RuntimeException {
        try {
            isDueDateValid(bill);
        }
        catch (RuntimeException e) {
            throw new RuntimeException(e);

        }
        return billRepository.save(bill);
    }

    private Boolean isDueDateValid(Bill bill) {
        if (bill.getDueDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Due Date must be in the future");
        }
        return true;
    }
}
