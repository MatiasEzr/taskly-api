package com.matias.taskly.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Clase contenedora de propiedades de configuración relacionadas con las tareas.
 *
 * @ConfigurationProperties vincula propiedades externas, por ejemplo desde
 * application.properties, con campos Java tipados.
 *
 * El prefix "taskly.tasks" significa que Spring buscará propiedades que empiecen así:
 *
 * taskly.tasks.page-size=20
 */
@Validated
@ConfigurationProperties(prefix = "taskly.tasks")
public class TaskProperties {

    /**
     * Cantidad de tareas que se muestran por página.
     *
     * Valor por defecto: 20.
     *
     * Si no configuras taskly.tasks.page-size en application.properties,
     * Spring usará este valor.
     *
     * @Min evita valores inválidos como 0 o negativos.
     * @Max evita traer demasiados registros en una sola consulta.
     */
    @Min(value = 1, message = "pageSize debe ser como mínimo 1")
    @Max(value = 100, message = "pageSize debe ser como máximo 100")
    private int pageSize = 20;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}