package com.murex.retail.operations;

import com.murex.retail.model.component.ComputerComponent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;

public class ComponentOperations {
    public static void sortComponents(List<ComputerComponent> computerComponents) {
        computerComponents.sort(
                Comparator.comparing(ComputerComponent::getCategory)
                        .thenComparing(ComputerComponent::getName)
                        .thenComparing(ComputerComponent::getBrand));
    }

    public static double computeAverageComponentPrice(List<ComputerComponent> computerComponents) {
        double avgPrice = computerComponents.stream()
                .mapToDouble(ComputerComponent::getPrice)
                .average()
                .getAsDouble();
        return roundValue(avgPrice);
    }

    public static double computeAverageCPUPrice(List<ComputerComponent> computerComponents) {
        double avgPrice = computerComponents.stream()
                .filter(c -> c.getCategory().toString().equals("CPU"))
                .mapToDouble(ComputerComponent::getPrice)
                .average()
                .getAsDouble();
        return roundValue(avgPrice);
    }

    public static ComputerComponent findCheapestComponent(List<ComputerComponent> computerComponents) {
        return computerComponents.stream()
                .min((c1, c2) -> (int) (c1.getPrice() - c2.getPrice())).get();
    }

    public static ComponentMapOutput findMostExpensiveOfEachComponentCategory(List<ComputerComponent> computerComponents) {
        return new ComponentMapOutput(computerComponents.stream()
                .collect(groupingBy(
                                ComputerComponent::getCategory,
                                maxBy(java.util.Comparator.comparing(ComputerComponent::getPrice))
                        )
                ));
    }

    public static LongMapOutput reportNumberOfComponentsGroupedByCategory(List<ComputerComponent> computerComponents) {
        return new LongMapOutput(computerComponents.stream()
                .collect(groupingBy(
                        ComputerComponent::getCategory,
                        counting())));
    }

    public static NestedMapOutput reportNumberOfComponentsGroupedByCategoryAndBrand(List<ComputerComponent> computerComponents) {
        return new NestedMapOutput(computerComponents.stream()
                .collect(groupingBy(
                        ComputerComponent::getCategory,
                        groupingBy(ComputerComponent::getBrand,
                                counting()))));
    }

    private static double roundValue(double d) {
        return new BigDecimal(String.valueOf(d))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
