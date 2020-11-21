package eu.getmangos.dto;

public enum EncounterState {
    NOT_STARTED(0),
    IN_PROGRESS(1),
    FAIL(2),
    DONE(3),
    SPECIAL(4);

    public final int code;

    private EncounterState(int code) {
        this.code = code;
    }

    public static EncounterState convert(int code) {
        for(EncounterState e : values()) {
            if(e.code == code) {
                return e;
            }
        }
        return null;
    }
}
