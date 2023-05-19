package it.inps.entrate.rlaq.batch.reader;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;

public class NotificheReader<T> implements ItemReader<T> {
	
	
	public NotificheReader() {
		super();
	}

	public NotificheReader(ItemReader<T> delegate) {
		super();
		this.delegate = delegate;
	}

	private ItemReader<T> delegate;

	/**
	 * @param delegate the delegate to set
	 */
	public void setDelegate(ItemReader<T> delegate) {
		this.delegate = delegate;
	}

	/**
	 * Reads next record from input
	 */
	public T read() throws Exception {
		return delegate.read();
	}

	@AfterStep
	public ExitStatus afterStep(StepExecution stepExecution) {
		if (delegate instanceof ItemStream d) {
			d.close();
		}
		return null;
	}

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		if (delegate instanceof ItemStream d) {
			 d.open(stepExecution.getExecutionContext());
		}
	}
}
