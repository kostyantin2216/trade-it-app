package com.tradeitsignals.parse;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;
import com.tradeitsignals.SignalsApplication;
import com.tradeitsignals.json.JSONFetcher;
import com.tradeitsignals.logging.TILogger;
import com.tradeitsignals.utils.CommonUtils;
import com.tradeitsignals.helpers.TIConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParseHelper {

	// Cloud Functions
	public final static String FUNC_SEND_SIGNAL = "sendSignal";

	public final static String LOG_FILE_NAME = "PARSE_LOGS";
	public final static String LOG_TAG = " $$$$$$$ ParseHelper $$$$$$$";

	private boolean parseInitialized;
	private boolean initializationError;

	private ParseHelper() {
		parseInitialized = false;
		initializationError = false;
	}

	private final static ParseHelper instance = new ParseHelper();;

	public static ParseHelper getInstance() {
		return instance;
	}

	public boolean isParseInitialized() {
		return parseInitialized;
	}

	public void setParseInitialized(boolean parseInitialized) {
		this.parseInitialized = parseInitialized;
	}

	public boolean isInitializationError() {
		return initializationError;
	}

	public void setInitializationError(boolean initializationError) {
		this.initializationError = initializationError;
	}

	public void init() {
		try {
			JSONObject json = new JSONFetcher(SignalsApplication.getAppContext()).getParseKeys();
			JSONArray jsonKeys = json.getJSONArray("keys");
			String[] result = new String[jsonKeys.length()];
			for (int i = 0; i < jsonKeys.length(); i++) {
				result[i] = jsonKeys.getString(i);
			}
			connectToParse(result);
		} catch (JSONException | IOException e) {
			TILogger.getLog().e("Failed to get keys from assets");
		}
	}

	public void connectToParse(String[] keys) {
		keys = reallyGetKeys(keys);
		Parse.initialize(SignalsApplication.getAppContext(), keys[0], keys[1]);//"VdFpuXogDzNe3YvbzRuxWVBFMhy24GmkQOtOwR7U", "1BnyMMdUfW3rNClxtEQVg0JWDqhpQ0nPrcal3RZc");
		parseInitialized = true;
		ParseInstallation installation = ParseInstallation.getCurrentInstallation();
		final List<String> userChannels = getUserSubscribedChannels();
		installation.addAllUnique("channels", userChannels);
		installation.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					if (TILogger.isDebug()) {
						TILogger.getLog().i(LOG_TAG, "successfully saved channels " + userChannels + " to parse installation", true, LOG_FILE_NAME);
					}
				} else {
					TILogger.getLog().e(LOG_TAG, "failed to save channels " + userChannels + " to parse installation", e, true, LOG_FILE_NAME);
				}
			}
		});
	}

	public List<String> getUserSubscribedChannels() {
		List<String> channels = new ArrayList<>();
		channels.add("signals");
		if(TIConfiguration.isDevelopment()) {
			channels.add("test");
		}
		return channels;
	}

	public String[] reallyGetKeys(String[] keys) {
		String[] realKeys = new String[2];
		realKeys[0] = CommonUtils.decodeBase64(keys[0]);
		realKeys[1] = CommonUtils.decodeBase64(keys[1]);
		return realKeys;
	}

}
