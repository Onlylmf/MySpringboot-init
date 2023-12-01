package com.murphy.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {
	
	public static final BigDecimal MAX_PURCHASE_NUM =new BigDecimal("100000");
	/**
	 *  除以100  保留2位小数
	 */
	public static BigDecimal divideHundred(BigDecimal denominator){
		
		return denominator.divide(new BigDecimal("100.00"), 2, BigDecimal.ROUND_HALF_UP);
	}
	

	/**
	 * 
	 * @Title:  除以100  精度自定义
	 *@param denominator 分母
	 *@param accuracy	精度
	 *@return
	 *@return : BigDecimal
	 */
	public static BigDecimal divideHundred(BigDecimal denominator,int accuracy){
		
		return denominator.divide(new BigDecimal("100.00"), accuracy, BigDecimal.ROUND_HALF_UP);
	}
	/**
	 * 	除法 保留2位小数
	 */
	public static BigDecimal divide(BigDecimal denominator,BigDecimal numerator){
		return denominator.divide(numerator, 2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 
	 * @Title: 除法 精度自定义
	 *@param denominator 分母
	 *@param numerator   分子
	 *@param accuracy	精度
	 *@return
	 *@return : BigDecimal
	 */
	public static BigDecimal divide(BigDecimal denominator,BigDecimal numerator,int accuracy){
		return denominator.divide(numerator, accuracy, BigDecimal.ROUND_HALF_UP);
	}
	/**
	 * 	乘法 保留2位小数
	 */
	public static BigDecimal multiply(BigDecimal a,BigDecimal b){
		return a.multiply(b).setScale(2,  BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 	乘法 保留scale位小数
	 */
	public static BigDecimal multiply(BigDecimal a,BigDecimal b,int scale){
		return a.multiply(b).setScale(scale,  BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 	判断2个数是否相等
	 */
	public static boolean isEqual(BigDecimal a,BigDecimal b){
		return a.compareTo(b) == 0;
	}
	/**
	 * 	判断 a是不是小于b
	 */
	public static boolean isLess(BigDecimal a,BigDecimal b){
		return a.compareTo(b) == -1;
	}
	/**
	 * 	判断 a是不是大于b
	 */
	public static boolean isGreat(BigDecimal a,BigDecimal b){
		return a.compareTo(b) == 1;
	}
}
