
package com.alimuya.kevvy.reflect.benchmark;

import java.lang.reflect.Field;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Setup;

import com.alimuya.benchmark.jmh.AbstractMicrobenchmark;
import com.alimuya.kevvy.reflect.KevvyField;
import com.alimuya.kevvy.reflect.KevvyFieldReflect;
import com.alimuya.kevvy.reflect.exception.FieldReflectException;

/**
 * @author ov_alimuya
 *
 */
public class FieldAccessBenchmark extends AbstractMicrobenchmark {
	
	private KevvyField kevvyPublicField;
	private KevvyField kevvyGSField;
	private KevvyField kevvyPrivateField;
	private Field javaField;
	private String testString;
	private BenchmarkFieldBean testFieldBean;

	@Setup
	public void init() throws NoSuchFieldException, SecurityException{
		this.testFieldBean=new BenchmarkFieldBean();
		this.kevvyPublicField = KevvyFieldReflect.createFieldReflect(BenchmarkFieldBean.class).getField("publicField");
		this.kevvyGSField = KevvyFieldReflect.createFieldReflect(BenchmarkFieldBean.class).getField("gsField");
		this.kevvyPrivateField = KevvyFieldReflect.createFieldReflect(BenchmarkFieldBean.class).getField("privateField");
		this.javaField = BenchmarkFieldBean.class.getDeclaredField("publicField");
		javaField.setAccessible(true);
		this.testString ="ov-alimuya";
	}
	
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public String javaReflectField() throws IllegalArgumentException, IllegalAccessException{
		javaField.set(testFieldBean, testString);
		return (String) javaField.get(testFieldBean);
	}
	
	
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public String javaGetterSetterField(){
		testFieldBean.setGsField(testString);
		return testFieldBean.getGsField();
	}
	
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public String kevvyPublicField() throws FieldReflectException{
		kevvyPublicField.setObject(testFieldBean, testString);
		return (String) kevvyPublicField.get(testFieldBean);
	}
	
	
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public String kevvyPrivateField() throws FieldReflectException{
		kevvyPrivateField.setObject(testFieldBean, testString);
		return (String) kevvyPrivateField.get(testFieldBean);
	}
	
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public String kevvyGetterSetterField() throws FieldReflectException{
		kevvyGSField.setObject(testFieldBean, testString);
		return (String) kevvyGSField.get(testFieldBean);
	}

	@Override
	protected String benchmarkName() {
		return "Kevvy Field Reflect Benchmark";
	}
}
