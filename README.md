# ADB Final Project of Team Michita Children
Show multi-storage-transaction-sample

# How to Use
フロントエンドはshop，バックエンドはwarehouseに．
multi-storage-transaction-sampleにもバックエンドの機能があるが，コマンドラインからの操作しかできない．

# DB Schima

amazon, rakutenそれぞれのnamespaceにitems,customers,orders,statementsテーブルを定義した

warehouseのnamespaceにitemsとordersテーブルを定義した


[The schema](warehouse/tools/schema.json) is as follows:

```json
{
    "amazon.items": {
        "transaction": true,
        "partition-key": [
            "amazon_item_id"
        ],
        "columns": {
            "amazon_item_id": "INT",
            "name": "TEXT",
            "price": "INT",
            "quantity": "INT"
        }
    },
    "amazon.customers": {
        "transaction": true,
        "partition-key": [
            "amazon_customer_id"
        ],
        "columns": {
            "amazon_customer_id": "INT",
            "name": "TEXT",
            "address": "TEXT"
        }
    },
    "amazon.orders": {
        "transaction": true,
        "partition-key": [
            "amazon_customer_id"
        ],
        "clustering-key": [
            "timestamp"
        ],
        "secondary-index": [
            "amazon_order_id"
        ],
        "columns": {
            "amazon_order_id": "TEXT",
            "amazon_customer_id": "INT",
            "timestamp": "BIGINT"
        }
    },
    "amazon.statements": {
        "transaction": true,
        "partition-key": [
            "amazon_order_id"
        ],
        "clustering-key": [
            "amazon_item_id"
        ],
        "columns": {
            "amazon_order_id": "TEXT",
            "amazon_item_id": "INT",
            "count": "INT"
        }
    },
    "rakuten.items": {
        "transaction": true,
        "partition-key": [
            "rakuten_item_id"
        ],
        "columns": {
            "rakuten_item_id": "INT",
            "name": "TEXT",
            "price": "INT",
            "quantity": "INT"
        }
    },
    "rakuten.customers": {
        "transaction": true,
        "partition-key": [
            "rakuten_customer_id"
        ],
        "columns": {
            "rakuten_customer_id": "INT",
            "name": "TEXT",
            "address": "TEXT"
        }
    },
    "rakuten.orders": {
        "transaction": true,
        "partition-key": [
            "rakuten_customer_id"
        ],
        "clustering-key": [
            "timestamp"
        ],
        "secondary-index": [
            "rakuten_order_id"
        ],
        "columns": {
            "rakuten_order_id": "TEXT",
            "rakuten_customer_id": "INT",
            "timestamp": "BIGINT"
        }
    },
    "rakuten.statements": {
        "transaction": true,
        "partition-key": [
            "rakuten_order_id"
        ],
        "clustering-key": [
            "rakuten_item_id"
        ],
        "columns": {
            "rakuten_order_id": "TEXT",
            "rakuten_item_id": "INT",
            "count": "INT"
        }
    },
    "warehouse.items": {
        "transaction": true,
        "partition-key": [
            "item_id"
        ],
        "secondary-index": [
            "seller_id",
            "amazon_item_id",
            "rakuten_item_id"
        ],
        "columns": {
            "item_id": "INT",
            "name": "TEXT",
            "quantity": "INT",
            "rakuten_item_id": "INT",
            "amazon_item_id": "INT",
            "seller_id": "INT"
        }
    },
    "warehouse.orders": {
        "transaction": true,
        "partition-key": [
            "order_id"
        ],
        "secondary-index": [
            "seller_id"
        ],
        "columns": {
            "order_id": "TEXT",
            "marketplace": "TEXT",
            "timestamp": "BIGINT",
            "item_id": "INT",
            "address": "TEXT",
            "count": "INT",
            "seller_id": "INT"
        }
    }
}
```
