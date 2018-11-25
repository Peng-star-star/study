/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.spark;

// $example on$
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.ml.classification.MultilayerPerceptronClassificationModel;
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier;
import org.apache.spark.ml.classification.RandomForestClassificationModel;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.ml.feature.LabeledPoint;
import org.apache.spark.ml.linalg.VectorUDT;
import org.apache.spark.sql.types.*;

import com.example.spark.DataOperation.Person;

import scala.Function1;

// $example off$
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.linalg.Vectors;

/**
 * An example for Multilayer Perceptron Classification.
 */
public class JavaMultilayerPerceptronClassifierExample {

	
	public static void main(String[] args) {
		//HADOOP_HOME在环境变量加入
		System.setProperty("hadoop.home.dir", "E:\\Program\\hadoop-common-2.2.0-bin-master");
		SparkSession spark = SparkSession.builder().master("local").appName("JavaMultilayerPerceptronClassifierExample").getOrCreate();
		// JavaMultilayerPerceptronClassifierExample.dataTest();
		//JavaMultilayerPerceptronClassifierExample.MultilayerTest(spark);
		JavaMultilayerPerceptronClassifierExample.testModel2(spark);
	}

	public static void MultilayerTest(SparkSession spark) {
		// $example on$
		// Load training data
		String path = "C:\\Users\\peng\\Desktop\\桂山甲线060-2.txt";
		Dataset<Row> dataFrame = spark.read().format("libsvm").load(path);

		// Split the data into train and test
		Dataset<Row>[] splits = dataFrame.randomSplit(new double[] { 0.9, 0.1 }, 1234L);
		Dataset<Row> train = splits[0];
		Dataset<Row> test = splits[1];

		// specify layers for the neural network:
		// input layer of size 4 (features), two intermediate of size 5 and 4
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

		System.err.println("Test set accuracy = " + evaluator.evaluate(predictionAndLabels));
		// $example off$

		System.out.println("测试样本数量:" + result.count());
		result.show(100, false);

		try {
			model.save("C:\\Users\\peng\\Desktop\\iceModel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		spark.stop();
	}

	public static void testModel(SparkSession spark) {
		// $example on$
		// Load training data
		String path = "C:\\Users\\peng\\Desktop\\桂山甲线060-2.txt";
		Dataset<Row> dataFrame = spark.read().format("libsvm").load(path);

		// Split the data into train and test
		Dataset<Row>[] splits = dataFrame.randomSplit(new double[] { 0.9, 0.1 }, 1234L);
		Dataset<Row> train = splits[0];
		Dataset<Row> test = splits[1];

		MultilayerPerceptronClassificationModel model = MultilayerPerceptronClassificationModel.load("C:\\Users\\peng\\Desktop\\iceModel");
		Dataset<Row> result = model.transform(test);

		Dataset<Row> predictionAndLabels = result.select("prediction", "label");
		MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy");

		System.err.println("Test set accuracy = " + evaluator.evaluate(predictionAndLabels));
		// $example off$

		System.out.println("测试样本数量:" + result.count());
		result.show(100, false);
		
		Dataset<Row> rows = result.select("features");
		Dataset<Vector> rows2 = rows.map(new MapFunction<Row, Vector>() {
			public Vector call(Row row) throws Exception {
				Vector vec = row.getAs("features");
				return vec;
			}

		}, Encoders.bean(Vector.class));
		rows2.show();
	}
	
	public static void testModel2(SparkSession spark){
		LabeledPoint lp = LabeledPoint.apply(0.0, Vectors.sparse(3,new int[] {0,1, 2}, new double[] {1.1,100, 4.3}));
		List<LabeledPoint> lps = new ArrayList<>();
		lps.add(lp);
		JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());
		JavaRDD<LabeledPoint> jRDD = sc.parallelize(lps);
		//Dataset<Row> ds2 = spark.createDataFrame(jRDD,LabeledPoint.class);
		Dataset<Row> ds2 =spark.createDataFrame(lps,LabeledPoint.class);
		ds2.show();
		
		/*List<Row> rows = new ArrayList<>();
		Vector sv = Vectors.sparse(3, new int[] {0,1, 2}, new double[] {1.1,100, 4.3});
		Row row = RowFactory.create(1,sv);
		rows.add(row);
		
		ArrayList<StructField> fields = new ArrayList<StructField>();
		StructField field = null;
		field = DataTypes.createStructField("label", DataTypes.IntegerType, true);
		fields.add(field);
		field = DataTypes.createStructField("features", null, false, null);
		fields.add(field);
		StructType schema = DataTypes.createStructType(fields);
		
		
		Dataset<Row> ds = spark.createDataFrame(rows,schema);
		ds.show();*/
		
		MultilayerPerceptronClassificationModel model = MultilayerPerceptronClassificationModel.load("C:\\Users\\peng\\Desktop\\iceModel");
		Dataset<Row> result = model.transform(ds2);
		result.show(100, false);
	}

	public static void dataTest() {
		SparkSession spark = SparkSession.builder().appName("JavaMultilayerPerceptronClassifierExample").getOrCreate();

		runBasicDataSourceExample(spark);

		spark.stop();
	}

	private static void runBasicDataSourceExample(SparkSession spark) {
		String path = "F:\\MyProgram\\source-code\\spark-master\\examples\\src\\main\\resources\\people.csv";
		String path2 = "C:\\Users\\peng\\Desktop\\桂山甲线060_2.csv";
		String path3 = "F:\\MyProgram\\springBoot\\data\\people.csv";
		String path4 = "F:\\MyProgram\\source-code\\spark-master\\examples\\src\\main\\resources\\people - 副本.csv";
		Dataset<Row> peopleDFCsv = spark.read().format("org.apache.spark.sql.execution.datasources.csv.CSVFileFormat")
				.option("sep", ",").option("inferSchema", "true").option("header", "true").load(path2);
		Dataset<Row> data1 = peopleDFCsv.select("ICING_THICKNESS", "REC_TEMPERATURE", "REC_HUMIDITY", "REC_WIND_SPEED");
		data1.show(20, false);
	}
	
	public static class Libsvm implements Serializable{
		public Double lable;
		public Vector features;
	}
}
