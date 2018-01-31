package uk.gov.hmcts.reform.em.annotation.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class RectangleTest {

    @Test
    public void TestToString() {
        Rectangle rectangle = Rectangle.builder()
            .height((long) 10)
            .width((long) 50)
            .pointX((long) 0)
            .pointY((long) 10)
            .build();

        String expectResult = String.format(
            "{\\n\"height\": %n,\\n\"width\": %n,\\n\"x\": %n,\\n\"y\": %n\\n }",
            10,50,0,10);
        assertEquals(expectResult,rectangle.toString());
    }

}
