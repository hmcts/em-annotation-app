package uk.gov.hmcts.reform.em.annotation.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @Setter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "annotationSet")
    private Set<Annotation> annotations;

    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        String s = "";

        try {
            s = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return s;
    }
}
