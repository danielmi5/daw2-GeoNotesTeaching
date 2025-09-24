package com.example.geonotesteaching;

import static java.util.Objects.hash;

public class LegacyPoint {
    private double lat;
    private double lon;

    public LegacyPoint(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof LegacyPoint)) return false;
        LegacyPoint object = (LegacyPoint) obj;
        return this.lat == object.lat && this.lon == object.lon;
    }

    @Override
    public int hashCode() {
        return hash(lat, lon);
    }

    @Override
    public String toString() {
        return "LegacyPoint(latitud: " + lat + ", longitud: " + lon + ")";
    }
}

/*
Ventajas de usar `record`:
- Reducción de código repetitivo: genera automáticamente constructor, getters, equals, hashCode y toString.
- Inmutabilidad: los campos son final y no pueden modificarse después de crear el objeto.
- Mayor legibilidad y facilidad de mantenimiento.
- Optimización en el rendimiento.

Desventajas de usar `record`:
- No permite modificar el estado del objeto (inmutabilidad obligatoria).
- Limita la posibilidad de declarar atributos adicionales fuera de la declaración.
- No admite herencia de otras clases (solo puede implementar interfaces).
- No es adecuado para lógica compleja.

Conclusión:
- Usar `record` como en GeoPoint es ideal para representar datos simples e inmutables.
- Usar clases clásicas como `LegacyPoint` sigue siendo útil en casos donde se necesita más control, mutabilidad o herencia.
*/
