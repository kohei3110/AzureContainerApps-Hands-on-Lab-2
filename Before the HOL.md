Azure Container Hands-on lab  
Mar. 2023

### 参考情報

- [名前付け規則を定義する](https://learn.microsoft.com/ja-jp/azure/cloud-adoption-framework/ready/azure-best-practices/resource-naming)

- [Azure リソースの種類に推奨される省略形](https://raw.githubusercontent.com/hiroyay-ms/AzureContainerApps-Hands-on-Lab-1/main/Before%20the%20HOL.md)

<br />

### 共通リソースの展開

<br />

[![Deploy to Azure](https://aka.ms/deploytoazurebutton)](https://portal.azure.com/#create/Microsoft.Template/uri/https%3A%2F%2Fraw.githubusercontent.com%2Fkohei3110%2FAzureContainerApps-Hands-on-Lab-2%2Fmain%2Ftemplates%2Fdeploy-vnet-hub.json)

### パラメーター

- **virtualNetwork**: 仮想ネットワーク名（2 ～ 64 文字/英数字、アンダースコア、ピリオド、およびハイフン）

- **addressPrefix**: IPv4 アドレス空間

- **subnet1**: サブネットの名前 (1)（1 ～ 80 文字/英数字、アンダースコア、ピリオド、およびハイフン）

- **subnet1Prefix**: サブネット アドレス範囲 (1)

- **bastionPrefix**: AzureBastionSubnet サブネットのアドレス範囲

- **bastionHost**: Bastion リソースの名前（1 ～ 80 文字/英数字、アンダースコア、ピリオド、およびハイフン）

※ 事前にリソース グループの作成が必要

※ 選択したリソース グループのリージョンにすべてのリソースを展開

<br />

### リソースの展開

<br />

[![Deploy to Azure](https://aka.ms/deploytoazurebutton)](https://portal.azure.com/#create/Microsoft.Template/uri/https%3A%2F%2Fraw.githubusercontent.com%2Fkohei3110%2FAzureContainerApps-Hands-on-Lab-2%2Fmain%2Ftemplates%2Fdeploy-vnet-resources.json)

### パラメーター

- **virtualNetwork**: 仮想ネットワーク名 (2 ～ 64 文字/英数字、アンダースコア、ピリオド、およびハイフン)

- **addressPrefix**: IPv4 アドレス空間

- **subnet1**: サブネットの名前 (1) (1 ～ 80 文字/英数字、アンダースコア、ピリオド、およびハイフン)

- **subnet1Prefix**: サブネット アドレス範囲 (1)

- **networkSecurityGroup-1**: ネットワーク セキュリティ グループ名 (2 ～ 64 文字/英数字、アンダースコア、ピリオド、およびハイフン)

- **networkSecurityGroup-ContainerApps**: ネットワーク セキュリティ グループ名 (2 ～ 64 文字/英数字、アンダースコア、ピリオド、およびハイフン)

- **logAnalyticsWorkspace**: Log Analytics ワークスペース名 (4 ～ 63 文字/ 英数字、およびハイフン)

- **applicationInsightsName**: Application Insights の名前 (1 ～ 255 文字/英数字、アンダースコア、ピリオド、ハイフンおよびかっこ)

- **registryName**: Azure Container Registry の名前 (5 ～ 50 文字/英数字のみ)

- **keyVaultName**: Key Vault の名前 (3 ～ 24 文字/ 英数字、およびハイフン)

- **sqlServerName**: SQL Server のサーバー名 (1 ～ 63 文字/英数字、およびハイフン)

- **sqlAdministratorLogin**: SQL Server の管理者名 (英数字のみ)

- **sqlAdministratorPassword**: パスワード (8 - 128 文字/英大文字、小文字、数字、英数字以外の文字 (! など) のうち、3 つのカテゴリの文字を含む)

※ 事前にリソース グループの作成が必要

※ リソース グループは East US, East US2, West US2, West US3, Australia East, East Asia, UK South, North Europe のいずれかを選択

※ 選択したリソース グループのリージョンにすべてのリソースを展開

※ 共通リソースで Bastion を展開した後は、リソース展開後に手動で VNet Peering を構成

※ Key Vault のキーコンテナー管理者ロールにワークショップで使用するユーザーを追加

※ Cloud Shell の起動、Azure CLI の Upgrade
