# Coindesk Currency API 專案

這是一個使用 Spring Boot 實作的後端專案，包含以下功能：

1. 建立幣別對應表的 CRUD API。
2. 呼叫 coindesk API 並顯示原始資料。
3. 將 coindesk API 資料格式轉換並回傳。
4. 單元測試與整合測試覆蓋核心邏輯。

## 使用技術

- Java 8
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 資料庫（記憶體型）
- Maven
- JUnit 5

## 專案啟動方式

在專案根目錄執行以下指令：

```bash
mvn clean install
mvn spring-boot:run
