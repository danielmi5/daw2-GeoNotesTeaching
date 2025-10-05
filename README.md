# GeoNotesTeaching (Java 21)

Proyecto docente con Java clásico + moderno (records, sealed, text blocks, switch expression, pattern matching).
Incluye Gradle Wrapper (scripts) para facilitar la ejecución.

## Ejecutar
- IntelliJ: Abrir carpeta y ejecutar tarea Gradle `run` o `examples`.
- Terminal:
  ```bash
  ./gradlew run
  ./gradlew examples
  ```

## Ejercicios completados

- Bloque A ✅
- Bloque B ✅
- Bloque C ✅
- Bloque D ✅
- Bloque E ✅
- Bloque F ✅
- Bloque G ✅

Todos los ejercicios de cada bloque hechos.

## Notas sobre decisiones de diseño

### Clase Describe - pattern matching
En la clase Describe, el método describeAttachment(Attachment a) usa la expresión switch con pattern matching para procesar de forma concisa y segura distintos tipos de contenido multimedia (Photo, Audio, Link) sin casts ni condicionales anidados; cada caso devuelve directamente el resultado mediante yield, lo que mejora la legibilidad y facilita la extensión.
```java
final class Describe {
    public static String describeAttachment(Attachment a) {
        return switch (a) {
            case Photo p when p.width() > 1920 -> {
                int width = p.width();
                int height = p.height();
                yield "📷 Foto en alta definición (%d x %d)".formatted(width, height);
            }
            case Photo p -> "📷 Foto";
            case Audio audio when audio.duration() > 300 -> {
                var mins = audio.duration() / 60;
                yield "🎵 Audio (" + mins + " min)";
            }
            case Audio audio -> "🎵 Audio";
            case Link l -> {
                String label = (l.label() == null || l.label().isEmpty()) ? l.url() : l.label();
                yield "🔗 %s".formatted(label);
            }
        };
    }
}
```

### Clase RenderMarkdown, MarkdownExporter

La clase RenderMarkdown, se encuentra internamente en Timeline y genera una salida en formato Markdown usando bloques de texto para mostrar una lista de notas, donde cada elemento incluye el ID, título, coordenadas y la fecha de creación. Cree otra clase para exportar el markdown, para separar la lógica, ya que la otra manejaba JSON. Esta separación permite modularizar el código, facilitando la mantenibilidad y la reutilización. Además, usar bloques de texto en Java para construir el Markdown hace que la generación del formato sea más legible y clara visualmente. Además, como JSONExporter, cree su respectiva clase MarkdownExporter.

```java
public final class RenderMarkdown extends AbstractExporter implements Exporter {
        @Override
        public String export() {
            var notesList = notes.values().stream()
                    .map(note -> """
                    - [ ID %d] %s — (%f, %f) — %s
                    """.formatted(note.id(), note.title(), note.location().lat(), note.location().lon(), note.createdAt().toString().substring(0,10)))
                    .collect(Collectors.joining(""));
            return """
                # GeoNotes
                %s
                """.formatted(notesList);
        }
    }
```

### Clase VirtualDemo

La clase VirtualDemo es una demo de ejcución de múltiples tareas concurrentes utilizando hilos virtuales. Define una constante para establecer la cantidad de tareas a realizar (50), un método estático runIO() encargado de gestionar y ejecutar dichas tareas, y un método main para poder probarla. El método runIO() utiliza el ejecutador de hilos virtuales (Executors.newVirtualThreadPerTaskExecutor()) para lanzar 50 tareas que simulan operaciones de entrada/salida mediante pausas aleatorias, aprovechando la ligereza y escalabilidad de los hilos virtuales en Java para manejar muchas tareas sin saturar los recursos del sistema; cada tarea se ejecuta de forma aislada, capturando interrupciones correctamente, y el executor se cierra al final para liberar recursos.

```java
public class VirtualDemo {
    private static final int tasks = 50;

    public static void runIO() {
        var exec = Executors.newVirtualThreadPerTaskExecutor();
        try {

            for (int i = 0; i < tasks; i++) {
                int numTask = i+1;
                exec.submit(() -> {
                    try {

                        int sleep = ThreadLocalRandom.current().nextInt(200, 301);
                        Thread.sleep(sleep);

                        System.out.println("Hilo actual de la tarea " + numTask + " --> " +  Thread.currentThread() +
                                " (sleep: " + sleep + " ms)");
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            exec.close();
        }
    }


    public static void main(String[] args) {
        runIO();
    }
}
```

## Java vs Kotlin
- Java es muy verboso, requiere más líneas de código y declaraciones explícitas, mientras que Kotlin es mucho más conciso y legible, eliminando la redundancia.

- En cuanto a null safety, Kotlin previene errores por nulos haciendo que las variables sean no nulas por defecto y permitiendo nulos solo explícitamente, mientras que Java permite nulls sin restricciones, lo que puede generar NullPointerExceptions.

- Kotlin usa data class que ofrecen una sintaxis sencilla y moderna. Sin embargo, Java ha introducido records, que son clases inmutables con métodos automáticos, pero la sintaxis todavía es más extensa.

- El bloque switch en Java compara un valor con varios casos constantes y requiere usar break para evitar la ejecución en cascada; su uso es limitado a tipos primitivos, enums y strings, con menor expresividad para condiciones complejas. En cambio, en Kotlin, when reemplaza y mejora al switch, permitiendo evaluar múltiples condiciones, rangos, tipos y expresiones booleanas en un código más legible y sin necesidad de break. Además, when realiza castings automáticos y permite múltiples valores por cada caso.

- El ciclo for en Java se usa para iterar sobre rangos o colecciones con sintaxis clásica o enhanced for, mientras que Kotlin ofrece una sintaxis más concisa y natural para iterar colecciones, rangos y arrays con for que facilita la lectura y escritura del código
