package com.zachholt.InventoryManagement.controllers.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition (
        info = @Info(
                contact = @Contact(
                      name = "Zach Holt",
                        email = "zacjac96@icloud.com",
                        url = "https://github.com/zachholt"
                ),
                title = "Inventory Management",
                description = "Doc site for Inventory Management API",
                version = "1.0"
        )
)
public class OpenAPIConfig {
}
