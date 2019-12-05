package com.github.canglan.cm.gate.gateway.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class UserDetails implements Serializable {

    private String tenant_id;
    private String license;
    private String user_id;
    private String user_name;
    private List<String> scope;
    private boolean active;
    private long exp;
    private String dept_id;
    private List<String> authorities;
    private String client_id;
    private String username;
    private String code;
    private String message;
    private boolean success;
}
