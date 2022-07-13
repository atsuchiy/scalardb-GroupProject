package sample.command;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import sample.Sample;

@Command(name = "GetItems", description = "Get item information by seller ID")
public class GetItemsCommand implements Callable<Integer> {

  @Parameters(index = "0", paramLabel = "SELLER_ID", description = "seller ID")
  private int sellerId;

  @Override
  public Integer call() throws Exception {
    try (Sample sample = new Sample()) {
      System.out.println(sample.getItems(sellerId));
    }
    return 0;
  }
}
