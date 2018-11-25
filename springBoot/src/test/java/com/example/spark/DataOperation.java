package com.example.spark;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder;
import org.apache.spark.sql.catalyst.encoders.RowEncoder;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.IntegerType;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;


public class DataOperation {

	public static void main(String[] args) {
		SparkSession spark = SparkSession.builder().master("local").appName("DataOperation").getOrCreate();
		DataOperation.createJavaRDD(spark);
		spark.stop();
	}

	public static void example(SparkSession spark) {
		JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());
		List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
		JavaRDD<Integer> rddData = sc.parallelize(data);
		JavaRDD<Row> rddRow = rddData.map(new Function<Integer, Row>() {
			public Row call(Integer d) {
				return RowFactory.create(d);
			}
		});
		ArrayList<StructField> fields = new ArrayList<StructField>();
		StructField field = null;
		field = DataTypes.createStructField("num", DataTypes.IntegerType, true);
		fields.add(field);
		StructType schema = DataTypes.createStructType(fields);
		Dataset<Row> ds = spark.createDataFrame(rddRow, schema);
		ds.show();

		List<Person> persons = new ArrayList<Person>();
		Person person = new Person();
		person.setName("test");
		person.setAge(19);
		persons.add(person);
		JavaRDD<Person> rddPerson = sc.parallelize(persons);

		Dataset<Row> ds2 = spark.createDataFrame(rddPerson, Person.class);
		ds2.show();

	}
	
	public static Row createRow(SparkSession spark){
		Vector sv = Vectors.sparse(3, new int[] {0,1, 2}, new double[] {1.1,100, 4.3});
		Row row = RowFactory.create(1,sv);
		return row;
	}
	
	public static JavaRDD<Person> createJavaRDD(SparkSession spark){
		List<Person> persons = new ArrayList<Person>();
		Person person = new Person();
		person.setName("test");
		person.setAge(19);
		persons.add(person);
		JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());
		JavaRDD<Person> rddData = sc.parallelize(persons);
		return rddData;
	}
	
	public static Dataset<Row> createDataSet(SparkSession spark) {
		List<Person> persons = new ArrayList<Person>();
		Person person = new Person();
		person.setName("test");
		person.setAge(19);
		persons.add(person);
		Dataset<Row> ds = spark.createDataFrame(persons, Person.class);
		return ds;
	}

	// JavaRDD<Row> -> JavaRDD<Person>
	public static JavaRDD<Person> javaRDDToPerson(JavaRDD<Row> data) {
		JavaRDD<Person> rdds = data.map(new Function<Row, Person>() {
			public Person call(Row row) {
				String name = row.getAs("name");
				int age = row.getAs("age");
				return new Person(name, age);
			}
		});
		return rdds;
	}

	// JavaRDD<Person> -> JavaRDD<Row>
	public static JavaRDD<Row> javaRDDToRow(JavaRDD<Person> data) {
		JavaRDD<Row> rdds = data.map(new Function<Person, Row>() {
			public Row call(Person person) {
				String name = person.name;
				int age = person.age;
				return RowFactory.create(name, age);
			}
		});
		return rdds;
	}

	// Dataset<Row> -> Dataset<Person>
	public static Dataset<Person> datasetToPerson(Dataset<Row> data) {
		List<StructField> fieldList = new ArrayList<>();
		fieldList.add(DataTypes.createStructField("name", DataTypes.StringType, false));
		fieldList.add(DataTypes.createStructField("age", DataTypes.IntegerType, false));
		StructType rowSchema = DataTypes.createStructType(fieldList);
		ExpressionEncoder<Row> rowEncoder = RowEncoder.apply(rowSchema);
		Dataset<Person> dss = data.map(new MapFunction<Row, Person>() {
			public Person call(Row row) throws Exception {
				String name = row.getAs("name");
				int age = row.getAs("age");
				return new Person(name, age);
			}

		}, Encoders.bean(Person.class));
		return dss;
	}

	// Dataset<Person> -> Dataset<Row>
	public static Dataset<Row> datasetToRow(Dataset<Person> data) {
		List<StructField> fieldList = new ArrayList<>();
		fieldList.add(DataTypes.createStructField("name", DataTypes.StringType, false));
		fieldList.add(DataTypes.createStructField("age", DataTypes.IntegerType, false));
		StructType rowSchema = DataTypes.createStructType(fieldList);
		ExpressionEncoder<Row> rowEncoder = RowEncoder.apply(rowSchema);
		Dataset<Row> dss = data.map(new MapFunction<Person, Row>() {
			public Row call(Person person) throws Exception {
				String name = person.name;
				int age = person.age;
				return RowFactory.create(name, age);
			}

		}, rowEncoder);
		return dss;
	}

	// 方法1 JavaRDD<Person> -> Dataset<Row> 利用实体名
	public static void javaRDDToDataSet1(SparkSession spark) {
		JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());
		List<Person> persons = new ArrayList<Person>();
		Person person = new Person();
		person.setName("test");
		person.setAge(19);
		persons.add(person);
		JavaRDD<Person> rddPerson = sc.parallelize(persons);
		Dataset<Row> ds2 = spark.createDataFrame(rddPerson, Person.class);
		ds2.show();
	}

	// JavaRDD<Person> -> Dataset<Person> 利用实体名
	public static void javaRDDToDataSet2(SparkSession spark) {
		JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());
		List<Person> persons = new ArrayList<Person>();
		Person person = new Person();
		person.setName("test");
		person.setAge(19);
		persons.add(person);
		JavaRDD<Person> rddPerson = sc.parallelize(persons);
		Dataset<Person> ds2 = spark.createDataset(persons, Encoders.bean(Person.class));
		ds2.show();
	}

	// 方法3 JavaRDD<Row>到Dataset<Row> 自定义列名
	public static void javaRDDToDataSet3(SparkSession spark) {
		JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());
		List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
		JavaRDD<Integer> rddData = sc.parallelize(data);
		JavaRDD<Row> rddRow = rddData.map(new Function<Integer, Row>() {
			public Row call(Integer d) {
				// create一行
				return RowFactory.create(d);
			}
		});
		ArrayList<StructField> fields = new ArrayList<StructField>();
		StructField field = null;
		field = DataTypes.createStructField("num", DataTypes.IntegerType, true);
		fields.add(field);
		StructType schema = DataTypes.createStructType(fields);
		Dataset<Row> ds = spark.createDataFrame(rddRow, schema);
		ds.show();
	}

	// 方法1 Dataset<Person> -> JavaRDD<Person>
	public static void dataSetToJavaRDD1(Dataset<Person> data) {
		JavaRDD<Person> rdds = data.toJavaRDD();
	}

	// 方法2 Dataset<Row> -> JavaRDD<Person>
	public static void dataSetToJavaRDD1() {
		// Dataset<Person> -> JavaRDD<Person> -> JavaRDD<Person>
	}

	public static class Person implements Serializable {
		private static final long serialVersionUID = 1L;
		private String name;
		private int age;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public Person() {

		}

		public Person(String name, int age) {
			this.name = name;
			this.age = age;
		}
	}
}
