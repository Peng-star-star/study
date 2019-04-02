package com.example.spark;

import java.io.Serializable;

/**
 * 覆冰预测参数
 * 
 * @author peng
 *
 */
public class IceClimate implements Serializable {

	private static final long serialVersionUID = 1815450599565646674L;
	private Double temperature;
	private Double humidity;
	private Double windSpeed;
	/** 预测 */
	private Double prediction;

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getHumidity() {
		return humidity;
	}

	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

	public Double getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(Double windSpeed) {
		this.windSpeed = windSpeed;
	}

	public Double getPrediction() {
		return prediction;
	}

	public void setPrediction(Double prediction) {
		this.prediction = prediction;
	}

}
