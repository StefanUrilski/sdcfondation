package bg.sdcf.domain.entities;

public enum Section {
    INDEX("Начало"),
    PURPOSE("Цели и дейности"),
    PUBLICATIONS("Публикации"),
    ADDICTIONS("Зависимости"),
    FAMILY_PROGRAMS("Семейни програми"),
    EVENTS("Събития");


    private final String value;

    Section(String qwez) {
        this.value = qwez;
    }

    public String getValue() {
        return value;
    }
}
