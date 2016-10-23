create table projects (
    project_id int(11) not null auto_increment,
    name varchar(256) not null,
    unique key(name),
    primary key(project_id)
) ENGINE=MYISAM;

create table jobs (
    job_id int(11) not null auto_increment,
    project_id int(11) not null,
    name varchar(256) not null,
    unique key(project_id, name),
    primary key(job_id)
) ENGINE=MYISAM;

create table builds (
    build_id int(11) not null auto_increment,
    name varchar(256) not null,
    project_id int(11) not null,
    created_date datetime not null,
    unique key(page_id, name),
    primary key(build_id)
) ENGINE=MYISAM;

create table test_reports (
    test_report_id int(11) not null auto_increment,
    name varchar(2048) not null,
    error text null,
    reason varchar(64) not null,
    created_date datetime not null,
    started_date datetime not null,
    ended_date datetime not null,
    reported_by varchar(128) null,
    status enum('passed', 'failed', 'warning', 'skipped') not null,
    report text null,
    hist_statuses varchar(2048) not null,
    hist_ids varchar(2048) not null
    hist_reasons varchar (2048) not null,
    primary key(test_report_id)
) ENGINE=MYISAM;

create table files (
    file_id int(11) not null auto_increment,
    name varchar(128) not null,
    image_path varchar(2048) not null,
    created_date datetime not null,
    hash varchar(128) not null,
    media_type varchar(32) not null,
    primary key(file_id)
) ENGINE=MYISAM;

create table test_attachments (
    test_attachment_id int(11) not null auto_increment,
    test_report_id int(11) not null,
    file_id int(11) not null,
    created_date datetime not null,
    primary key(test_attachment_id)
) ENGINE=MYISAM;
