# Hotel Booking Api
***

![](https://img.shields.io/badge/java_11-✓-blue.svg)
![](https://img.shields.io/badge/spring_boot-✓-blue.svg)
![](https://img.shields.io/badge/H2Database-✓-blue.svg)
![](https://img.shields.io/badge/security-✓-blue.svg)
![](https://img.shields.io/badge/swagger-✓-blue.svg)
![](https://img.shields.io/badge/Thymeleaf-✓-blue.svg)
![](https://img.shields.io/badge/JUnit5-✓-blue.svg)
***
* Bu uygulamayı EtsTur Java Developer Bootcamp final projesi için yaptım.
* Readme içine Uml diyagramını, H2Console arayüzünü, Swagger arayüzünü, 
müşteriler için geliştirdiğim basit bir Thymeleaf arayüzünü ekledim. 
* Spring Security ile uygulama güvenliğini oluşturdum. 
* Servis teslerini JUnit5 ile yaptım. Servislerim için %80 method covarage sağladım. 
* Uygulamadan kısaca bahsetmem gerekirse Entity'ler ve verileri almak için Dto'larını 
oluşturdum. İşlemleri genel olarak servis katmanı içinde yapsam da sınıflara özgü olan 
metotlarımı ilgili Entity'ler içinde oluşturdum. Jpa Relationship'lerlerinden OneToMany,
ManyToOne, OneToOne ilişkilerinin hepsinin projemde olmasını istediğim için Entity'lerimi 
doğru yapıdaki ilişkiler ile birbirine bağlamaya çalıştım. 

***
* Tanıtım videosunu ilgili linkten izleyebilirsiniz
* https://www.loom.com/share/b640fc78d0d74e24b375fb8b8a09877e
***
UML DIAGRAM : 
![](src/main/resources/img/UmlDiagram.png)
***

### Rest Controller
- ChefController, CustomerController, HouseKeeperController,
- ManagerController, ReceptionistController, GeneralController

***
### Thymeleaf Controller
- CustomerRegistrationController, CustomerMainController

***
### Services
- ChefService, CustomerService, HouseKeeperService
- ManagerService, ReceptionistService, GeneralService
***

```
```

* H2Database :

![](src/main/resources/img/H2Console.png)
```
```

* Swagger :

![](src/main/resources/img/Swagger.png)

* Swagger Jpa Relationship Örnek Gösterim : 

![](src/main/resources/img/JpaRelationsRespnsBody.png)
```
```

* Thymeleaf Ekranları (Müşteriler için): 

![](src/main/resources/img/LoginPage.png)
***
![](src/main/resources/img/RegisterPage.png)
***
![](src/main/resources/img/CustomerIndexPage.png)
***
![](src/main/resources/img/NewReservationPage.png)
***
![](src/main/resources/img/ReservationPage.png)
***
![](src/main/resources/img/ReservationAfterRecpt.png)
***
![](src/main/resources/img/NewFoodOrder.png)

