package com.revature.EmployeeTicketApplication.DAO;

public interface DAOChildTable<V,K,FK> extends DAO<V, K> {

    /**
     * Get all records associated with foreign key.
     * @param foreignKey used to identify records being returned.
     * @return array of records owned by foreign key.
     * */
    V[] getAll(FK foreignKey);

}
