package uk.gov.hmcts.reform.em.annotation.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class AnnotationSet {

    public AnnotationSet() {
    }

    public AnnotationSet(UUID uuid,
                         String createdBy,
                         String lastModifiedBy,
                         Date modifiedOn,
                         Date createdOn,
                         String documentUri,
                         Set<Annotation> annotations) {
        this.uuid = uuid;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
        this.modifiedOn = modifiedOn;
        this.createdOn = createdOn;
        this.documentUri = documentUri;
        setAnnotations(annotations);
    }

    @Getter
    @Setter
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID uuid;

    @Getter
    @Setter
    @CreatedBy
    private String createdBy;

    @Getter
    @Setter
    @LastModifiedBy
    private String lastModifiedBy;

    @Getter
    @Setter
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedOn;

    @Getter
    @Setter
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Getter
    @Setter
    @NotNull
    private String documentUri;

    @Getter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "annotationSet")
    private Set<Annotation> annotations;

    public final void setAnnotations(Set<Annotation> annotations) {
        this.annotations = annotations;
        if (this.annotations != null) {
            this.annotations.forEach(annotation -> annotation.setAnnotationSet(this));
        }
    }

}

