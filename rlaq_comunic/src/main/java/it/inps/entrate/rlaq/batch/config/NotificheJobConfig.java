package it.inps.entrate.rlaq.batch.config;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.dao.PessimisticLockingFailureException;

import it.inps.entrate.rlaq.batch.decider.StepExecutionDecider;
import it.inps.entrate.rlaq.batch.entity.Notifica;
import it.inps.entrate.rlaq.batch.entity.Provvedimento;
import it.inps.entrate.rlaq.batch.item.StepDecision;
import it.inps.entrate.rlaq.batch.listener.ExceptionListener;
import it.inps.entrate.rlaq.batch.listener.LogListener;
import it.inps.entrate.rlaq.batch.processor.InterrogazioneProcessor;
import it.inps.entrate.rlaq.batch.processor.InvioProcessor;
import it.inps.entrate.rlaq.batch.processor.PreparazioneNotificaProvvedimentoProcessor;

@Configuration
@EnableBatchProcessing
@Import(BatchConfig.class)
public class NotificheJobConfig {

	@Autowired
	private PlatformTransactionManager transactionManager;
	
	
	@Autowired
	private ExceptionListener<?,?> exceptionListener;
	@Autowired
	private LogListener logListener;
	
	@Value("${threadpool}")
	private int threadPool;
	@Autowired
	private DataSource dataSource;
	
	@Autowired 
	private JobRepository jobRepository;
	
	@Value("${pageSize}") 
	private Integer pageSize;
	
	@Value("${db.schema}")
	private String dbSchema;

	@Bean
	public Job notificheJob() {
		JobExecutionDecider stepPreparazioneNotProvvDecider = decider("step.preparazione.provvedimenti.enabled");
		JobExecutionDecider stepInvioDecider = decider("step.invio.enabled");
		JobExecutionDecider stepInterrogazioneDecider = decider("step.interrogazione.enabled");
		Flow notificheFlow = new FlowBuilder<Flow>("notificheFlowBuilder").start(stepPreparazioneNotProvvDecider)
				.on(StepDecision.ENABLED.name()).to(preparazioneNotProvvStep()).next(stepInvioDecider)
				.on(StepDecision.ENABLED.name()).to(invioStep()).next(stepInterrogazioneDecider)
				.on(StepDecision.ENABLED.name()).to(interrogazioneStep())
				.from(stepPreparazioneNotProvvDecider)
				.on(StepDecision.DISABLED.name()).to(stepInvioDecider)
				.on(StepDecision.DISABLED.name()).to(stepInterrogazioneDecider)
				.on(StepDecision.DISABLED.name()).end().build();
		
		

		return new JobBuilder("notificheJob",jobRepository).listener(logListener).preventRestart().start(notificheFlow)
				.end().build();
	}

	private <I, O> SimpleStepBuilder<I, O>abstractStepBuilder(String stepName) {
		return new StepBuilder(stepName,jobRepository)
				.listener(exceptionListener)
				.listener(logListener)
				.<I, O>chunk(1,transactionManager)
				.faultTolerant().skipPolicy(new AlwaysSkipItemSkipPolicy()).skipLimit(2)
				.retry(PessimisticLockingFailureException.class).retryLimit(5)
				.taskExecutor(taskExecutor()).transactionManager(transactionManager);
	}
	
	private <T>JdbcPagingItemReaderBuilder<T> abstractReaderBuilder(){
		return new JdbcPagingItemReaderBuilder<T>().dataSource(dataSource).pageSize(pageSize).saveState(false);
	} 
	
	private <T> JdbcBatchItemWriterBuilder<T> abstractWriterBuilder() {
		return new JdbcBatchItemWriterBuilder<T>().dataSource(dataSource).itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		
	}
	
	
//	@Bean
//	public Step preparazioneStep() {
//			return abstractStepBuilder()
//					.reader(preparazioneReader())
//					.processor(preparazioneProcessor())
//					.writer(preparazioneWriter())
//					.build();
//		}
	
	@Bean
	public Step preparazioneNotProvvStep() {
			return this.<Provvedimento,Notifica>abstractStepBuilder("preparazioneNotProvvStep")
					.reader(preparazioneNotProvvReader())
					.processor(preparazioneNotProvvProcessor())
					.writer(preparazioneWriter())
					.build();
		}
	
	

