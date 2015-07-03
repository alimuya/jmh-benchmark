
package com.alimuya.kevvy.stupid.map.benchmark;

import java.util.HashMap;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Setup;

import com.alimuya.benchmark.jmh.AbstractMicrobenchmark;
import com.alimuya.kevvy.stupid.map.IStupidMap;
import com.alimuya.kevvy.stupid.map.StupidMapFactroy;

/**
 * @author ov_alimuya
 *
 */
@BenchmarkMode(Mode.AverageTime)
public class SingleBenchmark extends AbstractMicrobenchmark {
	private String key;
	private HashMap<String, String> hashMap;
	private IStupidMap<String> stupidMap;
	
	@Setup
	public void init(){
		this.key="sdf";
		this.hashMap=new HashMap<String, String>();
		hashMap.put("sdf", "ssdf");
		this.stupidMap = StupidMapFactroy.creatMap(hashMap, false);
	}
	
	@Benchmark
	public String baseline(){
		return key;
	}
	
	@Benchmark
	public String hashMapGet(){
		return hashMap.get(key);
	}
	
	@Benchmark
	public String stupidMapGet(){
		return stupidMap.get(key);
	}

	@Override
	protected String benchmarkName() {
		return "Kevvy StupidMap Single Benchmark";
	}

	@Override
	protected String baseLineMethodName() {
		return "baseline";
	}
	
}
