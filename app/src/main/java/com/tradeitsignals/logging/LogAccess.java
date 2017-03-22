package com.tradeitsignals.logging;

import android.content.Context;
import android.os.AsyncTask;

import com.tradeitsignals.SignalsApplication;
import com.tradeitsignals.utils.CommonUtils;
import com.tradeitsignals.utils.DateUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Kostyantin on 8/22/2015.
 *
 *  Use {@link com.tradeitsignals.rest.api.ServerLogEntryAPI} instead.
 *
 *
 *  Buggy and needs Improvements
 *
 */
@Deprecated
public class LogAccess {

    public final static int TASK_TYPE_SAVE = 0;
    public final static int TASK_TYPE_LOAD = 1;

    public final static String SIGNALS_LOG_FILE_NAME = "SIGNALS_LOG.txt";
    public final static String ALL_SAVED_LOGS_FILE_NAME = "TI_ERROR_LOGS.txt";
    public final static String LOG_NAMES_SEPARATOR = "|";

    private OnLogAccessListener mListner;

    private int taskType;
    private static String defaultFileName;

    private String getDefaultFileName() {
        if(defaultFileName == null) {
            defaultFileName = "TI_ERROR_LOG_" + DateUtils.formatToString(DateUtils.FORMAT_LOG_FILE_NAME, new Date()) + ".txt";
        }

        return defaultFileName;
    }

    public void saveLog(String fileName, String output) {
        taskType = TASK_TYPE_SAVE;

        if (fileName == null) {
            fileName = getDefaultFileName();
        } else if(!fileName.contains(".txt")) {
            fileName += ".txt";
        }

        LogAccessTask accessTask = new LogAccessTask();
        accessTask.execute(fileName, output);
    }

    public void loadLog(String fileName) {
        taskType = TASK_TYPE_LOAD;

        if (fileName == null) {
            fileName = getDefaultFileName();
        }

        LogAccessTask accessTask = new LogAccessTask();
        accessTask.execute(fileName);
    }

    public void setLogListener(OnLogAccessListener listener) {
        this.mListner = listener;
    }

