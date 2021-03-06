package co.alexdev.bitsbake.model;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;

 /*AppExecutors class used to do background work*/

public class AppExecutors {

    private static final Object LOCK = new Object();

    private final Executor diskIO;

    private static AppExecutors sExecutor;

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
    }

    public static AppExecutors getInstance() {
        if (sExecutor == null) {
            synchronized (LOCK) {
                sExecutor = new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return sExecutor;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }

    public Executor getDiskIO() {
        return diskIO;
    }
}
