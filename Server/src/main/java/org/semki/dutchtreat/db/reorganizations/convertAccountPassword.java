package org.semki.dutchtreat.db.reorganizations;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import liquibase.change.custom.CustomTaskChange;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.DatabaseException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;

import org.semki.dutchtreat.DAO.AccountDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class convertAccountPassword implements CustomTaskChange{
	
	@Autowired
	private ReflectionSaltSource saltSource;
	
	@Autowired
	private Md5PasswordEncoder passEncoder;

	@Override
	public String getConfirmationMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUp() throws SetupException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFileOpener(ResourceAccessor resourceAccessor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ValidationErrors validate(Database database) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(Database database) throws CustomChangeException {
		final JdbcConnection conn = (JdbcConnection) database.getConnection();
        try {
            conn.setAutoCommit(false);
            final String insertTableSQL = "UPDATE CAR SET name = ? WHERE id = ?";
            final PreparedStatement stmt = conn.prepareStatement(insertTableSQL);
            
            stmt.executeUpdate();
            conn.commit();
        } catch (DatabaseException|SQLException e) {
            // ...
        } finally
        {
        	try {
				conn.close();
			} catch (DatabaseException e) {
				e.printStackTrace();
			}
        }
	}

}
