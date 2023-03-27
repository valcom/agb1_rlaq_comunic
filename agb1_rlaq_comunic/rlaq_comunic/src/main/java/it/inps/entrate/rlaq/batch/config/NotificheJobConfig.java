package it.inps.entrate.rlaq.batch.config;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.TaskExecutor;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import it.inps.entrate.rlaq.batch.decider.StepExecutionDecider;
import it.inps.entrate.rlaq.batch.item.StepDecision;

@Configuration
@EnableBatchProcessing
@Import(BatchConfig.class)
public class NotificheJobConfig {

	@Autowired
	private PlatformTransactionManager transactionManager;
	@Autowired
	private Object exceptionListener;
	@Autowired
	private Object logListener;
	
	@Value("${threadpool}")private int threadPool;

	@Bean
	public Job comunicazioniJob(JobRepository jobRepository,@Autowired Step preparazioneStep, @Autowired Step invioStep, @Autowired Step interrogazioneStep) {
		FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("notificheFlowBuilder");
		JobExecutionDecider stepPreparazioneDecider = decider("step.preparazione.enabled");
		JobExecutionDecider stepInvioDecider = decider("step.invio.enabled");
		JobExecutionDecider stepInterrogazioneDecider = decider("step.interrogazione.enabled");
		Flow notificheFlow = flowBuilder.start(stepPreparazioneDecider).on(StepDecision.ENABLED.name())
				.to(preparazioneStep()).from(stepPreparazioneDecider).on(StepDecision.DISABLED.name())
				.to(stepInvioDecider).from(preparazioneStep).next(stepInvioDecider).on(StepDecision.ENABLED.name())
				.to(invioStep).from(stepInvioDecider).on(StepDecision.DISABLED.name()).to(stepInterrogazioneDecider)
				.from(invioStep).next(stepInterrogazioneDecider).on(StepDecision.ENABLED.name())
				.to(interrogazioneStep).from(stepInterrogazioneDecider).on(StepDecision.DISABLED.name()).end()
				.from(interrogazioneStep).end();

		return new JobBuilder("comunicazioniJob").repository(jobRepository).listener(logListener).start(notificheFlow)
				.end().build();
	}

	private SimpleStepBuilder<String, String> commonStepBuilder() {
		return new StepBuilder("abstractStep").transactionManager(transactionManager).<String, String>chunk(1)
				.faultTolerant().skipPolicy(new AlwaysSkipItemSkipPolicy())
				.retry(DeadlockLoserDataAccessException.class).retryLimit(10)
				.taskExecutor(taskExecutor())
				.listener(exceptionListener)
				.listener(logListener);
	}
	
	@Bean
	public Step preparazioneStep() {
			return commonStepBuilder()
					.reader(preparazioneReader())
					.processor(preparazioneProcessor())
					.writer(preparazioneWriter())
					.build();
		}
	
	

	@Bean
	public Step invioStep() {
		return commonStepBuilder()
				.reader(invioReader())
				.processor(invioProcessor())
				.writer(invioWriter())
				.build();
	}
	

	@Bean
	public Step interrogazioneStep() {
		return commonStepBuilder()
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
	public ItemWriter<? super String> preparazioneWriter() {
		// TODO Auto-generated method stub
		return null;
	}


	@Bean
	public ItemReader<? extends String> invioReader() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Bean
	public ItemProcessor<? super String, ? extends String> invioProcessor() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Bean
	public ItemWriter<? super String> invioWriter() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Bean
	public ItemReader<? extends String> interrogazioneReader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Bean
	public ItemProcessor<? super String, ? extends String> interrogazioneProcessor() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Bean
	public ItemWriter<? super String> interrogazioneWriter() {
		// TODO Auto-generated method stub
		return null;
	}

	

	

	@Bean
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
