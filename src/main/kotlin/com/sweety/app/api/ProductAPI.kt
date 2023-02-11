package com.sweety.app.api

import com.sweety.app.models.Product
import com.sweety.app.models.Size
import com.sweety.app.models.Topping
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import kotlin.collections.HashMap

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
    fun getProduct(@PathVariable(value = "productID") productID:String ) : ResponseEntity<HashMap<String, Any>>{
        val sql = "select Product.*\n" +
                "from Product\n" +
                "where Product.id = '${productID}'"

        var map : HashMap<String, Any> = HashMap()

        val statement = connection.createStatement()
        val result = statement.executeQuery(sql)
        if (!result.next())
            return ResponseEntity.noContent().build()
        val product = Product(result.getString(1),result.getString(2),result.getString(3),result.getString(4),result.getString(5).toDouble(),result.getString(6))
        map["product"] = product

        val listSize = ArrayList<Size>();
        val sqlSize = "select Size.*\n" +
                "from ProductSize inner join Size on ProductSize.sizeName = Size.sizeName\n" +
                "where ProductSize.prodId = '${productID}'"
        val resultSize = statement.executeQuery(sqlSize)
        while (resultSize.next()){
            listSize.add(Size(resultSize.getString(1),resultSize.getString(2)))
        }
        map["size"] = listSize

        val listTopping = ArrayList<Topping>();
        val sqlTopping = "select Topping.*\n" +
                "from ProductTopping inner join Topping on ProductTopping.toppingId = Topping.toppingId\n" +
                "where ProductTopping.prodId = '${productID}'"
        val resultTopping = statement.executeQuery(sqlTopping)
        while (resultTopping.next()){
            listTopping.add(Topping(resultTopping.getString(1),resultTopping.getString(2),resultTopping.getString(3)))
        }
        map["topping"] = listTopping
        return ResponseEntity.ok().body(map)
    }

    @GetMapping("/product/search/{key}")
    fun searchProduct(@PathVariable(value = "key") key:String) : ResponseEntity<List<Product>> {
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