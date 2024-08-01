package com.example.valven.domain;

import com.example.valven.util.base.BaseDomain;
import lombok.Data;

@Data
public class Developer extends BaseDomain {

    private String username;
    private String email;
}
