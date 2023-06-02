using System.Collections.Generic;
using System.Text.Json;
using Microsoft.Data.SqlClient;
using Microsoft.AspNetCore.Mvc;

namespace Aspnet.Backend.Api.Controllers;

[ApiController]
[Route("api/[controller]")]
public class ProductController: ControllerBase
{
    private readonly ILogger<ProductController> _logger;
    private readonly IConfiguration _configuration;

    public ProductController(ILogger<ProductController> logger, IConfiguration configuration)
    {
        _logger = logger;
        _configuration = configuration;
    }

    [HttpGet(Name = "GetProducts")]
    public string Get()
    {
        var connectionString = _configuration.GetValue<string>("SqlConnectionString");
        var query = "select " +
                        "p.ProductID, " +
                        "pc.Name as CategoryName, " + 
                        "p.Name as ProductName, " +
                        "p.Color, " +
                        "p.StandardCost, " +
                        "p.SellStartDate " +
                        "from [SalesLT].[Product] p " +
                        "inner join [SalesLT].[ProductCategory] pc on p.ProductCategoryID = pc.ProductCategoryID " +
                        "where pc.Name = 'Mountain Bikes'";

        var products = new List<Product>();

        using (var connection = new SqlConnection(connectionString))
        {
            connection.Open();

            using (var command = new SqlCommand(query, connection))
            {
                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        products.Add(
                            new Product
                            {
                                ProductId = reader.GetInt32(0),
                                CategoryName = reader.GetString(1),
                                ProductName = reader.GetString(2),
                                Color = reader.GetString(3),
                                StandardCost = reader.GetDecimal(4),
                                SellStartDate = reader.GetDateTime(5)
                            }
                        );
                    }
                }
            }
        }

        string jsonStr = JsonSerializer.Serialize(products);
        return jsonStr;
    }

    [HttpGet("{id}", Name = "GetProduct")]
    public string Get(int id)
    {
        var connectionString = _configuration.GetValue<string>("SqlConnectionString");
        var query = "select " +
                        "p.ProductID, " +
                        "pc.Name as CategoryName, " + 
                        "p.Name as ProductName, " +
                        "p.Color, " +
                        "p.StandardCost, " +
                        "p.SellStartDate " +
                        "from [SalesLT].[Product] p " +
                        "inner join [SalesLT].[ProductCategory] pc on p.ProductCategoryID = pc.ProductCategoryID " +
                        "where pc.Name = 'Mountain Bikes' " + 
                        "and p.ProductID = @id";

        var product = new Product();
        using (var connection = new SqlConnection(connectionString))
        {
            connection.Open();
            using (var command = new SqlCommand(query, connection))
            {
                command.Parameters.AddWithValue("@id", id);
                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        product.ProductId = reader.GetInt32(0);
                        product.CategoryName = reader.GetString(1);
                        product.ProductName = reader.GetString(2);
                        product.Color = reader.GetString(3);
                        product.StandardCost = reader.GetDecimal(4);
                        product.SellStartDate = reader.GetDateTime(5);
                    }
                }
            }
        }

        string jsonStr = JsonSerializer.Serialize(product);
        return jsonStr;
    }
}
