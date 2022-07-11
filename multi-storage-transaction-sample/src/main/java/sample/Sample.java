package sample;

import com.scalar.db.api.DistributedTransaction;
import com.scalar.db.api.DistributedTransactionManager;
import com.scalar.db.api.Get;
import com.scalar.db.api.Put;
import com.scalar.db.api.Result;
import com.scalar.db.api.Scan;
import com.scalar.db.config.DatabaseConfig;
import com.scalar.db.exception.transaction.TransactionException;
import com.scalar.db.io.Key;
import com.scalar.db.service.TransactionFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Sample implements AutoCloseable {

  private final DistributedTransactionManager manager;

  public Sample() throws IOException {
    // Create a transaction manager object
    TransactionFactory factory =
        new TransactionFactory(new DatabaseConfig(new File("database.properties")));
    manager = factory.getTransactionManager();
  }

  public void loadInitialData() throws TransactionException {
    DistributedTransaction transaction = null;
    try {
      transaction = manager.start();
      loadAmazonItemsIfNotExists(transaction, 1, "Apple", 190, 100);
      loadAmazonItemsIfNotExists(transaction, 2, "Orange", 250, 50);
      loadAmazonItemsIfNotExists(transaction, 3, "Grape", 500, 10);
      loadAmazonItemsIfNotExists(transaction, 4, "Mango", 1500, 1000);
      loadAmazonItemsIfNotExists(transaction, 5, "Melon", 1900, 300);
      loadAmazonCustomersIfNotExists(transaction, 1, "Yamada Taro", "America");
      loadAmazonCustomersIfNotExists(transaction, 2, "Sato Jiro", "Japan");
      loadAmazonCustomersIfNotExists(transaction, 3, "Mita Taro", "Japan");
      loadRakutenItemsIfNotExists(transaction, 1, "Melon", 1500, 300);
      loadRakutenItemsIfNotExists(transaction, 2, "Mango", 1000, 1000);
      loadRakutenItemsIfNotExists(transaction, 3, "Grape", 700, 10);
      loadRakutenItemsIfNotExists(transaction, 4, "Orange", 300, 50);
      loadRakutenItemsIfNotExists(transaction, 5, "Apple", 200, 100);
      loadRakutenCustomersIfNotExists(transaction, 1, "Yamada Hanako", "China");
      loadRakutenCustomersIfNotExists(transaction, 2, "Suzuki Ichiro", "Japan");
      loadWarehouseItemsIfNotExists(transaction, 1, "Apple", 100, 5, 1, 1);
      loadWarehouseItemsIfNotExists(transaction, 2, "Orange", 50, 4, 2, 1);
      loadWarehouseItemsIfNotExists(transaction, 3, "Grape", 10, 3, 3, 1);
      loadWarehouseItemsIfNotExists(transaction, 4, "Mango", 1000, 2, 4, 1);
      loadWarehouseItemsIfNotExists(transaction, 5, "Melon", 300, 1, 5, 1);
      transaction.commit();
    } catch (TransactionException e) {
      if (transaction != null) {
        // If an error occurs, abort the transaction
        transaction.abort();
      }
      throw e;
    }
  }

  private void loadAmazonItemsIfNotExists(
      DistributedTransaction transaction,
      int amazonItemId,
      String name,
      int price,
      int quantity)
      throws TransactionException {
    Optional<Result> item =
        transaction.get(
            new Get(new Key("amazon_item_id", amazonItemId))
                .forNamespace("amazon")
                .forTable("items"));
    if (!item.isPresent()) {
      transaction.put(
          new Put(new Key("amazon_item_id", amazonItemId))
              .withValue("name", name)
              .withValue("price", price)
              .withValue("quantity", quantity)
              .forNamespace("amazon")
              .forTable("items"));
    }
  }

  private void loadAmazonCustomersIfNotExists(
      DistributedTransaction transaction,
      int amazonCustomerId,
      String name,
      String address)
      throws TransactionException {
    Optional<Result> customer =
        transaction.get(
            new Get(new Key("amazon_customer_id", amazonCustomerId))
                .forNamespace("amazon")
                .forTable("customers"));
    if (!customer.isPresent()) {
      transaction.put(
          new Put(new Key("amazon_customer_id", amazonCustomerId))
              .withValue("name", name)
              .withValue("address", address)
              .forNamespace("amazon")
              .forTable("customers"));
    }
  }

  private void loadRakutenItemsIfNotExists(
      DistributedTransaction transaction,
      int rakutenItemId,
      String name,
      int price,
      int quantity)
      throws TransactionException {
    Optional<Result> item =
        transaction.get(
            new Get(new Key("rakuten_item_id", rakutenItemId))
                .forNamespace("rakuten")
                .forTable("items"));
    if (!item.isPresent()) {
      transaction.put(
          new Put(new Key("rakuten_item_id", rakutenItemId))
              .withValue("name", name)
              .withValue("price", price)
              .withValue("quantity", quantity)
              .forNamespace("rakuten")
              .forTable("items"));
    }
  }

  private void loadRakutenCustomersIfNotExists(
    DistributedTransaction transaction,
    int rakutenCustomerId,
    String name,
    String address)
    throws TransactionException {
    Optional<Result> customer =
      transaction.get(
          new Get(new Key("rakuten_customer_id", rakutenCustomerId))
              .forNamespace("rakuten")
              .forTable("customers"));
    if (!customer.isPresent()) {
    transaction.put(
        new Put(new Key("rakuten_customer_id", rakutenCustomerId))
            .withValue("name", name)
            .withValue("address", address)
            .forNamespace("rakuten")
            .forTable("customers"));
    }
  }

  private void loadWarehouseItemsIfNotExists(
      DistributedTransaction transaction, 
      int itemId, 
      String name, 
      int quantity,
      int rakutenItemId,
      int amazonItemId,
      int sellerId)
      throws TransactionException {
    Optional<Result> item =
        transaction.get(
            new Get(new Key("item_id", itemId))
            .forNamespace("warehouse")
            .forTable("items"));
    if (!item.isPresent()) {
      transaction.put(
          new Put(new Key("item_id", itemId))
              .withValue("name", name)
              .withValue("quantity", quantity)
              .withValue("rakuten_item_id", rakutenItemId)
              .withValue("amazon_item_id", amazonItemId)
              .withValue("seller_id", sellerId)
              .forNamespace("warehouse")
              .forTable("items"));
    }
  }

  public String getAmazonCustomerInfo(int customerId) throws TransactionException {
    DistributedTransaction transaction = null;
    try {
      // Start a transaction
      transaction = manager.start();

      // Retrieve the customer info for the specified customer ID from the customers table
      Optional<Result> customer =
          transaction.get(
              new Get(new Key("amazon_customer_id", customerId))
                  .forNamespace("amazon")
                  .forTable("customers"));

      if (!customer.isPresent()) {
        // If the customer info the specified customer ID doesn't exist, throw an exception
        throw new RuntimeException("Customer not found");
      }

      // Commit the transaction (even when the transaction is read-only, we need to commit)
      transaction.commit();

      // Return the customer info as a JSON format
      return String.format(
          "{\"id\": %d, \"name\": \"%s\", \"address\": %s}",
          customerId,
          customer.get().getValue("name").get().getAsString().get(),
          customer.get().getValue("address").get().getAsString().get());
    } catch (Exception e) {
      if (transaction != null) {
        // If an error occurs, abort the transaction
        transaction.abort();
      }
      throw e;
    }
  }

  public String getRakutenCustomerInfo(int customerId) throws TransactionException {
    DistributedTransaction transaction = null;
    try {
      // Start a transaction
      transaction = manager.start();

      // Retrieve the customer info for the specified customer ID from the customers table
      Optional<Result> customer =
          transaction.get(
              new Get(new Key("rakuten_customer_id", customerId))
                  .forNamespace("rakuten")
                  .forTable("customers"));

      if (!customer.isPresent()) {
        // If the customer info the specified customer ID doesn't exist, throw an exception
        throw new RuntimeException("Customer not found");
      }

      // Commit the transaction (even when the transaction is read-only, we need to commit)
      transaction.commit();

      // Return the customer info as a JSON format
      return String.format(
          "{\"id\": %d, \"name\": \"%s\", \"address\": %s}",
          customerId,
          customer.get().getValue("name").get().getAsString().get(),
          customer.get().getValue("address").get().getAsString().get());
    } catch (Exception e) {
      if (transaction != null) {
        // If an error occurs, abort the transaction
        transaction.abort();
      }
      throw e;
    }
  }

  public String placeAmazonOrder(int customerId, int itemId, int itemCount)
      throws TransactionException {

    DistributedTransaction transaction = null;
    try {
      String orderId = UUID.randomUUID().toString();

      // Start a transaction
      transaction = manager.start();

      /*
      // Put the order info into the orders table
      transaction.put(
          new Put(
              new Key("amazon_order_id", orderId),
              new Key("amazon_customer_id", customerId))
              .withValue("amazon_item_id", )
              .forNamespace("order")
              .forTable("orders"));
      */

      // Put the order info into the orders table
      transaction.put(
          new Put(
              new Key("amazon_customer_id", customerId),
              new Key("timestamp", System.currentTimeMillis()))
              .withValue("amazon_order_id", orderId)
              .forNamespace("amazon")
              .forTable("orders"));

      // Put the order info into the statements table
      transaction.put(
          new Put(new Key("amazon_order_id", orderId),
          new Key("amazon_item_id", itemId))
              .withValue("count", itemCount)
              .forNamespace("amazon")
              .forTable("statements"));

      // Retrieve the item info from the items table
      Optional<Result> amazonItem =
          transaction.get(
              new Get(new Key("amazon_item_id", itemId)).forNamespace("amazon").forTable("items"));
      if (!amazonItem.isPresent()) {
        throw new RuntimeException("Item not found");
      }
      Optional<Result> item =
          transaction.get(
              new Get(new Key("item_id", itemId)).forNamespace("warehouse").forTable("items"));
      if (!item.isPresent()) {
        throw new RuntimeException("Item not found");
      }
      Optional<Result> rakutenItem =
          transaction.get(
              new Get(new Key("rakuten_item_id", itemId)).forNamespace("rakuten").forTable("items"));

      // Check if the count exceeds the quantity
      int warehouseQuantity = item.get().getValue("quantity").get().getAsInt();
      int amazonQuantity = amazonItem.get().getValue("quantity").get().getAsInt();
      if (itemCount > warehouseQuantity || itemCount > amazonQuantity) {
        throw new RuntimeException("Count exceeded");
      }

      //Update quantity
      transaction.put(
          new Put(new Key("amazon_item_id", itemId)).withValue("quantity", amazonQuantity - itemCount)
          .forNamespace("amazon")
          .forTable("items")
      );
      transaction.put(
          new Put(new Key("item_id", itemId)).withValue("quantity", warehouseQuantity - itemCount)
          .forNamespace("warehouse")
          .forTable("items")
      );
      if (rakutenItem.isPresent()) {
          int rakutenQuantity = warehouseQuantity;
          transaction.put(
              new Put(new Key("rakuten_item_id", itemId)).withValue("quantity", rakutenQuantity - itemCount)
              .forNamespace("rakuten")
              .forTable("items")
          );
      }

      // Check if the credit total exceeds the credit limit after payment
      Optional<Result> customer =
          transaction.get(
              new Get(new Key("amazon_customer_id", customerId))
                  .forNamespace("amazon")
                  .forTable("customers"));
      if (!customer.isPresent()) {
        throw new RuntimeException("Customer not found");
      }
      /*
      int creditLimit = customer.get().getValue("credit_limit").get().getAsInt();
      int creditTotal = customer.get().getValue("credit_total").get().getAsInt();
      if (creditTotal + amount > creditLimit) {
        throw new RuntimeException("Credit limit exceeded");
      }
      */
      /*
      // Update credit_total for the customer
      transaction.put(
          new Put(new Key("customer_id", customerId))
              .withValue("credit_total", creditTotal + amount)
              .forNamespace("customer")
              .forTable("customers"));
      */

      // Commit the transaction
      transaction.commit();

      // Return the order id
      return String.format("{\"order_id\": \"%s\"}", orderId);
    } catch (Exception e) {
      if (transaction != null) {
        // If an error occurs, abort the transaction
        transaction.abort();
      }
      throw e;
    }
  }

  public String placeRakutenOrder(int customerId, int itemId, int itemCount)
      throws TransactionException {

    DistributedTransaction transaction = null;
    try {
      String orderId = UUID.randomUUID().toString();

      // Start a transaction
      transaction = manager.start();

      /*
      // Put the order info into the orders table
      transaction.put(
          new Put(
              new Key("amazon_order_id", orderId),
              new Key("amazon_customer_id", customerId))
              .withValue("amazon_item_id", )
              .forNamespace("order")
              .forTable("orders"));
      */

      // Put the order statement into the orders table
      transaction.put(
          new Put(new Key("rakuten_customer_id", customerId),
          new Key("timestamp", System.currentTimeMillis()))
              .withValue("rakuten_order_id", orderId)
              .forNamespace("rakuten")
              .forTable("orders"));

       // Put the order info into the statements table
      transaction.put(
        new Put(new Key("rakuten_order_id", orderId),
        new Key("rakuten_item_id", itemId))
            .withValue("count", itemCount)
            .forNamespace("rakuten")
            .forTable("statements"));

      // Retrieve the item info from the items table
      Optional<Result> rakutenItem =
          transaction.get(
              new Get(new Key("rakuten_item_id", itemId)).forNamespace("rakuten").forTable("items"));
      if (!rakutenItem.isPresent()) {
        throw new RuntimeException("Item not found");
      }
      Optional<Result> item =
          transaction.get(
              new Get(new Key("item_id", itemId)).forNamespace("warehouse").forTable("items"));
      if (!item.isPresent()) {
        throw new RuntimeException("Item not found");
      }
      Optional<Result> amazonItem =
          transaction.get(
              new Get(new Key("amazon_item_id", itemId)).forNamespace("amazon").forTable("items"));

      // Check if the count exceeds the quantity
      int warehouseQuantity = item.get().getValue("quantity").get().getAsInt();
      int rakutenQuantity = rakutenItem.get().getValue("quantity").get().getAsInt();
      if (itemCount > warehouseQuantity || itemCount > rakutenQuantity) {
        throw new RuntimeException("Count exceeded");
      }

      //Update quantity
      transaction.put(
          new Put(new Key("rakuten_item_id", itemId)).withValue("quantity", rakutenQuantity - itemCount)
          .forNamespace("rakuten")
          .forTable("items")
      );
      transaction.put(
          new Put(new Key("item_id", itemId)).withValue("quantity", warehouseQuantity - itemCount)
          .forNamespace("warehouse")
          .forTable("items")
      );
      if (amazonItem.isPresent()) {
          int amazonQuantity = warehouseQuantity;
          transaction.put(
              new Put(new Key("amazon_item_id", itemId)).withValue("quantity", amazonQuantity - itemCount)
              .forNamespace("amazon")
              .forTable("items")
          );
      }

      // Check if the credit total exceeds the credit limit after payment
      Optional<Result> customer =
          transaction.get(
              new Get(new Key("rakuten_customer_id", customerId))
                  .forNamespace("rakuten")
                  .forTable("customers"));
      if (!customer.isPresent()) {
        throw new RuntimeException("Customer not found");
      }
      /*
      int creditLimit = customer.get().getValue("credit_limit").get().getAsInt();
      int creditTotal = customer.get().getValue("credit_total").get().getAsInt();
      if (creditTotal + amount > creditLimit) {
        throw new RuntimeException("Credit limit exceeded");
      }
      */
      /*
      // Update credit_total for the customer
      transaction.put(
          new Put(new Key("customer_id", customerId))
              .withValue("credit_total", creditTotal + amount)
              .forNamespace("customer")
              .forTable("customers"));
      */

      // Commit the transaction
      transaction.commit();

      // Return the order id
      return String.format("{\"order_id\": \"%s\"}", orderId);
    } catch (Exception e) {
      if (transaction != null) {
        // If an error occurs, abort the transaction
        transaction.abort();
      }
      throw e;
    }
  }

  private String getAmazonOrderJson(DistributedTransaction transaction, String orderId)
      throws TransactionException {
    // Retrieve the order info for the order ID from the orders table
    Optional<Result> order =
        transaction.get(
            new Get(new Key("amazon_order_id", orderId)).forNamespace("amazon").forTable("orders"));
    if (!order.isPresent()) {
      throw new RuntimeException("Order not found");
    }
    
    int customerId = order.get().getValue("amazon_customer_id").get().getAsInt();
    // System.out.println("customerId" +  Integer.valueOf(customerId).toString());
    // Retrieve the customer info for the specified customer ID from the customers table
    Optional<Result> customer =
        transaction.get(
            new Get(new Key("amazon_customer_id", customerId))
                .forNamespace("amazon")
                .forTable("customers"));

    /* Optional<Result> state =
        transaction.get(
            new Get(new Key("amazon_order_id", orderId))
            .forNamespace("amazon")
            .forTable("statements"));
       */     
    List<Result> state =
        transaction.scan(
            new Scan(new Key("amazon_order_id", orderId))
            .forNamespace("amazon")
            .forTable("statements")); 
               
    // Retrieve the item data from the items table
    //int itemId = state.get().getValue("amazon_item_id").get().getAsInt();
    int itemId = state.get(0).getValue("amazon_item_id").get().getAsInt();
    // System.out.println("itemId" +  Integer.valueOf(itemId).toString());
    Optional<Result> item =
        transaction.get(
            new Get(new Key("amazon_item_id", itemId)).forNamespace("amazon").forTable("items"));
    if (!item.isPresent()) {
      throw new RuntimeException("Item not found");
    }

    int price = item.get().getValue("price").get().getAsInt();
    int count = state.get(0).getValue("count").get().getAsInt();

    //System.out.println("price" +  Integer.valueOf(price).toString());
    //System.out.println("count" +  Integer.valueOf(count).toString());
    String statement = String.format(
            "{\"item_id\": %d,\"item_name\": \"%s\",\"price\": %d,\"count\": %d,\"total\": %d}",
            itemId,
            item.get().getValue("name").get().getAsString().get(),
            price,
            count,
            price * count);

    // Return the order info as a JSON format
    return String.format(
        "{\"order_id\": \"%s\",\"customer_id\": %d,\"customer_name\": \"%s\",\"customer_address\": \"%s\",\"statement\": %s}",
        orderId,
        customerId,
        customer.get().getValue("name").get().getAsString().get(),
        customer.get().getValue("address").get().getAsString().get(),
        statement);
  }

  private String getRakutenOrderJson(DistributedTransaction transaction, String orderId)
      throws TransactionException {
    // Retrieve the order info for the order ID from the orders table
    Optional<Result> order =
        transaction.get(
            new Get(new Key("rakuten_order_id", orderId)).forNamespace("rakuten").forTable("orders"));
    if (!order.isPresent()) {
      throw new RuntimeException("Order not found");
    }

    int customerId = order.get().getValue("rakuten_customer_id").get().getAsInt();

    // Retrieve the customer info for the specified customer ID from the customers table
    Optional<Result> customer =
        transaction.get(
            new Get(new Key("rakuten_customer_id", customerId))
                .forNamespace("rakuten")
                .forTable("customers"));

    List<Result> state =
        transaction.scan(
            new Scan(new Key("rakuten_order_id", orderId))
                .forNamespace("rakuten")
                .forTable("statements")); 
                // Retrieve the item data from the items table
      //int itemId = order.get().getValue("rakuten_item_id").get().getAsInt();
      int itemId = state.get(0).getValue("rakuten_item_id").get().getAsInt();
      Optional<Result> item =
          transaction.get(
              new Get(new Key("rakuten_item_id", itemId)).forNamespace("rakuten").forTable("items"));
      if (!item.isPresent()) {
        throw new RuntimeException("Item not found");
      }

      int price = item.get().getValue("price").get().getAsInt();
      int count = state.get(0).getValue("count").get().getAsInt();

      String statement = String.format(
              "{\"item_id\": %d,\"item_name\": \"%s\",\"price\": %d,\"count\": %d,\"total\": %d}",
              itemId,
              item.get().getValue("name").get().getAsString().get(),
              price,
              count,
              price * count);

    // Return the order info as a JSON format
    return String.format(
        "{\"order_id\": \"%s\",\"customer_id\": %d,\"customer_name\": \"%s\",\"customer_address\": \"%s\",\"statement\": %s",
        orderId,
        customerId,
        customer.get().getValue("name").get().getAsString().get(),
        customer.get().getValue("address").get().getAsString().get(),
        statement);
  }

  public String getAmazonOrderByOrderId(String orderId) throws TransactionException {
    DistributedTransaction transaction = null;
    try {
      // Start a transaction
      transaction = manager.start();

      // Get an order JSON for the specified order ID
      String orderJson = getAmazonOrderJson(transaction, orderId);

      // Commit the transaction (even when the transaction is read-only, we need to commit)
      transaction.commit();

      // Return the order info as a JSON format
      return String.format("{\"order\": %s}", orderJson);
    } catch (Exception e) {
      if (transaction != null) {
        // If an error occurs, abort the transaction
        transaction.abort();
      }
      throw e;
    }
  }

  public String getRakutenOrderByOrderId(String orderId) throws TransactionException {
    DistributedTransaction transaction = null;
    try {
      // Start a transaction
      transaction = manager.start();

      // Get an order JSON for the specified order ID
      String orderJson = getRakutenOrderJson(transaction, orderId);

      // Commit the transaction (even when the transaction is read-only, we need to commit)
      transaction.commit();

      // Return the order info as a JSON format
      return String.format("{\"order\": %s}", orderJson);
    } catch (Exception e) {
      if (transaction != null) {
        // If an error occurs, abort the transaction
        transaction.abort();
      }
      throw e;
    }
  }

  // doesn't work well because of the error of scan
  // partition keyがamazon_customer_idではなくamazon_order_idなため使えなさそう
  public String getAmazonOrdersByCustomerId(int customerId) throws TransactionException {
    DistributedTransaction transaction = null;
    try {
      // Start a transaction
      transaction = manager.start();

      // Retrieve the order info for the customer ID from the orders table
      List<Result> orders =
          transaction.scan(
              new Scan(new Key("amazon_customer_id", customerId))
                  .forNamespace("amazon")
                  .forTable("orders"));

      // Make order JSONs for the orders of the customer
      List<String> orderJsons = new ArrayList<>();
      for (Result order : orders) {
        orderJsons.add(
            getAmazonOrderJson(transaction, order.getValue("amazon_order_id").get().getAsString().get()));
      }

      // Commit the transaction (even when the transaction is read-only, we need to commit)
      transaction.commit();

      // Return the order info as a JSON format
      return String.format("{\"order\": [%s]}", String.join(",", orderJsons));
    } catch (Exception e) {
      if (transaction != null) {
        // If an error occurs, abort the transaction
        transaction.abort();
      }
      throw e;
    }
  }

  // doesn't work well because of the error of scan
  // partition keyがrakuten_customer_idではなくrakuten_order_idなため使えなさそう
  public String getRakutenOrdersByCustomerId(int customerId) throws TransactionException {
    DistributedTransaction transaction = null;
    try {
      // Start a transaction
      transaction = manager.start();

      // Retrieve the order info for the customer ID from the orders table
      List<Result> orders =
          transaction.scan(
              new Scan(new Key("rakuten_customer_id", customerId))
                  .forNamespace("rakuten")
                  .forTable("orders"));

      // Make order JSONs for the orders of the customer
      List<String> orderJsons = new ArrayList<>();
      for (Result order : orders) {
        orderJsons.add(
            getRakutenOrderJson(transaction, order.getValue("rakuten_order_id").get().getAsString().get()));
      }

      // Commit the transaction (even when the transaction is read-only, we need to commit)
      transaction.commit();

      // Return the order info as a JSON format
      return String.format("{\"order\": [%s]}", String.join(",", orderJsons));
    } catch (Exception e) {
      if (transaction != null) {
        // If an error occurs, abort the transaction
        transaction.abort();
      }
      throw e;
    }
  }

  // doesn't work well because of the error of scan
  public String getItems(int sellerId) throws TransactionException {
    DistributedTransaction transaction = null;
    try {
      // Start a transaction
      transaction = manager.start();

      List<Result> items =
          transaction.scan(
              new Scan(new Key("seller_id", sellerId)) //ここ”seller_id"の代わりにパーティションキー”item_id"にするとエラー起きずに実行できた
                  .forNamespace("warehouse")
                  .forTable("items"));
      
      // Make order JSONs for the orders of the customer
      List<String> itemJsons = new ArrayList<>();
      int count = 1;
      for (Result item : items) {
        itemJsons.add(
            String.format("%d: {\"id\": %d, \"name\": %s, \"quantity\": %d}",
                          count++,
                          item.getValue("item_id").get().getAsInt(),
                          item.getValue("name").get().getAsString().get(),
                          item.getValue("quantity").get().getAsInt()));
      }

      // Commit the transaction (even when the transaction is read-only, we need to commit)
      transaction.commit();

      // Return the item info as a JSON format
      return String.format("{%s}", String.join(",", itemJsons));
    } catch (Exception e) {
      if (transaction != null) {
        // If an error occurs, abort the transaction
        transaction.abort();
      }
      throw e;
    }
  }

  public String setQuantity(int itemId, int quantity) throws TransactionException {
    DistributedTransaction transaction = null;
    try {
      // Start a transaction
      transaction = manager.start();

      Optional<Result> item =
          transaction.get(
              new Get(new Key("item_id", itemId)).forNamespace("warehouse").forTable("items"));
      if (!item.isPresent()) {
        throw new RuntimeException("Item not found");
      }
      transaction.put(
          new Put(new Key("item_id", itemId)).withValue("quantity", quantity)
          .forNamespace("warehouse")
          .forTable("items")
      );

      int amazonItemId = item.get().getValue("amazon_item_id").get().getAsInt();
      Optional<Result> amazonItem =
          transaction.get(
              new Get(new Key("amazon_item_id", amazonItemId)).forNamespace("amazon").forTable("items"));
      transaction.put(
          new Put(new Key("amazon_item_id", amazonItemId)).withValue("quantity", quantity)
          .forNamespace("amazon")
          .forTable("items")
      );

      int rakutenItemId = item.get().getValue("rakuten_item_id").get().getAsInt();
      Optional<Result> rakutenItem =
          transaction.get(
              new Get(new Key("rakuten_item_id", rakutenItemId)).forNamespace("rakuten").forTable("items"));
      transaction.put(
          new Put(new Key("rakuten_item_id", rakutenItemId)).withValue("quantity", quantity)
          .forNamespace("rakuten")
          .forTable("items")
      );

      transaction.commit();

      return "ok";
    } catch (Exception e) {
      if (transaction != null) {
        // If an error occurs, abort the transaction
        transaction.abort();
      }
      throw e;
    }
  }

  /*
  public void repayment(int customerId, int amount) throws TransactionException {
    DistributedTransaction transaction = null;
    try {
      // Start a transaction
      transaction = manager.start();

      // Retrieve the customer info for the specified customer ID from the customers table
      Optional<Result> customer =
          transaction.get(
              new Get(new Key("customer_id", customerId))
                  .forNamespace("customer")
                  .forTable("customers"));
      if (!customer.isPresent()) {
        throw new RuntimeException("Customer not found");
      }

      int updatedCreditLimit = customer.get().getValue("credit_total").get().getAsInt() - amount;

      // Check if over repayment or not
      if (updatedCreditLimit < 0) {
        throw new RuntimeException("Over repayment");
      }

      // Reduce credit_total in the customer
      transaction.put(
          new Put(new Key("customer_id", customerId))
              .withValue("credit_total", updatedCreditLimit)
              .forNamespace("customer")
              .forTable("customers"));

      // Commit the transaction
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        // If an error occurs, abort the transaction
        transaction.abort();
      }
      throw e;
    }
  }
  */

  @Override
  public void close() {
    manager.close();
  }
}
