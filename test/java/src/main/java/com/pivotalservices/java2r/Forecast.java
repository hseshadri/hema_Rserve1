package com.pivotalservices.java2r;

import java.util.Arrays;
import java.util.Random;

import org.rosuda.REngine.REngine;
import org.rosuda.REngine.Rserve.RConnection;

public class Forecast {

	RConnection connection = null;
	REngine engine = null;

	public Forecast() {
		openRserveConn();
		runForcast();
		closeRserveConn();
	}

	public void openRserveConn() {
		try {
			connection = new RConnection();
			engine = connection;

			connection.eval("library(forecast)");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public void closeRserveConn() {
		if (connection != null) {
			connection.close();
			engine.close();
		}
	}

	public void runForcast() {
		double[] x = generateSamples();

		System.out.println("Stock market forecast:");
		runRserve(x);
		System.out.println();
	}

	public void runRserve(double[] stockClosePrices) {
		try {
			connection.assign("x", stockClosePrices);
			final double[] predicted = connection
					.eval("f = forecast(auto.arima(x), h=10, level=90); res=as.numeric(f$mean);res")
					.asDoubles();

			System.out.println("Forecast results: " + Arrays.toString(predicted));

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	public double[] generateSamples() {
		Random random = new Random(12345);
		double[] stockClosePrices = new double[100];
		stockClosePrices[0] = 0;
		for (int i = 1; i < stockClosePrices.length; i++) {
			stockClosePrices[i] = 0.5 + 1 * stockClosePrices[i - 1]
					+ random.nextGaussian();
		}
		return stockClosePrices;
	}
}
