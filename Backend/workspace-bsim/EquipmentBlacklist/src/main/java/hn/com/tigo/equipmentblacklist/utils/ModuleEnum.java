package hn.com.tigo.equipmentblacklist.utils;

public enum ModuleEnum {
	
    CREATE("Agrega Elemento"),
    LOAD("Consulta Elemento"),
    MODULE("Mantenimiento de %s"),
    UPDATE("Actualiza");


    private String description;

    ModuleEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }


}
