# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Superficie de practica — NO resuelvas

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs.

- `RunCucumberTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- `src/test/resources/features/order_management.feature` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- `src/test/java/com/pragma/ordersystem/features/OrderManagementTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- `src/test/java/com/pragma/ordersystem/tasks/AddProductToOrder.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- `src/test/java/com/pragma/ordersystem/questions/OrderTotal.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- `src/test/java/com/pragma/ordersystem/interactions/ChangeOrderStatus.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- `src/test/java/com/pragma/ordersystem/features/steps/OrderManagementSteps.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- `src/test/java/com/pragma/ordersystem/OrderServiceUnitTests.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- `src/test/java/com/pragma/ordersystem/PaymentGatewayUnitTests.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- `src/test/java/com/pragma/ordersystem/InventoryClientUnitTests.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/pragma/ordersystem/infrastructure/inventory/InventoryClient.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/pragma/ordersystem/questions/OrderTotal.java` — `org.openqa.selenium`: El import org.openqa.selenium.WebElement pertenece a org.openqa.selenium, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/ordersystem/domain/service/OrderService.java` — `InventoryClient.checkAvailability`: Se invoca `checkAvailability` sobre `InventoryClient`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/ordersystem/domain/service/OrderService.java` — `InventoryClient.getAvailableQuantity`: Se invoca `getAvailableQuantity` sobre `InventoryClient`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/ordersystem/domain/service/OrderService.java` — `InMemoryOrderRepository.update`: Se invoca `update` sobre `InMemoryOrderRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/ordersystem/domain/service/OrderService.java` — `InventoryClient.reserveStock`: Se invoca `reserveStock` sobre `InventoryClient`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/ordersystem/domain/service/OrderService.java` — `PaymentGateway.refundPayment`: Se invoca `refundPayment` sobre `PaymentGateway`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/ordersystem/infrastructure/payment/PaymentGateway.java` — `PaymentFailedException.getMessage`: Se invoca `getMessage` sobre `PaymentFailedException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/ordersystem/OrderServiceUnitTests.java` — `InventoryClient.checkAvailability`: Se invoca `checkAvailability` sobre `InventoryClient`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/ordersystem/PaymentGatewayUnitTests.java` — `PaymentGateway.processPaymentWithToken`: Se invoca `processPaymentWithToken` sobre `PaymentGateway`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/ordersystem/InventoryClientUnitTests.java` — `InventoryClient.checkAvailability`: Se invoca `checkAvailability` sobre `InventoryClient`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/ordersystem/InventoryClientUnitTests.java` — `InventoryClient.reduceStock`: Se invoca `reduceStock` sobre `InventoryClient`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/ordersystem/InventoryClientUnitTests.java` — `InventoryClient.getProductDetails`: Se invoca `getProductDetails` sobre `InventoryClient`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean test-compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Calidad de Software, Especialidad Automatizador, Tecnología Testing, Advanced

### Brecha de conocimiento
Aplica los conceptos fundamentales del paradigma de Programación Orientada a Objetos, tales como Clase, Herencia, Objeto, Método, Evento, Atributo y Mensaje, en un lenguaje de programación específico como Java, Python, JavaScript o TypeScript en la resolucion de problemas cotidianos

### Misión / candidato
Candidato con experiencia en automatización y calidad de software

### Reto
- Tema: implementación de programación orientada a objetos (POO)
- Seniority: advanced-l2
- Tipo: practical
- Título: Aplicación de POO en un Sistema de Gestión de Pedidos
- Tiempo estimado: 8 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Modelado de Clases — objetivo: Definir las clases y atributos necesarios para representar los conceptos del dominio. — entregable (NO resolver): Diagrama de clases y descripción de atributos y métodos.
- Fase 2: Implementación de Métodos — objetivo: Implementar los métodos definidos en la fase anterior para manejar las operaciones del sistema. — entregable (NO resolver): Código implementado de los métodos y manejo de errores.
- Fase 3: Integración y Pruebas — objetivo: Integrar las clases y métodos implementados y realizar pruebas para verificar su funcionamiento. — entregable (NO resolver): Sistema integrado y documentación de pruebas.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.pragma</groupId>
    <artifactId>order-system</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <serenity.version>4.1.0</serenity.version>
        <cucumber.version>7.15.0</cucumber.version>
        <junit.version>5.10.0</junit.version>
        <assertj.version>3.25.3</assertj.version>
        <mockito.version>5.11.0</mockito.version>
    </properties>

    <dependencies>
        <!-- Serenity BDD -->
        <dependency>
            <groupId>net.serenity-bdd</groupId>
            <artifactId>serenity-core</artifactId>
            <version>${serenity.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>net.serenity-bdd</groupId>
            <artifactId>serenity-cucumber</artifactId>
            <version>${serenity.version}</version>
            <scope>test</scope>
        </dependency>

        <!-- Cucumber -->
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-java</artifactId>
            <version>${cucumber.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-junit-platform-engine</artifactId>
            <version>${cucumber.version}</version>
            <scope>test</scope>
        </dependency>

        <!-- JUnit -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.platform</groupId>
            <artifactId>junit-platform-suite</artifactId>
            <version>1.10.0</version>
            <scope>test</scope>
        </dependency>

        <!-- AssertJ -->
        <dependency>
            <groupId>org.assertj</groupId>
            <artifactId>assertj-core</artifactId>
            <version>${assertj.version}</version>
            <scope>test</scope>
        </dependency>

        <!-- Mockito -->
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>${mockito.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.2.5</version>
                <configuration>
                    <includes>
                        <include>**/*UnitTests.java</include>
                    </includes>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-failsafe-plugin</artifactId>
                <version>3.2.5</version>
                <configuration>
                    <includes>
                        <include>**/*Test.java</include>
                        <include>**/RunCucumberTest.java</include>
                    </includes>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>integration-test</goal>
                            <goal>verify</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>net.serenity-bdd.maven.plugins</groupId>
                <artifactId>serenity-maven-plugin</artifactId>
                <version>${serenity.version}</version>
                <executions>
                    <execution>
                        <id>serenity-reports</id>
                        <phase>post-integration-test</phase>
                        <goals>
                            <goal>aggregate</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

    <profiles>
        <profile>
            <id>serenity</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <tags></tags>
            </properties>
        </profile>
    </profiles>
</project>

// === ARCHIVO: src/main/java/com/pragma/ordersystem/domain/model/Order.java ===
package com.pragma.ordersystem.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Representa un pedido realizado por un cliente.
 * Contiene información sobre los productos incluidos, el estado del pedido y el total.
 */
public class Order {
    private final String id;
    private final LocalDateTime orderDate;
    private final Customer customer;
    private final List<Product> products;
    private OrderStatus status;

    public enum OrderStatus {
        CREATED,
        PROCESSING,
        SHIPPED,
        DELIVERED,
        CANCELLED
    }

    /**
     * Constructor para crear un nuevo pedido.
     * @param customer Cliente que realiza el pedido.
     */
    public Order(Customer customer) {
        this.id = UUID.randomUUID().toString();
        this.orderDate = LocalDateTime.now();
        this.customer = Objects.requireNonNull(customer, "Customer cannot be null");
        this.products = new ArrayList<>();
        this.status = OrderStatus.CREATED;
    }

    /**
     * Constructor para recrear un pedido existente (usado en repositorios).
     * @param id Identificador del pedido.
     * @param orderDate Fecha del pedido.
     * @param customer Cliente que realizó el pedido.
     * @param products Lista de productos.
     * @param status Estado del pedido.
     */
    public Order(String id, LocalDateTime orderDate, Customer customer, List<Product> products, OrderStatus status) {
        this.id = Objects.requireNonNull(id, "Order ID cannot be null");
        this.orderDate = Objects.requireNonNull(orderDate, "Order date cannot be null");
        this.customer = Objects.requireNonNull(customer, "Customer cannot be null");
        this.products = new ArrayList<>(Objects.requireNonNull(products, "Products list cannot be null"));
        this.status = Objects.requireNonNull(status, "Order status cannot be null");
    }

    /**
     * Agrega un producto al pedido si está disponible.
     * @param product Producto a agregar.
     * @param quantity Cantidad a agregar.
     * @throws IllegalArgumentException Si el producto es nulo, la cantidad es <= 0 o el producto no está disponible.
     */
    public void addProduct(Product product, int quantity) {
        Objects.requireNonNull(product, "Product cannot be null");
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (!product.isAvailable()) {
            throw new IllegalArgumentException("Product is not available");
        }
        
        // Verificar si el producto ya existe en el pedido para actualizar cantidad
        for (Product p : products) {
            if (p.getId().equals(product.getId())) {
                p.setQuantity(p.getQuantity() + quantity);
                return;
            }
        }
        // Si no existe, agregar nuevo producto con la cantidad especificada
        Product productToAdd = new Product(product.getId(), product.getName(), product.getPrice(), quantity);
        products.add(productToAdd);
    }

    /**
     * Calcula el total del pedido sumando el precio de todos los productos.
     * @return Total del pedido.
     */
    public double calculateTotal() {
        return products.stream()                .mapToDouble(product -> product.getPrice() * product.getQuantity())
                .sum();
    }

    /**
     * Cambia el estado del pedido.
     * @param newStatus Nuevo estado.
     * @throws IllegalArgumentException Si el nuevo estado es nulo o la transición no es válida.
     */
    public void changeStatus(OrderStatus newStatus) {
        Objects.requireNonNull(newStatus, "New status cannot be null");
        
        // Validar transición de estados
        switch (this.status) {
            case CREATED:
                if (newStatus != OrderStatus.PROCESSING && newStatus != OrderStatus.CANCELLED) {
                    throw new IllegalArgumentException("Invalid status transition from CREATED");
                }
                break;
            case PROCESSING:
                if (newStatus != OrderStatus.SHIPPED && newStatus != OrderStatus.CANCELLED) {
                    throw new IllegalArgumentException("Invalid status transition from PROCESSING");
                }
                break;
            case SHIPPED:
                if (newStatus != OrderStatus.DELIVERED) {
                    throw new IllegalArgumentException("Invalid status transition from SHIPPED");
                }
                break;
            case DELIVERED:
            case CANCELLED:
                throw new IllegalArgumentException("Order is already " + this.status + " and cannot be changed");
            default:
                throw new IllegalArgumentException("Unknown current status");
        }
        this.status = newStatus;
    }

    // Getters
    public String getId() {
        return id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public OrderStatus getStatus() {
        return status;
    }
}

// === ARCHIVO: src/main/java/com/pragma/ordersystem/domain/model/Product.java ===
package com.pragma.ordersystem.domain.model;

import java.util.Objects;

/**
 * Representa un producto disponible en el inventario.
 * Contiene información sobre el precio, disponibilidad y cantidad.
 */
public class Product {
    private final String id;
    private final String name;
    private final double price;
    private int quantity;
    private boolean available;

    /**
     * Constructor para crear un nuevo producto.
     * @param id Identificador del producto.
     * @param name Nombre del producto.
     * @param price Precio del producto.
     * @param quantity Cantidad disponible.
     */
    public Product(String id, String name, double price, int quantity) {
        this.id = Objects.requireNonNull(id, "Product ID cannot be null");
        this.name = Objects.requireNonNull(name, "Product name cannot be null");
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        this.price = price;
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
        this.available = quantity > 0;
    }

    /**
     * Actualiza la cantidad disponible del producto.
     * @param newQuantity Nueva cantidad.
     * @throws IllegalArgumentException Si la nueva cantidad es negativa.
     */
    public void setQuantity(int newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = newQuantity;
        this.available = newQuantity > 0;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isAvailable() {
        return available;
    }
}

// === ARCHIVO: src/main/java/com/pragma/ordersystem/domain/model/Customer.java ===
package com.pragma.ordersystem.domain.model;

import java.util.Objects;

/**
 * Representa un cliente del sistema.
 * Contiene información básica como nombre y dirección.
 */
public class Customer {
    private final String id;
    private final String name;
    private final String address;

    /**
     * Constructor para crear un nuevo cliente.
     * @param id Identificador del cliente.
     * @param name Nombre del cliente.
     * @param address Dirección del cliente.
     */
    public Customer(String id, String name, String address) {
        this.id = Objects.requireNonNull(id, "Customer ID cannot be null");
        this.name = Objects.requireNonNull(name, "Customer name cannot be null");
        this.address = Objects.requireNonNull(address, "Customer address cannot be null");
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}

// === ARCHIVO: src/main/java/com/pragma/ordersystem/domain/exception/OrderException.java ===
package com.pragma.ordersystem.domain.exception;


import com.pragma.ordersystem.domain.model.Order;
public class OrderException extends RuntimeException {
    private final String orderId;
    private final String errorCode;

    public OrderException(String message) {
        super(message);
        this.orderId = null;
        this.errorCode = null;
    }

    public OrderException(String message, String orderId) {
        super(message);
        this.orderId = orderId;
        this.errorCode = null;
    }

    public OrderException(String message, String orderId, String errorCode) {
        super(message);
        this.orderId = orderId;
        this.errorCode = errorCode;
    }

    public OrderException(String message, Throwable cause) {
        super(message, cause);
        this.orderId = null;
        this.errorCode = null;
    }

    public OrderException(String message, String orderId, Throwable cause) {
        super(message, cause);
        this.orderId = orderId;
        this.errorCode = null;
    }

    public OrderException(String message, String orderId, String errorCode, Throwable cause) {
        super(message, cause);
        this.orderId = orderId;
        this.errorCode = errorCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("OrderException: ");
        sb.append(getMessage());
        if (orderId != null) {
            sb.append(" [Order ID: ").append(orderId).append("]");
        }
        if (errorCode != null) {
            sb.append(" [Code: ").append(errorCode).append("]");
        }
        return sb.toString();
    }
}

// === ARCHIVO: src/main/java/com/pragma/ordersystem/domain/exception/ProductNotAvailableException.java ===
package com.pragma.ordersystem.domain.exception;


import com.pragma.ordersystem.domain.model.Product;
public class ProductNotAvailableException extends OrderException {
    private final String productId;
    private final int requestedQuantity;
    private final int availableQuantity;

    public ProductNotAvailableException(String productId, int requestedQuantity, int availableQuantity) {
        super(buildMessage(productId, requestedQuantity, availableQuantity), null, "PRODUCT_NOT_AVAILABLE");
        this.productId = productId;
        this.requestedQuantity = requestedQuantity;
        this.availableQuantity = availableQuantity;
    }

    public ProductNotAvailableException(String productId, int requestedQuantity, int availableQuantity, Throwable cause) {
        super(buildMessage(productId, requestedQuantity, availableQuantity), null, "PRODUCT_NOT_AVAILABLE", cause);
        this.productId = productId;
        this.requestedQuantity = requestedQuantity;
        this.availableQuantity = availableQuantity;
    }

    private static String buildMessage(String productId, int requestedQuantity, int availableQuantity) {
        return String.format(
            "Product with ID '%s' is not available in the requested quantity. Requested: %d, Available: %d",
            productId, requestedQuantity, availableQuantity
        );
    }

    public String getProductId() {
        return productId;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    @Override
    public String toString() {
        return String.format(
            "ProductNotAvailableException{productId='%s', requested=%d, available=%d, message='%s'}",
            productId, requestedQuantity, availableQuantity, getMessage()
        );
    }
}

// === ARCHIVO: src/main/java/com/pragma/ordersystem/domain/service/OrderService.java ===
package com.pragma.ordersystem.domain.service;


import com.pragma.ordersystem.domain.model.Customer;
import com.pragma.ordersystem.domain.exception.OrderException;
import com.pragma.ordersystem.domain.exception.PaymentFailedException;
import com.pragma.ordersystem.domain.exception.ProductNotAvailableException;
import com.pragma.ordersystem.domain.model.Order;
import com.pragma.ordersystem.domain.model.Order.OrderStatus;
import com.pragma.ordersystem.domain.model.Product;
import com.pragma.ordersystem.infrastructure.inventory.InventoryClient;
import com.pragma.ordersystem.infrastructure.payment.PaymentGateway;
import com.pragma.ordersystem.infrastructure.persistence.InMemoryOrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderService {
    private final InMemoryOrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final PaymentGateway paymentGateway;
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_DELAY_MILLIS = 1000;

    public OrderService(InMemoryOrderRepository orderRepository, 
                        InventoryClient inventoryClient, 
                        PaymentGateway paymentGateway) {
        if (orderRepository == null) {
            throw new IllegalArgumentException("OrderRepository cannot be null");
        }
        if (inventoryClient == null) {
            throw new IllegalArgumentException("InventoryClient cannot be null");
        }
        if (paymentGateway == null) {
            throw new IllegalArgumentException("PaymentGateway cannot be null");
        }
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
        this.paymentGateway = paymentGateway;
    }

    public Order createOrder(String customerId, String customerName, String customerAddress) {
        if (customerId == null || customerId.isBlank()) {
            throw new OrderException("Customer ID cannot be null or empty", null, "INVALID_CUSTOMER_ID");
        }
        if (customerName == null || customerName.isBlank()) {
            throw new OrderException("Customer name cannot be null or empty", null, "INVALID_CUSTOMER_NAME");
        }
        if (customerAddress == null || customerAddress.isBlank()) {
            throw new OrderException("Customer address cannot be null or empty", null, "INVALID_ADDRESS");
        }

        String orderId = UUID.randomUUID().toString();
        LocalDateTime orderDate = LocalDateTime.now();
        var customer = new com.pragma.ordersystem.domain.model.Customer(customerId, customerName, customerAddress);
        var order = new Order(orderId, orderDate, customer, List.of(), OrderStatus.PENDING);
        
        orderRepository.save(order);
        return order;
    }

    public Order addProductToOrder(String orderId, String productId, String productName, 
                                    double price, int quantity) {
        if (orderId == null || orderId.isBlank()) {
            throw new OrderException("Order ID cannot be null or empty", null, "INVALID_ORDER_ID");
        }
        if (productId == null || productId.isBlank()) {
            throw new OrderException("Product ID cannot be null or empty", null, "INVALID_PRODUCT_ID");
        }
        if (quantity <= 0) {
            throw new OrderException("Quantity must be greater than zero", orderId, "INVALID_QUANTITY");
        }

        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new OrderException("Order not found with ID: " + orderId, orderId, "ORDER_NOT_FOUND");
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new OrderException(
                "Cannot add products to an order that is not in PENDING status. Current status: " + order.getStatus(),
                orderId,
                "INVALID_ORDER_STATUS"
            );
        }

        boolean productExistsInOrder = order.getProducts().stream()
            .anyMatch(p -> p.getId().equals(productId));

        if (productExistsInOrder) {
            Product existingProduct = order.getProducts().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow();
            
            int newQuantity = existingProduct.getQuantity() + quantity;
            
            if (!inventoryClient.checkAvailability(productId, newQuantity)) {
                throw new ProductNotAvailableException(
                    productId, 
                    newQuantity, 
                    inventoryClient.getAvailableQuantity(productId)
                );
            }
            
            existingProduct.setQuantity(newQuantity);
        } else {
            if (!inventoryClient.checkAvailability(productId, quantity)) {
                throw new ProductNotAvailableException(
                    productId, 
                    quantity, 
                    inventoryClient.getAvailableQuantity(productId)
                );
            }
            
            Product newProduct = new Product(productId, productName, price, quantity);
            order.addProduct(newProduct, quantity);
        }

        orderRepository.update(order);
        return order;
    }

    public double calculateOrderTotal(String orderId) {
        if (orderId == null || orderId.isBlank()) {
            throw new OrderException("Order ID cannot be null or empty", null, "INVALID_ORDER_ID");
        }

        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new OrderException("Order not found with ID: " + orderId, orderId, "ORDER_NOT_FOUND");
        }

        return order.calculateTotal();
    }

    public Order changeOrderStatus(String orderId, OrderStatus newStatus) {
        if (orderId == null || orderId.isBlank()) {
            throw new OrderException("Order ID cannot be null or empty", null, "INVALID_ORDER_ID");
        }
        if (newStatus == null) {
            throw new OrderException("New status cannot be null", orderId, "INVALID_STATUS");
        }

        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new OrderException("Order not found with ID: " + orderId, orderId, "ORDER_NOT_FOUND");
        }

        OrderStatus currentStatus = order.getStatus();
        
        if (!isValidStatusTransition(currentStatus, newStatus)) {
            throw new OrderException(
                String.format("Invalid status transition from %s to %s", currentStatus, newStatus),
                orderId,
                "INVALID_STATUS_TRANSITION"
            );
        }

        order.changeStatus(newStatus);
        orderRepository.update(order);
        return order;
    }

    public Order processPayment(String orderId) {
        if (orderId == null || orderId.isBlank()) {
            throw new OrderException("Order ID cannot be null or empty", null, "INVALID_ORDER_ID");
        }

        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new OrderException("Order not found with ID: " + orderId, orderId, "ORDER_NOT_FOUND");
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new OrderException(
                "Cannot process payment for an order that is not in PENDING status",
                orderId,
                "INVALID_ORDER_STATUS"
            );
        }

        double totalAmount = order.calculateTotal();
        if (totalAmount <= 0) {
            throw new OrderException(
                "Cannot process payment for an order with zero or negative total",
                orderId,
                "INVALID_ORDER_AMOUNT"
            );
        }

        boolean paymentProcessed = executePaymentWithRetry(orderId, totalAmount);
        
        if (!paymentProcessed) {
            order.changeStatus(OrderStatus.PAYMENT_FAILED);
            orderRepository.update(order);
            throw new PaymentFailedException(
                "Payment failed after maximum retry attempts",
                orderId,
                "PAYMENT_RETRY_EXHAUSTED"
            );
        }

        order.changeStatus(OrderStatus.PAID);
        orderRepository.update(order);
        
        return order;
    }

    public Order confirmOrder(String orderId) {
        if (orderId == null || orderId.isBlank()) {
            throw new OrderException("Order ID cannot be null or empty", null, "INVALID_ORDER_ID");
        }

        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new OrderException("Order not found with ID: " + orderId, orderId, "ORDER_NOT_FOUND");
        }

        if (order.getStatus() != OrderStatus.PAID) {
            throw new OrderException(
                "Cannot confirm an order that has not been paid. Current status: " + order.getStatus(),
                orderId,
                "INVALID_ORDER_STATUS"
            );
        }

        if (order.getProducts().isEmpty()) {
            throw new OrderException(
                "Cannot confirm an order with no products",
                orderId,
                "EMPTY_ORDER"
            );
        }

        boolean inventoryReserved = reserveInventory(order);
        if (!inventoryReserved) {
            throw new OrderException(
                "Failed to reserve inventory for the order",
                orderId,
                "INVENTORY_RESERVATION_FAILED"
            );
        }

        order.changeStatus(OrderStatus.CONFIRMED);
        orderRepository.update(order);
        
        return order;
    }

    public Order cancelOrder(String orderId) {
        if (orderId == null || orderId.isBlank()) {
            throw new OrderException("Order ID cannot be null or empty", null, "INVALID_ORDER_ID");
        }

        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new OrderException("Order not found with ID: " + orderId, orderId, "ORDER_NOT_FOUND");
        }

        OrderStatus currentStatus = order.getStatus();
        if (currentStatus == OrderStatus.CANCELLED) {
            throw new OrderException(
                "Order is already cancelled",
                orderId,
                "ORDER_ALREADY_CANCELLED"
            );
        }

        if (currentStatus == OrderStatus.DELIVERED) {
            throw new OrderException(
                "Cannot cancel a delivered order",
                orderId,
                "INVALID_STATUS_TRANSITION"
            );
        }

        if (currentStatus == OrderStatus.PAID || currentStatus == OrderStatus.CONFIRMED) {
            refundPayment(orderId);
        }

        order.changeStatus(OrderStatus.CANCELLED);
        orderRepository.update(order);
        
        return order;
    }

    private boolean executePaymentWithRetry(String orderId, double amount) {
        int attempts = 0;
        Exception lastException = null;
        
        while (attempts < MAX_RETRY_ATTEMPTS) {
            try {
                attempts++;
                boolean result = paymentGateway.processPayment(orderId, amount);
                if (result) {
                    return true;
                }
            } catch (Exception e) {
                lastException = e;
            }
            
            if (attempts < MAX_RETRY_ATTEMPTS) {
                try {
                    Thread.sleep(RETRY_DELAY_MILLIS * attempts);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        
        return false;
    }

    private boolean reserveInventory(Order order) {
        for (Product product : order.getProducts()) {
            boolean reserved = inventoryClient.reserveStock(product.getId(), product.getQuantity());
            if (!reserved) {
                return false;
            }
        }
        return true;
    }

    private void refundPayment(String orderId) {
        try {
            paymentGateway.refundPayment(orderId);
        } catch (Exception e) {
        }
    }

    private boolean isValidStatusTransition(OrderStatus from, OrderStatus to) {
        return switch (from) {
            case PENDING -> to == OrderStatus.PAID || to == OrderStatus.CANCELLED || to == OrderStatus.PAYMENT_FAILED;
            case PAID -> to == OrderStatus.CONFIRMED || to == OrderStatus.CANCELLED || to == OrderStatus.PAYMENT_FAILED;
            case CONFIRMED -> to == OrderStatus.SHIPPED || to == OrderStatus.CANCELLED;
            case SHIPPED -> to == OrderStatus.DELIVERED;
            case CANCELLED, PAYMENT_FAILED, DELIVERED -> false;
        };
    }

    public Order getOrderById(String orderId) {
        if (orderId == null || orderId.isBlank()) {
            throw new OrderException("Order ID cannot be null or empty", null, "INVALID_ORDER_ID");
        }
        return orderRepository.findById(orderId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        if (status == null) {
            throw new OrderException("Status cannot be null", null, "INVALID_STATUS");
        }
        return orderRepository.findByStatus(status);
    }
}

// === ARCHIVO: src/main/java/com/pragma/ordersystem/domain/exception/PaymentFailedException.java ===
package com.pragma.ordersystem.domain.exception;

/**
 * Excepción especializada que se lanza cuando el proceso de pago falla.
 * Representa errores en la comunicación con el motor de pago o datos de pago inválidos.
 */
public class PaymentFailedException extends RuntimeException {
    
    private final String orderId;
    private final String paymentMethod;
    private final int attemptNumber;
    
    /**
     * Constructor básico que inicializa la excepción con un mensaje descriptivo.
     * @param message Descripción del error de pago
     */
    public PaymentFailedException(String message) {
        super(message);
        this.orderId = null;
        this.paymentMethod = null;
        this.attemptNumber = 0;
    }
    
    /**
     * Constructor completo que incluye contexto del pago que falló.
     * @param message Descripción del error de pago
     * @param orderId Identificador del pedido asociado al pago
     * @param paymentMethod Método de pago utilizado
     * @param attemptNumber Número de intento de pago
     */
    public PaymentFailedException(String message, String orderId, String paymentMethod, int attemptNumber) {
        super(message);
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.attemptNumber = attemptNumber;
    }
    
    /**
     * Constructor que incluye la causa raíz del fallo.
     * @param message Descripción del error de pago
     * @param cause Excepción que originó el fallo
     */
    public PaymentFailedException(String message, Throwable cause) {
        super(message, cause);
        this.orderId = null;
        this.paymentMethod = null;
        this.attemptNumber = 0;
    }
    
    /**
     * Constructor completo con causa raíz y contexto.
     * @param message Descripción del error de pago
     * @param orderId Identificador del pedido asociado al pago
     * @param paymentMethod Método de pago utilizado
     * @param attemptNumber Número de intento de pago
     * @param cause Excepción que originó el fallo
     */
    public PaymentFailedException(String message, String orderId, String paymentMethod, 
                                   int attemptNumber, Throwable cause) {
        super(message, cause);
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.attemptNumber = attemptNumber;
    }
    
    /**
     * Obtiene el identificador del pedido asociado al pago fallido.
     * @return ID del pedido o null si no está disponible
     */
    public String getOrderId() {
        return orderId;
    }
    
    /**
     * Obtiene el método de pago que se intentó utilizar.
     * @return Método de pago o null si no está disponible
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    /**
     * Obtiene el número de intento de pago.
     * @return Número de intento (0 si no aplica)
     */
    public int getAttemptNumber() {
        return attemptNumber;
    }
    
    /**
     * Proporciona información detallada del fallo para logging.
     * @return String con formato legible para diagnóstico
     */
    public String getDetailedMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("PaymentFailedException: ").append(getMessage());
        if (orderId != null) {
            sb.append(" | OrderID: ").append(orderId);
        }
        if (paymentMethod != null) {
            sb.append(" | Method: ").append(paymentMethod);
        }
        if (attemptNumber > 0) {
            sb.append(" | Attempt: ").append(attemptNumber);
        }
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return getDetailedMessage();
    }
}

// === ARCHIVO: src/main/java/com/pragma/ordersystem/infrastructure/persistence/InMemoryOrderRepository.java ===
package com.pragma.ordersystem.infrastructure.persistence;

import com.pragma.ordersystem.domain.model.Order;
import com.pragma.ordersystem.domain.model.Order.OrderStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Implementación en memoria del repositorio de pedidos.
 * Simula la persistencia utilizando un ConcurrentHashMap para almacenar los pedidos.
 * Esta implementación es útil para pruebas y entornos de desarrollo sin base de datos.
 */
public class InMemoryOrderRepository {
    
    private final Map<String, Order> orders;
    private final AtomicLong idGenerator;
    
    /**
     * Constructor que inicializa el repositorio con un mapa vacío.
     */
    public InMemoryOrderRepository() {
        this.orders = new ConcurrentHashMap<>();
        this.idGenerator = new AtomicLong(System.currentTimeMillis());
    }
    
    /**
     * Guarda un nuevo pedido en el repositorio.
     * Si el pedido ya existe, actualiza sus datos.
     * @param order Pedido a guardar
     * @return El pedido guardado con su ID asignado
     */
    public Order save(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("El pedido no puede ser null");
        }
        
        String orderId = order.getId();
        if (orderId == null || orderId.isEmpty()) {
            orderId = generateOrderId();
        }
        
        Order orderToSave = new Order(
            orderId,
            order.getOrderDate(),
            order.getCustomer(),
            order.getProducts(),
            order.getStatus()
        );
        
        orders.put(orderId, orderToSave);
        return orderToSave;
    }
    
    /**
     * Busca un pedido por su identificador único.
     * @param id Identificador del pedido
     * @return Optional conteniendo el pedido si existe, o vacío si no se encuentra
     */
    public Optional<Order> findById(String id) {
        if (id == null || id.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(orders.get(id));
    }
    
    /**
     * Recupera todos los pedidos almacenados en el repositorio.
     * @return Lista inmutable de todos los pedidos
     */
    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }
    
    /**
     * Busca pedidos por estado específico.
     * @param status Estado de los pedidos a buscar
     * @return Lista de pedidos que coinciden con el estado
     */
    public List<Order> findByStatus(OrderStatus status) {
        if (status == null) {
            return List.of();
        }
        return orders.values().stream()
            .filter(order -> order.getStatus() == status)
            .collect(Collectors.toList());
    }
    
    /**
     * Busca pedidos de un cliente específico.
     * @param customerId Identificador del cliente
     * @return Lista de pedidos del cliente
     */
    public List<Order> findByCustomerId(String customerId) {
        if (customerId == null || customerId.isEmpty()) {
            return List.of();
        }
        return orders.values().stream()
            .filter(order -> order.getCustomer() != null)
            .filter(order -> customerId.equals(order.getCustomer().getId()))
            .collect(Collectors.toList());
    }
    
    /**
     * Actualiza el estado de un pedido existente.
     * @param orderId Identificador del pedido
     * @param newStatus Nuevo estado a asignar
     * @return Optional con el pedido actualizado, o vacío si no existe
     */
    public Optional<Order> updateStatus(String orderId, OrderStatus newStatus) {
        if (orderId == null || newStatus == null) {
            return Optional.empty();
        }
        
        Order existingOrder = orders.get(orderId);
        if (existingOrder == null) {
            return Optional.empty();
        }
        
        Order updatedOrder = new Order(
            existingOrder.getId(),
            existingOrder.getOrderDate(),
            existingOrder.getCustomer(),
            existingOrder.getProducts(),
            newStatus
        );
        
        orders.put(orderId, updatedOrder);
        return Optional.of(updatedOrder);
    }
    
    /**
     * Elimina un pedido del repositorio.
     * @param orderId Identificador del pedido a eliminar
     * @return true si el pedido fue eliminado, false si no existía
     */
    public boolean delete(String orderId) {
        if (orderId == null || orderId.isEmpty()) {
            return false;
        }
        return orders.remove(orderId) != null;
    }
    
    /**
     * Verifica si existe un pedido con el identificador dado.
     * @param orderId Identificador del pedido
     * @return true si existe, false en caso contrario
     */
    public boolean existsById(String orderId) {
        return orderId != null && !orderId.isEmpty() && orders.containsKey(orderId);
    }
    
    /**
     * Cuenta el número total de pedidos en el repositorio.
     * @return Cantidad de pedidos almacenados
     */
    public long count() {
        return orders.size();
    }
    
    /**
     * Limpia todos los pedidos del repositorio.
     * Útil para pruebas que requieren un estado limpio.
     */
    public void clear() {
        orders.clear();
    }
    
    /**
     * Genera un identificador único para nuevos pedidos.
     * @return String con formato "ORD-" seguido de un número secuencial
     */
    private String generateOrderId() {
        return "ORD-" + idGenerator.getAndIncrement();
    }
}

// === ARCHIVO: src/main/java/com/pragma/ordersystem/infrastructure/payment/PaymentGateway.java ===
package com.pragma.ordersystem.infrastructure.payment;


import com.pragma.ordersystem.domain.model.Order;
import com.pragma.ordersystem.domain.exception.PaymentFailedException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Cliente que simula la interacción con el motor de pago externo.
 * Implementa tolerancia a fallos mediante reintentos automáticos y manejo de excepciones.
 */
public class PaymentGateway {
    
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_DELAY_MS = 1000;
    private static final double SUCCESS_RATE = 0.85;
    
    private final Random random;
    private final AtomicInteger paymentCounter;
    private final Map<String, PaymentRecord> paymentHistory;
    private final boolean simulateFailures;
    
    /**
     * Constructor por defecto que inicializa el gateway con simulación de fallos activada.
     */
    public PaymentGateway() {
        this(true);
    }
    
    /**
     * Constructor que permite configurar la simulación de fallos.
     * @param simulateFailures true para simular fallos aleatorios, false para entorno estable
     */
    public PaymentGateway(boolean simulateFailures) {
        this.random = new Random();
        this.paymentCounter = new AtomicInteger(0);
        this.paymentHistory = new HashMap<>();
        this.simulateFailures = simulateFailures;
    }
    
    /**
     * Procesa el pago de un pedido.
     * Implementa reintentos automáticos en caso de fallo temporal.
     * @param orderId Identificador del pedido a pagar
     * @param amount Monto total a pagar
     * @param paymentMethod Método de pago (tarjeta, PayPal, etc.)
     * @return TransactionResult con el resultado del procesamiento
     * @throws PaymentFailedException si el pago falla después de todos los reintentos
     */
    public TransactionResult processPayment(String orderId, double amount, String paymentMethod) {
        validatePaymentRequest(orderId, amount, paymentMethod);
        
        int attempt = 0;
        PaymentFailedException lastException = null;
        
        while (attempt < MAX_RETRY_ATTEMPTS) {
            attempt++;
            try {
                return executePaymentAttempt(orderId, amount, paymentMethod, attempt);
            } catch (PaymentFailedException e) {
                lastException = e;
                if (!isRetryable(e) || attempt >= MAX_RETRY_ATTEMPTS) {
                    break;
                }
                waitBeforeRetry(attempt);
            }
        }
        
        PaymentFailedException finalException = new PaymentFailedException(
            "Payment failed after " + MAX_RETRY_ATTEMPTS + " attempts",
            orderId,
            paymentMethod,
            attempt,
            lastException
        );
        throw finalException;
    }
    
    /**
     * Valida los parámetros de la solicitud de pago.
     * @param orderId Identificador del pedido
     * @param amount Monto a pagar
     * @param paymentMethod Método de pago
     */
    private void validatePaymentRequest(String orderId, double amount, String paymentMethod) {
        if (orderId == null || orderId.isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be null or empty");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than zero");
        }
        if (paymentMethod == null || paymentMethod.isEmpty()) {
            throw new IllegalArgumentException("Payment method cannot be null or empty");
        }
    }
    
    /**
     * Ejecuta un intento de pago individual.
     * @param orderId Identificador del pedido
     * @param amount Monto a pagar
     * @param paymentMethod Método de pago
     * @param attemptNumber Número de intento
     * @return TransactionResult con el resultado
     */
    private TransactionResult executePaymentAttempt(String orderId, double amount, 
                                                      String paymentMethod, int attemptNumber) {
        int transactionId = paymentCounter.incrementAndGet();
        
        if (simulateFailures && random.nextDouble() > SUCCESS_RATE) {
            String failureReason = generateRandomFailureReason();
            throw new PaymentFailedException(
                failureReason,
                orderId,
                paymentMethod,
                attemptNumber
            );
        }
        
        String authorizationCode = generateAuthorizationCode(transactionId);
        PaymentRecord record = new PaymentRecord(
            transactionId,
            orderId,
            amount,
            paymentMethod,
            "APPROVED",
            authorizationCode,
            LocalDateTime.now()
        );
        
        paymentHistory.put(orderId, record);
        
        return new TransactionResult(
            true,
            transactionId,
            authorizationCode,
            "Payment processed successfully",
            null
        );
    }
    
    /**
     * Determina si un error es susceptible de reintento.
     * @param exception Excepción ocurrida
     * @return true si se debe reintentar, false si es un error permanente
     */
    private boolean isRetryable(PaymentFailedException exception) {
        String message = exception.getMessage().toLowerCase();
        return message.contains("timeout") || 
               message.contains("connection") || 
               message.contains("temporary") ||
               message.contains("retry");
    }
    
    /**
     * Espera antes de reintentar el pago.
     * @param attemptNumber Número de intento actual
     */
    private void waitBeforeRetry(int attemptNumber) {
        try {
            Thread.sleep(RETRY_DELAY_MS * attemptNumber);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Genera una razón de fallo aleatoria para simulación.
     * @return String con la razón del fallo
     */
    private String generateRandomFailureReason() {
        String[] failureReasons = {
            "Connection timeout with payment provider",
            "Temporary service unavailable",
            "Payment gateway internal error",
            "Network connectivity issue",
            "Bank gateway timeout"
        };
        return failureReasons[random.nextInt(failureReasons.length)];
    }
    
    /**
     * Genera un código de autorización único para transacciones exitosas.
     * @param transactionId ID de la transacción
     * @return Código de autorización formateado
     */
    private String generateAuthorizationCode(int transactionId) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "AUTH-" + timestamp + "-" + transactionId;
    }
    
    /**
     * Consulta el estado de un pago realizado previamente.
     * @param orderId Identificador del pedido
     * @return TransactionResult con el estado actual o null si no existe
     */
    public TransactionResult getPaymentStatus(String orderId) {
        PaymentRecord record = paymentHistory.get(orderId);
        if (record == null) {
            return null;
        }
        return new TransactionResult(
            "APPROVED".equals(record.status),
            record.transactionId,
            record.authorizationCode,
            "Status: " + record.status,
            null
        );
    }
    
    /**
     * Reinicia el contador de transacciones y el historial.
     * Útil para pruebas que requieren un estado limpio.
     */
    public void reset() {
        paymentCounter.set(0);
        paymentHistory.clear();
    }
    
    /**
     * Registro interno de un pago procesado.
     */
    private static class PaymentRecord {
        final int transactionId;
        final String orderId;
        final double amount;
        final String paymentMethod;
        final String status;
        final String authorizationCode;
        final LocalDateTime timestamp;
        
        PaymentRecord(int transactionId, String orderId, double amount, String paymentMethod,
                      String status, String authorizationCode, LocalDateTime timestamp) {
            this.transactionId = transactionId;
            this.orderId = orderId;
            this.amount = amount;
            this.paymentMethod = paymentMethod;
            this.status = status;
            this.authorizationCode = authorizationCode;
            this.timestamp = timestamp;
        }
    }
    
    /**
     * Resultado de una operación de pago.
     */
    public static class TransactionResult {
        private final boolean successful;
        private final int transactionId;
        private final String authorizationCode;
        private final String message;
        private final String errorCode;
        
        public TransactionResult(boolean successful, int transactionId, String authorizationCode,
                                  String message, String errorCode) {
            this.successful = successful;
            this.transactionId = transactionId;
            this.authorizationCode = authorizationCode;
            this.message = message;
            this.errorCode = errorCode;
        }
        
        public boolean isSuccessful() {
            return successful;
        }
        
        public int getTransactionId() {
            return transactionId;
        }
        
        public String getAuthorizationCode() {
            return authorizationCode;
        }
        
        public String getMessage() {
            return message;
        }
        
        public String getErrorCode() {
            return errorCode;
        }
    }
}

// === ARCHIVO: src/main/java/com/pragma/ordersystem/infrastructure/inventory/InventoryClient.java ===
package com.pragma.ordersystem.infrastructure.inventory;

import com.pragma.ordersystem.domain.exception.ProductNotAvailableException;
import com.pragma.ordersystem.domain.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class InventoryClient {
    private static final Logger logger = LoggerFactory.getLogger(InventoryClient.class);
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_DELAY_MS = 100;
    
    private final Map<String, Integer> inventoryCache;
    private final Random random;
    private boolean simulateFailure;
    private int currentRetryCount;
    
    public InventoryClient() {
        this.inventoryCache = new ConcurrentHashMap<>();
        this.random = new Random();
        this.simulateFailure = false;
        this.currentRetryCount = 0;
    }
    
    public boolean checkProductAvailability(Product product) {
        logger.debug("Verificando disponibilidad del producto: {}", product.getId());
        
        if (product == null || product.getId() == null) {
            throw new IllegalArgumentException("Producto inválido para verificar disponibilidad");
        }
        
        if (simulateFailure) {
            return simulateInventoryCheckWithFailure();
        }
        
        Integer availableQuantity = inventoryCache.get(product.getId());
        boolean isAvailable = availableQuantity != null && availableQuantity > 0;
        
        logger.info("Producto {} disponible: {}", product.getId(), isAvailable);
        return isAvailable;
    }
    
    public void reserveProduct(String productId, int quantity) {
        logger.info("Reservando {} unidades del producto: {}", quantity, productId);
        
        if (productId == null || quantity <= 0) {
            throw new IllegalArgumentException("Parámetros de reserva inválidos");
        }
        
        if (simulateFailure) {
            simulateInventoryFailure();
            return;
        }
        
        Integer currentStock = inventoryCache.get(productId);
        if (currentStock == null || currentStock < quantity) {
            logger.error("Stock insuficiente para producto: {}. Stock actual: {}", productId, currentStock);
            throw new ProductNotAvailableException(
                "Producto no disponible en cantidad solicitada. Producto: " + productId + 
                ", Solicitado: " + quantity + ", Disponible: " + (currentStock != null ? currentStock : 0)
            );
        }
        
        inventoryCache.put(productId, currentStock - quantity);
        logger.info("Reserva exitosa. Nuevo stock para {}: {}", productId, currentStock - quantity);
    }
    
    public void releaseProduct(String productId, int quantity) {
        logger.info("Liberando {} unidades del producto: {}", quantity, productId);
        
        if (productId == null || quantity <= 0) {
            throw new IllegalArgumentException("Parámetros de liberación inválidos");
        }
        
        if (simulateFailure) {
            simulateInventoryFailure();
            return;
        }
        
        Integer currentStock = inventoryCache.get(productId);
        int newStock = (currentStock != null ? currentStock : 0) + quantity;
        inventoryCache.put(productId, newStock);
        logger.info("Liberación exitosa. Nuevo stock para {}: {}", productId, newStock);
    }
    
    public void initializeStock(String productId, int initialQuantity) {
        logger.info("Inicializando stock para producto {}: {}", productId, initialQuantity);
        
        if (productId == null) {
            throw new IllegalArgumentException("ID de producto no puede ser null");
        }
        
        if (initialQuantity < 0) {
            throw new IllegalArgumentException("La cantidad inicial no puede ser negativa");
        }
        
        inventoryCache.put(productId, initialQuantity);
    }
    
    public int getCurrentStock(String productId) {
        if (productId == null) {
            return 0;
        }
        return inventoryCache.getOrDefault(productId, 0);
    }
    
    public void updateStock(String productId, int newQuantity) {
        logger.info("Actualizando stock para producto {}: {}", productId, newQuantity);
        
        if (productId == null) {
            throw new IllegalArgumentException("ID de producto no puede ser null");
        }
        
        if (newQuantity < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        
        inventoryCache.put(productId, newQuantity);
    }
    
    public boolean validateBulkAvailability(List<Product> products) {
        logger.debug("Validando disponibilidad masiva de {} productos", products.size());
        
        if (products == null || products.isEmpty()) {
            return false;
        }
        
        for (Product product : products) {
            if (!checkProductAvailability(product)) {
                logger.warn("Producto no disponible en validación masiva: {}", product.getId());
                return false;
            }
        }
        
        logger.info("Todos los productos disponibles para validación masiva");
        return true;
    }
    
    public void reserveBulkProducts(Map<String, Integer> productQuantities) {
        logger.info("Reservando {} productos en lote", productQuantities.size());
        
        if (productQuantities == null || productQuantities.isEmpty()) {
            throw new IllegalArgumentException("Mapa de productos vacío");
        }
        
        Map<String, Integer> reservedProducts = new HashMap<>();
        
        try {
            for (Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
                String productId = entry.getKey();
                Integer quantity = entry.getValue();
                
                if (quantity == null || quantity <= 0) {
                    throw new IllegalArgumentException("Cantidad inválida para producto: " + productId);
                }
                
                reserveProduct(productId, quantity);
                reservedProducts.put(productId, quantity);
            }
            logger.info("Reserva en lote completada exitosamente");
        } catch (Exception e) {
            logger.error("Error en reserva en lote, liberando productos reservados: {}", e.getMessage());
            rollbackBulkReservations(reservedProducts);
            throw e;
        }
    }
    
    private void rollbackBulkReservations(Map<String, Integer> reservedProducts) {
        for (Map.Entry<String, Integer> entry : reservedProducts.entrySet()) {
            try {
                releaseProduct(entry.getKey(), entry.getValue());
            } catch (Exception ex) {
                logger.error("Error en rollback para producto {}: {}", entry.getKey(), ex.getMessage());
            }
        }
    }
    
    private boolean simulateInventoryCheckWithFailure() {
        currentRetryCount++;
        logger.warn("Simulando fallo de conexión con inventario. Intento: {}/{}", currentRetryCount, MAX_RETRY_ATTEMPTS);
        
        if (currentRetryCount >= MAX_RETRY_ATTEMPTS) {
            currentRetryCount = 0;
            return random.nextBoolean();
        }
        
        try {
            Thread.sleep(RETRY_DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return simulateInventoryCheckWithFailure();
    }
    
    private void simulateInventoryFailure() {
        currentRetryCount++;
        logger.warn("Simulando fallo de inventario. Intento: {}/{}", currentRetryCount, MAX_RETRY_ATTEMPTS);
        
        if (currentRetryCount >= MAX_RETRY_ATTEMPTS) {
            currentRetryCount = 0;
            throw new RuntimeException("Error de conexión con el sistema de inventario después de " + MAX_RETRY_ATTEMPTS + " intentos");
        }
        
        try {
            Thread.sleep(RETRY_DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        simulateInventoryFailure();
    }
    
    public void enableFailureSimulation() {
        this.simulateFailure = true;
        this.currentRetryCount = 0;
        logger.info("Simulación de fallos habilitada");
    }
    
    public void disableFailureSimulation() {
        this.simulateFailure = false;
        this.currentRetryCount = 0;
        logger.info("Simulación de fallos deshabilitada");
    }
    
    public void resetInventory() {
        inventoryCache.clear();
        currentRetryCount = 0;
        logger.info("Inventario reseteado");
    }
    
    public Map<String, Integer> getInventorySnapshot() {
        return new HashMap<>(inventoryCache);
    }
}

// === ARCHIVO: RunCucumberTest.java ===
package com.pragma.ordersystem;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "json:target/cucumber-reports/cucumber.json"},
    features = "src/test/resources/features",
    glue = {"com.pragma.ordersystem.features.steps"},
    tags = "@wip"
)
public class RunCucumberTest {
}

// === ARCHIVO: README.md ===
# Sistema de Gestión de Pedidos - Pragma

## Descripción del Proyecto

Proyecto de automatización de pruebas para un sistema de gestión de pedidos de tienda en línea. Implementa el patrón **Page Object Model** con **Screenplay** para estructurar las pruebas de aceptación.

## Estructura del Proyecto

```
order-system/
├── pom.xml
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── pragma/
│   │               └── ordersystem/
│   │                   ├── domain/
│   │                   │   ├── model/
│   │                   │   │   ├── Order.java
│   │                   │   │   ├── Product.java
│   │                   │   │   └── Customer.java
│   │                   │   ├── service/
│   │                   │   │   └── OrderService.java
│   │                   │   └── exception/
│   │                   │       ├── OrderException.java
│   │                   │       ├── ProductNotAvailableException.java
│   │                   │       └── PaymentFailedException.java
│   │                   └── infrastructure/
│   │                       ├── inventory/
│   │                       │   └── InventoryClient.java
│   │                       ├── persistence/
│   │                       │   └── InMemoryOrderRepository.java
│   │                       └── payment/
│   │                           └── PaymentGateway.java
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── pragma/
│       │           └── ordersystem/
│       │               ├── features/
│       │               │   ├── steps/
│       │               │   │   └── OrderManagementSteps.java
│       │               │   └── OrderManagementTest.java
│       │               ├── tasks/
│       │               │   └── AddProductToOrder.java
│       │               ├── questions/
│       │               │   └── OrderTotal.java
│       │               └── interactions/
│       │                   └── ChangeOrderStatus.java
│       └── resources/
│           └── features/
│               └── order_management.feature
├── RunCucumberTest.java
└── README.md
```

## Tecnologías y Versiones

- **Java**: 21
- **Serenity BDD**: 4.1.0
- **Cucumber**: 7.15.0
- **JUnit 5**: 5.10.0
- **AssertJ**: 3.25.3
- **Mockito**: 5.11.0

## Requisitos Previos

- JDK 21 instalado
- Maven 3.8+ instalado
- Variables de entorno configuradas (`JAVA_HOME`, `MAVEN_HOME`)

## Configuración del Entorno

1. Verificar instalación de Java:
   ```bash
   java -version
   ```

2. Verificar instalación de Maven:
   ```bash
   mvn -version
   ```

3. Compilar el proyecto:
   ```bash
   mvn clean compile
   ```

4. Compilar y ejecutar pruebas unitarias:
   ```bash
   mvn test-compile
   ```

## Ejecución de Pruebas

### Pruebas Unitarias

Ejecutar pruebas unitarias con Maven:
```bash
mvn test
```

### Pruebas de Integración (Cucumber)

Ejecutar pruebas de aceptación con Serenity:
```bash
mvn verify
```

### Ejecutar un Escenario Específico

Para ejecutar un escenario específico por tags:
```bash
mvn verify -Dcucumber.filter.tags="@smoke"
```

Para ejecutar un escenario por nombre:
```bash
mvn verify -Dcucumber.filter.name="Agregar producto al pedido"
```

### Generación de Reportes Serenity

Los reportes se generan automáticamente durante la fase `post-integration-test`:
```bash
mvn serenity:aggregate
```

Ver reportes en: `target/site/serenity/index.html`

## Patrones de Diseño Utilizados

### Page Object Model (POM)

Separación entre la lógica de pruebas y la representación de páginas/elementos del sistema.

### Screenplay

- **Tasks**: Acciones de alto nivel que realiza el actor
- **Questions**: Consultas sobre el estado del sistema
- **Interactions**: Interacciones de bajo nivel con los elementos

### BDD con Gherkin

Escenarios escritos en lenguaje natural usando la sintaxis Gherkin:
- `Given`: Precondiciones
- `When`: Acciones realizadas
- `Then`: Resultados esperados

## Gestión de Excepciones

El sistema define excepciones personalizadas en `com.pragma.ordersystem.domain.exception`:
- `OrderException`: Errores generales del pedido
- `ProductNotAvailableException`: Producto no disponible en inventario
- `PaymentFailedException`: Fallo en el procesamiento del pago

## Integración con Servicios Externos

- **InventoryClient**: Cliente para verificación y reserva de inventario
- **PaymentGateway**: Gateway para procesamiento de pagos

## Contribución

1. Crear una rama para la nueva funcionalidad
2. Implementar los tests correspondientes
3. Ejecutar las pruebas localmente
4. Enviar pull request para revisión

## Licencia

Pragma - Todos los derechos reservados.

// === ARCHIVO: src/test/resources/features/order_management.feature ===
Feature: Gestión de Pedidos
  Como cliente del sistema de pedidos
  Quiero gestionar productos en mi pedido
  Para completar una compra en línea

  Background:
    Given que existe un cliente con id "CUST-001" llamado "Juan Pérez" con dirección "Calle Principal 123"
    And que el sistema tiene el pedido con id "ORD-001" en estado "PENDIENTE"

  Scenario: Agregar un producto disponible al pedido
    Given que el producto "PROD-001" con nombre "Laptop" y precio 1500.00 está disponible
    When el usuario agrega el producto "PROD-001" con cantidad 2 al pedido "ORD-001"
    Then el pedido "ORD-001" debe contener el producto "PROD-001" con cantidad 2
    And el total del pedido debe ser 3000.00

  Scenario: Agregar múltiples productos al pedido
    Given que el producto "PROD-001" con nombre "Laptop" y precio 1500.00 está disponible
    And que el producto "PROD-002" con nombre "Mouse" y precio 25.50 está disponible
    When el usuario agrega el producto "PROD-001" con cantidad 1 al pedido "ORD-001"
    And el usuario agrega el producto "PROD-002" con cantidad 3 al pedido "ORD-001"
    Then el pedido "ORD-001" debe contener 2 productos
    And el total del pedido debe ser 1576.50

  Scenario: Agregar producto no disponible al pedido
    Given que el producto "PROD-003" con nombre "Teclado" y precio 80.00 no está disponible
    When el usuario intenta agregar el producto "PROD-003" con cantidad 1 al pedido "ORD-001"
    Then el sistema debe mostrar el error "Producto no disponible"
    And el pedido "ORD-001" debe estar vacío

  Scenario: Calcular total del pedido vacío
    Given que el pedido "ORD-001" está vacío
    When se solicita el cálculo del total del pedido "ORD-001"
    Then el total debe ser 0.00

  Scenario: Cambiar estado del pedido a CONFIRMADO
    Given que el pedido "ORD-001" está en estado "PENDIENTE"
    When se cambia el estado del pedido "ORD-001" a "CONFIRMADO"
    Then el pedido "ORD-001" debe estar en estado "CONFIRMADO"

  Scenario: Cambiar estado del pedido a CANCELADO
    Given que el pedido "ORD-001" está en estado "CONFIRMADO"
    When se cambia el estado del pedido "ORD-001" a "CANCELADO"
    Then el pedido "ORD-001" debe estar en estado "CANCELADO"

  Scenario: Idempotencia al agregar el mismo producto
    Given que el producto "PROD-001" con nombre "Laptop" y precio 1500.00 está disponible
    And que el pedido "ORD-001" ya contiene el producto "PROD-001" con cantidad 2
    When el usuario agrega el producto "PROD-001" con cantidad 2 al pedido "ORD-001"
    Then el pedido "ORD-001" debe contener el producto "PROD-001" con cantidad 4
    And el total del pedido debe ser 6000.00

  Scenario: Procesar pago exitosamente
    Given que el pedido "ORD-001" tiene productos con total 3000.00
    And que el pago está disponible
    When se procesa el pago del pedido "ORD-001"
    Then el pago debe ser exitoso
    And el pedido debe cambiar a estado "PAGADO"

  Scenario: Fallo en el procesamiento de pago
    Given que el pedido "ORD-001" tiene productos con total 5000.00
    And que el pago falla por fondos insuficientes
    When se procesa el pago del pedido "ORD-001"
    Then debe mostrar el error "Pago fallido: fondos insuficientes"
    And el pedido debe mantener el estado "PENDIENTE"

// === ARCHIVO: src/test/java/com/pragma/ordersystem/features/OrderManagementTest.java ===
package com.pragma.ordersystem.features;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.pragma.ordersystem.features.steps"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json",
        "net.serenitybdd.cucumber. SerenityReporter"
    },
    tags = "@wip",
    monochrome = true,
    dryRun = false
)
public class OrderManagementTest {
}

// === ARCHIVO: src/test/java/com/pragma/ordersystem/tasks/AddProductToOrder.java ===
package com.pragma.ordersystem.tasks;

import com.pragma.ordersystem.domain.model.Order;
import com.pragma.ordersystem.domain.model.Product;
import com.pragma.ordersystem.infrastructure.persistence.InMemoryOrderRepository;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.annotations.Step;

import java.time.LocalDateTime;

public class AddProductToOrder implements Task {

    private final String orderId;
    private final String productId;
    private final String productName;
    private final double productPrice;
    private final int quantity;
    private final InMemoryOrderRepository orderRepository;

    public AddProductToOrder(String orderId, String productId, String productName, 
                              double productPrice, int quantity, InMemoryOrderRepository orderRepository) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.orderRepository = orderRepository;
    }

    @Override
    @Step("{0} agrega el producto #productName con cantidad #quantity al pedido #orderId")
    public <T extends Actor> void performAs(T actor) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado: " + orderId));

        Product product = new Product(productId, productName, productPrice, quantity);
        order.addProduct(product, quantity);

        orderRepository.save(order);
    }

    public static AddProductToOrderBuilder withProduct(String productId, String productName, 
                                                        double productPrice, int quantity) {
        return new AddProductToOrderBuilder(productId, productName, productPrice, quantity);
    }

    public static class AddProductToOrderBuilder {
        private final String productId;
        private final String productName;
        private final double productPrice;
        private final int quantity;
        private InMemoryOrderRepository orderRepository;
        private String orderId;

        public AddProductToOrderBuilder(String productId, String productName, 
                                         double productPrice, int quantity) {
            this.productId = productId;
            this.productName = productName;
            this.productPrice = productPrice;
            this.quantity = quantity;
        }

        public AddProductToOrderBuilder toOrder(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public AddProductToOrderBuilder withRepository(InMemoryOrderRepository orderRepository) {
            this.orderRepository = orderRepository;
            return this;
        }

        public AddProductToOrder build() {
            return new AddProductToOrder(orderId, productId, productName, 
                                         productPrice, quantity, orderRepository);
        }
    }
}

// === ARCHIVO: src/test/java/com/pragma/ordersystem/questions/OrderTotal.java ===
package com.pragma.ordersystem.questions;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.targets.TheTarget;
import org.openqa.selenium.WebElement;

public class OrderTotal {

    public static Question<Double> fromOrderSummary() {
        return new Question<Double>() {
            @Override
            public Double answeredBy(net.serenitybdd.screenplay.Actor actor) {
                return null;
            }
        };
    }

    public static Question<String> displayedInElement(TheTarget target) {
        return new Question<String>() {
            @Override
            public String answeredBy(net.serenitybdd.screenplay.Actor actor) {
                return null;
            }
        };
    }
}

// === ARCHIVO: src/test/java/com/pragma/ordersystem/interactions/ChangeOrderStatus.java ===
package com.pragma.ordersystem.interactions;

import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Actor;

public class ChangeOrderStatus implements Interaction {

    private final String newStatus;

    public ChangeOrderStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public static ChangeOrderStatus to(String status) {
        return new ChangeOrderStatus(status);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
    }
}

// === ARCHIVO: src/test/java/com/pragma/ordersystem/features/steps/OrderManagementSteps.java ===
package com.pragma.ordersystem.features.steps;

import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.TheActor;
import org.assertj.core.api.Assertions;

public class OrderManagementSteps {

    @Dado("que el cliente {string} ha iniciado un nuevo pedido")
    public void queElClienteHaIniciadoUnNuevoPedido(String customerName) {
    }

    @Cuando("agrega el producto {string} con cantidad {int}")
    public void agregaElProductoConCantidad(String productName, int quantity) {
    }

    @Cuando("el sistema procesa el pago por {double}")
    public void elSistemaProcesaElPagoPor(double amount) {
    }

    @Cuando("el sistema verifica el inventario del producto {string}")
    public void elSistemaVerificaElInventarioDelProducto(String productName) {
    }

    @Entonces("el total del pedido debe ser {double}")
    public void elTotalDelPedidoDebeSer(double expectedTotal) {
    }

    @Entonces("el estado del pedido debe ser {string}")
    public void elEstadoDelPedidoDebeSer(String expectedStatus) {
    }

    @Entonces("el sistema debe mostrar un mensaje de error indicando {string}")
    public void elSistemaDebeMostrarUnMensajeDeErrorIndicando(String errorMessage) {
    }

    @Dado("que existe un pedido con id {string} en estado {string}")
    public void queExisteUnPedidoConIdEnEstado(String orderId, String status) {
    }

    @Cuando("el administrador cambia el estado del pedido a {string}")
    public void elAdministradorCambiaElEstadoDelPedidoA(String newStatus) {
    }

    @Entonces("el pedido debe aparecer en la lista de pedidos con estado {string}")
    public void elPedidoDebeAppearEnLaListaDePedidosConEstado(String expectedStatus) {
    }
}

// === ARCHIVO: src/test/java/com/pragma/ordersystem/OrderServiceUnitTests.java ===
package com.pragma.ordersystem;





import com.pragma.ordersystem.domain.service.OrderService;
import com.pragma.ordersystem.infrastructure.inventory.InventoryClient;
import com.pragma.ordersystem.infrastructure.payment.PaymentGateway;
import com.pragma.ordersystem.infrastructure.persistence.InMemoryOrderRepository;
import com.pragma.ordersystem.domain.model.*;
import com.pragma.ordersystem.domain.exception.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OrderServiceUnitTests {

    @Mock
    private com.pragma.ordersystem.infrastructure.persistence.InMemoryOrderRepository orderRepository;

    @Mock
    private com.pragma.ordersystem.infrastructure.payment.PaymentGateway paymentGateway;

    @Mock
    private com.pragma.ordersystem.infrastructure.inventory.InventoryClient inventoryClient;

    @InjectMocks
    private com.pragma.ordersystem.domain.service.OrderService orderService;

    private Customer testCustomer;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer("CUST-001", "Juan Pérez", "Calle 123, Bogotá");
        testProduct = new Product("PROD-001", "Laptop", 1500.00, 10);
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        // Given
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        // When
        Order result = orderService.createOrder(testCustomer);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCustomer()).isEqualTo(testCustomer);
        assertThat(result.getStatus()).isEqualTo(Order.OrderStatus.PENDING);
    }

    @Test
    void shouldAddProductToOrder() {
        // Given
        Order order = new Order(testCustomer);
        when(orderRepository.findById(anyString())).thenReturn(java.util.Optional.of(order));
        when(inventoryClient.checkAvailability(anyString(), anyInt())).thenReturn(true);
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        // When
        Order result = orderService.addProductToOrder(order.getId(), testProduct, 2);

        // Then
        assertThat(result.getProducts()).hasSize(1);
    }

    @Test
    void shouldThrowExceptionWhenProductNotAvailable() {
        // Given
        Order order = new Order(testCustomer);
        when(orderRepository.findById(anyString())).thenReturn(java.util.Optional.of(order));
        when(inventoryClient.checkAvailability(anyString(), anyInt())).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> orderService.addProductToOrder(order.getId(), testProduct, 5))
            .isInstanceOf(ProductNotAvailableException.class);
    }

    @Test
    void shouldCalculateOrderTotal() {
        // Given
        Order order = new Order(testCustomer);
        order.addProduct(testProduct, 2);
        when(orderRepository.findById(anyString())).thenReturn(java.util.Optional.of(order));

        // When
        double total = orderService.calculateOrderTotal(order.getId());

        // Then
        assertThat(total).isEqualTo(3000.00);
    }

    @Test
    void shouldProcessPaymentSuccessfully() {
        // Given
        Order order = new Order(testCustomer);
        order.addProduct(testProduct, 1);
        when(orderRepository.findById(anyString())).thenReturn(java.util.Optional.of(order));
        when(paymentGateway.processPayment(anyString(), anyDouble())).thenReturn(true);
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        // When
        Order result = orderService.processPayment(order.getId());

        // Then
        assertThat(result.getStatus()).isEqualTo(Order.OrderStatus.PAID);
    }

    @Test
    void shouldThrowExceptionWhenPaymentFails() {
        // Given
        Order order = new Order(testCustomer);
        order.addProduct(testProduct, 1);
        when(orderRepository.findById(anyString())).thenReturn(java.util.Optional.of(order));
        when(paymentGateway.processPayment(anyString(), anyDouble())).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> orderService.processPayment(order.getId()))
            .isInstanceOf(PaymentFailedException.class);
    }

    @Test
    void shouldChangeOrderStatus() {
        // Given
        Order order = new Order(testCustomer);
        when(orderRepository.findById(anyString())).thenReturn(java.util.Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        // When
        Order result = orderService.changeOrderStatus(order.getId(), Order.OrderStatus.SHIPPED);

        // Then
        assertThat(result.getStatus()).isEqualTo(Order.OrderStatus.SHIPPED);
    }

    @Test
    void shouldThrowExceptionWhenOrderNotFound() {
        // Given
        when(orderRepository.findById(anyString())).thenReturn(java.util.Optional.empty());

        // When & Then
        assertThatThrownBy(() -> orderService.createOrder(testCustomer))
            .isInstanceOf(OrderException.class);
    }

    @Test
    void shouldBeIdempotentWhenAddingSameProduct() {
        // Given
        Order order = new Order(testCustomer);
        when(orderRepository.findById(anyString())).thenReturn(java.util.Optional.of(order));
        when(inventoryClient.checkAvailability(anyString(), anyInt())).thenReturn(true);
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        // When
        orderService.addProductToOrder(order.getId(), testProduct, 2);
        orderService.addProductToOrder(order.getId(), testProduct, 2);

        // Then
        verify(orderRepository, times(2)).save(any(Order.class));
    }
}

// === ARCHIVO: src/test/java/com/pragma/ordersystem/PaymentGatewayUnitTests.java ===
package com.pragma.ordersystem;


import com.pragma.ordersystem.infrastructure.payment.PaymentGateway;
import com.pragma.ordersystem.domain.exception.PaymentFailedException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PaymentGatewayUnitTests {

    @Mock
    private com.pragma.ordersystem.infrastructure.payment.PaymentGateway paymentGateway;

    @Test
    void shouldProcessPaymentSuccessfully() {
        // Given
        String orderId = "ORD-001";
        double amount = 150.00;
        when(paymentGateway.processPayment(orderId, amount)).thenReturn(true);

        // When
        boolean result = paymentGateway.processPayment(orderId, amount);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void shouldThrowExceptionWhenPaymentDeclined() {
        // Given
        String orderId = "ORD-002";
        double amount = 200.00;
        when(paymentGateway.processPayment(orderId, amount)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> {
            if (!paymentGateway.processPayment(orderId, amount)) {
                throw new PaymentFailedException("Payment declined for order: " + orderId);
            }
        }).isInstanceOf(PaymentFailedException.class)
          .hasMessageContaining("Payment declined");
    }

    @Test
    void shouldHandleNetworkFailure() {
        // Given
        String orderId = "ORD-003";
        double amount = 100.00;
        when(paymentGateway.processPayment(orderId, amount))
            .thenThrow(new RuntimeException("Network unavailable"));

        // When & Then
        assertThatThrownBy(() -> paymentGateway.processPayment(orderId, amount))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Network unavailable");
    }

    @Test
    void shouldRetryPaymentOnFailure() {
        // Given
        String orderId = "ORD-004";
        double amount = 250.00;
        when(paymentGateway.processPayment(orderId, amount))
            .thenReturn(false)
            .thenReturn(false)
            .thenReturn(true);

        // When
        boolean firstAttempt = paymentGateway.processPayment(orderId, amount);
        boolean secondAttempt = paymentGateway.processPayment(orderId, amount);
        boolean thirdAttempt = paymentGateway.processPayment(orderId, amount);

        // Then
        assertThat(firstAttempt).isFalse();
        assertThat(secondAttempt).isFalse();
        assertThat(thirdAttempt).isTrue();
    }

    @Test
    void shouldHandleInvalidAmount() {
        // Given
        String orderId = "ORD-005";
        double invalidAmount = -50.00;

        // When & Then
        assertThatThrownBy(() -> paymentGateway.processPayment(orderId, invalidAmount))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldProcessPaymentWithValidCard() {
        // Given
        String orderId = "ORD-006";
        double amount = 500.00;
        String cardToken = "tok_valid_card";
        when(paymentGateway.processPaymentWithToken(orderId, amount, cardToken)).thenReturn(true);

        // When
        boolean result = paymentGateway.processPaymentWithToken(orderId, amount, cardToken);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void shouldHandleExpiredCard() {
        // Given
        String orderId = "ORD-007";
        double amount = 75.00;
        String expiredCardToken = "tok_expired";
        when(paymentGateway.processPaymentWithToken(orderId, amount, expiredCardToken))
            .thenThrow(new PaymentFailedException("Card expired"));

        // When & Then
        assertThatThrownBy(() -> 
            paymentGateway.processPaymentWithToken(orderId, amount, expiredCardToken))
            .isInstanceOf(PaymentFailedException.class)
            .hasMessageContaining("Card expired");
    }
}

// === ARCHIVO: src/test/java/com/pragma/ordersystem/InventoryClientUnitTests.java ===
package com.pragma.ordersystem;


import com.pragma.ordersystem.infrastructure.inventory.InventoryClient;
import com.pragma.ordersystem.domain.exception.ProductNotAvailableException;
import com.pragma.ordersystem.domain.model.Product;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InventoryClientUnitTests {

    @Mock
    private com.pragma.ordersystem.infrastructure.inventory.InventoryClient inventoryClient;

    @Test
    void shouldCheckProductAvailability() {
        // Given
        String productId = "PROD-001";
        int requestedQuantity = 5;
        when(inventoryClient.checkAvailability(productId, requestedQuantity)).thenReturn(true);

        // When
        boolean result = inventoryClient.checkAvailability(productId, requestedQuantity);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenInsufficientStock() {
        // Given
        String productId = "PROD-002";
        int requestedQuantity = 100;
        when(inventoryClient.checkAvailability(productId, requestedQuantity)).thenReturn(false);

        // When
        boolean result = inventoryClient.checkAvailability(productId, requestedQuantity);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        // Given
        String nonExistentProductId = "PROD-999";
        when(inventoryClient.checkAvailability(nonExistentProductId, 1))
            .thenThrow(new ProductNotAvailableException("Product not found: " + nonExistentProductId));

        // When & Then
        assertThatThrownBy(() -> 
            inventoryClient.checkAvailability(nonExistentProductId, 1))
            .isInstanceOf(ProductNotAvailableException.class)
            .hasMessageContaining("Product not found");
    }

    @Test
    void shouldReduceStockAfterOrderConfirmation() {
        // Given
        String productId = "PROD-003";
        int quantity = 3;
        when(inventoryClient.reduceStock(productId, quantity)).thenReturn(true);

        // When
        boolean result = inventoryClient.reduceStock(productId, quantity);

        // Then
        assertThat(result).isTrue();
        verify(inventoryClient).reduceStock(productId, quantity);
    }

    @Test
    void shouldHandleStockReductionFailure() {
        // Given
        String productId = "PROD-004";
        int quantity = 10;
        when(inventoryClient.reduceStock(productId, quantity)).thenReturn(false);

        // When
        boolean result = inventoryClient.reduceStock(productId, quantity);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void shouldCheckAvailabilityForZeroQuantity() {
        // Given
        String productId = "PROD-005";
        int zeroQuantity = 0;
        when(inventoryClient.checkAvailability(productId, zeroQuantity)).thenReturn(true);

        // When
        boolean result = inventoryClient.checkAvailability(productId, zeroQuantity);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void shouldHandleConcurrentStockRequests() {
        // Given
        String productId = "PROD-006";
        int quantity = 2;
        when(inventoryClient.checkAvailability(productId, quantity))
            .thenReturn(true)
            .thenReturn(true);

        // When
        boolean firstRequest = inventoryClient.checkAvailability(productId, quantity);
        boolean secondRequest = inventoryClient.checkAvailability(productId, quantity);

        // Then
        assertThat(firstRequest).isTrue();
        assertThat(secondRequest).isTrue();
    }

    @Test
    void shouldReturnProductDetails() {
        // Given
        String productId = "PROD-007";
        Product expectedProduct = new Product(productId, "Test Product", 99.99, 50);
        when(inventoryClient.getProductDetails(productId)).thenReturn(expectedProduct);

        // When
        Product result = inventoryClient.getProductDetails(productId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(productId);
        assertThat(result.getName()).isEqualTo("Test Product");
    }
}
```
