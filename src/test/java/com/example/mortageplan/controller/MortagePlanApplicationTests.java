package com.example.mortageplan.controller;

import com.example.mortageplan.entity.ProspectEntity;
import com.example.mortageplan.service.ProspectService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class MortagePlanApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private ProspectService prospectService;

    @Test
    public void prospectEntityIsCorrect() {
        Mockito.when(prospectService.getProspectById(1)).thenReturn(
                new ProspectEntity("Mock CustomerName", 1234, 0.1, 2.5)
        );
        String testName = prospectService.getProspectById(1).getCustomerName();
        assertEquals("Mock CustomerName", testName);
        assertEquals(1234.0, prospectService.getProspectById(1).getLoanTotal());
        assertEquals(0.1, prospectService.getProspectById(1).getYearlyInterest());
        assertEquals(2.5, prospectService.getProspectById(1).getTermYears());
    }
}
