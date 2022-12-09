package com.murex.retail.operations;

import com.murex.retail.model.component.Category;
import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.operations.strategy.ComputeAveragePriceOfAllComponents;
import com.murex.retail.operations.strategy.ComputeAveragePriceOfCPUs;
import com.murex.retail.operations.strategy.CountNumberOfComponentsInEachCategory;
import com.murex.retail.operations.strategy.CountNumberOfComponentsInEachCategoryAndBrand;
import com.murex.retail.operations.strategy.FindCheapestComponent;
import com.murex.retail.operations.strategy.FindMostExpensiveComponentOfEachCategory;
import com.murex.retail.operations.strategy.QueryContext;
import com.murex.retail.operations.strategy.QueryStrategy;
import com.murex.retail.operations.strategy.SortComponents;
import com.murex.retail.parser.InventoryParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryStrategyTest {
    private static final String TEST_PATH = "Test.csv";
    private static List<ComputerComponent> computerComponents;
    private static QueryContext context;
    private static QueryStrategy strategy;

    @BeforeEach
    public void setup() {
        computerComponents = InventoryParser.parseInventory(TEST_PATH);
        context = new QueryContext();
    }

    @Test
    public void testSortComponents() {
        strategy = new SortComponents();
        context.setStrategy(strategy);
        String res = (String) context.executeStrategy(computerComponents).getResult();
        assertEquals("Finished sorting components", res);
    }

    @Test
    public void testComputeAveragePriceOfAllComponents() {
        strategy = new ComputeAveragePriceOfAllComponents();
        context.setStrategy(strategy);
        double res = (double) context.executeStrategy(computerComponents).getResult();
        assertEquals(145.83, res, 0.0);
    }

    @Test
    public void testComputeAveragePriceOfCPUs() {
        strategy = new ComputeAveragePriceOfCPUs();
        context.setStrategy(strategy);
        double res = (double) context.executeStrategy(computerComponents).getResult();
        assertEquals(92.43, res, 0.0);
    }

    @Test
    public void testCountNumberOfComponentsInEachCategory() {
        strategy = new CountNumberOfComponentsInEachCategory();
        context.setStrategy(strategy);
        LongMapOutput res = (LongMapOutput) context.executeStrategy(computerComponents).getResult();
        assertEquals(6, (long) res.getResult().get(Category.STORAGE));
        assertEquals(11, (long) res.getResult().get(Category.MONITOR));
        assertEquals(12, (long) res.getResult().get(Category.MOUSE));
        assertEquals(9, (long) res.getResult().get(Category.MEMORY));
        assertEquals(20, (long) res.getResult().get(Category.KEYBOARD));
        assertEquals(37, (long) res.getResult().get(Category.CPU));
        assertEquals(5, (long) res.getResult().get(Category.GPU));
    }

    @Test
    public void testCountNumberOfComponentsInEachCategoryAndBrand() {
        strategy = new CountNumberOfComponentsInEachCategoryAndBrand();
        context.setStrategy(strategy);
        context.executeStrategy(computerComponents);
        NestedMapOutput numberOfComponents = (NestedMapOutput) context.executeStrategy(computerComponents).getResult();
        assertEquals(6, (long) numberOfComponents.getResult().get(Category.STORAGE).values().stream().reduce(Long::sum).get());
        assertEquals(11, (long) numberOfComponents.getResult().get(Category.MONITOR).values().stream().reduce(Long::sum).get());
        assertEquals(12, (long) numberOfComponents.getResult().get(Category.MOUSE).values().stream().reduce(Long::sum).get());
        assertEquals(9, (long) numberOfComponents.getResult().get(Category.MEMORY).values().stream().reduce(Long::sum).get());
        assertEquals(20, (long) numberOfComponents.getResult().get(Category.KEYBOARD).values().stream().reduce(Long::sum).get());
        assertEquals(37, (long) numberOfComponents.getResult().get(Category.CPU).values().stream().reduce(Long::sum).get());
        assertEquals(5, (long) numberOfComponents.getResult().get(Category.GPU).values().stream().reduce(Long::sum).get());
    }

    @Test
    public void testFindCheapestComponent() {
        strategy = new FindCheapestComponent();
        context.setStrategy(strategy);
        ComputerComponent res = (ComputerComponent) context.executeStrategy(computerComponents).getResult();
        assertEquals("b5709966-76a6-48fb-ab72-18be9135230a", res.getId());
    }

    @Test
    public void testFindMostExpensiveComponentOfEachCategory() {
        strategy = new FindMostExpensiveComponentOfEachCategory();
        context.setStrategy(strategy);
        ComponentMapOutput res = (ComponentMapOutput) context.executeStrategy(computerComponents).getResult();
        List<String> expectedIds = List.of("375cfcec-9655-4c68-9afc-8c706685c883", "79b536c7-6a19-4099-96ec-5cdcb33b9548",
                "8611b32f-5efc-4452-9bfe-0f0776c63195", "abd86688-2ed5-4a16-af25-e7e2118a1af0",
                "1a4eea28-0681-4ca3-b151-b13ecce8d85d", "96fc477c-0c66-4400-9217-94817072429f",
                "ea2b9fd9-d908-4c78-84f0-201483cd91ff");

        assertEquals(expectedIds.size(), res.getResult().size());
        res.getResult().forEach((category, component) -> assertThat(expectedIds, hasItem(component.get().getId())));
    }
}
