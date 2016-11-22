start transaction;

create table projects (
    project_id int(11) not null auto_increment,
    name varchar(256) not null,
    constraint UQ_name unique key(name),
    primary key(project_id)
) ENGINE=MYISAM;

create table jobs (
    job_id int(11) not null auto_increment,
    project_id int(11) not null,
    name varchar(256) not null,
    constraint UQ_project_job_name unique key(project_id, name),
    constraint FK_project foreign key (project_id) references projects(project_id)
        on update cascade
        on delete cascade,
    primary key(job_id)
) ENGINE=MYISAM;

create table builds (
    build_id int(11) not null auto_increment,
    name varchar(256) not null,
    job_id int(11) not null,
    status enum('passed', 'failed') not null,
    aggr_cnt_tests_failed int(11) not null DEFAULT 0,
    aggr_cnt_tests_passed int(11) not null DEFAULT 0,
    aggr_cnt_tests_skipped int(11) not null DEFAULT 0,
    created_date datetime not null,
    constraint UQ_job_build_name unique key(job_id, name),
    constraint FK_job foreign key (job_id) references projects(job_id)
        on update cascade
        on delete cascade,
    primary key(build_id)
) ENGINE=MYISAM;

create table test_reports (
    test_report_id int(11) not null auto_increment,
    build_id int(11) not null,
    name varchar(2048) not null,
    error text null,
    reason varchar(64) null,
    created_date datetime not null,
    started_date datetime null,
    ended_date datetime null,
    reported_by varchar(128) null,
    status enum('passed', 'failed', 'skipped') not null,
    report_type varchar(32) null,
    report text null,
    aggregated_status_history text null,
    constraint FK_build foreign key (build_id) references builds(build_id)
        on update cascade
        on delete cascade,
    primary key(test_report_id)
) ENGINE=MYISAM;

create table test_attachments (
    test_attachment_id int(11) not null auto_increment,
    test_report_id int(11) not null,
    name varchar(128) not null,
    url varchar (2048) not null,
    created_date datetime not null,
    constraint FK_test_report foreign key (test_report_id) references test_reports(test_report_id)
        on update cascade
        on delete cascade,
    primary key(test_attachment_id)
) ENGINE=MYISAM;

commit;
