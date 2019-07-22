package com.winback.core.dto._case;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CaseStepDto {

    private String branchFlag;

    private String name;

    private String nameDesc;

    private String nextId;

    private String groupId;
}