    private class LogAccessTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            String fileName = String.valueOf(params[0]);
            switch (taskType) {
                case TASK_TYPE_SAVE:
                    String output = String.valueOf(params[1]);
                    save(output, fileName);
                    break;
                case TASK_TYPE_LOAD:
                    load(fileName);
                    break;
            }
            return null;
        }
    }

    private void load(String fileName) {
        StringBuilder sBuilder = new StringBuilder();
        FileInputStream in = null;
        try {
            in = SignalsApplication.getAppContext().openFileInput(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sBuilder.append(line);
            }
        } catch (FileNotFoundException e) {
            TILogger.getLog().e("Could not load file : " + fileName + " in LogAccess.load()", e);
        } catch (IOException e) {
            TILogger.getLog().e("Could read from file : " + fileName + " in LogAccess.load()", e);
        } finally {
          if(in != null) {
              try {
                  in.close();
              } catch (IOException e) {
                  TILogger.getLog().e("Could not close FileIputStream in LogAccess.load()", e);
              }
          }
        }

        if(mListner != null) {
            mListner.onLogLoaded(sBuilder.toString());
        } else {
            TILogger.getLog().e("LogAccess must have OnLogAccessListner set in order to load logs.");
            throw new RuntimeException("LogAccess must have OnLogAccessListner set in order to load logs.");
        }
    }

    private void save(String output, String fileName) {
        if(output != null && fileName != null) {
            saveLogFileNameToFile(fileName);
            FileOutputStream fos = null;
            try {
                fos = SignalsApplication.getAppContext().openFileOutput(fileName, Context.MODE_APPEND);
                fos.write(output.getBytes());
            } catch (FileNotFoundException e) {
                TILogger.getLog().e("Could not find or create file : " + fileName + " in LogAccess.save()", e);
            } catch (IOException e) {
                TILogger.getLog().e("Could not write to file : " + fileName + " in LogAccess.save()", e);
            } finally {
                if(fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        TILogger.getLog().e("Failure close FileOutputStream in LogAccess.save()", e);
                    }
                }
            }
        }
    }

    private void saveLogFileNameToFile(String... logFileNames) {
        saveLogFileNamesToFile(true, logFileNames);
    }

    private void saveLogFileNamesToFile(boolean appendToFile, String... logFileNames) {
        List<String> savedLogNames = Arrays.asList(getSavedLogNamesAsArray());
        String logNamesToSave = "";
        for(String logName : logFileNames) {
            if(!savedLogNames.contains(logName)) {
                if(savedLogNames.size() > 0) {
                    logNamesToSave += LOG_NAMES_SEPARATOR;
                }
                logNamesToSave += logName;
            }
        }
        if(logNamesToSave.length() > 0 || !appendToFile) {
            FileOutputStream fos = null;
            try {
                File file = new File(SignalsApplication.getAppContext().getFilesDir(), ALL_SAVED_LOGS_FILE_NAME);
                fos = new FileOutputStream(file, appendToFile);
                fos.write(logNamesToSave.getBytes());
            } catch (FileNotFoundException e) {
                TILogger.getLog().e("Could not find file : " + ALL_SAVED_LOGS_FILE_NAME + " in LogAccess.saveLogFileName()", e);
            } catch (IOException e) {
                TILogger.getLog().e("Could not write to file : " + ALL_SAVED_LOGS_FILE_NAME + " in LogAccess.saveLogFileName()", e);
            } finally {
                if(fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        TILogger.getLog().e("Failed to close FileOutPutStream in LogAccess.saveLogFileName()", e);
                    }
                }
            }
        }
    }

    private void deleteLogFileNamesFromFile(String... fileNames) {
        List<String> fileNamesToDelete = Arrays.asList(fileNames);
        String[] savedLogNames = getSavedLogNamesAsArray();
        List<String> fileNamesToKeep = new ArrayList<>();
        for(String fileName : savedLogNames) {
            if(!fileNamesToDelete.contains(fileName)) {
                fileNamesToKeep.add(fileName);
            }
        }
        saveLogFileNamesToFile(false, fileNamesToKeep.toArray(new String[fileNamesToKeep.size()]));
    }

    public String[] getSavedLogNamesAsArray() {
        String savedLogNames = getSavedLogNames();
        return savedLogNames.toString().split(Pattern.quote(LOG_NAMES_SEPARATOR));
    }

    public String getSavedLogNames() {
        if(CommonUtils.doesFileExist(ALL_SAVED_LOGS_FILE_NAME)) {
            StringBuilder currentSavedLogs = new StringBuilder();

            FileInputStream in = null;
            try {
                in = SignalsApplication.getAppContext().openFileInput(ALL_SAVED_LOGS_FILE_NAME);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    currentSavedLogs.append(line);
                }
            } catch (FileNotFoundException e) {
                TILogger.getLog().e("Could not find file : " + ALL_SAVED_LOGS_FILE_NAME + " in LogAccess.getSavedLogNames()", e);
            } catch (IOException e) {
                TILogger.getLog().e("Could not read from file : " + ALL_SAVED_LOGS_FILE_NAME + " in LogAccess.getSavedLogNames()", e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        TILogger.getLog().e("Could not close FileInputStream in LogAccess.getSavedLogNames()", e);
                    }
                }
            }


            return currentSavedLogs.toString();
        } else {
            return "";
        }
    }

    public void clearAllLogs() {
        String[] savedLogs = getSavedLogNamesAsArray();

        int amountDeleted = 0;
        for(String log : savedLogs) {
            boolean deleteSuccess = SignalsApplication.getAppContext().deleteFile(log + ".txt");
            if(deleteSuccess) {
                amountDeleted++;
            }
        }

        if(TILogger.isDebug()) {
            TILogger.getLog().i("");
        }
    }

    /**
     * @param logNames Names of log files to delete.
     * @return Amount of log files successfully deleted.
     */
    public int clearLogs(String... logNames) {
        int amountDeleted = 0;
        for(String log : logNames) {
            boolean deleteSuccess = SignalsApplication.getAppContext().deleteFile(log + (log.contains(".txt") ? "" : ".txt"));
            if(deleteSuccess) {
                amountDeleted++;
            }
        }
        deleteLogFileNamesFromFile(logNames);
        return amountDeleted;
    }

    public interface OnLogAccessListener {
        void onLogLoaded(String log);
    }
}
