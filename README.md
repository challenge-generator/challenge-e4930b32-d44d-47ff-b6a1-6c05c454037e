# Aplicación de POO en un Sistema de Gestión de Pedidos

En el contexto de un sistema de gestión de pedidos para una tienda en línea, aplica los conceptos de POO para modelar y resolver problemas cotidianos. Los actores involucrados son el 'cliente', el'sistema de gestión de inventario' y el'motor de pago'. El sistema debe manejar pedidos con atributos como 'id', 'fecha', 'lista de productos' y 'estado'. Los métodos clave incluyen 'agregar producto al pedido', 'calcular total' y 'cambiar estado del pedido'. El sistema debe ser idempotente al agregar productos y tolerante a fallos en la conexión con el motor de pago.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | implementación de programación orientada a objetos (POO) |
| **Nivel** | advanced-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 8 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Modelado de Clases

**Objetivo:** Definir las clases y atributos necesarios para representar los conceptos del dominio.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Identificar y definir las clases necesarias para el sistema de gestión de pedidos.
- Especificar los atributos y métodos para cada clase.
- Asegurar que las clases modelen adecuadamente las relaciones entre los diferentes componentes del sistema.

**Entregable:** Diagrama de clases y descripción de atributos y métodos.

<details>
<summary>Pistas de conocimiento</summary>

- Considera la herencia y la composición para estructurar las clases.
- Piensa en cómo las clases interactúan entre sí.

</details>

### Fase 2: Implementación de Métodos

**Objetivo:** Implementar los métodos definidos en la fase anterior para manejar las operaciones del sistema.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- Implementar los métodos para agregar productos al pedido, calcular el total y cambiar el estado del pedido.
- Asegurar que los métodos sean idempotentes y tolerantes a fallos.
- Manejar posibles errores y excepciones que puedan surgir durante la ejecución de los métodos.

**Entregable:** Código implementado de los métodos y manejo de errores.

<details>
<summary>Pistas de conocimiento</summary>

- Considera el uso de excepciones para manejar errores.
- Piensa en cómo garantizar la idempotencia y tolerancia a fallos en los métodos.

</details>

### Fase 3: Integración y Pruebas

**Objetivo:** Integrar las clases y métodos implementados y realizar pruebas para verificar su funcionamiento.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- Integrar las clases y métodos implementados en un sistema funcional.
- Realizar pruebas unitarias y de integración para verificar el funcionamiento correcto de las operaciones del sistema.
- Documentar los resultados de las pruebas y cualquier problema encontrado.

**Entregable:** Sistema integrado y documentación de pruebas.

<details>
<summary>Pistas de conocimiento</summary>

- Utiliza frameworks de testing para realizar las pruebas.
- Documenta detalladamente los resultados y cualquier problema encontrado.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué son las clases y objetos en el contexto de POO?
- **paraQueSirve**: ¿Para qué sirven los métodos y atributos en las clases?
- **comoSeUsa**: ¿Cómo se implementan y utilizan los métodos en el sistema?
- **erroresComunes**: ¿Cuáles son los errores comunes al implementar POO y cómo se manejan?
- **queDecisionesImplica**: ¿Qué decisiones arquitectónicas implica la implementación de POO en este sistema?

## Criterios de Evaluacion

- Definición clara y correcta de clases y atributos.
- Implementación adecuada de métodos con idempotencia y tolerancia a fallos.
- Manejo correcto de errores y excepciones.
- Integración exitosa de clases y métodos en un sistema funcional.
- Realización y documentación de pruebas unitarias y de integración.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean test-compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
