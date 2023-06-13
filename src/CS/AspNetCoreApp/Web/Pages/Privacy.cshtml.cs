using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using System.Text.Json.Serialization;
using Dapr.Client;

namespace Aspnet.Frontend.Ui.Pages;

public class PrivacyModel : PageModel
{
    private readonly ILogger<PrivacyModel> _logger;
    private readonly DaprClient _daprClient;
    public string Message { get; set; } = string.Empty;

    public PrivacyModel(ILogger<PrivacyModel> logger, DaprClient daprClient)
    {
        _logger = logger;
        _daprClient = daprClient;
    }

    public void OnGet()
    {
    }

    public async Task<IActionResult> OnPost(int messageCount)
    {
        for (int i = 1; i <= messageCount; i++)
        {
            string orderId = Guid.NewGuid().ToString();
            DateTime orderDate = DateTime.Now;

            var order = new Order(orderId, orderDate);

            await _daprClient.PublishEventAsync<Order>("pubsub-servicebus", "orders", order);

            _logger.LogInformation($"Order {i} - {order} sent to the message bus.");
        }

        Message = $"Sent {messageCount} messages to the message bus.";

        return Page();
    }
}

public record Order([property: JsonPropertyName("orderId")] string OrderId, [property: JsonPropertyName("orderDate")] DateTime OrderDate);
