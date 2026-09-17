package com.biblioteca;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = BibliotecaApplication.class)
public class CucumberSpringConfiguration {
}