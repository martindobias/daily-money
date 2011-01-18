package com.bottleworks.dailymoney.reports;

import java.text.DecimalFormat;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;

import android.content.Context;
import android.content.Intent;

import com.bottleworks.dailymoney.data.AccountType;

public class BalancePieChart extends AbstractChart {

    DecimalFormat percentageFormat = new DecimalFormat("##0");
    
    public BalancePieChart(Context context, float dpRatio) {
        super(context, dpRatio);
    }

    public Intent createIntent(AccountType at,List<Balance> balances) {
        double total = 0;
        for(Balance b : balances){
            total += b.money<=0?0:b.money;
        }
        CategorySeries series = new CategorySeries(at.getDisplay(i18n));
        for(Balance b : balances){
            if(b.money>0){
                StringBuilder sb = new StringBuilder();
                sb.append(b.getName());
                double p = (b.money*100)/total;
                if(p>=1){
                    sb.append("(").append(percentageFormat.format(p)).append("%)");
                    series.add(sb.toString(),b.money);
                }
            }
        }
        int[] color = createColor(series.getItemCount());
        DefaultRenderer renderer = buildCategoryRenderer(color);
        renderer.setLabelsTextSize(14 * dpRatio);
        renderer.setLegendTextSize(16 * dpRatio);
        return ChartFactory.getPieChartIntent(context,series , renderer, series.getTitle());
    }
}