package com.dev.share.test;

import org.nfunk.jep.JEP;
import org.nfunk.jep.function.HJEP;
import org.nfunk.jep.function.Min;

public class JEPTest {
	public static void test1() {
		JEP jep = new HJEP(); // 一个数学表达式
//		String exp = "((a+b)*(c+b))/(c+a)/b"; // 给变量赋值
		String exp = "a*b+c-b/c"; // 给变量赋值
		jep.addVariable("a", 1);
		jep.addVariable("b", 2);
		jep.addVariable("c", 3);
		jep.parseExpression(exp);
		Object result = jep.getValueAsObject();
		System.out.println("[+-*/]计算结果： " + result);
	}

	public static void test11() {
		JEP jep = new HJEP(); // 一个数学表达式
//		String exp = "((a+b)*(c+b))/(c+a)/b"; // 给变量赋值
		String exp = "sin(a)"; // 给变量赋值
		jep.addVariable("a", 30);
		jep.parseExpression(exp);
		Object result = jep.getValueAsObject();
		System.out.println("[sin]计算结果： " + result);
	}

	public static void test2() {
		JEP jep = new HJEP(); // 一个数学表达式

		String exp = "max(a,b,c)"; // 给变量赋值
		jep.addVariable("a", 1);
		jep.addVariable("b", 2);
		jep.addVariable("c", 3);
		jep.parseExpression(exp);
		Object result = jep.getValueAsObject();
		System.out.println("[max]计算结果： " + result);
	}

	public static void test3() {
		JEP jep = new HJEP(); // 一个数学表达式
		String exp = "min(a,b,c)"; // 给变量赋值
		jep.addFunction("min", new Min());
		jep.addVariable("a", 1);
		jep.addVariable("b", 2);
		jep.addVariable("c", 3);
		jep.parseExpression(exp);
		Object result = jep.getValueAsObject();
		System.out.println("[min]计算结果： " + result);
	}

	public static void test4() {
		JEP jep = new HJEP(); // 一个数学表达式
		String exp = "avg(a,b,c)"; // 给变量赋值
		jep.addVariable("a", 1);
		jep.addVariable("b", 2);
		jep.addVariable("c", 3);
		jep.parseExpression(exp);
		Object result = jep.getValueAsObject();
		System.out.println("[avg]计算结果： " + result);
	}

	public static void test5() {
		JEP jep = new HJEP(); // 一个数学表达式
//		jep.setAllowUndeclared(true);//默认的设置是false(不允许未声明的变量)
//		jep.setImplicitMul(true);//默认的设置是false(没有缩略乘法)
//		jep.setAllowAssignment(true);//赋值允许变量的值被设置通过使用等式中的“=”操作符
		String exp = "sum(a,b,c)"; // 给变量赋值
		jep.addVariable("a", 1);
		jep.addVariable("b", 2);
		jep.addVariable("c", 3);
		jep.parseExpression(exp);
		Object result = jep.getValueAsObject();
		System.out.println("[sum]计算结果： " + result);
	}

	public static void test6() {
		JEP jep = new HJEP(); // 一个数学表达式
		String exp = "count(a,b,c)"; // 给变量赋值
		jep.addVariable("a", 1);
		jep.addVariable("b", 2);
		jep.addVariable("c", 3);
		jep.parseExpression(exp);
		Object result = jep.getValueAsObject();
		System.out.println("[count]计算结果： " + result);
	}

	public static void test7() {
		JEP jep = new HJEP(); // 一个数学表达式
		String exp = "if(a==1,b,c)"; // 给变量赋值
		jep.addVariable("a", 1);
		jep.addVariable("b", 2);
		jep.addVariable("c", 3);
		jep.parseExpression(exp);
		Object result = jep.getValueAsObject();
		System.out.println("[if]计算结果： " + result);
	}

	public static void main(String[] args) {
		test1();
		test11();
		test2();
		test3();
		test4();
		test5();
		test6();
		test7();
	}
}
