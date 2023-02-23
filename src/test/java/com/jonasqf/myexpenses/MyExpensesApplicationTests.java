package com.jonasqf.myexpenses;

import com.jonasqf.myexpenses.account.AccountModel;
import com.jonasqf.myexpenses.account.AccountRepository;
import com.jonasqf.myexpenses.account.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class MyExpensesApplicationTests {

	@Autowired
	AccountService accountService;

	@Test
	void balanceShouldBeZero() {
		//given
		AccountModel underTest = new AccountModel("test description",
				"bill",
				2,
				new BigDecimal(String.valueOf(200.0)),
				new BigDecimal(String.valueOf(200.0)));
		//when
		accountService.register(underTest);
		//then
		BigDecimal expected = new BigDecimal(String.valueOf(0.0));
		assertThat(expected).isEqualTo(underTest.getBalance());

	}

}
