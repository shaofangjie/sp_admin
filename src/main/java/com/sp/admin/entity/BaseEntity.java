package com.sp.admin.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by shaofangjie on 5/8/18.
 */

public class BaseEntity implements Serializable {
    private Long id;

    private Long version;

    private Timestamp whenCreated;

    private Timestamp whenUpdated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Timestamp getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Timestamp whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Timestamp getWhenUpdated() {
        return whenUpdated;
    }

    public void setWhenUpdated(Timestamp whenUpdated) {
        this.whenUpdated = whenUpdated;
    }
}
