package com.alimuya.benchmark.jmh;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.results.Result;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * @author ov_alimuya
 *
 */
@Warmup(iterations = AbstractMicrobenchmark.DEFAULT_WARMUP_ITERATIONS)
@Measurement(iterations = AbstractMicrobenchmark.DEFAULT_MEASURE_ITERATIONS)
@Fork(AbstractMicrobenchmark.DEFAULT_FORKS)
@State(Scope.Thread)
@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public abstract class AbstractMicrobenchmark {
    protected static final int DEFAULT_WARMUP_ITERATIONS = 10;
    protected static final int DEFAULT_MEASURE_ITERATIONS = 10;
    protected static final int DEFAULT_FORKS = 2;
    
    protected static final String[] JVM_ARGS = {
        "-server",
    };
    
    
    protected abstract String baseLineMethodName();
//    "-Xms768m", "-Xmx768m",
//    "-XX:MaxDirectMemorySize=768m", "-XX:+AggressiveOpts", "-XX:+UseBiasedLocking",
//    "-XX:+UseFastAccessorMethods", "-XX:+OptimizeStringConcat",
//    "-XX:+HeapDumpOnOutOfMemoryError",
    @Test
    public void run() throws Exception {
        String className = getClass().getSimpleName();
        ChainedOptionsBuilder runnerOptions = new OptionsBuilder()
            .include(".*" + className + ".*")
            .resultFormat(ResultFormatType.JSON)
            .result(filePath())
            .jvmArgs(JVM_ARGS);
        Collection<RunResult> results = new Runner(runnerOptions.build()).run();
        System.out.println("");
        this.chart(results);
    }
    
    
    private String filePath() throws IOException{
    	File root=new File("");
    	String filePath=root.getAbsolutePath()+"/result/"+this.getClass().getName()+"_"+System.currentTimeMillis()+".json";
    	File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        } else {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        return filePath;
    }
    
    protected abstract String benchmarkName();
    
    
    private Map<String,Long> getcharMap(Collection<RunResult> results){
    	Map<String,Long> map=new HashMap<String,Long>();
    	long baselineValue=0;
    	String baseLineMethod=this.baseLineMethodName();
    	for (RunResult result :results) {
 			Result<?> tmp = result.getPrimaryResult();
 			String key=tmp.getLabel();
 			double score=tmp.getScore();
			BigDecimal b = new BigDecimal(score);
			long value = (long) ((b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()) * 1000);
			if (key.equals(baseLineMethod)) {
				baselineValue=value;
 			}else{
 				map.put(key, value);
 			}
 		}
    	if(baselineValue>0){
    		for (String key:map.keySet()) {
				map.put(key, map.get(key)-baselineValue);
			}
    	}
    	return map;
    }
    
    private void chart (Collection<RunResult> results) {
    	String title=this.benchmarkName();
    	Map <String,Long>  resultMap=this.getcharMap(results);
		Comparator<Entry<String, Long>> comparator = new Comparator<Entry<String, Long>>() {
			public int compare (Entry<String, Long> o1, Entry<String, Long> o2) {
				return (int)(o1.getValue() - o2.getValue());
			}
		};
		ArrayList<Entry<String, Long>> list = new ArrayList<Entry<String, Long>>(resultMap.entrySet());
		Collections.sort(list, comparator);

		StringBuilder names = new StringBuilder(512);
		StringBuilder times = new StringBuilder(512);
		long max = 0;
		int count = 0;
		for (Entry<String, Long> entry : list) {
			String name = entry.getKey();
			names.insert(0, '|');
			names.insert(0, name);
			long time = entry.getValue();
			times.append(time);
			times.append(',');
			max = Math.max(max, time);
			count++;
		}
		times.setLength(times.length() - 1);
		names.setLength(names.length() - 1);
		int height = count * 18 + 21;
		int width = Math.min(700, 300000 / height);
		System.out.println("-------------------------------------------");
		System.out.println("GOOGLE CHART : ");
		System.out.println("");
		System.out.println("http://chart.apis.google.com/chart?chtt=" + title + "&" + "chs=" + width + "x" + height
			+ "&chd=t:" + times + "&chds=0," + max + "&chxl=0:|" + names + "&cht=bhg&chbh=10&chxt=y&"
			+ "chco=660000|660033|660066|660099|6600CC|6600FF|663300|663333|"
			+ "663366|663399|6633CC|6633FF|666600|666633|666666");
	}
 
}
