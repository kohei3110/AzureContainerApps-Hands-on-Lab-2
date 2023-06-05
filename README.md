# AzureContainerApps-Hands-on-Lab-2

<br />

### ディレクトリ構成
┣ .docker  
┃   ┣ CS  
┃   ┃   ┣ dockerfile_backend_api (ASP.NET Web Api)  
┃   ┃   ┣ dockerfile_frontend_ui (ASP.NET Web アプリ)  
┃   ┃   ┗ dockerfile_job (.NET コンソール アプリ)  
┃   ┗ Java (Java アプリの dockerfile)  
┣ deploy  
┃   ┗ container-app-job.azcli (Azure Cli コマンド - ジョブの作成・削除)  
┣ src  
┃   ┣ CS (C# アプリのソース コード)  
┃   ┃   ┣ AspNetCoreApp  
┃   ┃   ┃   ┣ Api (Web Api)  
┃   ┃   ┃   ┗ Web (Web アプリ)  
┃   ┃   ┗ Job (コンソール アプリ)  
┃   ┗ Java (Java アプリのソース コード)  
┣ templates (ARM テンプレート)  
┣ Before the HOL.md (事前準備)  
┣ HOL step-by-step Guide.md (ハンズオン手順書)  
┗ README.md  