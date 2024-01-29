# Palworld Online Counter

![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/turtton/palworld-online-counter/lint-and-build.yml?style=flat-square)

パルワールドのDedicatedServerのオンライン人数を取得して、Discordに反映するBOT

## 使い方

Linuxサーバーでの使用を想定しています

PalworldのServerでは外部との疎通にRCONを使用しています。コマンドの実行には[rcon-cli](https://github.com/gorcon/rcon-cli)
のようなツールが必要です。  
このシステムにはRCONの実装は含まれていません  
[/ShowPlayers](https://tech.palworldgame.com/server-commands#:~:text=your%20current%20location-,%2Fshowplayers,-Show%20information%20on)
を実行して、プレイヤー情報を切り取るようなコマンドを用意してください(例として[.env.example](.env.example)
にワンライナーを記載しています)
> サーバー内にマルチバイト文字を含む名前のプレイヤーが存在すると、サーバーからの返答がうまく処理できない可能性があります
> これはお使いのRCONクライアントの問題です

環境変数を設定して実行すれば動きます。

### 環境変数

| 環境変数名               | 説明                     |
|---------------------|------------------------|
| DISCORD_TOKEN       | DiscordのBOTのトークン       |
| DISCORD_CHANNEL     | DiscordのBOTの出力先チャンネルID |
| SHOW_PLAYER_COMMAND | Playerの一覧を出力するコマンド     |

## ビルド方法

JDK 17以上が必要です

```shell
./gradlew shadowJar
cp ./build/libs/palworld-online-counter-*-all.jar ./palworld-online-counter.jar
```

`java -jar palworld-online-counter.jar`で実行できます(JRE 17以上が必要です)

https://github.com/turtton/palworld-online-counter/actions
から最新のビルドを選択し、Artifactsからダウンロードすることもできます