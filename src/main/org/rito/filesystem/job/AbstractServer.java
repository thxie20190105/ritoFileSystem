package org.rito.filesystem.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author 山五洲
 */
public abstract class AbstractServer implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }
}
