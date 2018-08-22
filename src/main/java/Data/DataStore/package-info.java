/**
 * データを実際に取得更新する処理を記述する
 * サーバからデータを取得するか、DBやキャッシュのデータを使用するかどうかもここで判断する
 * 複数のDataStoreを扱う場合はFactoryパターンを用いてRepositoryがData種別を意識しない設計にする
 */
package Data.DataStore;