	@Bean
	public Step invioStep() {
		return this.<Notifica,Notifica>abstractStepBuilder("invioStep")
				.reader(invioReader())
				.processor(invioProcessor())
				.writer(invioWriter())
				.build();
	}
	

	@Bean
	public Step interrogazioneStep() {
		return this.<Notifica,Notifica>abstractStepBuilder("interrogazioneStep")
				.reader(interrogazioneReader())
				.processor(interrogazioneProcessor())
				.writer(interrogazioneWriter())
				.build();
	}
	
	@Bean
	public ItemReader<? extends String> preparazioneReader() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Bean
	public ItemProcessor<? super String, ? extends String> preparazioneProcessor() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Bean
	public ItemReader<Provvedimento> preparazioneNotProvvReader() {
		
		Map<String, Object> paramValues = new HashMap<>();
		paramValues.put("minData", new Date());
		return this.<Provvedimento>abstractReaderBuilder().name("preparazioneNotProvvReader")
				.beanRowMapper(Provvedimento.class)
				.selectClause("SELECT id_provvedimento idProvvedimento,d.id_domanda idDomanda,a.codice_fiscale cfAzienda")
				.fromClause(dbSchema+".TBProvvedimento p JOIN "+dbSchema+".TbDomanda d on p.id_domanda = d.id_domanda JOIN "+dbSchema+".TbAzienda a on a.id_azienda = d.id_azienda")
				.whereClause("p.data_inserimento > :minData")
				.sortKeys(Collections.singletonMap("idProvvedimento",Order.ASCENDING))
				.parameterValues(paramValues)
				.build();
	}
	
	@Bean
	public ItemProcessor<Provvedimento, Notifica> preparazioneNotProvvProcessor() {
		return new PreparazioneNotificaProvvedimentoProcessor();
	}
	
	
	@Bean
	public ItemWriter<Notifica> preparazioneWriter() {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO"+dbSchema+".TBPecEnte(id_pec_ente,id_ente) VALUES(:idNotifica,3)";
		return this.<Notifica>abstractWriterBuilder().sql(sql).build();
	}


	@Bean
	public ItemReader<Notifica> invioReader() {
		Map<String, Object> paramValues = new HashMap<>();
		paramValues.put("minData", new Date());
		return this.<Notifica>abstractReaderBuilder().name("invioReader")
				.beanRowMapper(Notifica.class)
				.selectClause("SELECT id_provvedimento idProvvedimento,d.id_domanda idDomanda,a.codice_fiscale cfAzienda")
				.fromClause(dbSchema+".TBProvvedimento p JOIN "+dbSchema+".TbDomanda d on p.id_domanda = d.id_domanda JOIN "+dbSchema+".TbAzienda a on a.id_azienda = d.id_azienda")
				.whereClause("p.data_inserimento > :minData")
				.sortKeys(Collections.singletonMap("idProvvedimento",Order.ASCENDING))
				.parameterValues(paramValues)
				.build();
	}
	
	@Bean
	public ItemProcessor<Notifica, Notifica> invioProcessor() {
		// TODO Auto-generated method stub
		return new InvioProcessor();
	}
	
	
	@Bean
	public ItemWriter<Notifica> invioWriter() {
		// TODO Auto-generated method stub
		return preparazioneWriter();
	}
	
	@Bean
	public ItemReader<Notifica> interrogazioneReader() {
		// TODO Auto-generated method stub
		return invioReader();
	}

	@Bean
	public ItemProcessor<Notifica, Notifica> interrogazioneProcessor() {
		// TODO Auto-generated method stub
		return new InterrogazioneProcessor();
	}
	
	@Bean
	public ItemWriter<Notifica> interrogazioneWriter() {
		// TODO Auto-generated method stub
		return preparazioneWriter();
	}

	


	public JobExecutionDecider decider(String property) {
		StepExecutionDecider decider = new StepExecutionDecider();
		decider.setProperty(property);
		return decider;
	}
	
	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor(); 
		taskExecutor.setBeanName("taskExcecutor");
		taskExecutor.setCorePoolSize(threadPool);
		taskExecutor.setMaxPoolSize(threadPool);
		return taskExecutor;
	}

}
