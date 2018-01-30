package uk.gov.hmcts.reform.em.annotation.domain;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PointTest {

    @Test
    public void testToString() {
        Point point = new Point(10,21);
        Assert.assertEquals("[10,21]",point.toString());

    }
}
