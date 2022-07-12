package sample.command;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import sample.Sample;

@Command(name = "GetAmazonOrdersBySellerId", description = "Get Amazon order information by seller ID")
public class GetAmazonOrdersBySellerIdCommand implements Callable<Integer> {

  @Parameters(index = "0", paramLabel = "SELLER_ID", description = "seller ID")
  private int sellerId;

  @Override
  public Integer call() throws Exception {
    try (Sample sample = new Sample()) {
      System.out.println(sample.getAmazonOrdersBySellerId(sellerId));
    }
    return 0;
  }
}
