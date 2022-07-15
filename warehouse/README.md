# 目次
- [目次](#目次)
- [必要なもの](#必要なもの)
- [init](#init)
- [API](#api)
- [ファイルの編集](#ファイルの編集)
# 必要なもの
- Docker
- Java 1.8

# init
1. Docker を立ち上げる．
`warehouse % docker-compose up -d`
2. 以下を実行．
`warehouse % ./gradlew build`
3. schema.json に書かれたテーブルを作成（データの挿入はなし）．
`java -jar tools/scalardb-schema-loader-3.5.2.jar --config app/database.properties --coordinator -f tools/schema.json`
4. サーバーを立ち上げる．
`warehouse % ./gradlew run`
5. <http://localhost:4567/api/loadinitialdata> にアクセスする．  データベースにデータが挿入される．
6. 実行したい内容に合わせたページにGET or POST．
APIを実行するためのURLは全て <http://localhost:4567/api/XXX> から始まる．XXXの部分に機能に関する言葉が入る．
テストのため等で簡単にPOSTを行いたければ，[Postman](https://www.postman.com/)などを使用すれば良い．Postmanはアプリをダウンロードした方が楽．

※ 4. を実行すると実行が75％で止まるが，正常に動作している．GETやPOSTを行えば普通に動く．

※ テーブルを削除したいときは，
`java -jar tools/scalardb-schema-loader-3.5.2.jar --config app/database.properties  -f tools/schema.json -D`

# API

| Path                         | Method | 概要                                              | パラメータ名                           | 型                    | 内容                                                         |
| ---------------------------- | ------ | ------------------------------------------------- | -------------------------------------- | --------------------- | ------------------------------------------------------------ |
| loadinitialdata              | get    | tools/schema.jsonから <br >初期データをロードする | -                                      | -                     | -                                                            |
| getitems                     | get    | 倉庫の商品一覧を取得する                          | -                                      | -                     | -                                                            |
| placeamazonorder             | post   | Amazonに注文をする                                | customer_id <br> item_id <br> quantity | int <br> int <br> int | Amazonのカスタマーid <br> Amazonのアイテムid <br> 注文数量   |
| placerakutenorder            | post   | Rakutenに注文をする                               | customer_id <br> item_id <br> quantity | int <br> int <br> int | Rakutenのカスタマーid <br> Rakutenのアイテムid <br> 注文数量 |
| getamazonorder               | post   | Amazonの注文を取得する                            | order_id                               | int                   | Amazonの注文id                                               |
| getrakutenorder              | post   | Rakutenの注文を取得する                           | order_id                               | int                   | Rakutenの注文id                                              |
| getamazoncustomerinfo        | post   | Amazonの顧客情報を取得する                        | id                                     | int                   | Amazonの顧客id                                               |
| getrakutencustomerinfo       | post   | Rakutenの顧客情報を取得する                       | id                                     | int                   | Rakutenの顧客id                                              |
| getamazonordersbycustomerid  | post   | Amazonの注文一覧を表示する                        | id                                     | int                   | Amazonのカスタマーid                                         |
| getrakutenordersbycustomerid | post   | Rakutenの注文一覧を表示する                       | id                                     | int                   | Rakutenのカスタマーid                                        |
| setquantity                  | post   | 在庫数を調整する                                  | id   <br> quantity                     | int<br>int            | 商品id<br>増減させる数量                                     |
| getamazonhistory             | get    | Amazonの注文履歴を取得する                        | -                                      | -                     | -                                                            |
| getrakutenhistory            | get    | Rakutenの注文履歴を取得する                       | -                                      | -                     | -                                                            |
| hello                        | get    | 動作確認用                                        | -                                      | -                     | -                                                            |
# ファイルの編集

ファイルを編集した後に以下を実行する．
1. ビルドする．
`./gradlew build`
2. サーバーを立ち上げる．
`./gradlew run`
