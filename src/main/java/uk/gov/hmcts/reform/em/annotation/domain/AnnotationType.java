package uk.gov.hmcts.reform.em.annotation.domain;

public enum AnnotationType {
    DRAWING("drawing"),
    HIGHLIGHT("highlight"),
    POINT("point"),
    STRIKEOUT("strikeout"),
    TEXTBOX("textbox"),
    PAGE_NOTE("pagenote");

    private String type;

    AnnotationType(String type) {
        this.type = type;
    }

    public String toString() {
        return type;
    }
}
