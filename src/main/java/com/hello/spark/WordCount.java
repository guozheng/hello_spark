package com.hello.spark;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

/**
 * Sample Spark word count from: http://www.robertomarchetto.com/spark_java_maven_example
 *
 */
public class WordCount 
{
    public static void main( String[] args )
    {
    	System.out.println("args: " + Arrays.toString(args) + ", length: " + args.length);
        if (args.length < 1) {
        	System.err.println("Missing input path");
        	System.exit(0);
        }
        
        String path = args[0];
        String outputPath = "/tmp/spark-out/wordcount-out";
        System.out.println("input path: " + path);
        
        SparkConf conf = new SparkConf().setAppName("com.hell.spark.WordCount")
        		.setMaster("local");
        JavaSparkContext ctx = new JavaSparkContext(conf);
        JavaRDD<String> file = ctx.textFile(path);
        JavaRDD<String> words = file.flatMap(WORDS_EXTRACTOR);
        JavaPairRDD<String, Integer> pairs = words.mapToPair(WORDS_MAPPER);
        JavaPairRDD<String, Integer> counter = pairs.reduceByKey(WORDS_REDUCER);
        
        counter.saveAsTextFile(outputPath);
        System.out.println("output path: " + outputPath);
    }
    
    private static final FlatMapFunction<String, String> WORDS_EXTRACTOR = new FlatMapFunction<String, String>() {
    	public Iterable<String> call(String s) throws Exception {
    		return Arrays.asList(s.split(" "));
    	}
    };
    
    private static final PairFunction<String, String, Integer> WORDS_MAPPER = new PairFunction<String, String, Integer>() {
    	public Tuple2<String, Integer> call(String s) throws Exception {
    		return new Tuple2<String, Integer>(s, 1);
    	}
    };
    
    private static final Function2<Integer, Integer, Integer> WORDS_REDUCER = new Function2<Integer, Integer, Integer>() {
    	public Integer call(Integer a, Integer b) throws Exception {
    		return a + b;
    	}
    };
}
