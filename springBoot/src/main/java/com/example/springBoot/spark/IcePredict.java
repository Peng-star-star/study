package com.example.springBoot.spark;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.ml.classification.MultilayerPerceptronClassificationModel;
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.ml.feature.LabeledPoint;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;


/**
 * 覆冰预测： 使用多层感知进行覆冰预测MLP
 * 
 * @author peng
 *
 */
public class IcePredict {

	private SparkSession spark;

	public IcePredict() {
		// 建议在环境变量里配置
		//System.setProperty("hadoop.home.dir", "E:\\Program\\hadoop-common-2.2.0-bin-master");
		this.spark = SparkSession.builder().master("local").appName("JavaMultilayerPerceptronClassifierExample")
				.getOrCreate();
	}

	public IcePredict(SparkSession spark) {
		this.spark = spark;
	}

	/**
	 * 创建模型
	 * 
	 * @param spark
	 * @param sourcePath
	 * @param savePath
	 * @throws IOException
	 */
	public int createModel(String sourcePath, String savePath, double requireResult) throws IOException {

		Dataset<Row> dataFrame = spark.read().format("libsvm").load(sourcePath);

		// Split the data into train and test
		Dataset<Row>[] splits = dataFrame.randomSplit(new double[] { 0.9, 0.1 }, 1234L);
		Dataset<Row> train = splits[0];
		Dataset<Row> test = splits[1];

		// specify layers for the neural network:
		// input layer of size 3 (features), two intermediate of size 100 and 10
		// and output of size 3 (classes)
		int[] layers = new int[] { 3, 100, 10, 3 };

		// create the trainer and set its parameters
		MultilayerPerceptronClassifier trainer = new MultilayerPerceptronClassifier().setLayers(layers)
				.setBlockSize(128).setSeed(1234L).setMaxIter(100);

		// train the model
		MultilayerPerceptronClassificationModel model = trainer.fit(train);

		// compute accuracy on the test set
		Dataset<Row> result = model.transform(test);
		Dataset<Row> predictionAndLabels = result.select("prediction", "label");
		MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy");

		Double evaluateResult = evaluator.evaluate(predictionAndLabels);
		System.err.println("Test set accuracy = " + evaluateResult);
		// $example off$

		System.out.println("测试样本数量:" + result.count());
		result.show(100, false);

		if (evaluateResult > requireResult) {
			model.save(savePath);
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 获取模型
	 * 
	 * @param modelPath
	 * @return
	 */
	public MultilayerPerceptronClassificationModel getModel(String modelPath) {
		MultilayerPerceptronClassificationModel model = MultilayerPerceptronClassificationModel
				.load(modelPath);
		return model;
	}

	/**
	 * 预测
	 * 
	 * @param model
	 * @param iceClimates
	 */
	public List<IceClimate> predict(MultilayerPerceptronClassificationModel model, List<IceClimate> iceClimates) {
		for (IceClimate iceClimate : iceClimates) {
			iceClimate.setPrediction(predict(model, iceClimate));
		}
		return iceClimates;
	}

	/**
	 * 预测
	 * 
	 * @param model
	 * @param iceClimate
	 * @return
	 */
	public Double predict(MultilayerPerceptronClassificationModel model, IceClimate iceClimate) {
		List<LabeledPoint> lps = new ArrayList<>();
		LabeledPoint lp = LabeledPoint.apply(0.0, Vectors.sparse(3, new int[] { 0, 1, 2 },
				new double[] { iceClimate.getTemperature(), iceClimate.getHumidity(), iceClimate.getWindSpeed() }));
		lps.add(lp);
		Dataset<Row> ds = spark.createDataFrame(lps, LabeledPoint.class);
		Dataset<Row> result = model.transform(ds);
		List<Row> rows = result.collectAsList();
		Double prediction = null;
		for (int i = 0; i < rows.size(); i++) {
			if (i == 0) {
				prediction = rows.get(i).getAs("prediction");
			}
		}
		return prediction;
	}
}
