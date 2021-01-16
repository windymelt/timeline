# TODO

- [ ] データのハードコードをやめる
  - [ ] データをDBに格納する
    - [ ] DBドライバを選択する(ScalikeJDBC?) / ORMはいったん使わない
      - ScalikeJDBC 3 (requires Java SE 8 or higher)
    - [ ] MySQLがDocker Composeで起動できるようにする
    - [ ] SQL Schema
    - [ ] DB migration scriptを用意する
    - [ ] コネクションプールを用意する
    - [ ] Cake Patternの中でInfra. 層から自由にSQLを発行できるような仕組みにする
    - [ ] Infra. 層を実装する
- [ ] ログ基盤を整える
  - [ ] ログライブラリ検討する（依存でSLF4Jがある）

## 技術選定基準

- ロッククライミング定理
  - 「離す腕は1本だけ」 ---- チャレンジは同時に1つしかしない，あとは既知の技術を採用する