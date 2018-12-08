package com.example.tringuyen.financialappfinalproject;

public class DailyIncomeCalculator {

    double totalIncome, savingPercentage, totalPlannedSavingPerDay, totalRecurring;
    int daysOfMonth;
    String frequency;
    public DailyIncomeCalculator(String totalIncome, String savingPercentage, String totalPlannedSavingPerDay,  String totalRecurring, String frequency, int daysOfMonth){
        this.totalIncome = Double.parseDouble(totalIncome);
        this.savingPercentage = Double.parseDouble(savingPercentage);
        this.totalPlannedSavingPerDay = Double.parseDouble(totalPlannedSavingPerDay);
        this.totalRecurring = Double.parseDouble(totalRecurring);
        this.frequency = frequency;
        this.daysOfMonth = daysOfMonth;
    }

    public double dailyReturn(){
        double totalPerDaysBeforeDeduct =0;
        double totalPerDayAfterDeductPlannedSavingAndRecurring=0;
        double totalRecurringPerDay = totalRecurring/daysOfMonth;
        if(frequency.equals("yearly")){
            totalIncome = totalIncome/12;
        }
        else if(frequency.equals("bi-weekly")){
            totalIncome = totalIncome*2;
        }

        totalPerDaysBeforeDeduct = (totalIncome/daysOfMonth)*((100-savingPercentage)/100);
        totalPerDayAfterDeductPlannedSavingAndRecurring = totalPerDaysBeforeDeduct-totalPlannedSavingPerDay - totalRecurringPerDay;

        return Math.round(totalPerDayAfterDeductPlannedSavingAndRecurring*100.0)/100.0;
    }

    public String dailyReturnString(){
        double totalPerDaysBeforeDeduct =0;
        double totalPerDayAfterDeductPlannedSavingAndRecurring=0;
        double totalRecurringPerDay = totalRecurring/daysOfMonth;
        if(frequency.equals("yearly")){
            totalIncome = totalIncome/12;
        }
        else if(frequency.equals("bi-weekly")){
            totalIncome = totalIncome*2;
        }

        totalPerDaysBeforeDeduct = (totalIncome/daysOfMonth)*((100-savingPercentage)/100);
        totalPerDayAfterDeductPlannedSavingAndRecurring = totalPerDaysBeforeDeduct-totalPlannedSavingPerDay - totalRecurringPerDay;


        return String.valueOf(Math.round(totalPerDayAfterDeductPlannedSavingAndRecurring*100.0)/100.0);
    }
}
