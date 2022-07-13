package sample.command;

import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import sample.Sample;

@Command(name = "GetRakutenOrdersByCustomerId", description = "Get Rakuten order information by customer ID")
public class GetRakutenOrdersByCustomerIdCommand implements Callable<Integer> {

  @Parameters(index = "0", paramLabel = "CUSTOMER_ID", description = "customer ID")
  private int customerId;

  @Override
  public Integer call() throws Exception {
    try (Sample sample = new Sample()) {
      System.out.println(sample.getRakutenOrdersByCustomerId(customerId));
    }
    return 0;
  }
}
