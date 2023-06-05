using System.Threading.Tasks;

Console.WriteLine("The program has started.");
Console.WriteLine($"Using Task.Delay() to add 10 second delay ({DateTime.Now.ToString()})");

await Task.Delay(10000);

Console.WriteLine($"Time after wait: {DateTime.Now.ToString()}");
Console.WriteLine("The program has ended.");