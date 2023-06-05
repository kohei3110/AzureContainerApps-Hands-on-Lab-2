using Dapr.Client;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using System.Text.Json;
using Aspnet.Frontend.Ui.Models;

namespace Aspnet.Frontend.Ui.Pages;

public class IndexModel : PageModel
{
    private readonly ILogger<IndexModel> _logger;
    private readonly IConfiguration _configuration;
    private readonly DaprClient _daprClient;

    public IndexModel(ILogger<IndexModel> logger, IConfiguration configuration, DaprClient daprClient)
    {
        _logger = logger;
        _configuration = configuration;
        _daprClient = daprClient;
    }

    public async Task OnGet()
    {
        var appId = _configuration.GetValue<string>("AppId");
        var jsonString = string.Empty;

        List<Product> products = new List<Product>();

        //HttpClient client = new HttpClient();
        //HttpResponseMessage response = await client.GetAsync("http://localhost:5000/api/Product");

        var result = _daprClient.CreateInvokeMethodRequest(HttpMethod.Get, appId, "api/Product");
        var response = await _daprClient.InvokeMethodWithResponseAsync(result);

        if (response.IsSuccessStatusCode)
        {
            jsonString = await response.Content.ReadAsStringAsync();
            products = JsonSerializer.Deserialize<List<Product>>(jsonString);
        }

        ViewData["products"] = products;
    }
}
