package com.softgrid.flawAssessmentLite.constant;

public enum SteelGrade {

    SteelGrade1("L175/A25", 175, 310),
    SteelGrade2("L175p/A25P", 175, 310),
    SteelGrade3("L210/A", 210, 335),
    SteelGrade4("L245/B", 245, 415),
    SteelGrade5("L290/X42", 290, 415),
    SteelGrade6("L320/X46", 320, 435),
    SteelGrade7("L360/X52", 360, 460),
    SteelGrade8("L390/X56", 390, 490),
    SteelGrade9("L415/X60", 415, 520),
    SteelGrade10("L450/X65", 450, 535),
    SteelGrade11("L485/X70", 485, 570);

    private final String steelGrade;
    private final int sigma_s;
    private final int sigma_b;

    SteelGrade(String steelGrade, int sigma_s, int sigma_b) {
        this.steelGrade = steelGrade;
        this.sigma_s = sigma_s;
        this.sigma_b = sigma_b;
    }

    public String getSteelGrade() {
        return steelGrade;
    }

    public int getSigma_s() {
        return sigma_s;
    }

    public int getSigma_b() {
        return sigma_b;
    }

    @Override
    public String toString() {
        return this.steelGrade;
    }
}
