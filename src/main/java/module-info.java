module com.quannm.hsf_licenseshop_app {
    // Key test
    // TEST-APP-2025-HSF-001

    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    // Dependencies cho Hibernate and JPA
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.sql;

    // MySQL Driver
    requires mysql.connector.j;

    // Dependency cho Lombok
    requires static lombok;

    // Spring dependencies
    requires spring.security.crypto;
    requires spring.security.core;
    requires spring.data.jpa;
    requires spring.data.commons;
    requires spring.context; // Cần cho Auditing
    requires spring.beans; // Cần cho Auditing
    requires org.aspectj.weaver; // Cần cho Auditing
    requires spring.aop; // Cần cho Auditing

    // Dependency cho commons-logging (cần cho spring-security-crypto)
    requires org.apache.commons.logging;

    opens com.quannm.hsf_licenseshop_app to javafx.fxml;
    exports com.quannm.hsf_licenseshop_app;


    // Mở các package để JavaFX và Hibernate có thể truy cập
    // Mở package controller chính
    opens com.quannm.hsf_licenseshop_app.controller to javafx.fxml; // Mở package controller cho FXML
    exports com.quannm.hsf_licenseshop_app.controller;


    // Mở package entity cho Hibernate để nó có thể đọc metadata
    opens com.quannm.hsf_licenseshop_app.entity to org.hibernate.orm.core;
    exports com.quannm.hsf_licenseshop_app.entity;

    // Mở package model cho JavaFX để TableView có thể hoạt động
    opens com.quannm.hsf_licenseshop_app.model to javafx.base;
    exports com.quannm.hsf_licenseshop_app.model;


    // Exports các package khác để có thể sử dụng trong toàn bộ ứng dụng
    exports com.quannm.hsf_licenseshop_app.service;
    exports com.quannm.hsf_licenseshop_app.dao;
    exports com.quannm.hsf_licenseshop_app.utils;
    exports com.quannm.hsf_licenseshop_app.service.impl;
}
