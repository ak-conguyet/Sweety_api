package com.sweety.app.api

import com.sweety.app.models.Category
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
class CategoryAPI {
    var connection = DatabaseConnection().connection
    @GetMapping("/category/all")
    fun getCategorye() : ResponseEntity<List<Category>>{
        var sql = "select *\n" +
                "from Category"
        val statement = connection.createStatement()
        val result = statement.executeQuery(sql)
        val list = ArrayList<Category>()
        while (result.next()){
            var category = Category(result.getString(1),result.getString(2),result.getString(3))
            list.add(category)
        }
        return ResponseEntity.ok(list)
    }



}