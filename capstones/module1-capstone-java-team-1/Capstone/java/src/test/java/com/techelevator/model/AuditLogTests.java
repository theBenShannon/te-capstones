package com.techelevator.model;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class AuditLogTests {

    @Test
    public void file_audit_txt_exists() {
        File auditLog = new File("audit/audit.txt");
        Assert.assertTrue(auditLog.exists() && !auditLog.isDirectory());
    }

}
