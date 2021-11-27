package transaction.programmic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManagerFactory;

//@Configuration
//@ComponentScan(basePackages = "transaction")
public class ServicesConfig {

	@Autowired
	EntityManagerFactory entityManagerFactory;

	@Bean
	public TransactionTemplate transactionTemplate() { //применение программных Т
		TransactionTemplate tt = new TransactionTemplate();
			tt.setPropagationBehavior(TransactionDefinition.PROPAGATION_NEVER); //управляет свойствами Т
			tt.setTimeout(30);
			tt.setTransactionManager(transactionManager());
		return tt;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
