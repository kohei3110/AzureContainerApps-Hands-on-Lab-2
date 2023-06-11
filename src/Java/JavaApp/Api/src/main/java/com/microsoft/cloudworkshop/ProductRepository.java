package com.microsoft.cloudworkshop;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.microsoft.cloudworkshop.models.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("select p.ProductId, pc.Name as CategoryName, p.Name as ProductName, p.Color, p.StandardCost, p.SellStartDate from [SalesLT].[Product] p inner join[SalesLT].[ProductCategory] pc on p.ProductCategoryID = pc.ProductCategoryID where pc.Name='Mountain Bikes'")
    Iterable<Product> findAll();
}