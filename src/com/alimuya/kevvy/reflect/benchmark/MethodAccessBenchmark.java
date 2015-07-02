
package com.alimuya.kevvy.reflect.benchmark;

import java.lang.reflect.Method;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Setup;

import com.alimuya.benchmark.jmh.AbstractMicrobenchmark;
import com.alimuya.kevvy.reflect.KevvyMethod;
import com.alimuya.kevvy.reflect.KevvyMethodReflect;

/**
 * @author ov_alimuya
 *
 */
public class MethodAccessBenchmark extends AbstractMicrobenchmark {
	
	private Method javaVoidNoParameter;
	private Method javaVoidParameters;
	private Method javaReturnNoParameter;
	private Method javaReturnParameters;
	private KevvyMethod kevvyVoidNoParameter;
	private KevvyMethod kevvyVoidParameters;
	private KevvyMethod kevvyReturnNoParameter;
	private KevvyMethod kevvyReturnParameters;
	private KevvyMethod kevvyPrivateVoidNoParameter;
	private KevvyMethod kevvyPrivateVoidParameters;
	private KevvyMethod kevvyPrivateReturnNoParameter;
	private KevvyMethod kevvyPrivateReturnParameters;
	private BenchmarkMethodBean bean;

	@Setup
	public void init() throws NoSuchMethodException, SecurityException{
		this.bean=new BenchmarkMethodBean();
		this.javaVoidNoParameter = BenchmarkMethodBean.class.getDeclaredMethod("voidNoParameter");
		this.javaVoidParameters = BenchmarkMethodBean.class.getDeclaredMethod("voidParameters",String.class,String.class,String.class,double.class,int.class);
		this.javaReturnNoParameter = BenchmarkMethodBean.class.getDeclaredMethod("returnNoParameter");
		this.javaReturnParameters = BenchmarkMethodBean.class.getDeclaredMethod("returnParameters",String.class,String.class,String.class,double.class,int.class);
		javaVoidNoParameter.setAccessible(true);
		javaVoidParameters.setAccessible(true);
		javaReturnNoParameter.setAccessible(true);
		javaReturnParameters.setAccessible(true);
		
		
		KevvyMethodReflect kv = KevvyMethodReflect.createMethodReflect(BenchmarkMethodBean.class);
		this.kevvyVoidNoParameter = kv.getMethod("voidNoParameter");
		this.kevvyVoidParameters = kv.getMethod("voidParameters",String.class,String.class,String.class,double.class,int.class);
		this.kevvyReturnNoParameter = kv.getMethod("returnNoParameter");
		this.kevvyReturnParameters = kv.getMethod("returnParameters",String.class,String.class,String.class,double.class,int.class);
		
		
		this.kevvyPrivateVoidNoParameter = kv.getMethod("privateVoidNoParameter");
		this.kevvyPrivateVoidParameters = kv.getMethod("privateVoidParameters",String.class,String.class,String.class,double.class,int.class);
		this.kevvyPrivateReturnNoParameter = kv.getMethod("privateReturnNoParameter");
		this.kevvyPrivateReturnParameters = kv.getMethod("privateReturnParameters",String.class,String.class,String.class,double.class,int.class);
	}
	
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public Object kevvyVoidNoParameter()throws Exception {
		return kevvyVoidNoParameter.invoke(bean);
	}
	
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public Object kevvyPrivateVoidNoParameter()throws Exception {
		return  kevvyPrivateVoidNoParameter.invoke(bean);
	}
	
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public Object javaVoidNoParameter()throws Exception {
		return javaVoidNoParameter.invoke(bean);
	}
	
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public Object kevvyVoidParameters()throws Exception {
		return kevvyVoidParameters.invoke(bean,"vv","ov","alimuya",3.14,1);
	}
	
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public Object kevvyPrivateVoidParameters()throws Exception {
		return kevvyPrivateVoidParameters.invoke(bean,"vv","ov","alimuya",3.14,1);
	}
	
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public Object javaVoidParameters()throws Exception {
		return javaVoidParameters.invoke(bean,"vv","ov","alimuya",3.14,1);
	}
	
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public Object kevvyReturnNoParameter()throws Exception {
		return kevvyReturnNoParameter.invoke(bean);
	}
	
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public Object kevvyPrivateReturnNoParameter()throws Exception {
		return kevvyPrivateReturnNoParameter.invoke(bean);
	}
	
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public Object javaReturnNoParameter()throws Exception {
		return javaReturnNoParameter.invoke(bean);
	}
	
	
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public Object kevvyReturnParameters()throws Exception {
		return kevvyReturnParameters.invoke(bean,"vv","ov","alimuya",3.14,1);
	}
	

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public Object kevvyPrivateReturnParameters()throws Exception {
		return kevvyPrivateReturnParameters.invoke(bean,"vv","ov","alimuya",3.14,1);
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public Object javaReturnParameters()throws Exception {
		return javaReturnParameters.invoke(bean,"vv","ov","alimuya",3.14,1);
	}

	@Override
	protected String benchmarkName() {
		return "Kevvy Method Reflect Benchmark";
	}
}
