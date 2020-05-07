# Android Architecture Components Samples Contributors

## 概要

[Android Architecture Components samples](https://github.com/googlesamples/android-architecture-components)リポジトリの contributors 一覧を表示するアプリ

## プロジェクトの構造

- app
  - アプリケーションモジュール
  - daggerなどの設定をここで行う
- core
  - アプリ全体のモジュールで使用するクラスなどを置く
  - 定数や、Extentionなど
- presentation
  - 後述するアーキテクチャのpresentation層を実装する
- domain
  - 後述するアーキテクチャのdomain層を実装する
- infra
  - 後述するアーキテクチャのinfra層を実装する

## アーキテクチャ

- 全体の構造
  - 以下の三つの層からなるレイヤードアーキテクチャを採用している
    - presentation 層
    - domain 層
    - infra 層
  - 大まかな役目としては
    - infra 層で生のデータを取得し
    - domain 層で生のデータをアプリ用に加工し
    - presentation 層で加工したデータを表示する
  - それぞれの層の繋ぎ目を RxJava によって実装している
- 各層の詳細
  - presentation 層
    - 画面に関しての処理を行う層
    - MVVM アーキテクチャを採用しており基本的に一画面につき以下がペアになる
      - Fragment
      - ViewModel
  - domain 層
    - アプリのドメインロジックについて実装する層
    - presentation層とinfra層の仲介役を担う
    - 具体的な処理としてはinfra 層で取得したデータを presentation 層で使う形(= アプリで使用する形)に加工するなど
  - infra 層
    - アプリで使用するデータを取得する層
    - API や DB からデータを取得する
    - この層では特にデータの加工などの処理は行わないで取得したデータをそのまま流す(加工などの処理は domain 層で行う)

## 使用ライブラリ

- [android jetpack](https://developer.android.com/jetpack/)
  - Foundation
    - AppCompat
    - Android KTX
    - Test
  - Architecture
    - ViewBinding
    - ViewModel
    - navigation
  - UI
    - constraintlayout
    - swiperefreshlayout
    - recyclerview
- [kotlin](https://kotlinlang.org/)
- [Material Components for Android](https://github.com/material-components/material-components-android)
- [coil](https://github.com/coil-kt/coil)
- [RxJava](https://github.com/ReactiveX/RxJava)
- [RxAndroid](https://github.com/ReactiveX/RxAndroid)
- [RxKotlin](https://github.com/ReactiveX/RxKotlin)
- [Dagger](https://github.com/google/dagger)
- [Retrofit](https://github.com/square/retrofit)
- [Gson](https://github.com/google/gson)
- [ktlint](https://github.com/pinterest/ktlint)
- [Timber](https://github.com/JakeWharton/timber)

## 開発環境

MacOS: 10.15.3
android studio: 3.6.3
kotlin: 1.3.71
