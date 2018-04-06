package com.example.assignment.helpers;

import android.os.Build;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

/**
 * The pool manager. The number of thread are based on the number of device's cores
 */
public class PoolManager {

    private static int MAX_THREADS = 3;
    final static String TAG = PoolManager.class.getSimpleName();
    final static PoolManager mInstance = new PoolManager();
    private static int numberOfCores = getNumberOfCores();
    private static ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(numberOfCores);

    public ExecutorService getThreadPoolExecutor() {
        if(null==threadPoolExecutor){
            threadPoolExecutor = Executors.newFixedThreadPool(numberOfCores);
        }
        return threadPoolExecutor;
    }

    public static void setThreadPoolExecutor(ExecutorService threadPoolExecutor) {
        PoolManager.threadPoolExecutor = threadPoolExecutor;
    }

    private PoolManager() {
    }

    public static PoolManager getInstance() {
      return mInstance;
    }


    private static int getNumberOfCores() {
        try {
            if (Build.VERSION.SDK_INT >= 17) {
                return Runtime.getRuntime().availableProcessors() + 1;
            } else {
                return getNumCoresOldPhones() + 1;
            }
        } catch (Exception e) {
            return MAX_THREADS;
        }
    }

    /**
     * Gets the number of cores available in this device, across all processors.
     * Requires: Ability to peruse the filesystem at "/sys/devices/system/cpu"
     *
     * @return The number of cores, or 1 if failed to get result
     */
    private static int getNumCoresOldPhones() {
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]+", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }
        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
//            cp.e(TAG, e);
            //Default to return 1 core
            return MAX_THREADS;
        }
    }
}
