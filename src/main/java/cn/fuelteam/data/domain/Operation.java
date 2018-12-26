package cn.fuelteam.data.domain;

import java.io.Serializable;

public class Operation implements Serializable {

    private static final long serialVersionUID = 2230749178717946440L;

    private Long id;

    private String contents;

    public Long getId() {
        return id;
    }

    public Operation setId(Long id) {
        this.id = id;
        return this;
    }

    public String getContents() {
        return contents;
    }

    public Operation setContents(String contents) {
        this.contents = contents == null ? null : contents.trim();
        return this;
    }
}
