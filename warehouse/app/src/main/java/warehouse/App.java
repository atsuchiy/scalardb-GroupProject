/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package warehouse;
import static spark.Spark.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import warehouse.sparkjson.JsonTransformer;
import warehouse.controller.Sample;

public class App {
  public static void main(String[] args) {
    JsonTransformer jsonTransformer = new JsonTransformer();
    ObjectMapper mapper = new ObjectMapper();

    // /api/~~~~
    
    // initialize database
    get("/api/loadinitialdata", (req, res) -> {
        try{
            try (Sample sample = new Sample()) {
                sample.loadInitialData();
            }                
            return "Done!";
        } catch (Exception e) {
            throw(e);
        }
    }, jsonTransformer);

    // order items in amazon
    post("/api/placeamazonorder", (req, res) -> {
        try{
            String json = req.body();
            System.out.println(json);
            JsonNode node = mapper.readTree(json);
            int customerId = node.get("customer_id").asInt();
            int itemId = node.get("item_id").asInt();
            int quantity = node.get("quantity").asInt();
            String returnJson = "";
            try (Sample sample = new Sample()) {
                returnJson = sample.placeAmazonOrder(customerId, itemId, quantity);
            }
            res.type("application/json");
            return returnJson;
        } catch (Exception e) {
            throw(e);
        }
    }, jsonTransformer); 

    // order items in rakuten
    post("/api/placerakutenorder", (req, res) -> {
        try{
            String json = req.body();
            System.out.println(json);
            JsonNode node = mapper.readTree(json);
            int customerId = node.get("customer_id").asInt();
            int itemId = node.get("item_id").asInt();
            int quantity = node.get("quantity").asInt();
            String returnJson = "";
            try (Sample sample = new Sample()) {
                returnJson = sample.placeRakutenOrder(customerId, itemId, quantity);
            }
            res.type("application/json");
            return returnJson;
        } catch (Exception e) {
            throw(e);
        }
    }, jsonTransformer); 

    // get information about order_id in amazon
    post("/api/getamazonorder", (req, res) -> {
        try{
            String json = req.body();
            System.out.println(json);
            JsonNode node = mapper.readTree(json);
            String orderID = node.get("order_id").asText();
            String returnJson = "";
            try (Sample sample = new Sample()) {
                returnJson = sample.getAmazonOrderByOrderId(orderID);
            }
            res.type("application/json");
            return returnJson;
        } catch (Exception e) {
            throw(e);
        }
    }, jsonTransformer); 

    // get information about order_id in rakuten
    post("/api/getrakutenorder", (req, res) -> {
        try{
            String json = req.body();
            System.out.println(json);
            JsonNode node = mapper.readTree(json);
            String orderID = node.get("order_id").asText();
            String returnJson = "";
            try (Sample sample = new Sample()) {
                returnJson = sample.getRakutenOrderByOrderId(orderID);
            }
            res.type("application/json");
            return returnJson;
        } catch (Exception e) {
            throw(e);
        }
    }, jsonTransformer); 

    // get infomation about customer id in amazon
    post("/api/getamazoncustomerinfo", (req, res) -> {
        try{
            String json = req.body();
            System.out.println(json);
            JsonNode node = mapper.readTree(json);
            int id = node.get("id").asInt();
            String returnJson = "";
            try (Sample sample = new Sample()) {
                returnJson = sample.getAmazonCustomerInfo(id);
            }
            res.type("application/json");
            return returnJson;
        } catch (Exception e) {
            throw(e);
        }
    }, jsonTransformer); 

    // get infomation about customer id in rakuten
    post("/api/getrakutencustomerinfo", (req, res) -> {
        try{
            String json = req.body();
            System.out.println(json);
            JsonNode node = mapper.readTree(json);
            int id = node.get("id").asInt();
            String returnJson = "";
            try (Sample sample = new Sample()) {
                returnJson = sample.getRakutenCustomerInfo(id);
            }
            res.type("application/json");
            return returnJson;
        } catch (Exception e) {
            throw(e);
        }
    }, jsonTransformer); 

    // get infomation about order history of customer id in amazon
    post("/api/getamazonordersbycustomerid", (req, res) -> {
        try{
            String json = req.body();
            System.out.println(json);
            JsonNode node = mapper.readTree(json);
            int id = node.get("id").asInt();
            String returnJson = "";
            try (Sample sample = new Sample()) {
                returnJson = sample.getAmazonOrdersByCustomerId(id);
            }
            res.type("application/json");
            return returnJson;
        } catch (Exception e) {
            throw(e);
        }
    }, jsonTransformer); 

    // get infomation about order history of customer id in rakuten
    post("/api/getrakutenordersbycustomerid", (req, res) -> {
        try{
            String json = req.body();
            System.out.println(json);
            JsonNode node = mapper.readTree(json);
            int id = node.get("id").asInt();
            String returnJson = "";
            try (Sample sample = new Sample()) {
                returnJson = sample.getRakutenOrdersByCustomerId(id);
            }
            res.type("application/json");
            return returnJson;
        } catch (Exception e) {
            throw(e);
        }
    }, jsonTransformer); 

    // get item lists in warehouse.items
    get("/api/getitems", (req, res) -> {
        try{
            String json = "";
            try (Sample sample = new Sample()) {
                json = sample.getItems(1);
            }
            res.type("application/json");
            return json;
        } catch (Exception e) {
            throw(e);
        }
    }, jsonTransformer);

    // adjust inventory
    post("/api/setquantity", (req, res) -> {
        try{
            String json = req.body();
            System.out.println(json);
            JsonNode node = mapper.readTree(json);
            int id = node.get("id").asInt();
            int quantity = node.get("quantity").asInt();
            String returnJson = "";
            try (Sample sample = new Sample()) {
                returnJson = sample.setQuantity(id, quantity);
            }
            res.type("application/json");
            return returnJson;
        } catch (Exception e) {
            throw(e);
        }
    }, jsonTransformer);

    // order history in amazon
    get("/api/getamazonhistory", (req, res) -> {
        try{
            String json = "";
            try (Sample sample = new Sample()) {
                json = sample.getAmazonOrdersBySellerId(1);
            }
            res.type("application/json");
            return json;
        } catch (Exception e) {
            throw(e);
        }
    }, jsonTransformer);

    // order history in rakuten
    get("/api/getrakutenhistory", (req, res) -> {
        try{
            String json = "";
            try (Sample sample = new Sample()) {
                json = sample.getRakutenOrdersBySellerId(1);
            }
            res.type("application/json");
            return json;
        } catch (Exception e) {
            throw(e);
        }
    }, jsonTransformer);

    // test page
    get("/api/hello", (req, res) -> {
        try{
            return "hello!";
        } catch (Exception e) {
            throw(e);
        }
    }, jsonTransformer);
  }
}
