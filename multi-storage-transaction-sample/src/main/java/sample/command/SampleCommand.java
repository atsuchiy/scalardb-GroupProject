package sample.command;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
    name = "bin/sample",
    description = "Sample application for Microservice Transaction",
    subcommands = {
      LoadInitialDataCommand.class,
      //PlaceOrderCommand.class,
      PlaceAmazonOrderCommand.class, //ok
      PlaceRakutenOrderCommand.class, //ok
      //GetOrderCommand.class,
      GetAmazonOrderCommand.class, //ok
      GetRakutenOrderCommand.class,
      //GetOrdersCommand.class,
      GetAmazonOrdersByCustomerIdCommand.class,
      GetRakutenOrdersByCustomerIdCommand.class,
      GetAmazonOrdersBySellerIdCommand.class,
      GetRakutenOrdersBySellerIdCommand.class,
      //GetCustomerInfoCommand.class,
      GetAmazonCustomerInfoCommand.class, //ok
      GetRakutenCustomerInfoCommand.class, //ok
      //RepaymentCommand.class

      GetOrdersCommand.class,
      GetItemsCommand.class,
      SetQuantityCommand.class
    })
public class SampleCommand implements Runnable {

  @Option(
      names = {"-h", "--help"},
      usageHelp = true,
      description = "Displays this help message and quits.",
      defaultValue = "true")
  private Boolean showHelp;

  @Override
  public void run() {
    if (showHelp) {
      CommandLine.usage(this, System.out);
    }
  }

  public static void main(String[] args) {
    new CommandLine(new SampleCommand()).execute(args);
  }
}
