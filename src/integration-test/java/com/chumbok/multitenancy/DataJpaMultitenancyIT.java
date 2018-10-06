package com.chumbok.multitenancy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("it")
public class DataJpaMultitenancyIT {

    @Autowired
    private HelloRepository helloRepository;

    @Test
    public void shouldMultitenancyFieldsBePopulated() {

        // Given
        Hello unsaved = new Hello();
        unsaved.setId("uuid");
        unsaved.setMessage("sampleMessage");

        // When
        Hello saved = helloRepository.save(unsaved);

        // Then
        assertEquals("TestOrg", saved.getOrg());
        assertEquals("TestTenant", saved.getTenant());
    }


}
