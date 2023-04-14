package com.jonasqf.myexpenses;


import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertTrue;


@SpringBootTest
class MyExpensesApplicationTests {

    @Test
    public void testMain() {
        MyExpensesApplication.main(new String[] {});
        assertTrue(true);
    }

}
