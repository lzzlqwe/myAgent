package com.example.myagent.tools;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PDFGenerationToolTest {

    @Test
    public void testGeneratePDF() {
        PDFGenerationTool tool = new PDFGenerationTool();
        String fileName = "laker.pdf";
        String content = "湖人总冠军，湖人总冠军，湖人总冠军";
        String result = tool.generatePDF(fileName, content);
        assertNotNull(result);
    }
}