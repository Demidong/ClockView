package com.dx.demi;


import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * 测试一些公共类的方法，Math， String ，ArrayList等
 * https://www.cnblogs.com/xiaxj/p/7856496.html
 */


public class TestPublicClass {
    public static void main(String args[]) {
        testMathMethods(10,20);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void testMathMethods(int a, int b) {
        /**
         * 求一个数的绝对值
         */
        System.out.println("Math.abs(a):" + Math.abs(a));
        /**
         * 求两个数中的最大最小值
         */
        System.out.println("Math.max(a,b):" + Math.max(a,b));
        System.out.println("Math.min(a,b):" + Math.min(a,b));
        /**
         * 对数
         */
        System.out.println("Math.log(a):" + Math.log(Math.E));
        System.out.println("Math.log10(a):" + Math.log10(a));
        System.out.println("Math.log1p(a):" + Math.log1p(Math.E-1));

        /**
         * 指数
         */
        System.out.println("Math.exp(1):" + Math.exp(1));
        System.out.println("Math.exp(0):" + Math.exp(0));
        System.out.println("Math.exp(0):" + Math.expm1(0));
        /**
         * 幂 ,根
         */
        System.out.println("Math.pow(a,b)):" + Math.pow(a,b));
        System.out.println("Math.sqrt(a):" + Math.sqrt(a));
        System.out.println("Math.cbrt(a):" + Math.cbrt(a));
        System.out.println("Math.hypot(a,b):" + Math.hypot(a,b));
        System.out.println("Math.scalb(a,b):" + Math.scalb(a,b));
        /**
         * 随机数
         */

        System.out.println("Math.random() :" + Math.random());

        /**
         * 最接近的整数
         */
        System.out.println("Math.round() :" + Math.round(-10.5));


        System.out.println("Math.nextAfter(10f,-10f) :" + Math.nextAfter(10f,-10f));
        System.out.println("Math.nextAfter(10f,10f) :" + Math.nextAfter(10f,10f));
        System.out.println("Math.nextAfter(10f,20f) :" + Math.nextAfter(10f,20f));

        System.out.println("Math.nextDown(10f) :" + Math.nextDown(10f));
        System.out.println("Math.nextDown(-10f) :" + Math.nextDown(-10f));
        System.out.println("Math.nextUp(10f) :" + Math.nextUp(10f));
        System.out.println("Math.nextUp(10f) :" + Math.nextUp(-10f));

        System.out.println("Math.ceil(10f) :" + Math.ceil(20.5f));
        System.out.println("Math.ceil(10f) :" + Math.ceil(-20.5f)); //浮点数向上取整

        System.out.println("Math.ceil(10f) :" + Math.floor(20.5f)); //浮点数向下取整
        System.out.println("Math.ceil(10f) :" + Math.floor(-20.5f)); //浮点数向下取整

        System.out.println("Math.floorDiv(10f) :" + Math.floorDiv(-10,20)); //浮点数向下取整
        System.out.println("Math.floorDiv(10f) :" + Math.floorDiv(10,20)); //浮点数向下取整

        System.out.println("Math.floorMod(10f) :" + Math.floorMod(-10,20)); //浮点数向下取整
        System.out.println("Math.floorMod(10f) :" + Math.floorMod(10,20)); //浮点数向下取整


        System.out.println("Math.signum(10f) :" + Math.signum(-100)); //浮点数向下取整
        System.out.println("Math.signum(10f) :" + Math.signum(-10)); //浮点数向下取整
        System.out.println("Math.signum(10f) :" + Math.signum(10)); //浮点数向下取整
        System.out.println("Math.signum(10f) :" + Math.signum(20)); //浮点数向下取整
        System.out.println("Math.signum(10f) :" + Math.signum(0)); //浮点数向下取整


        System.out.println("Math.toDegrees(10f) :" + Math.toDegrees(0)); //浮点数向下取整
        System.out.println("Math.toDegrees(10f) :" + Math.toRadians(0)); //浮点数向下取整

        System.out.println("Math.getExponent(10f) :" + Math.getExponent(0)); //浮点数向下取整
        System.out.println("Math.rint(10f) :" + Math.rint(0)); //浮点数向下取整

        System.out.println("Math.copySign(10f) :" + Math.copySign(0,9)); //浮点数向下取整

        System.out.println("Math.IEEEremainder(10f) :" + Math.IEEEremainder(0,9)); //浮点数向下取整

        System.out.println("Math.ulp(10f) :" + Math.ulp(0)); //浮点数向下取整


    }
}
