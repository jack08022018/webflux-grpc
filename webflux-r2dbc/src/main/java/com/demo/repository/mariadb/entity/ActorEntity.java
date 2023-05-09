package com.demo.repository.mariadb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table("actor")
public class ActorEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column("actor_id")
    private Long actorId;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @JsonIgnore
    @Column("last_update")
    private LocalDateTime lastUpdate;

//    @Version
//    @JsonIgnore
//    @Column("version")
//    private Integer version;

}
