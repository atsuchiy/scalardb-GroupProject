package sample.command;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import sample.Sample;

@Command(name = "SetQuantity", description = "Set item quantity")
public class SetQuantityCommand implements Callable<Integer> {

  @Parameters(index = "0", paramLabel = "ITEM_ID", description = "ITEM ID")
  private int itemId;
  @Parameters(index = "1", paramLabel = "QUANTITY", description = "QUANTITY")
  private int quantity;

  @Override
  public Integer call() throws Exception {
    try (Sample sample = new Sample()) {
      System.out.println(sample.setQuantity(itemId, quantity));
    }
    return 0;
  }
}
