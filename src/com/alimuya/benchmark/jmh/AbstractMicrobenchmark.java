package com.alimuya.benchmark.jmh;

import java.io.File;
import java.io.IOException;
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
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.OptionsBuilder;

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
        "-server","-Xms768m", "-Xmx768m",
        "-XX:MaxDirectMemorySize=768m", "-XX:+AggressiveOpts", "-XX:+UseBiasedLocking",
        "-XX:+UseFastAccessorMethods", "-XX:+UseStringCache", "-XX:+OptimizeStringConcat",
        "-XX:+HeapDumpOnOutOfMemoryError",
    };
 
    @Test
    public void run() throws Exception {
        String className = getClass().getSimpleName();
        ChainedOptionsBuilder runnerOptions = new OptionsBuilder()
            .include(".*" + className + ".*")
            .resultFormat(ResultFormatType.JSON)
            .result(filePath())
            .jvmArgs(JVM_ARGS);
        new Runner(runnerOptions.build()).run();
    }
    
    
    private String filePath() throws IOException{
    	File root=new File("");
    	String filePath=root.getAbsolutePath()+"/result/"+this.getClass().getName()+".json";
    	File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        } else {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        return filePath;
    }
 
}
