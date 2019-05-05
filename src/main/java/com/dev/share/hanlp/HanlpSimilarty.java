package com.dev.share.hanlp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

public class HanlpSimilarty {
	private static class HanlpSimilartySingleton {
		private static final HanlpSimilarty INSTANCE = new HanlpSimilarty();
	}

	private HanlpSimilarty() {
	};

	public final static HanlpSimilarty getInstance() {
		return HanlpSimilartySingleton.INSTANCE;
	}

	/**
	 * 描述: 文本相似度算法
	 * 作者: ZhangYi
	 * 时间: 2019年1月11日 下午2:03:04
	 * 参数: (参数列表)
	 * 
	 * @param source 源文本
	 * @param target 目标文本
	 * @return
	 */
	public double similarity(String source, String target) {
		List<String> sourceWords = analysis(source, null);
		List<String> targetWords = analysis(target, null);
		List<String> allWords = merge(sourceWords, targetWords);

		int[] vec1 = frequency(allWords, sourceWords);
		int[] vec2 = frequency(allWords, targetWords);
		double similarity = cosineSimilarity(vec1, vec2);
		return similarity;
	}

	private double cosineSimilarity(int[] vec1, int[] vec2) {
		double dividend = 0;
		double divisor1 = 0;
		double divisor2 = 0;
		for (int i = 0; i < vec1.length; i++) {
			dividend += vec1[i] * vec2[i];
			divisor1 += Math.pow(vec1[i], 2);
			divisor2 += Math.pow(vec2[i], 2);
		}
		double similarity = dividend / (Math.sqrt(divisor1) * Math.sqrt(divisor2));
		return similarity;
	}

	/**
	 * 描述: 文本相似度算法
	 * 作者: ZhangYi
	 * 时间: 2019年1月11日 下午2:03:04
	 * 参数: (参数列表)
	 * 
	 * @param source    源文本
	 * @param target    目标文本
	 * @param algorithm 算法
	 *                  维特比 (viterbi)：效率和效果的最佳平衡
	 *                  双数组trie树 (dat)：极速词典分词，千万字符每秒
	 *                  条件随机场 (crf)：分词、词性标注与命名实体识别精度都较高，适合要求较高的NLP任务
	 *                  感知机 (perceptron)：分词、词性标注与命名实体识别，支持在线学习
	 *                  N最短路 (nshort)：命名实体识别稍微好一些，牺牲了速度
	 * @return
	 */
	public double similarity(String source, String target, String algorithm) {
		List<String> sourceWords = analysis(source, algorithm);
		List<String> targetWords = analysis(target, algorithm);
		List<String> allWords = merge(sourceWords, targetWords);
		int[] vec1 = frequency(allWords, sourceWords);
		int[] vec2 = frequency(allWords, targetWords);
		double similarity = cosine(vec1, vec2);
		return similarity;
	}

	private double cosine(int[] vec1, int[] vec2) {
		double dividend = 0;
		double divisor1 = 0;
		double divisor2 = 0;
		for (int i = 0; i < vec1.length; i++) {
			dividend += vec1[i] * vec2[i];
			divisor1 += Math.pow(vec1[i], 2);
			divisor2 += Math.pow(vec2[i], 2);
		}
		double similarity = dividend / Math.sqrt(divisor1 * divisor2);
		return similarity;
	}

	/**
	 * 描述: 词频率
	 * 作者: ZhangYi
	 * 时间: 2019年1月11日 下午2:03:53
	 * 参数: (参数列表)
	 * 
	 * @param allWords  合集分词
	 * @param sentWords 目标分析分词
	 * @return
	 */
	private int[] frequency(List<String> allWords, List<String> sentWords) {
		int[] result = new int[allWords.size()];
		for (int i = 0; i < allWords.size(); i++) {
			result[i] = Collections.frequency(sentWords, allWords.get(i));
		}
		return result;
	}

	/**
	 * 描述: 合并分词
	 * 作者: ZhangYi
	 * 时间: 2019年1月11日 下午2:04:33
	 * 参数: (参数列表)
	 * 
	 * @param sourceWords 源文本分词
	 * @param targetWords 目标文本分词
	 * @return
	 */
	private List<String> merge(List<String> sourceWords, List<String> targetWords) {
		List<String> all = new ArrayList<>();
		all.addAll(sourceWords);
		all.addAll(targetWords);
		return all.stream().distinct().collect(Collectors.toList());
	}

	/**
	 * 描述: HanLP分词
	 * 作者: ZhangYi
	 * 时间: 2019年1月11日 下午2:06:37
	 * 参数: (参数列表)
	 * 
	 * @param words 文本
	 * @return algorithm 算法
	 */
	private List<String> analysis(String words, String algorithm) {
		List<Term> term = null;
		if (algorithm == null) {
			term = HanLP.segment(words);// 标准分词器
		} else {
			/**
			 * 维特比 (viterbi)：效率和效果的最佳平衡
			 * 双数组trie树 (dat)：极速词典分词，千万字符每秒
			 * 条件随机场 (crf)：分词、词性标注与命名实体识别精度都较高，适合要求较高的NLP任务
			 * 感知机 (perceptron)：分词、词性标注与命名实体识别，支持在线学习
			 * N最短路 (nshort)：命名实体识别稍微好一些，牺牲了速度
			 */
			term = HanLP.newSegment(algorithm).seg(words);
		}
		String filter = "`~!@#$^&*()=|{}':;',\\\\[\\\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？ ";
		List<String> tokenizer = term.stream().map(a -> a.word).filter(s -> !filter.contains(s)).collect(Collectors.toList());
		return tokenizer;
	}
}
