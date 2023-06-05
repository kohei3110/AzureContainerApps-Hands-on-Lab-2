namespace Aspnet.Frontend.Ui.Models;

public class Product
{
    public int ProductId { get; set; }
    public string CategoryName { get; set; } = string.Empty;
    public string ProductName { get; set; } = string.Empty;
    public string Color { get; set; } = string.Empty;
    public decimal StandardCost { get; set; }
    public DateTime SellStartDate { get; set; }
}