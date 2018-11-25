package com.example.springBoot.spark;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.ml.classification.MultilayerPerceptronClassificationModel;


public class IcePredictTest {
	public static void main(String[] args) {
		IcePredict icePrediction = new IcePredict();
		String savePath = "/home/cci/target/model/iceModel";
		MultilayerPerceptronClassificationModel model = icePrediction.getModel(savePath);
		List<IceClimate> iceClimates = new ArrayList<>();
		IceClimate iceClimate = new IceClimate();
		iceClimate.setTemperature(-1.4);
		iceClimate.setHumidity(100.0);
		iceClimate.setWindSpeed(4.7);
		iceClimates.add(iceClimate);
		iceClimate = new IceClimate();
		iceClimate.setTemperature(-1.5);
		iceClimate.setHumidity(100.0);
		iceClimate.setWindSpeed(0.0);
		iceClimates.add(iceClimate);
		List<IceClimate> predictions = icePrediction.predict(model, iceClimates);
		for (IceClimate prediction : predictions) {
			System.out.println("temperature:" + prediction.getTemperature() + "humidity:" + prediction.getHumidity()
					+ "windSpeed:" + prediction.getWindSpeed() + "prediction:" + prediction.getPrediction());
		}
	}
}
