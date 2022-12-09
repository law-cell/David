package com.murex.retail.operations;

import com.murex.retail.model.component.Category;
import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.parser.InventoryParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComponentOperationsTest {

    public static final String TEST_PATH = "Test.csv";
    private static List<ComputerComponent> computerComponents ;

    @BeforeAll
    public static void setup() {
        computerComponents = InventoryParser.parseInventory(TEST_PATH);
    }

    @Test
    public void testSortComponents() {
        Comparator<ComputerComponent> componentComparator = Comparator.comparing(ComputerComponent::getCategory)
                .thenComparing(ComputerComponent::getName)
                .thenComparing(ComputerComponent::getBrand);

        ComponentOperations.sortComponents(computerComponents);

        Iterator<ComputerComponent> iter = computerComponents.iterator();
        ComputerComponent current, previous = iter.next();
        while (iter.hasNext()) {
            current = iter.next();
            assertTrue(componentComparator.compare(previous, current) <= 0);
            previous = current;
        }
    }

    @Test
    public void testComputeAverageComponentPrice() {
        double avgPrice = ComponentOperations.computeAverageComponentPrice(computerComponents);
        assertEquals(145.83, avgPrice, 0.0);
    }

    @Test
    public void testComputeAverageCPUPrice() {
        double avgPrice = ComponentOperations.computeAverageCPUPrice(computerComponents);
        assertEquals(92.43, avgPrice, 0.0);
    }

    @Test
    public void testFindCheapestComponent() {
        assertEquals("b5709966-76a6-48fb-ab72-18be9135230a", ComponentOperations.findCheapestComponent(computerComponents).getId());
    }

    @Test
    public void testFindMostExpensiveOfEachComponentCategory() {
        List<String> expectedIds = List.of("375cfcec-9655-4c68-9afc-8c706685c883", "79b536c7-6a19-4099-96ec-5cdcb33b9548",
                "8611b32f-5efc-4452-9bfe-0f0776c63195", "abd86688-2ed5-4a16-af25-e7e2118a1af0",
                "1a4eea28-0681-4ca3-b151-b13ecce8d85d", "96fc477c-0c66-4400-9217-94817072429f",
                "ea2b9fd9-d908-4c78-84f0-201483cd91ff");
        Map<Category, Optional<ComputerComponent>> mostExpensiveComponents = ComponentOperations.findMostExpensiveOfEachComponentCategory(computerComponents).getResult();
        assertEquals(expectedIds.size(), mostExpensiveComponents.size());
        mostExpensiveComponents.forEach((category, component) -> assertThat(expectedIds, hasItem(component.get().getId())));
    }

    @Test
    public void testReportNumberOfComponentsGroupedByCategory() {
        Map<Category, Long> numberOfComponents = ComponentOperations.reportNumberOfComponentsGroupedByCategory(computerComponents).getResult();
        assertEquals(6, (long) numberOfComponents.get(Category.STORAGE));
        assertEquals(11, (long) numberOfComponents.get(Category.MONITOR));
        assertEquals(12, (long) numberOfComponents.get(Category.MOUSE));
        assertEquals(9, (long) numberOfComponents.get(Category.MEMORY));
        assertEquals(20, (long) numberOfComponents.get(Category.KEYBOARD));
        assertEquals(37, (long) numberOfComponents.get(Category.CPU));
        assertEquals(5, (long) numberOfComponents.get(Category.GPU));
    }

    @Test
    public void testReportNumberOfComponentsGroupedByCategoryAndBrand() {
        Map<Category, Map<String, Long>> numberOfComponents = ComponentOperations.reportNumberOfComponentsGroupedByCategoryAndBrand(computerComponents).getResult();
        assertEquals(6, (long) numberOfComponents.get(Category.STORAGE).values().stream().reduce(Long::sum).get());
        assertEquals(11, (long) numberOfComponents.get(Category.MONITOR).values().stream().reduce(Long::sum).get());
        assertEquals(12, (long) numberOfComponents.get(Category.MOUSE).values().stream().reduce(Long::sum).get());
        assertEquals(9, (long) numberOfComponents.get(Category.MEMORY).values().stream().reduce(Long::sum).get());
        assertEquals(20, (long) numberOfComponents.get(Category.KEYBOARD).values().stream().reduce(Long::sum).get());
        assertEquals(37, (long) numberOfComponents.get(Category.CPU).values().stream().reduce(Long::sum).get());
        assertEquals(5, (long) numberOfComponents.get(Category.GPU).values().stream().reduce(Long::sum).get());
    }
}
