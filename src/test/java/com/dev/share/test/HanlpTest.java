package com.dev.share.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

import com.dev.share.hanlp.HanlpSimilarty;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CoreSynonymDictionary;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

public class HanlpTest {
	// 阈值,用于决定语言分析和语序分析占相似度的百分比，此处0.2为语已占比
	public static double YUZHI = 0.2;

	public static Vector<String> participle(String str) {

		Vector<String> str1 = new Vector<String>();// 对输入进行分词
		Segment segment = HanLP.newSegment().enableCustomDictionary(true);
		CustomDictionary.add("梁山广");// 动态添加自定义词汇
		List<Term> termList = segment.seg(str);
		for (Term term : termList) {
//                System.out.println(term.toString());
			str1.add(term.toString());
		}

		if (str1.size() == 0) {
			return null;
		}

		// 分词后
		System.out.println("str分词后：" + str1);
		return str1;
	}

	public static double getSimilarity(Vector<String> T1, Vector<String> T2) throws Exception {
		int size = 0, size2 = 0;
		if (T1 != null && (size = T1.size()) > 0 && T2 != null && (size2 = T2.size()) > 0) {

			Map<String, double[]> T = new HashMap<String, double[]>();

			// T1和T2的并集T
			String index = null;
			for (int i = 0; i < size; i++) {
				index = T1.get(i);
				if (index != null) {
					double[] c = T.get(index);
					c = new double[2];
					c[0] = 1; // T1的语义分数Ci
					c[1] = YUZHI;// T2的语义分数Ci
					T.put(index, c);
				}
			}

			for (int i = 0; i < size2; i++) {
				index = T2.get(i);
				if (index != null) {
					double[] c = T.get(index);
					if (c != null && c.length == 2) {
						c[1] = 1; // T2中也存在，T2的语义分数=1
					} else {
						c = new double[2];
						c[0] = YUZHI; // T1的语义分数Ci
						c[1] = 1; // T2的语义分数Ci
						T.put(index, c);
					}
				}
			}

			// 开始计算，百分比
			Iterator<String> it = T.keySet().iterator();
			double s1 = 0, s2 = 0, Ssum = 0; // S1、S2
			while (it.hasNext()) {
				double[] c = T.get(it.next());
				Ssum += c[0] * c[1];
				s1 += c[0] * c[0];
				s2 += c[1] * c[1];
			}
			// 百分比
			return Ssum / Math.sqrt(s1 * s2);
		} else {
			throw new Exception("传入参数有问题！");
		}
	}

