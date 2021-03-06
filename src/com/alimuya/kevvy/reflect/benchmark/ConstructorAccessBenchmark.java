
package com.alimuya.kevvy.reflect.benchmark;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Setup;

import com.alimuya.benchmark.jmh.AbstractMicrobenchmark;
import com.alimuya.kevvy.reflect.KevvyConstructor;
import com.alimuya.kevvy.reflect.KevvyConstructorReflect;
import com.alimuya.kevvy.reflect.exception.ConstructorReflectException;
import com.alimuya.kevvy.reflect.exception.InvokeTargetException;


/**
 * @author ov_alimuya
 *
 */
@BenchmarkMode(Mode.AverageTime)
public class ConstructorAccessBenchmark extends AbstractMicrobenchmark {
	private KevvyConstructorReflect<BenchmarkConstructorBean> kevvyReflect;
	private KevvyConstructor<BenchmarkConstructorBean> kevvyNoParameter;
	private KevvyConstructor<BenchmarkConstructorBean> kevvyParameters;
	private Constructor<BenchmarkConstructorBean> javaNoParameter;
	private Constructor<BenchmarkConstructorBean> javaParameters;
	private Class<BenchmarkConstructorBean> type;
	private BenchmarkConstructorBean bean;

	@Setup
	public void init() throws NoSuchMethodException, SecurityException{
		this.type = BenchmarkConstructorBean.class;
		this.bean=new BenchmarkConstructorBean();
		this.kevvyReflect = KevvyConstructorReflect.createConstructor(type);
		this. kevvyNoParameter = kevvyReflect.getConstructor();
		this. kevvyParameters = kevvyReflect.getConstructor(String.class, String.class,String.class,double.class,int.class);
		this.javaNoParameter = type.getConstructor();
		this.javaParameters = type.getConstructor(String.class, String.class,String.class,double.class,int.class);
	}
	
	
	@Benchmark
	public BenchmarkConstructorBean kevvyNoParameter() throws ConstructorReflectException, InvokeTargetException{
		return kevvyNoParameter.newInstance();
	}
	
	
	@Benchmark
	public BenchmarkConstructorBean javaNoParameter() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		return javaNoParameter.newInstance();
	}
	
	
	@Benchmark
	public BenchmarkConstructorBean kevvyParameters() throws ConstructorReflectException, InvokeTargetException{
		return kevvyParameters.newInstance("vv","ov","alimuya",3.14,1);
	}
	
	@Benchmark
	public BenchmarkConstructorBean javaParameters() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		return javaParameters.newInstance("vv","ov","alimuya",3.14,1);
	}
	
	@Benchmark
	public BenchmarkConstructorBean kevvyInstanceWithoutConstructor() throws ConstructorReflectException{
		return KevvyConstructorReflect.newInstanceWithoutConstructor(type);
	}

	@Benchmark
	public BenchmarkConstructorBean baseline() throws ConstructorReflectException{
		return this.bean;
	}
	
	@Override
	protected String benchmarkName() {
		return "Kevvy Constructor Reflect Benchmark";
	}

	@Override
	protected String baseLineMethodName() {
		return "baseline";
	}
}
