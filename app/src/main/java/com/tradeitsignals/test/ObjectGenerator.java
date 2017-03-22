package com.tradeitsignals.test;

import com.tradeitsignals.R;
import com.tradeitsignals.datamodel.data.Broker;
import com.tradeitsignals.datamodel.data.ContentPage;
import com.tradeitsignals.datamodel.data.Country;
import com.tradeitsignals.datamodel.data.MarketReview;
import com.tradeitsignals.datamodel.data.Signal;
import com.tradeitsignals.datamodel.data.User;
import com.tradeitsignals.datamodel.enums.ContentPageType;
import com.tradeitsignals.logging.LogAccess;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class ObjectGenerator {

	// Signal variables.
	public final static int SECOND = 1000;
	public final static int MINUTE = 60 * SECOND;
	public final static String[] CURRENCY_PAIRS = {"AUD/CAD", "CHF/JPY", "EUR/NZD", "GBP/JPY",
		"TRY/JPY", "USD/NOK", "AUD/CHF", "EUR/AUD", "EUR/SEK", "GBP/NZD", "USD/CAD", "USD/SEK",
		"AUD/JPY", "EUR/CAD", "EUR/TRY", "GBP/USD", "USD/CHF", "USD/TRY", "AUD/NZD", "EUR/CHF",
		"EUR/USD", "NZD/CAD", "USD/CNH", "USD/ZAR", "AUD/USD", "EUR/GBP", "GBP/AUD", "NZD/CHF",
		"USD/HKD", "ZAR/JPY", "CAD/CHF", "EUR/JPY", "GBP/CAD", "NZD/JPY", "USD/JPY", "CAD/JPY",
		"EUR/NOK", "GBP/CHF", "NZD/USD", "USD/MXN"};
	
	// ==================================================================================
	// ||							  Signal Methods		    					   ||
	// ==================================================================================
	
	public static List<Signal> generateSignals(int amount) {
		Random random = new Random(System.currentTimeMillis());
		List<Signal> signals = new ArrayList<Signal>();
		for(int i = 0; i < amount; i++) {
			signals.add(generateSignal(random));
		}
		
		return signals;
	}
	
	public static Signal generateSignal() {
		return createRandomSignal(new Random(System.currentTimeMillis()));
	}
	
	public static Signal generateSignal(Random random) {
		return createRandomSignal(random);
	}
	
	public static Signal createRandomSignal(Random random) {
		Signal signal = new Signal(
				// Expiry time.
				generateFutureDate(random),
				// Currency pair
				CURRENCY_PAIRS[random.nextInt(CURRENCY_PAIRS.length)], 
				// Rate.
				random.nextFloat() * (random.nextInt(3) + random.nextFloat()), 
				// isCall.
				random.nextInt(10) > 4
		);		
		
		return signal;
	}
	
	public static Date generateFutureDate(Random random) {
		return new Date(System.currentTimeMillis() + random.nextInt(MINUTE * 20));
	}
	
	// ==================================================================================
	// ||							  Broker Methods		    					   ||
	// ==================================================================================
	
	public static Broker generateBroker() {
		Broker broker = new Broker(
				// Name.
				"24Option",
				// Description.
				"By default the dealing rates window displays the most commonly traded pairs. To access the other pairs available to trade, select the 'SYMBOLS' button at the top of the platform. This window displays all of the available currency pairs for trading. Add a check mark next to the pairs you are interested in trading, and remove a check mark from those you are not. The dealing rates will automatically update with your selections.",
				// Logo Path.
				"R.drawable.option",
				// Min Deposit.
				50,
				// Min Withdrawal.
				100,
				// Promotion.
				"blowjobs",
				// Rating.
				3.5F,
				// Updated At.
				new Date(),
				// Url.
				"www.24option.com",
				// Is Recommended.
				false,
				// Is Active
				true
		);
		
		return broker;
	}
	
	public static List<Broker> generateBrokers(int amount) {
		List<Broker> brokers = new ArrayList<Broker>();
		for(int i = 0; i < amount; i++) {
			brokers.add(generateBroker());
		}
		return brokers;
	}

	// ==================================================================================
	// ||						 Market Review Methods		    					   ||
	// ==================================================================================


	public static List<MarketReview> generateMarketReviews(int amount) {
		List<MarketReview> reviews = new ArrayList<MarketReview>();
		for(int i = 0; i < amount; i++) {
			reviews.add(generateMarketReview());
		}
		return reviews;
	}

	public static MarketReview generateMarketReview() {
		MarketReview review = new MarketReview(
				// Title
				"The EURO Crisis",
				// Content
				"Are you blah blah blah read for the best broker on the bus going home about to work out and program and make chicken and sing songs while not singing songs because i am not a sng song writer im a bing bong choker with a big bong pipe for your bing bong mouth mhmmmm mhhhmmmmm of all the blah blah blah brokers in the world becuase this blah blah blah broker has\n\n everything to offer blah blah blah best customer support blah blah blah best signals and of blah blah blah course the best profits trees underwear purple\n\n yellow rainbows with rabbits and loopholes on top of carrots climbing giants smoking bongs eating tin foil while playing \n\nthe accordian and growing a watermelon blah blah blah any trader out there could ask trees blah blah blah for so dont blah blah blah think twice sign trees blah blah blah up NOW!\n\nonce upon a time kostya and thorne made an app and then another app and then another app and another and another until they became gajillionaires!!!!",
				// Created at
				new Date(),
				// Image path
				""
		);

		return review;
	}

	// ==================================================================================
	// ||						 Content Page Methods		    					   ||
	// ==================================================================================

	public static List<ContentPage> generateContentPages(int amount) {
		List<ContentPage> pages = new ArrayList<>();
		for(int i = 0; i < amount; i++) {
			pages.add(generateContentPage());
		}
		return pages;
	}

	public static ContentPage generateContentPage() {
		ContentPage page = new ContentPage();
		page.setId(1);
		page.setTitle("60 Seconds");
		page.setDescription("Tested strategies on how to make profits within seconds.");
	//	page.setAndroidIconResId(R.drawable.ic_sixty_sec);
		page.setType(ContentPageType.EDUCATION);
		page.setTemplateFileName("sixty_sec.html");

		return page;
	}

	// ==================================================================================
	// ||					        Country Methods		    						   ||
	// ==================================================================================

	public static Country generateCountry() {
		Country country = new Country();
		country.setName("Israel");
		country.setIso3("ISR");
		country.setIso2("IL");
		country.setDialCode("910");

		return country;
	}

	// ==================================================================================
	// ||							User Methods			    					   ||
	// ==================================================================================

	public static User generateUser() {
		User user = new User();
		user.setFirstName("Test");
		user.setLastName("McGee");
		user.setPassword("123456");
		user.setEmail("test.mcgee@tradeitsignals.com");
		user.setTelephone("0502888991");
		user.setCountryId(1);
		user.setChannels("");
		user.setIsRegistered(false);
		user.setIsAdmin(false);
		user.setIsOnTrial(true);
		user.setCreatedAt(new Date());
		user.setUpdatedAt(new Date());

		return user;
	}

	// ==================================================================================
	// ||						   TILogger Methods			    					   ||
	// ==================================================================================

	public static void createErrorLogFiles(int amount) {
		LogAccess lAccess = new LogAccess();

		for(int i = 0; i < amount; i++) {
			lAccess.saveLog("TEST_LOG_" + i, "Test Log created at: " + System.currentTimeMillis());
		}
	}
}
