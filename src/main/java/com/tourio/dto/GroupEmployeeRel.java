package com.tourio.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "group_employee_rel")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class GroupEmployeeRel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "group_id")
    private long groupId;

    @Column(name = "employee_id")
    private long employeeId;

    @Column(name = "job_id")
    private long jobId;
}