	public static void main(String[] args) {

//		System.out.println(HanLP.segment("你好，欢迎使用HanLP汉语处理包！"));
//		List<Term> termList = StandardTokenizer.segment("商品和服务");
//		System.out.println(termList);
//		System.out.println(NLPTokenizer.segment("我新造一个词叫幻想乡你能识别并标注正确词性吗？"));
//		// 注意观察下面两个“希望”的词性、两个“晚霞”的词性
//		System.out.println(NLPTokenizer.analyze("我的希望是希望张晚霞的背影被晚霞映红").translateLabels());
//		System.out.println(NLPTokenizer.analyze("支援臺灣正體香港繁體：微软公司於1975年由比爾·蓋茲和保羅·艾倫創立。"));
//		List<Term> termList = IndexTokenizer.segment("主副食品");
//		for (Term term : termList)
//		{
//		    System.out.println(term + " [" + term.offset + ":" + (term.offset + term.word.length()) + "]");
//		}
//		Segment nShortSegment = new NShortSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
//		Segment shortestSegment = new DijkstraSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
//		String[] testCase = new String[]{
//		        "今天，刘志军案的关键人物,山西女商人丁书苗在市二中院出庭受审。",
//		        "刘喜杰石国祥会见吴亚琴先进事迹报告团成员",
//		        };
//		for (String sentence : testCase)
//		{
//		    System.out.println("N-最短分词：" + nShortSegment.seg(sentence) + "\n最短路分词：" + shortestSegment.seg(sentence));
//		}
//		CRFLexicalAnalyzer analyzer = new CRFLexicalAnalyzer();
//        String[] tests = new String[]{
//            "商品和服务",
//            "上海华安工业（集团）公司董事长谭旭光和秘书胡花蕊来到美国纽约现代艺术博物馆参观",
//            "微软公司於1975年由比爾·蓋茲和保羅·艾倫創立，18年啟動以智慧雲端、前端為導向的大改組。" // 支持繁体中文
//        };
//        for (String sentence : tests)
//        {
//            System.out.println(analyzer.analyze(sentence));
//        }
//        String text = "江西鄱阳湖干枯，中国最大淡水湖变成大草原";
//        System.out.println(SpeedTokenizer.segment(text));
//        long start = System.currentTimeMillis();
//        int pressure = 1000000;
//        for (int i = 0; i < pressure; ++i)
//        {
//            SpeedTokenizer.segment(text);
//        }
//        double costTime = (System.currentTimeMillis() - start) / (double)1000;
//        System.out.printf("分词速度：%.2f字每秒", text.length() * pressure / costTime);
//     // 动态增加
//        CustomDictionary.add("攻城狮");
//        // 强行插入
//        CustomDictionary.insert("白富美", "nz 1024");
//        // 删除词语（注释掉试试）
////        CustomDictionary.remove("攻城狮");
//        System.out.println(CustomDictionary.add("单身狗", "nz 1024 n 1"));
//        System.out.println(CustomDictionary.get("单身狗"));
//
//        String text = "攻城狮逆袭单身狗，迎娶白富美，走上人生巅峰";  // 怎么可能噗哈哈！
//
//        // AhoCorasickDoubleArrayTrie自动机扫描文本中出现的自定义词语
//        final char[] charArray = text.toCharArray();
//        CustomDictionary.parseText(charArray, new AhoCorasickDoubleArrayTrie.IHit<CoreDictionary.Attribute>()
//        {
//            @Override
//            public void hit(int begin, int end, CoreDictionary.Attribute value)
//            {
//                System.out.printf("[%d:%d]=%s %s\n", begin, end, new String(charArray, begin, end - begin), value);
//            }
//        });
//
//        // 自定义词典在所有分词器中都有效
//        System.out.println(HanLP.segment(text));
//        String[] testCase = new String[]{
//                "签约仪式前，秦光荣、李纪恒、仇和等一同会见了参加签约的企业家。",
//                "王国强、高峰、汪洋、张朝阳光着头、韩寒、小四",
//                "张浩和胡健康复员回家了",
//                "王总和小丽结婚了",
//                "编剧邵钧林和稽道青说",
//                "这里有关天培的有关事迹",
//                "龚学平等领导,邓颖超生前",
//                };
//        Segment segment = HanLP.newSegment().enableNameRecognize(true);
//        for (String sentence : testCase)
//        {
//            List<Term> termList = segment.seg(sentence);
//            System.out.println(termList);
//        }
//        String content = "程序员(英文Programmer)是从事程序开发、维护的专业人员。一般将程序员分为程序设计人员和程序编码人员，但两者的界限并不非常清楚，特别是在中国。软件从业人员分为初级程序员、高级程序员、系统分析员和项目经理四大类。";
//        List<String> keywordList = HanLP.extractKeyword(content, 5);
//        System.out.println(keywordList);
//        //内部采用TextRankKeyword实现，用户可以直接调用TextRankKeyword.getKeywordList(document, size)
//        
//        
//        WordVectorModel wordVectorModel = trainOrLoadModel();
//        printNearest("中国", wordVectorModel);
//        printNearest("美丽", wordVectorModel);
//        printNearest("购买", wordVectorModel);
//
//        // 文档向量
//        DocVectorModel docVectorModel = new DocVectorModel(wordVectorModel);
//        String[] documents = new String[]{
//            "山东苹果丰收",
//            "农民在江苏种水稻",
//            "奥运会女排夺冠",
//            "世界锦标赛胜出",
//            "中国足球失败",
//        };
//
//        System.out.println(docVectorModel.similarity(documents[0], documents[1]));
//        System.out.println(docVectorModel.similarity(documents[0], documents[4]));
//
//        for (int i = 0; i < documents.length; i++)
//        {
//            docVectorModel.addDocument(i, documents[i]);
//        }
//
//        printNearestDocument("体育", documents, docVectorModel);
//        printNearestDocument("农业", documents, docVectorModel);
//        printNearestDocument("我要看比赛", documents, docVectorModel);
//        printNearestDocument("要不做饭吧", documents, docVectorModel);
		String ctn1 = "11223344中国";
		String ctn2 = "中国足球失败";
//		double sim = CoreSynonymDictionary.similarity(ctn1, ctn2);
//		System.out.println(sim);
//		Vector<String> testLine1=participle(ctn1);
//        Vector<String> testLine2=participle(ctn2);
//        try
//        {
//            double similarity=getSimilarity(testLine1,testLine2);
//            System.out.println("两个句子的相似度为:"+similarity);
//        } catch (Exception e)
//        {
//            // TODO Auto-generated catch block
//            System.out.println("相似度 计算失败，失败原因如下：");
//            e.printStackTrace();
//        }
		double similarity = HanlpSimilarty.getInstance().similarity(ctn1, ctn2);
		System.out.println("两个句子的相似度为:" + similarity);
//      double distance = CoreSynonymDictionary.distance(ctn1, ctn2);
//      double similarity2 = CoreSynonymDictionary.similarity(ctn1, ctn2);
//      System.out.println("两个句子的相似度为:"+similarity+",相似距离:"+distance+",相似度:"+similarity2);
	}
}
