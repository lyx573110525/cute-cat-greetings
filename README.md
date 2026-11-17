# 🐱 喵呜小站 · cute-cat-greetings

一个用 **Spring Boot 3 + Java 17** 写的迷你服务：三个接口，每个接口都返回**一张可爱的猫咪图片 + 一句软乎乎的问候**。

## 接口一览

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | `/api/greeting/cat-star` | 捧着星星的橘白小猫 |
| GET | `/api/greeting/cat-snow` | 蓝眼睛的白雪小猫 |
| GET | `/api/greeting/cat-ginger` | 奶乎乎的橘色小猫 |
| GET | `/api/greeting/{id}/image` | 直接返回图片本身（`image/jpeg`） |
| GET | `/api/greeting/random` | 随机抽一只小猫 |
| GET | `/api/greetings` | 一次拿到三只小猫 |
| GET | `/` | 演示页面，三张卡片并排展示 |

## 返回示例

```json
{
  "id": "cat-star",
  "name": "星星猫",
  "greeting": "抱一颗小星星给你，今天也要闪闪发光呀～",
  "imageUrl": "http://localhost:8080/api/greeting/cat-star/image",
  "imageBase64": "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQ...",
  "imageType": "image/jpeg"
}
```

- `imageUrl`：图片地址，浏览器可直接打开。
- `imageBase64`：完整的 data URI，前端 `<img src="{imageBase64}">` 一行就能显示，不需要再发一次请求。

## 运行

```bash
# 方式一：Maven 直接跑
mvn spring-boot:run

# 方式二：打包后运行
mvn clean package
java -jar target/cute-cat-greetings-1.0.0.jar
```

启动后打开 <http://localhost:8080> 看猫咪，或者：

```bash
curl http://localhost:8080/api/greeting/cat-star
curl -o star.jpg http://localhost:8080/api/greeting/cat-star/image
```

## 测试

```bash
mvn test
```

覆盖三个问候接口、图片接口和 404 分支。

## 目录结构

```
cute-cat-greetings
├── pom.xml
└── src
    ├── main
    │   ├── java/com/example/cutegreetings
    │   │   ├── CuteGreetingsApplication.java
    │   │   ├── controller/GreetingController.java
    │   │   └── model/Greeting.java
    │   └── resources
    │       ├── application.properties
    │       └── static
    │           ├── index.html
    │           └── images/cat-star.jpg · cat-snow.jpg · cat-ginger.jpg
    └── test/java/com/example/cutegreetings/GreetingControllerTest.java
```

## 说明

图片素材来自本地相册的三张小猫头像，已随项目一起放在 `src/main/resources/static/images/`，开箱即用。
