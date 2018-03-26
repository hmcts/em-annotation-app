package uk.gov.hmcts.reform.em.annotation.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AnnotationType {
    DRAWING("drawing"),
    HIGHLIGHT("highlight"),
    POINT("point"),
    STRIKEOUT("strikeout"),
    TEXTBOX("textbox"),
    PAGENOTE("pagenote");


    private String type;

    @JsonValue
    public String getType() {
        return type;
    }

    AnnotationType(String type) {
        this.type = type;
    }

    public String toString() {
        return type;
    }
}
