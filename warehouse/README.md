# 必要なもの
- Docker

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

# ファイルを編集したい
ファイルを編集した後に以下を実行する．  
1. ビルドする．  
`./gradlew build`  
2. サーバーを立ち上げる．  
`./gradlew run`  
