package com.example.android.background.sync;


import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by Michael Klunko on 09/08/2018.
 */

public class WaterReminderFirebaseJobService extends JobService {
    private AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = WaterReminderFirebaseJobService.this;
                ReminderTasks.executeTask(context, ReminderTasks.ACTION_CHARGING_REMINDER);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(jobParameters,false);
            }
        };
        mBackgroundTask.execute();
        return true; //job is still doing work
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if(mBackgroundTask!=null)mBackgroundTask.cancel(true);
        return true;
    }
}
