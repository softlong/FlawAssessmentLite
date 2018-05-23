package com.softgrid.flawAssessmentLite.formula;

import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * Author: Cheryl
 * Description:
 * Date:Created in 10:15 2018/4/23
 */
public class GB30582BFormula {
    public static double FormulaB(double t,double R0,double R1,double R2,double L,double d){
        String str_epsilon1 = "(1/2)t * [(1/R0)-(1/R1)]";
        double epsilon1 = new ExpressionBuilder(str_epsilon1)
                .variables("t","R0","R1").build().setVariable("t",t).setVariable("R0",R0)
                .setVariable("R1",R1).evaluate();

        String str_epsilon2 = "-(1/2)(t/R2)";
        double epsilon2 = new ExpressionBuilder(str_epsilon2)
                .variables("t","R2").build().setVariable("t",t).setVariable("R2",R2).evaluate();

        String str_epsilon3 = "(1/2)(d/L)^2";
        double epsilon3 = new ExpressionBuilder(str_epsilon3)
                .variables("d","L").build().setVariable("d",d).setVariable("L",L).evaluate();

        String str_epsiloni = "[a^2-a(b+c)+(b+c)^2]^0.5";
        double epsilon1i = new ExpressionBuilder(str_epsiloni).variables("a","b","c").build()
                .setVariable("a",epsilon1).setVariable("b",epsilon2)
                .setVariable("c",epsilon3).evaluate();

        String str_epsilon10 = "[a^2+a(c-b)+(c-b)^2]^0.5";
        double epsilon10 = new ExpressionBuilder(str_epsilon10).variables("a","b","c").build()
                .setVariable("a",epsilon1).setVariable("b",epsilon2)
                .setVariable("c",epsilon3).evaluate();

        if(epsilon1i > epsilon10){
            return epsilon1i;
        }else {
            return epsilon10;
        }
    }
}
