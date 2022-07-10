package sample.command;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import sample.Sample;

@Command(name = "GetRakutenOrder", description = "Get Rakuten order information by order ID")
public class GetRakutenOrderCommand implements Callable<Integer> {

  @Parameters(index = "0", paramLabel = "ORDER_ID", description = "order ID")
  private String orderId;

  @Override
  public Integer call() throws Exception {
    try (Sample sample = new Sample()) {
      System.out.println(sample.getRakutenOrderByOrderId(orderId));
    }
    return 0;
  }
}
