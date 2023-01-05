package com.test.Superkassa_test.entity;


import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sk_example_table")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class SKEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "jsonb")
    @Column(name = "obj", columnDefinition = "jsonb")
    private SKObj skObj;

    @Version
    private Long version;

    public SKEntity() {
    }

    public SKEntity(SKObj skObj) {
        this.skObj = skObj;
    }
}
