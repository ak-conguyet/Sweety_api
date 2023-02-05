package com.sweety.app.api

import com.sweety.app.models.Product
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController()
class ProductAPI {
    var connection = DatabaseConnection().connection
    @GetMapping("/product/category/{categoryID}")
    fun getProductByCategory(@PathVariable(value = "categoryID") categoryID:String) : ResponseEntity<List<Product>> {
        val sql = "select Product.*\n" +
                "from Product inner join Category on Product.category = Category.id\n" +
                "where Product.category = '${categoryID}'"

        val statement = connection.createStatement()
        val result = statement.executeQuery(sql)
        var list = ArrayList<Product>()
        while (result.next()){
            val product = Product(result.getString(1),result.getString(2),result.getString(3),result.getString(4),result.getString(5).toDouble(),result.getString(6))
            list.add(product)
        }
        return ResponseEntity.ok().body(list)
    }

    @GetMapping("/product/{productID}")
    fun getProduct(@PathVariable(value = "productID") productID:String ) : ResponseEntity<Product>{
        val sql = "select Product.*\n" +
                "from Product\n" +
                "where Product.id = '${productID}'"

        val statement = connection.createStatement()
        val result = statement.executeQuery(sql)

        if (!result.next())
            return ResponseEntity.noContent().build()
        val product = Product(result.getString(1),result.getString(2),result.getString(3),result.getString(4),result.getString(5).toDouble(),result.getString(6))
        return ResponseEntity.ok().body(product)
    }

    @GetMapping("/product/search/{key}")
    fun searchProduct(@PathVariable(value = "key") key:String) : ResponseEntity<List<Product>> {
        print(key+"dsd")
        val sql = "select *\n" +
                "from Product\n" +
                "where Product.prodName like N'%${key}%' or Product.prodDescription like N'%${key}%'"

        val statement = connection.createStatement()
        val result = statement.executeQuery(sql)
        var list = ArrayList<Product>()
        while (result.next()){
            val product = Product(result.getString(1),result.getString(2),result.getString(3),result.getString(4),result.getString(5).toDouble(),result.getString(6))
            list.add(product)
        }

        return ResponseEntity.ok().body(list)
    }

}