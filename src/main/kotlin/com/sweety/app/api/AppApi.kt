package com.sweety.app.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import com.microsoft.sqlserver.jdbc.*;
import org.springframework.web.bind.annotation.PathVariable
import java.sql.Connection
import java.sql.DriverManager

@RestController
class AppApi {
    var connection: Connection = DriverManager.getConnection("jdbc:sqlserver://${DatabaseConfig.host}:${DatabaseConfig.port};serverName=${DatabaseConfig.serverName};databaseName=${DatabaseConfig.databaseName};trustServerCertificate=true;",DatabaseConfig.User,DatabaseConfig.Password)

    @GetMapping("/product/{id}")
    fun getProduct(@PathVariable(value = "id") productId:String) : ResponseEntity<String> {

        val sql = "select *\n" +
                "from Product\n" +
                "where id='${productId}'"

        val statement = connection.createStatement()
        val result = statement.executeQuery(sql)
        if (!result.next())
            return ResponseEntity.ok().body("Error")
        return ResponseEntity.badRequest().body(result.getString(2))
    }

    @GetMapping("/values")
    fun getA() : ResponseEntity<List<String>>{

        val sql = "select *\n" +
                "from Product"
        val statement = connection.createStatement()
        val result = statement.executeQuery(sql)
        val list = ArrayList<String>()

        while (result.next()){
            list.add("${result.getString(6)} | ${result.getString(1)} | ${result.getString(2)} | ${result.getString(3)} | ${result.getString(4)} | ${result.getString(5)} |")
        }

        return ResponseEntity.accepted().body(list)
    }

}