
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
public class MutableSeedBenchmark extends AbstractMicrobenchmark {
	private String key;
	private HashMap<String, String> hashMap;
	private IStupidMap<String> stupidMap;

	@Setup
	public void init(){
		this.key="sdf";
		this.hashMap=new HashMap<String, String>();
		String [] keys=new String []{"a","ss","hghfhfg","sdae","dwfsdsfse","yuuyij","14564","de56","sdf","sdfh444","sdfdg","4654%&^*","153462345"};
		for (int i = 0; i < keys.length; i++) {
			hashMap.put(keys[i], i+"");
		}
		this.stupidMap = StupidMapFactroy.creatMap(hashMap, false);
	}
	
	@Override
	protected String baseLineMethodName() {
		return "baseline";
	}


	@Override
	protected String benchmarkName() {
		return "Kevvy StupidMap MutableSeed Benchmark";
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
}
