package uk.gov.hmcts.reform.em.annotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-local.yaml")
public class ApplicationTests {

    @Test
    public void sample_test() {
        assertTrue(true);
    }

}
