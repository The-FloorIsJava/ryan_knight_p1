package com.revature.EmployeeTicketApplication.DAO;

import com.revature.EmployeeTicketApplication.Models.PasswordProtectedProfile;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 * Interface defines behavior of DAO classes.
 * */
public interface DAO<V,K> {

    /**
     * Add profile to database.
     * @param record being added.
     * @throws SQLIntegrityConstraintViolationException for duplicate key
     * @return successfully written or null.
     * */
    V save(V record) throws SQLIntegrityConstraintViolationException;


    /**
     * Remove record.
     * @param record to be deleted.
     * */
    void delete(V record);

    /**
     * Get record
     * @param primaryKey for record being fetched
     * */
    V get(K primaryKey);

    /**
     * Update record in database.
     * @param record to be updated.
     * @return updated record.
     * */
    V update(V record);

    /**
     * Get all records
     * @return list of all records
     */
    List<V> getAll();
}
