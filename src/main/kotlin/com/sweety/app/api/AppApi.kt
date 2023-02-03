package com.sweety.app.api

import com.sweety.app.models.Person
import org.springframework.boot.autoconfigure.batch.BatchProperties.Jdbc
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import com.microsoft.sqlserver.jdbc.*;
import java.sql.Connection
import java.sql.DriverManager

@RestController
class AppApi {
    lateinit var connection: Connection
    constructor(){
        connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Sweety_DB;integratedSecurity=true;")
    }

    @GetMapping("/values")
    fun getA() : ResponseEntity<Person>{
        return ResponseEntity.accepted().body(Person("Kiet","22"))
    }

}