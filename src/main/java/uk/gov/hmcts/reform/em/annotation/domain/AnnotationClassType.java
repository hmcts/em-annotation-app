package uk.gov.hmcts.reform.em.annotation.domain;

public enum AnnotationClassType {
    ANNOTATION("Annotation"),
    COMMENT("Comment");

    private String classType;

    AnnotationClassType(String type) {
        this.classType = type;
    }

    public String toString() {
        return classType;
    }
}
