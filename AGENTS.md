# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Aplicación de POO en un Sistema de Gestión de Pedidos**.

| | |
|---|---|
| Tema | implementación de programación orientada a objetos (POO) |
| Nivel | advanced-l2 |
| Chapter | Calidad de Software |
| Especialidad | Automatizador |
| Stack | Java / Serenity BDD 4.1.0 |
| Patron arquitectonico | Page Object Model con Screenplay para estructura de pruebas, y capas estándar (dominio, servicio, persistencia) para el sistema de gestión de pedidos |
| Tiempo estimado | 8 horas |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz con el runner y el plugin de reportes`
- `src/test/resources/features con los .feature en Gherkin`
- `src/test/java/.../runners con el runner`
- `src/test/java/.../pages o /tasks con Page Objects o Screenplay`
- `src/test/java/.../steps con los step definitions`
- `serenity.conf o config del entorno`

Trampas conocidas:

- Sin parent POM que gestione versiones, TODA dependencia lleva su `<version>` completa de tres segmentos.
- El groupId de Serenity es `net.serenity-bdd`, NO `org.serenity-bdd`. Con el groupId equivocado el artefacto no existe y el build muere resolviendo dependencias.
- Coordenadas exactas de lo mas usado: Selenium `org.seleniumhq.selenium:selenium-java`, Rest Assured `io.rest-assured:rest-assured`, Karate `com.intuit.karate:karate-junit5`, Cucumber `io.cucumber:cucumber-java`.
- JUnit 5 se declara con `junit-jupiter` (agregador) y necesita `maven-surefire-plugin` reciente para ejecutarse.
- Serenity y Cucumber tienen que ser de lineas compatibles entre si; mezclarlas rompe el runner.

Dependencias:

- net.serenity-bdd:serenity-core 4.1.0
- net.serenity-bdd:serenity-cucumber 4.1.0
- io.cucumber:cucumber-java 7.15.0
- io.cucumber:cucumber-junit-platform-engine 7.15.0
- org.junit.jupiter:junit-jupiter 5.10.0
- org.junit.platform:junit-platform-suite 1.10.0
- org.assertj:assertj-core 3.25.3
- org.mockito:mockito-core 5.11.0
- org.apache.maven.plugins:maven-surefire-plugin 3.2.5
- org.apache.maven.plugins:maven-failsafe-plugin 3.2.5

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean test-compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean test-compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Modelado de Clases**: Diagrama de clases y descripción de atributos y métodos.
- **Fase 2 — Implementación de Métodos**: Código implementado de los métodos y manejo de errores.
- **Fase 3 — Integración y Pruebas**: Sistema integrado y documentación de pruebas.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Superficie de practica (NO completes)

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs. No toques la logica que el reto pide completar.

- [ ] `RunCucumberTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- [ ] `src/test/resources/features/order_management.feature` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- [ ] `src/test/java/com/pragma/ordersystem/features/OrderManagementTest.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- [ ] `src/test/java/com/pragma/ordersystem/tasks/AddProductToOrder.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- [ ] `src/test/java/com/pragma/ordersystem/questions/OrderTotal.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- [ ] `src/test/java/com/pragma/ordersystem/interactions/ChangeOrderStatus.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- [ ] `src/test/java/com/pragma/ordersystem/features/steps/OrderManagementSteps.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- [ ] `src/test/java/com/pragma/ordersystem/OrderServiceUnitTests.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- [ ] `src/test/java/com/pragma/ordersystem/PaymentGatewayUnitTests.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.
- [ ] `src/test/java/com/pragma/ordersystem/InventoryClientUnitTests.java` — El topic pide TDD/pruebas: este archivo es el ejercicio.

## Lo que falta y tenes que completar

### 1. Referencias colgando (13)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/pragma/ordersystem/infrastructure/inventory/InventoryClient.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/pragma/ordersystem/questions/OrderTotal.java` — `org.openqa.selenium`
      El import org.openqa.selenium.WebElement pertenece a org.openqa.selenium, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/ordersystem/domain/service/OrderService.java` — `InventoryClient.checkAvailability`
      Se invoca `checkAvailability` sobre `InventoryClient`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/ordersystem/domain/service/OrderService.java` — `InventoryClient.getAvailableQuantity`
      Se invoca `getAvailableQuantity` sobre `InventoryClient`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/ordersystem/domain/service/OrderService.java` — `InMemoryOrderRepository.update`
      Se invoca `update` sobre `InMemoryOrderRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/ordersystem/domain/service/OrderService.java` — `InventoryClient.reserveStock`
      Se invoca `reserveStock` sobre `InventoryClient`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/ordersystem/domain/service/OrderService.java` — `PaymentGateway.refundPayment`
      Se invoca `refundPayment` sobre `PaymentGateway`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/ordersystem/infrastructure/payment/PaymentGateway.java` — `PaymentFailedException.getMessage`
      Se invoca `getMessage` sobre `PaymentFailedException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/ordersystem/OrderServiceUnitTests.java` — `InventoryClient.checkAvailability`
      Se invoca `checkAvailability` sobre `InventoryClient`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/ordersystem/PaymentGatewayUnitTests.java` — `PaymentGateway.processPaymentWithToken`
      Se invoca `processPaymentWithToken` sobre `PaymentGateway`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/ordersystem/InventoryClientUnitTests.java` — `InventoryClient.checkAvailability`
      Se invoca `checkAvailability` sobre `InventoryClient`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/ordersystem/InventoryClientUnitTests.java` — `InventoryClient.reduceStock`
      Se invoca `reduceStock` sobre `InventoryClient`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/ordersystem/InventoryClientUnitTests.java` — `InventoryClient.getProductDetails`
      Se invoca `getProductDetails` sobre `InventoryClient`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (22)

- `pom.xml`
- `src/main/java/com/pragma/ordersystem/domain/model/Order.java`
- `src/main/java/com/pragma/ordersystem/domain/model/Product.java`
- `src/main/java/com/pragma/ordersystem/domain/model/Customer.java`
- `src/main/java/com/pragma/ordersystem/domain/exception/OrderException.java`
- `src/main/java/com/pragma/ordersystem/domain/exception/ProductNotAvailableException.java`
- `src/main/java/com/pragma/ordersystem/domain/service/OrderService.java`
- `src/main/java/com/pragma/ordersystem/domain/exception/PaymentFailedException.java`
- `src/main/java/com/pragma/ordersystem/infrastructure/persistence/InMemoryOrderRepository.java`
- `src/main/java/com/pragma/ordersystem/infrastructure/payment/PaymentGateway.java`
- `src/main/java/com/pragma/ordersystem/infrastructure/inventory/InventoryClient.java`
- `RunCucumberTest.java`
- `README.md`
- `src/test/resources/features/order_management.feature`
- `src/test/java/com/pragma/ordersystem/features/OrderManagementTest.java`
- `src/test/java/com/pragma/ordersystem/tasks/AddProductToOrder.java`
- `src/test/java/com/pragma/ordersystem/questions/OrderTotal.java`
- `src/test/java/com/pragma/ordersystem/interactions/ChangeOrderStatus.java`
- `src/test/java/com/pragma/ordersystem/features/steps/OrderManagementSteps.java`
- `src/test/java/com/pragma/ordersystem/OrderServiceUnitTests.java`
- `src/test/java/com/pragma/ordersystem/PaymentGatewayUnitTests.java`
- `src/test/java/com/pragma/ordersystem/InventoryClientUnitTests.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/pragma/ordersystem/domain/model`
- `src/main/java/com/pragma/ordersystem/domain/service`
- `src/main/java/com/pragma/ordersystem/domain/exception`
- `src/main/java/com/pragma/ordersystem/infrastructure/persistence`
- `src/main/java/com/pragma/ordersystem/infrastructure/payment`
- `src/main/java/com/pragma/ordersystem/infrastructure/inventory`
- `src/test/java/com/pragma/ordersystem/features`
- `src/test/java/com/pragma/ordersystem/tasks`
- `src/test/java/com/pragma/ordersystem/questions`
- `src/test/java/com/pragma/ordersystem/interactions`
- `src/test/resources/features`

## Verificacion

```bash
mvn clean test-compile
```

El comando tiene que pasar SIN implementar los archivos de la superficie de practica: solo andamiaje.

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **Page Object Model con Screenplay para estructura de pruebas, y capas estándar (dominio, servicio, persistencia) para el sistema de gestión de pedidos**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Calidad de Software, Especialidad Automatizador, Tecnología Testing, Advanced
- Brecha que el reto ataca: Aplica los conceptos fundamentales del paradigma de Programación Orientada a Objetos, tales como Clase, Herencia, Objeto, Método, Evento, Atributo y Mensaje, en un lenguaje de programación específico como Java, Python, JavaScript o TypeScript en la resolucion de problemas cotidianos
- Mision: Candidato con experiencia en automatización y calidad de software

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
