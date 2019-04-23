# Tapia-sdk RV1 #

SDK RV1はTapiaで開発するためのものです。
SDK for developing on Tapia. 

### Installation ###

1.  git cloneするか、zipをダウンロードし、Android StudioでTapiaSdkSampleプロジェクトを開いてください。<br /><br />
    Download or clone the project and then open the sample app (tapia-sample) in Android Studio. 

#### Tapiaライブラリーの利用設定 ####
    This will add the Tapia library to your project. <br />
	アカウントをまだ作成していない場合は、TAPIA　Online SDKで取得して設定を進めてください。

2.  `gradle.properties`に追加します。<br />
    Tapiaのライブラリーを使用できます。<br /><br />
    Add the following to your `gradle.properties`<br />

    ```
    sdk_username=yourUserName
    sdk_password=yourPassword
    ````
    sdk_username及びskd_passwordは TAPIA Online SDKにログイン後「マイアカウント」の「アカウントステータス」ページで確認できます。<br/>
    
3.  `res\raw\sdk_license.txt`に追加します。<br />

	 sdk_licenseは、TAPIA Online SDKにログイン後「マイアカウント」の「アカウントステータス」ページで確認できます。<br/>

4.  `res\values\sdk_key.xml`に追加します。<br />

	 sdk_keyは、TAPIA Online SDKにログイン後「マイアカウント」の「アカウントステータス」ページで確認できます。<br/>

5.  アカウントをまだ作成していない場合は、TAPIA　Online SDKで取得してください。<br/>
    TAPIA Online SDKのurlは下記のとおりです。<br />
    If you don't have an account yet, please create one at :<br />
    https://tos.mjirobotics.co.jp/
  

### Release Note ###

ver1.0.0<br />
初回バージョン<br />
・ 遠隔地にあるタピアへ自動アップグレードする機能がつきました。<br />

