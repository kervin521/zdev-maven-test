package com.dev.share.test;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
/**
  * Throughput: 吞吐量，一段时间内可执行的次数,每秒可执行次数
  * AverageTime: 每次调用的平均耗时时间。
  * SampleTime: 随机进行采样执行的时间，最后输出取样结果的分布
  * SingleShotTime: 在每次执行中计算耗时,以上模式都是默认一次 iteration 是 1s，只有 SingleShotTime 是只运行一次。
 */
@BenchmarkMode({Mode.Throughput, Mode.AverageTime}) // 测试方法平均执行时间
@OutputTimeUnit(TimeUnit.SECONDS) // 输出结果的时间粒度为微秒
@State(Scope.Thread) // 每个测试线程一个实例
public class BenchmarkJmhTest {
	@Param
	private String[] arg;
	@Benchmark
    public void bench() {
        add(1, 1);
    }

    private static int add(int a, int b) {
        return a + b;
    }

    @Benchmark
    public String stringConcat() {
        String a = "a";
        String b = "b";
        String c = "c";
        return a + b + c;
    }
    @Setup
    public void init() {
    	
    }
    @TearDown
    public void finish() {
    	
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(BenchmarkJmhTest.class.getSimpleName())//代表我要测试的是哪个类的方法
                .exclude("stringConcat")//代表测试的时候需要排除stringConcat方法
                .forks(2)//指的是做2轮测试，在一轮测试无法得出最满意的结果时,可以多测几轮以便得出更全面的测试结果，而每一轮都是先预热，再正式计量。
                .warmupIterations(5)//代表先预热5次
                .measurementIterations(5)//正式运行测试5次
                .build();
        new Runner(options).run();
    }
}
