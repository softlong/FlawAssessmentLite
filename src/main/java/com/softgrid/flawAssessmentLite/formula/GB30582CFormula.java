package com.softgrid.flawAssessmentLite.formula;

import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;

/**
 * Author: Cheryl
 * Description:
 * Date:Created in 14:25 2018/4/23
 */
public class GB30582CFormula {
    public static double GB30582FormulaC(double t,double D,double sigma,double Cv,double E,double P,double H0,double d){
        double h;
        double K1 = 1.9;
        double K2 = 0.57;
        double A = 53.55;
        double sigma_avg = 1.15 * sigma * (1 - d/t);
        double R = (2*D-t)/2;

        double C = new ExpressionBuilder("2/3*Cv").variables("Cv").build().setVariable("Cv",Cv).evaluate();

        if(P == 0){
            h = H0;
        }else{
            h = H0 / 1.43;
        }

        String str_Y1 = "1.12-0.23(d/t)+10.6(d/t)^2-21.7(d/t)^3+30.4(d/t)^4";
        double Y1 = new ExpressionBuilder(str_Y1).variables("d","t").build().setVariable("d",d)
                .setVariable("t",t).evaluate();

        String str_Y2 = "1.12-1.39(d/t)+7.32(d/t)^2-13.1(d/t)^3+14.0(d/t)^4";
        double Y2 = new ExpressionBuilder(str_Y2).variables("d","t").build().setVariable("d",d)
                .setVariable("t",t).evaluate();

        String str_a = "113 * [(1.5*π*E)/(sigma_avg^2 * A * d)]";
        double a = new ExpressionBuilder(str_a).variables("E","A","sigma_avg","d").build()
                .setVariable("E",E).setVariable("A",A).setVariable("sigma_avg",sigma_avg)
                .setVariable("d",d).evaluate();

        String str_b = "{Y1[1-1.8(H0/D)]+Y2[10.2(R/t)*(H0/D)]}^2";
        double b = new ExpressionBuilder(str_b).variables("Y1","H0","D","Y2","R","t").build()
                .setVariable("Y1",Y1).setVariable("Y2",Y2)
                .setVariable("H0",h).setVariable("D",D)
                .setVariable("R",R).setVariable("t",t).evaluate();

        Function logb = new Function("logb", 2) {
            @Override
            public double apply(double... args) {
                return Math.log(args[0]) / Math.log(args[1]);
            }
        };
        double log = new ExpressionBuilder("logb(e, 0.738C)")
                .function(logb).variables("C")
                .build().setVariable("C",C)
                .evaluate();
        double c = (log - K1)/K2;

        double exp1 = new ExpressionBuilder("exp(c)").variables("c").build().setVariable("c",c).evaluate();

        double exp2 = new ExpressionBuilder("exp[-(a*b*exp1)]").variables("a","b","exp1").build()
                .setVariable("a",a).setVariable("b",b).setVariable("exp1",exp1).evaluate();

        double sita = new ExpressionBuilder("(2/π)*[1/cos(exp2)]").variables("exp2").build()
                .setVariable("exp2",exp2).evaluate();

        double sita1 = sita * sigma_avg;

        double sigma_h = (P * D)/(2 * t);

        if (sigma_h>sita1){
            return 0;
        }else {
            return 1;
        }
    }
}
