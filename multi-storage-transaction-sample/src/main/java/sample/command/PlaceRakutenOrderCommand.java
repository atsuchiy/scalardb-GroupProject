package sample.command;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import sample.Sample;

@Command(name = "PlaceRakutenOrder", description = "Place a Rakuten order")
public class PlaceRakutenOrderCommand implements Callable<Integer> {

  @Parameters(index = "0", paramLabel = "CUSTOMER_ID", description = "customer ID")
  private int customerId;

  @Parameters(
      index = "1",
      paramLabel = "ORDERS",
      description = "orders. The format is \"<Item ID>:<Count>\"")
  private String orders;

  @Override
  public Integer call() throws Exception {
    String[] split = orders.split(":", -1);
    int itemId = Integer.parseInt(split[0]);
    int itemCount = Integer.parseInt(split[1]);

    try (Sample sample = new Sample()) {
      System.out.println(sample.placeRakutenOrder(customerId, itemId, itemCount));
    }

    return 0;
  }
}
